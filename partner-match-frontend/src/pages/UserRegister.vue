<template>
  <template v-if="user">
    <div style="display: flex; justify-content: center; align-items: center;padding-bottom: 20px;padding-top: 20px">
      <van-image @click="updateAvatar('avatarUrl', '头像',user.avatarUrl)"
          round
          width="5rem"
          height="5rem"
          :src="user.avatarUrl"
      />
    </div>
    <van-cell title="用户名" is-link to="/user/edit" :value="user.username"  @click="toEdit('username', '用户名',user.username)"/>
    <van-cell title="账号" :value="user.userAccount" />
    <van-cell title="性别" is-link to="/user/edit" :value="user.gender" @click="toEdit('gender', '性别',user.gender)"/>
    <van-cell title="邮箱" is-link to="/user/edit" :value="user.email"  @click="toEdit('email', '邮箱',user.email)"/>
    <van-cell title="电话" is-link to="/user/edit" :value="user.phone"  @click="toEdit('phone', '电话',user.phone)"/>
    <van-cell title="标签" is-link to="/user/edit" :value="user.tags"  @click="toEdit('tags', '标签',user.tags)"/>
    <van-cell title="星球编号" :value="user.planetCode" />
    <van-cell title="注册时间" :value="user.createTime" />
  </template>
  <div style="margin: 12px">
    <van-button block type="primary" @click="doLogout">退出登录</van-button>
  </div>
</template>

<script setup lang="ts">
import {useRouter} from "vue-router";
import {onMounted, ref} from "vue";
import {myAxios} from "../plugins/my_axios";
import {showFailToast, showSuccessToast} from "vant";
import {getCurrentUser} from "../service/user.ts";

const router = useRouter()

const user = ref()

onMounted(async () =>{
  const res = await getCurrentUser()
  if (res.code === 0){
    user.value = res.data
  }
})
const updateAvatar = (editKey:String, editName:String, currentValue:String) => {
  router.push({
    path: '/user/edit',
    query: {
      editKey,
      editName,
      currentValue,
    }
  })
}

const toEdit = (editKey:String, editName:String, currentValue:String) => {
  router.push({
    path: '/user/edit',
    query: {
      editKey,
      editName,
      currentValue,
    }
  })
}

const doLogout = () => {
  myAxios.post('/user/logout').then(res => {
    if (res.code === 0){
      showSuccessToast("退出登录")
      router.push('/user/login')
    }else {
      showFailToast("退出登录失败")
    }
  })
}
</script>

<style scoped>
</style>