<template>
  <div class="login-container">
    <el-card class="login-card">
      <h2>学生管理系统-登录</h2>
      <el-form :model="loginForm">
        <el-form-item>
          <el-input v-model="loginForm.username" placeholder="用户名" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="loginForm.password" type="password" placeholder="密码" />
        </el-form-item>
        <el-form-item>
          <div style="display: flex; gap: 10px;">
            <el-input v-model="loginForm.code" placeholder="验证码" />
            <!-- 点击图片重新调用后端接口获取新验证码 -->
            <img :src="captchaUrl" @click="getCaptcha" style="cursor: pointer; height: 40px;" />
          </div>
        </el-form-item>
        <el-button type="primary" @click="handleLogin" style="width: 100%;">登录</el-button>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const router = useRouter()
const loginForm = ref({ username: '', password: '', code: '' })
const captchaUrl = ref('')

// 获取验证码的函数
const getCaptcha = () => {
  // 注意：这里访问的是网关端口 8080
  // 加时间戳 t=... 是为了防止浏览器缓存图片不刷新
  captchaUrl.value = 'http://localhost:8080/api/user/captcha?t=' + new Date().getTime()
}

// 登录处理
const handleLogin = async () => {
  try {
    const res = await axios.post(`http://localhost:8080/api/user/login?code=${loginForm.value.code}`, {
      username: loginForm.value.username,
      password: loginForm.value.password
    })
    
    if (res.data.code === 200) {
      ElMessage.success('登录成功')
      router.push('/student') // 登录成功跳转到学生管理页
    } else {
      ElMessage.error(res.data.message)
      getCaptcha() // 登录失败自动刷新验证码
    }
  } catch (error) {
    ElMessage.error('服务器连接失败')
  }
}

onMounted(() => {
  getCaptcha() // 页面加载时自动获取验证码
})
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f7fa;
}
.login-card {
  width: 400px;
  text-align: center;
}
</style>