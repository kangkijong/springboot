import React from 'react';
import { FaRegCircleQuestion } from 'react-icons/fa6';
import { TbThumbUp } from 'react-icons/tb';

export function LikeItem({style, icons, value}) {
    return (
        <div>
            <button type='button'
                    className={style}>
                <span>
                    {icons === "tb" && <TbThumbUp />}
                </span>
                <span>
                    {icons === "fa6" && <FaRegCircleQuestion />}
                </span>
                <span>{value}</span>
            </button>
        </div>
    );
}

