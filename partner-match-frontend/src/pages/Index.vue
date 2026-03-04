<script setup lang="ts">
import {useRoute, useRouter} from "vue-router";
import {onMounted, ref} from "vue";
import {myAxios} from "../plugins/my_axios";
import UserCardList from "../components/UserCardList.vue";
import {getCurrentUser} from "../service/user.ts";
import {showFailToast} from "vant";

const route = useRoute();
const router = useRouter();

const userList = ref([]);

onMounted(async () => {

  const res = await getCurrentUser()
  if (res.code === 0){
  } else {
    showFailToast("请先登录！")
    await router.replace('/user/login')
  }
  const getuserlist = await myAxios.get('user/recommend', {
    params: {},
  })

  if (getuserlist) {
    getuserlist.data.forEach(user => {
      if (user.tags) {
        user.tags = JSON.parse(user.tags);
      }
    })
    userList.value = getuserlist.data;
  }
})

</script>

<template style="padding-bottom: 80px">
  <user-card-list :user-list="userList"/>
  <van-empty v-if="!userList|| userList.length < 1" description="数据为空"/>

</template>

<style scoped>

</style>