// @ts-ignore
import {myAxios} from "../plugins/my_axios";

export const getCurrentUser = async () => {
    return await myAxios.get('/user/current')
}