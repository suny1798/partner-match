<template>
<van-nav-bar
  :title="barName"
  left-arrow
  @click-left="onClickLeft"
  @click-right="onClickRight"
>
  <template #right>
    <van-icon name="search" size="18" />
  </template>
</van-nav-bar>

<div   id="content"
       :style="{ paddingBottom: needTabbar ? '50px' : '0px' }">
  <router-view />
</div>

<van-tabbar route @change="onChange">
  <van-tabbar-item to="/" icon="home-o" name="index">主页</van-tabbar-item>
  <van-tabbar-item to="/team" icon="search" name="team">队伍</van-tabbar-item>
  <van-tabbar-item @click="handleUserClick" icon="friends-o" name="user">个人</van-tabbar-item>
</van-tabbar>


</template>


<script setup>
import {computed, onMounted, ref} from 'vue';
import {useRoute, useRouter} from "vue-router";
import { showSuccessToast, showFailToast } from 'vant';
import {getCurrentUser} from "../service/user.ts";

const router = useRouter();
const route = useRoute();

const needTabbar = computed(() => {
  return route.name !== 'login' && route.name !== 'register'
})

const barName = computed(() => route.meta.tittle);

const onClickLeft = () => {
  router.back();
};
const onClickRight = () => {
  router.push('/search');
}

const handleUserClick = async () => {
  const res = await getCurrentUser()
  if (res.code === 0){
    await router.replace('/user')
  } else {
    showFailToast("请先登录！")
    await router.replace('/user/login')
  }
}

</script>

<style lang="scss" scoped>
</style>