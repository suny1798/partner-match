<script setup lang="ts">
import {useRoute} from "vue-router";
import {onMounted, ref} from "vue";
import {myAxios} from "../plugins/my_axios";
import qs from "qs";
import UserCardList from "../components/UserCardList.vue";

const route = useRoute();

const { tags } = route.query

const userList = ref([]);

onMounted(async () => {
  const getuserlist = await myAxios.get('user/search/tags',{
      params: {
        tagsNameList: tags
      },
      paramsSerializer: params => {
        return qs.stringify(params, { indices: false })
      }
    })

  if (getuserlist) {
    getuserlist.data.forEach(user => {
      if (user.tags){
        user.tags = JSON.parse(user.tags);
      }
    })
    userList.value = getuserlist.data;
  }
})

</script>

<template>
  <user-card-list :user-list="userList" />
  <van-empty v-if="!userList|| userList.length < 1" description="搜索结果为空" />

</template>

<style scoped>

</style>