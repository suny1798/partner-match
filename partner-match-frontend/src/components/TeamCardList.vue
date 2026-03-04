<script setup lang="ts">

import type {TeamType} from "../models/team";
import girl from "../assets/1.png"
import {myAxios} from "../plugins/my_axios"
import {showToast} from "vant";
import {getCurrentUser} from "../service/user.ts";
import {onMounted, ref} from "vue";

interface Team {
  teamList : TeamType[]
}

const prpos =  defineProps<Team>()

const doJoinTeam = async (id: number)=>{
  const res =  await myAxios.post('/team/join', {teamId: id})
  if (res.code === 0) {
    showToast('加入成功');
  }else {
    showToast(res.description);
  }
}


const UserId = ref(0)
onMounted(async ()=>{
  const res = await getCurrentUser();
  UserId.value = res.data.id
})


const statusMap = {
  0: { text: '公开', type: 'success' },
  1: { text: '私有', type: 'danger' },
  2: { text: '加密', type: 'danger' }
}




</script>

<template>
  <div id="teamCard">
    <van-card
        v-for="team in prpos.teamList"
        :desc="team.description"
        :title="team.name"
        :thumb="girl"
    >
      <template #tags>
        <van-tag
            plain
            :type="statusMap[team.status]?.type"
            style="margin-right: 8px;margin-top: 5px"
        >
          {{ statusMap[team.status]?.text }}
        </van-tag>
      </template>
      <template #bottom>
        <div>
          {{ '最大人数: ' + team.maxNum }}
        </div>
        <div v-if="team.expireTime">
          {{ '过期时间: ' + team.expireTime }}
        </div>
        <div>
          {{ '创建时间: ' + team.createTime }}
        </div>
      </template>
      <template #footer>
        <van-button size="mini" type="primary" plain @click="doJoinTeam(team.id)">加入队伍</van-button>
        <van-button v-if="team.createUser?.id == UserId"
                    size="mini" type="primary" >更新队伍</van-button>
      </template>
    </van-card>

  </div>

</template>

<style scoped>
#teamCard :deep(.van-image__img) {
  object-fit: fill;
  height: 128px;
}
</style>