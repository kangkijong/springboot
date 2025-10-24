import { axiosPost } from '../../utils/dataFetch.js'

export const getList = async(type) => {
    const url = "/support/list";
    const data = {"type": type};
    const jsonData = await axiosPost(url, data);
    console.log("getList :: jsonData => ", jsonData);
    return jsonData;
}