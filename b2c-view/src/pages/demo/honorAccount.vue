<template>
  <div>
    <el-form :inline="true" :model="editForm" label-width="100px" ref="editFormRef">
      <fieldset>
        <legend>账户信息</legend>
        <el-row>
          <el-col>
            <el-form-item label="userID" prop="zzUserId">
              <el-input v-model="editForm.zzUserId"/>
            </el-form-item>
            <el-form-item label="注册时间">
              <el-input v-model="editForm.zzRegisterTime"/>
            </el-form-item>
            <el-form-item label="注册来源">
              <el-input v-model="editForm.zzRegsterSource"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col>
            <el-form-item label="注册推荐人">
              <el-input v-model="editForm.zzRegisterReffer"/>
            </el-form-item>
            <el-form-item label="昵称">
              <el-input v-model="editForm.zzNickName"/>
            </el-form-item>
            <el-form-item label="状态">
              <el-select clearable disabled v-model="editForm.zzStatus">
                <el-option :key="index" :label="item.name" :value="item.value" v-for="(item, index) in ENABLED"/>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col>
            <el-form-item label="最近登录时间">
              <el-input v-model="editForm.zzLastLogTime"/>
            </el-form-item>
            <el-form-item label="登录应用">
              <el-input v-model="editForm.zzLastLogApp"/>
            </el-form-item>
          </el-col>
        </el-row>
      </fieldset>
    </el-form>
  </div>
</template>

<script>
  import appConsts from '../../common/appConsts'
  import baseAxios from '../../common/baseAxios'
  import config from '../../config'
  import moment from 'moment'

  export default {
    props: {
      username: String,
    },
    data() {
      return {
        ENABLED: appConsts.ENABLED,
        editForm: {
          zzUserId: '',
          zzRegisterTime: null,
          zzRegsterSource: '',
          zzRegisterReffer: '',
          zzNickName: '',
          zzStatus: '',
          zzLastLogTime: null,
          zzLastLogApp: '',
        }
      }
    },
    methods: {
      findHonorAccount(username) {
        let self = this;
        baseAxios.post(config.FIND_HONOR_ACCOUNT, {username}).then((resJson) => {
          self.editForm = resJson.data
          self.editForm.zzRegisterTime = moment(resJson.data.zzRegisterTime).format("YYYY-MM-DD hh:mm:ss")
          self.editForm.zzLastLogTime = moment(resJson.data.zzLastLogTime).format("YYYY-MM-DD hh:mm:ss")
        }).finally(() => {

        })
      },
    },
    mounted() {
      this.findHonorAccount(this.username)
    }
  }
</script>

<style scoped>

</style>
