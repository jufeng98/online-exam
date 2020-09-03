<template>
  <div>
    <el-row>
      <el-col style="text-align: left">
        <el-form :inline="true" :model="usersForm" ref="usersFormRef">
          <el-form-item label="手机号" prop="mobile" size="small">
            <el-input placeholder="请输入会员手机号" v-model="usersForm.mobile"/>
          </el-form-item>
          <el-form-item label="是否实名" prop="name" size="small">
            <el-select clearable v-model="usersForm.name">
              <el-option :key="index" :label="item.name" :value="item.value" v-for="(item, index ) in SHOWLD_SHOW"/>
            </el-select>
          </el-form-item>
          <el-form-item label="是否绑定顾问" prop="angel" size="small">
            <el-select clearable v-model="usersForm.angel">
              <el-option :key="index" :label="item.name" :value="item.value" v-for="(item, index ) in SHOWLD_SHOW"/>
            </el-select>
          </el-form-item>
          <el-form-item label="性别" prop="sex" size="small">
            <el-select clearable v-model="usersForm.sex">
              <el-option :key="index" :label="item.name" :value="item.value" v-for="(item, index ) in GENDER"/>
            </el-select>
          </el-form-item>
          <el-form-item size="small">
            <el-button icon="el-icon-search" type="primary" v-on:click="findUsers">查询</el-button>
            <el-button icon="el-icon-warning" type="primary" v-on:click="resetForm('usersFormRef')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>

    <el-row>
      <el-col style="text-align: left">
        <el-button @click="openUserDetailsPage" icon="el-icon-search" plain type="primary">查看</el-button>
      </el-col>
    </el-row>

    <el-row>
      <el-col>
        <div>
          <el-table :data="usersList" @selection-change="selectUser" border element-loading-text="拼命加载中" row-key="id"
                    tooltip-effect="dark" v-loading="listLoading">
            <el-table-column type="selection" width="55"/>
            <el-table-column label="登录名" prop="username"></el-table-column>
            <el-table-column label="别名" prop="nickname"></el-table-column>
            <el-table-column :formatter="formatData" label="性别" prop="gender" width="80"></el-table-column>
            <el-table-column :formatter="formatData" label="状态" prop="enabled" width="80"></el-table-column>
            <el-table-column label="手机" prop="mobile"></el-table-column>
            <el-table-column :show-overflow-tooltip="true" label="邮箱" prop="email"></el-table-column>
            <el-table-column :formatter="formatData" :show-overflow-tooltip="true" label="创建时间"
                             prop="createTime"></el-table-column>
          </el-table>
        </div>
        <el-col style="text-align: left">
          <el-pagination
            :current-page="pageNum"
            :page-size="pageSize"
            :page-sizes="[10, 20, 30, 40]"
            :total="total"
            @current-change="handleCurrentChange"
            @size-change="handleSizeChange"
            layout="total, sizes, prev, pager, next, jumper">
          </el-pagination>
        </el-col>
      </el-col>
    </el-row>

  </div>
</template>

<script>
  import baseAxios from '../../common/baseAxios'
  import appConsts from '../../common/appConsts'
  import config from '../../config'
  import stringUtils from "../../common/stringUtils";
  import moment from 'moment'
  import userDetails from './userDetails'
  import commonUtils from '../../common/commonUtils'

  export default {
    data() {
      return {
        pageNum: 1,
        pageSize: 10,
        SHOWLD_SHOW: appConsts.SHOWLD_SHOW,
        ENABLED: appConsts.ENABLED,
        GENDER: appConsts.GENDER,
        usersForm: {
          mobile: '',
          name: '',
          angel: '',
          sex: ''
        },
        usersList: [],
        total: 0,
        selectUsers: [],
        listLoading: false,
      }
    },
    methods: {
      resetForm(formName) {
        this.$refs[formName].resetFields()
      },
      formatData(row, column) {
        if (column.property === 'createTime') {
          return moment(new Date(row.createTime)).format('YYYY-MM-DD HH:mm:ss');
        } else if (column.property === 'enabled') {
          return stringUtils.formatTableRowData(row.enabled, this.ENABLED)
        } else if (column.property === 'gender') {
          return stringUtils.formatTableRowData(row.gender, this.GENDER)
        }
      },
      handleSizeChange(val) {
        this.pageSize = val
        this.findUsers(this.pageNum)
      },
      handleCurrentChange(val) {
        this.pageNum = val
        this.findUsers(this.pageNum)
      },
      findUsers(pageNum) {
        this.pageNum = pageNum
        let reqJsonParams = {
          page: {pageNum: this.pageNum, pageSize: this.pageSize},
          usersForm: this.usersForm
        }
        this.listLoading = true
        baseAxios.post(config.FIND_USERS, reqJsonParams).then((resJson) => {
          this.usersList = resJson.data
          this.total = resJson.total
        }).finally(() => {
          this.listLoading = false
        })
      },
      selectUser(selection) {
        this.selectUsers = JSON.parse(JSON.stringify(selection));
      },
      openUserDetailsPage() {
        if (this.selectUsers.length !== 1) {
          this.$message.warning('请选择一条记录')
          return
        }
        let dimension = commonUtils.getDimensionFromPercent(100, 100)
        this.$dlg.modal(userDetails, {
          width: dimension.width,
          height: dimension.height,
          title: '用户详情',
          maxButton: false,
          params: {
            username: this.selectUsers[0].username
          },
          callback: data => {
            console.log(data)
          }
        });
      },
    },
    mounted() {
      this.findUsers(1)
      window.vue = this
    }
  }
</script>

<style scoped>

</style>
