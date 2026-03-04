<template>

  <div id="addTeam">
    <van-form @submit="onSubmit">
      <van-cell-group inset>
        <van-field
            v-model="addTemeDate.name"
            name="name"
            label="名称"
            placeholder="队伍名称"
            :rules="[{ required: true, message: '请填写用户名' }]"
        />

        <van-field
            v-model="addTemeDate.description"
            rows="4"
            autosize
            label="描述"
            type="textarea"
            placeholder="请输入队伍描述"
        />

        <van-field
            is-link
            readonly
            name="datePicker"
            label="过期时间"
            :placeholder="addTemeDate.expireTime ?? '请选择日期' "
            @click="showPicker = true"
        />
        <van-popup v-model:show="showPicker" destroy-on-close position="bottom">
          <van-date-picker
              v-model="pickerValue"
              title="选择日期"
              @confirm="onConfirm"
              @cancel="showPicker = false"
              :min-date="minDate"
          />

        </van-popup>

        <van-field name="stepper" label="队伍人数">
          <template #input>
            <van-stepper v-model="addTemeDate.maxNum" />
          </template>
        </van-field>

        <van-field name="radio" label="状态">
          <template #input>
            <van-radio-group v-model="addTemeDate.status" direction="horizontal">
              <van-radio name="0">公开</van-radio>
              <van-radio name="1">私有</van-radio>
              <van-radio name="2">加密</van-radio>
            </van-radio-group>
          </template>
        </van-field>

        <van-field
            v-model="addTemeDate.password"
            type="password"
            name="password"
            label="密码"
            placeholder="密码"
            v-if="addTemeDate.status == 2"
        />
      </van-cell-group>
      <div style="margin: 16px;">
        <van-button round block type="primary" native-type="submit">
          提交
        </van-button>
      </div>
    </van-form>


  </div>

</template>



<script setup lang="ts">

import {myAxios} from "../plugins/my_axios"
import {ref} from "vue";
import {showFailToast, showSuccessToast, Toast} from "vant";
import {useRouter} from "vue-router";

const router = useRouter()

const initFormData ={
  "description": "",
  "expireTime": null,
  "maxNum": 5,
  "name": "",
  "password": "",
  "status": 1,
}
const addTemeDate = ref({...initFormData})

const onSubmit = async () => {
  const postData = {
    ...addTemeDate.value,
    status : addTemeDate.value.status
  }

  const res = await myAxios.post(`/team/add`, postData)
  if (res?.code === 0  && res.data){
    showSuccessToast("添加成功")
    setTimeout(() => {
      router.push({
        path:'/team',
        replace: true,
      })
    }, 1000); // 延迟1秒跳转
  }else {
    showFailToast("添加失败")
  }
};

//日期选择器
const showPicker = ref(false);
const minDate = new Date();
const pickerValue = ref<string[]>([])
const onConfirm = () => {
  addTemeDate.value.expireTime = pickerValue.value.join('-')
  showPicker.value = false
}






</script>





<style scoped>

</style>