<template>
  <div style="width:300px;margin:200px auto;">
    <el-form :model="usersForm" ref="usersFormRef" :rules="usersFormRules">
      <h2 style="color: #40D4A6">在线学习考试管理系统</h2>
      <el-form-item prop="username">
        <el-input placeholder="用户名" v-model="usersForm.username">
          <i slot="prefix"><img src="../../assets/img/icon-user.png"/></i>
          <i scope="error"></i>
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input placeholder="密码" type="password" v-model="usersForm.password">
          <i slot="prefix"><img src="../../assets/img/icon-password.png"/></i>
        </el-input>
      </el-form-item>
      <el-form-item prop="coreRememberMe" style="height:20px;text-align:left;">
        <el-checkbox v-model="usersForm.coreRememberMe">7天内免登录</el-checkbox>
      </el-form-item>
      <el-form-item>
        <el-button style="background-color: #40D4A6;width: 300px;" type="success" @click="login">
          登录
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>

  import baseAxios from '../../common/baseAxios'
  import appConsts from '../../common/appConsts'
  import config from '../../config'
  import stringUtils from "../../common/stringUtils"

  export default {
    data() {
      return {
        usersForm: {
          username: '',
          password: '',
          coreRememberMe: false,
        },
        usersFormRules: {
          username: [
            {required: true, message: '请输入用户名', trigger: 'blur'},
            {validator: stringUtils.checkAlphabetNumber, message: '用户名须是字母或数字', trigger: 'blur'},
            {min: 5, max: 20, message: '长度在 5 到 20 个字符', trigger: 'blur'}
          ],
          password: [
            {required: true, message: '请输入密码', trigger: 'blur'},
            {min: 5, max: 20, message: '长度在 5 到 20 个字符', trigger: 'blur'}
          ]
        },
      }
    },
    methods: {
      login() {
        this.$refs.usersFormRef.validate((valid) => {
          if (!valid) {
            return false
          }
          baseAxios.post(config.LOGIN_URL, this.usersForm, {contentType: 'form'}).then(resJson => {
            localStorage.setItem(appConsts.LOGIN_USERNAME, resJson.data.username)
            localStorage.setItem(appConsts.LOGIN_USER_PIC_URL, config.BASE_PATH + config.APP_CONTEXT + resJson.data.picUrl)
            let authorities = resJson.data.authorities.map((item) => {
              return item.authority
            })
            localStorage.setItem(appConsts.LOGIN_USER_AUTHORITIES, JSON.stringify(authorities))
            this.$router.push('/mainMenu')
          })
        })
      },
    },
  }
</script>
