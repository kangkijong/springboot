package com.springboot.shoppy_fullstack_app.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.csrf.*;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new SpaCsrfTokenRequestHandler())
                )
                .cors((cors) -> cors
                        .configurationSource(corsConfigurationSource())
                )
                .authenticationProvider(authenticationProvider())
                .securityContext(sc -> sc.requireExplicitSave(true)) // ← 선택. true면 아래 로그인 컨트롤러에서 save 필요
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )
                .httpBasic(basic -> basic.disable())
                .formLogin(form -> form.disable())
                .requestCache(rc -> rc.disable()) //로그인 후 리다이렉트 방지
//                .securityContext(sc -> sc.requireExplicitSave(true)) //인증정보 세션 자동저장 방지
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/csrf/**", "/member/**", "/product/**")
                        .permitAll()
                        .requestMatchers("/cart/**", "/support/**", "/payment/**")
                        .hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")
                        .anyRequest().authenticated()
                );

        return http.build();

    }//SecurityFilterChain Bean


    /**
     * 사용자의 인증 상태를 Http 세션에 저장하고 로드하는 역할을 담당하는 핵심 컴포넌트
     */
    @Bean
    public HttpSessionSecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }


    /**
     * 로그인 사용자 정보를 저장한 UserDetailService객체를 Dao객체(DB연동객체)의 파라미터로
     * 전송하고 AuthenticationProvider를 통해 로그인 실행
     */
    /** ✅ DaoAuthenticationProvider 하나만 등록 */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /** AuthenticationManager: DaoAuthenticationProvider + BCrypt */
    // ✅ 권장: AuthenticationManager는 AuthenticationConfiguration에서 가져오기
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


    //CORS 보안정책 수행 객체
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
        configuration.setAllowCredentials(true);  // 🔥 프론트에서 JSESSIONID/CSRF 쿠키 받으려면 필수
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    // 회원가입 시 호출 --> 비밀번호 암호화 설정 (PasswordEncoder)
    // Spring Security는 반드시 비밀번호를 암호화하여 저장하고 비교해야 함!!
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}//SecurityConfig class


/**
 * ✨✨중요::
 * SPA(Single Page Application) : React, VUE 로 개발되는 사이트에서 CSRF 토큰 요청시
 * 필터링에서 호출하여 실행되는 CSRF 핸들러 객체
 */
final class SpaCsrfTokenRequestHandler implements CsrfTokenRequestHandler {
    private final CsrfTokenRequestHandler plain = new CsrfTokenRequestAttributeHandler();
    private final CsrfTokenRequestHandler xor = new XorCsrfTokenRequestAttributeHandler();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Supplier<CsrfToken> csrfToken) {
        this.xor.handle(request, response, csrfToken);
        csrfToken.get();
    }

    @Override
    public String resolveCsrfTokenValue(HttpServletRequest request, CsrfToken csrfToken) {
        String headerValue = request.getHeader(csrfToken.getHeaderName());
        return (StringUtils.hasText(headerValue) ? this.plain : this.xor).resolveCsrfTokenValue(request, csrfToken);
    }
}