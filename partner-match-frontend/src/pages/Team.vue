<script setup lang="ts">
import {getCurrentUser} from "../service/user.ts";
import {showFailToast} from "vant";
import {onMounted, ref} from "vue";
import {useRouter} from "vue-router";
import TeamCardList from "../components/TeamCardList.vue";
import {myAxios} from "../plugins/my_axios";


const teamList = ref([]);
const router = useRouter()

const doAddTeam = () => {
  router.push('/team/add')
}

onMounted (async () => {
  const res = await getCurrentUser()
  if (res.code === 0){
    await router.replace('/team')
  } else {
    showFailToast("请先登录！")
    await router.replace('/user/login')
  }

  const getteamList = await myAxios.post(
      'team/list',
      {
        params: {},
      }
  )
  if (getteamList) {
    teamList.value = getteamList.data;
  }
})
const init = {
  "id": "",
  "name": "",
  "description": "",
  "maxNum": "",
  "userId": "",
  "status": "",
}

const postdate = {...init}
const searchValue = ref('');

const doSearch =  async ()=>{
  console.log(searchValue.value)
  postdate.description = searchValue.value


  console.log(postdate)
  const getList = await myAxios.post(
      'team/list/team',
      postdate
  )
  if (getList) {
    teamList.value = getList.data;
  }
}

</script>


<template>
  <van-search @search="doSearch" v-model="searchValue" placeholder="请输入搜索关键词" />
  <div id="addTeam">
    <van-button @click="doAddTeam" icon="plus" type="primary" round />
  </div>
  <team-card-list :team-list="teamList"></team-card-list>
  <van-empty  v-if="!teamList|| teamList.length < 1" description="队伍数据为空"/>
</template>


<style scoped>
#addTeam {
  position: fixed;     /* 固定在视口 */
  left: 20px;          /* 距离左侧 */
  bottom: 55px;        /* 距离底部（避免被 tabbar 遮挡） */
  z-index: 9999;       /* 最高层级 */
}
</style>