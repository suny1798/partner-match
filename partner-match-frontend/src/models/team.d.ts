import type {UserType} from "./user";

/**
 * 队伍信息
 */
export type TeamType = {
    id: number;
    name: string;
    description: string;
    expireTime: Date;
    maxNum: number;
    status: number;
    password?: string;
    createTime: Date;
    updateTime: Date;
    createUser?: UserType;
}