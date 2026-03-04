import Index from '../pages/Index.vue';
// @ts-ignore
import Team from '../pages/Team.vue';
import User from '../pages/User.vue';
import Search from "../pages/Search.vue";
import SearchResult from "../pages/SearchResult.vue";
import Edit from "../pages/editUser.vue";
import userLogin from "../pages/userLogin.vue";
import userRegister from "../pages/UserRegister.vue";
import TeamAdd from "../pages/addTeam.vue";

const routes = [
    { path: '/',  component: Index , meta:{tittle : "伙伴匹配"} },
    { path: '/team', component: Team , meta:{tittle : "找队伍"} },
    { path: '/team/add', component: TeamAdd , meta:{tittle : "创建队伍"} },
    { path: '/user', component: User , meta:{tittle : "个人中心"}},
    { path: '/search', component: Search, meta:{tittle : "搜索伙伴"}},
    { path: '/search/list', component: SearchResult, meta:{tittle : "查找结果"}},
    { path: '/user/edit', component: Edit, meta:{tittle : "编辑信息"}},
    { path: '/user/login',name: "login", component: userLogin, meta:{tittle : "登录"}},
    { path: '/user/register',name: "register", component: userRegister, meta:{tittle : "注册"}}

]

export default routes