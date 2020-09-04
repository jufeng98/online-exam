<template>
  <div>
    <el-form :inline="true" :model="userDetailsForm" label-width="80px" ref="userDetailsFormRef">
      <fieldset>
        <legend>基本信息</legend>
        <el-row>
          <el-col>
            <el-form-item label="会员ID" prop="memberId">
              <el-input disabled v-model="userDetailsForm.memberId"/>
            </el-form-item>
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="userDetailsForm.phone"/>
            </el-form-item>
            <el-form-item label="性别" prop="sex">
              <el-select clearable v-model="userDetailsForm.sex">
                <el-option :key="index" :label="item.name" :value="item.value" v-for="(item, index) in GENDER"/>
              </el-select>
            </el-form-item>
            <el-form-item label="是否实名" prop="autoaym">
              <el-select clearable v-model="userDetailsForm.autoaym">
                <el-option :key="index" :label="item.name" :value="item.value" v-for="(item, index) in SHOWLD_SHOW"/>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </fieldset>
    </el-form>
    <el-tabs @tab-click="tabClick" type="border-card">
      <el-tab-pane label="用户档案">
        <el-tabs type="border-card">
          <el-tab-pane label="至尊账户">
            <honorAccount :username="username"/>
          </el-tab-pane>
          <el-tab-pane label="小屋账户" :lazy="true">
            <smallHouseAccount :username="username"/>
          </el-tab-pane>
          <el-tab-pane label="用户体系名称" :lazy="true">
            <userAccountSystem :username="username"/>
          </el-tab-pane>
        </el-tabs>
      </el-tab-pane>
      <el-tab-pane label="家庭信息" :lazy="true">
        <homeInfo :username="username"/>
      </el-tab-pane>
      <el-tab-pane label="产品消费记录" :lazy="true">
        <productConsumeRecord :username="username"/>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  import baseAxios from "../../common/baseAxios"
  import config from "../../config"
  import appConsts from "../../common/appConsts"
  import homeInfo from "./homeInfo"
  import productConsumeRecord from "./productConsumeRecord"
  import honorAccount from "./honorAccount";
  import smallHouseAccount from "./smallHouseAccount"
  import userAccountSystem from "./userAccountSystem"

  export default {
    name: "userDetails",
    props: {
      username: String,
    },
    components: {
      homeInfo,
      productConsumeRecord,
      honorAccount,
      smallHouseAccount,
      userAccountSystem,
    },
    data() {
      return {
        SHOWLD_SHOW: appConsts.SHOWLD_SHOW,
        ENABLED: appConsts.ENABLED,
        GENDER: appConsts.GENDER,
        userDetailsForm: {
          memberId: '',
          phone: '',
          sex: '',
          autoaym: null,
        },
      }
    },
    methods: {
      findUserDetails(username) {
        let self = this;
        baseAxios.post(config.FIND_USER_DETAILS, {username}).then((resJson) => {
          self.userDetailsForm = resJson.data
        }).finally(() => {

        })
      },
      tabClick(tab) {
        if (tab.label === '家庭信息') {
        }
        if (tab.label === '产品消费记录') {
        }
      },
    },
    mounted() {
      this.findUserDetails(this.username)
    }
  }
</script>

<style scoped>

</style>
