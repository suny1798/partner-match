<script setup lang="ts">
import {useRoute, useRouter} from "vue-router";
import {ref} from "vue";
import {myAxios} from "../plugins/my_axios";
import {showFailToast, showSuccessToast} from "vant";

const route = useRoute()
const router = useRouter()

const userAccount = ref('');
const userPassword = ref('');
const onSubmit = async () => {
  const res = await myAxios.post('/user/login',{
    userAccount:userAccount.value,
    userPassword:userPassword.value
  })
  console.log(res,"用户登录")
  if (res.code === 0 && res.data) {
    showSuccessToast("登录成功")
    router.replace('/user')
  } else {
    showFailToast("登录失败")
  }
};

</script>
<template>
  <div class="login-wrapper">
    <div class="login-card">
      <div class="login-header">
        <h2>欢迎回来</h2>
        <p>请登录您的账号</p>
      </div>

      <van-form @submit="onSubmit">
        <van-cell-group inset>
          <van-field
              v-model="userAccount"
              name="userAccount"
              label="账号"
              placeholder="请输入账号"
              left-icon="contact-o"
              clearable
              :rules="[{ required: true, message: '请填写用户名' }]"
          />
          <van-field
              v-model="userPassword"
              type="password"
              name="userPassword"
              label="密码"
              placeholder="请输入密码"
              left-icon="lock"
              clearable
              :rules="[{ required: true, message: '请填写密码' }]"
          />
        </van-cell-group>

        <div class="btn-area">
          <van-button
              round
              block
              type="primary"
              native-type="submit"
              size="large"
          >
            立即登录
          </van-button>
        </div>
      </van-form>

      <div class="login-footer">
        <span @click="router.push('/user/register')">
          还没有账号？去注册
        </span>
      </div>
    </div>
  </div>
</template>

<style scoped>
.login-wrapper {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  //align-items: center;
  align-items: flex-start;   /* 改成这个 */
  padding-top: 100px;         /* 控制上移距离 */
  background: linear-gradient(135deg, #e6f0ff, #ffffff);
  padding-left: 20px;
  padding-right: 20px;
  box-sizing: border-box;   /* 加这个 */
}

.login-card {
  width: 100%;
  max-width: 420px;
  background: #ffffff;
  border-radius: 16px;
  padding: 28px 24px 20px 24px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease;
}

.login-card:hover {
  box-shadow: 0 12px 32px rgba(0, 0, 0, 0.12);
}

.login-header {
  text-align: center;
  margin-bottom: 24px;
}

.login-header h2 {
  margin: 0;
  font-size: 22px;
  font-weight: 600;
  color: #323233;
}

.login-header p {
  margin-top: 6px;
  font-size: 14px;
  color: #969799;
}

.btn-area {
  margin-top: 24px;
}

.van-button--primary {
  background: linear-gradient(90deg, #1989fa, #3a8bff);
  border: none;
}

.login-footer {
  margin-top: 18px;
  text-align: center;
  font-size: 13px;
  color: #1989fa;
  cursor: pointer;
}
</style>