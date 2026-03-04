/**
 * 用户类别
 */
export type UserType = {
    id: number;
    username: string;
    userAccount: string;
    avatarUrl: string;
    gender: number;
    profile: string;
    phone: string;
    email: string;
    UserStatus: number;
    UserRole: number;
    planetCode: string;
    tags: String[];
    createTime: Date;
}