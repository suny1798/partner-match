<script setup lang="ts">
import {useRoute, useRouter} from "vue-router";
import {ref} from "vue";
import {myAxios} from "../plugins/my_axios";
import {showFailToast, showSuccessToast} from "vant";
import {getCurrentUser} from "../service/user.ts";

const route = useRoute()
const editUser = ref({
  editKey: route.query.editKey,
  currentValue: route.query.currentValue,
  editName: route.query.editName

})
const router = useRouter()

const user = ref({})

const onSubmit = async () => {
  const resu = await getCurrentUser()
  user.value = resu.data


  const res = await myAxios.post('/user/update', {
    id: user.value.id,
    [editUser.value.editKey as string]: editUser.value.currentValue,
  })
  if (res.code === 0 && res.data > 0) {
    showSuccessToast("修改成功")
    router.back()
  } else {
    showFailToast("修改失败")
  }
};

</script>

<template>
  <van-form @submit="onSubmit">
    <van-field
        v-model="editUser.currentValue"
        :name="editUser.editKey"
        :label="editUser.editName"
        :placeholder="`请输入${editUser.editName}`"
        :rules="[{ required: true, message: '不能为空！！' }]"
    />
    <div style="margin: 16px;">
      <van-button round block type="primary" native-type="submit">
        提交
      </van-button>
    </div>
  </van-form>

</template>

<style scoped>

</style>