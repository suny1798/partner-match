<script setup lang="ts">

import type {UserType} from "../models/user";

interface User {
  userList : UserType[]
}

const prpos =  defineProps<User>()
</script>

<template>
  <van-card
      v-for="user in prpos.userList"
      :desc="user.profile"
      :tag="String(user.gender)"
      :title="`${user.username} (${user.planetCode})`"
      :thumb="user.avatarUrl"
  >
    <template #tags>
      <van-tag
          v-for="(tag, index) in user.tags"
          :key="index"
          plain type="danger"
          style="margin-right: 8px;margin-top: 5px"
      >
        {{ tag }}
      </van-tag>
    </template>
    <template #footer>
      <van-button size="mini">联系我</van-button>
    </template>
  </van-card>
</template>

<style scoped>

</style>