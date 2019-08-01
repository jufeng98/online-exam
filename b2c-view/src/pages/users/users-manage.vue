<template>
  <div>
    <el-row>
      <el-col style="text-align: left">
        <el-form :inline="true" :model="usersForm" ref="usersFormRef">
          <el-form-item label="用户名:" prop="username">
            <el-input v-model="usersForm.username" placeholder="支持模糊搜索"></el-input>
          </el-form-item>
          <el-form-item label="手机:" prop="mobile">
            <el-input v-model="usersForm.mobile" placeholder="支持模糊搜索"></el-input>
          </el-form-item>
          <el-form-item label="邮箱:" prop="email">
            <el-input v-model="usersForm.email" placeholder="支持模糊搜索"></el-input>
          </el-form-item>
          <el-form-item>
            <el-form-item label="状态" prop="enabled">
              <el-select v-model="usersForm.enabled" clearable>
                <el-option
                  v-for="item in ENABLED"
                  :key="item.value"
                  :label="item.name"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" v-on:click="findUsers(1)">查询</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-warning" v-on:click="resetForm('usersFormRef')">重置</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-plus" v-on:click="showCreateUsersDialog">新增</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>

    <el-row>
      <el-col>
        <div>
          <el-table row-key="id" :data="usersList" border tooltip-effect="dark"
                    v-loading="listLoading" element-loading-text="拼命加载中">
            <el-table-column prop="username" label="登录名"></el-table-column>
            <el-table-column prop="nickname" label="别名"></el-table-column>
            <el-table-column prop="gender" :formatter="formatData" label="性别" width="80"></el-table-column>
            <el-table-column prop="enabled" label="状态" :formatter="formatData" width="80"></el-table-column>
            <el-table-column prop="mobile" label="手机"></el-table-column>
            <el-table-column prop="email" label="邮箱" :show-overflow-tooltip="true"></el-table-column>
            <el-table-column prop="createTime" label="创建时间" :formatter="formatData"
                             :show-overflow-tooltip="true"></el-table-column>
            <el-table-column label="操作" width="400px">
              <template slot-scope="scope">
                <el-button type="primary" size="mini" @click="showAuthoritiesDialog(scope.$index, scope.row)">
                  授权
                </el-button>
                <el-button type="primary" size="mini" @click="showEditUsersDialog(scope.$index, scope.row)">
                  编辑
                </el-button>
                <el-button type="primary" size="mini" :disabled="shouldDisabledButton(scope.$index, scope.row)"
                           @click="delUsers(scope.$index, scope.row)">
                  删除
                </el-button>
                <el-button type="primary" size="mini" :disabled="shouldDisabledButton(scope.$index, scope.row)"
                           @click="changeUsersEnabled(scope.row.enabled, scope.row)">
                  {{scope.row.enabled?'禁用':'启用'}}
                </el-button>
                <el-button type="primary" size="mini" @click="showUpdateUsersPasswordDialog(scope.$index, scope.row)">
                  更新密码
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <el-col style="text-align: left">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[10, 20, 30, 40]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
          </el-pagination>
        </el-col>
      </el-col>
    </el-row>

    <el-dialog :title="title" :visible.sync="createOrEditUsersDialogVisible" :close-on-click-modal="false"
               @closed="clearUploadShowImageUrlAndValidate">
      <el-form :model="createOrEditUsersForm" label-width="100px" :rules="createOrEditUsersFormRules"
               ref="createOrEditUsersFormRef">
        <el-row>
          <el-col>
            <el-form-item label="用户名" prop="username" auto-complete="off">
              <el-input v-model="createOrEditUsersForm.username" placeholder="请填写用户名，5-20个字符"
                        :disabled="!showPasswordFormItem"></el-input>
            </el-form-item>
          </el-col>
          <el-col style="text-align: left;">
            <el-form-item label="性别" prop="gender">
              <el-select v-model="createOrEditUsersForm.gender" clearable>
                <el-option
                  v-for="item in GENDER"
                  :key="item.value"
                  :label="item.name"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col>
            <el-form-item label="密码" prop="password" v-if="showPasswordFormItem" auto-complete="off">
              <el-input type="password" v-model="createOrEditUsersForm.password" placeholder="请填写密码，5-20个字符"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-form-item label="别名" prop="nickname">
              <el-input v-model="createOrEditUsersForm.nickname" :maxlength="15" placeholder="请填写别名"></el-input>
            </el-form-item>
          </el-col>
          <el-col>
            <el-form-item label="手机" prop="mobile">
              <el-input v-model="createOrEditUsersForm.mobile" :maxlength="11" placeholder="请填写手机号码"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="createOrEditUsersForm.email" :maxlength="30" placeholder="请填写邮箱"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-form-item label="备注" prop="remark">
              <el-input type="textarea" v-model="createOrEditUsersForm.remark" :maxlength="50"
                        placeholder="备注信息不超过50个字符"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="text-align: left;">
          <el-col>
            <el-form-item label="头像" prop="picUrl">
              <el-upload
                style="margin:0px;height:200px;"
                class="avatar-uploader"
                :action="uploadImageUrl"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload">
                <img v-if="uploadShowImageUrl" :src="uploadShowImageUrl" class="avatar">
                <i v-else class="el-upload el-icon-plus avatar-uploader-icon"></i>
                <div slot="tip" style="color: red" class="el-upload__tip">
                  只能上传jpg文件，且不超过3MB
                </div>
              </el-upload>
              <el-input type="hidden" style="width:0px;height:0px;"
                        v-model="createOrEditUsersForm.picUrl"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer">
        <el-button @click="cancelCreateOrEditUsers">取消</el-button>
        <el-button type="primary" @click="submitCreateOrEditUses" :loading="editLoading">保存</el-button>
      </div>
    </el-dialog>

    <el-dialog title="更新密码" :visible.sync="updatePasswordDialogVisible" width="30%">
      <div>
        <el-input type="password" v-model="newPassword" placeholder="请输入新密码，5-20个字符" :maxlength="20"
                  style="margin-bottom: 10px;"></el-input>
        <el-input type="password" v-if="showPasswordInput" v-model="password" placeholder="请输入原密码"
                  :maxlength="20"></el-input>
      </div>
      <div slot="footer">
        <el-button @click="cancelUpdateUsersPassword">取消</el-button>
        <el-button type="primary" @click="submitUpdateUsersPassword" :loading="editLoading">确定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="用户授权管理" :visible.sync="authDialogVisible">
      <template>
        <el-transfer style="text-align: left;margin-left:18%" :titles="['未授予角色','已授予角色']" :data="usersAuthorities"
                     v-model="authorities" @change="changeAuthorities"></el-transfer>
      </template>
    </el-dialog>

    <el-dialog title="查看头像" :visible.sync="showImageDialogVisible" width="30%">

    </el-dialog>

  </div>
</template>

<script>
  import baseAxios from '../../common/baseAxios'
  import appConsts from '../../common/appConsts'
  import config from '../../config'
  import dateUtils from '../../common/dateUtils'
  import stringUtils from '../../common/stringUtils'

  export default {
    data() {
      return {
        authorities: [],
        usersAuthorities: [],
        ENABLED: appConsts.ENABLED,
        GENDER: appConsts.GENDER,
        usersForm: {
          username: '',
          mobile: '',
          email: '',
          enabled: ''
        },
        usersList: [],
        listLoading: false,
        pageNum: 1,
        pageSize: 10,
        total: 0,
        createOrEditUsersDialogVisible: false,
        createOrEditUsersForm: {
          username: '',
          gender: '',
          password: '',
          nickname: '',
          mobile: '',
          email: '',
          remark: '',
          picUrl: '',
        },
        uploadShowImageUrl: '',
        createOrEditUsersFormRules: {
          username: [
            {required: true, message: '请填写用户名', trigger: 'blur'},
            {min: 5, max: 20, message: '长度在 5 到 20 个字符', trigger: 'blur'}
          ],
          password: [
            {required: true, message: '请填写密码', trigger: 'blur'},
            {min: 5, max: 20, message: '长度在 5 到 20 个字符', trigger: 'blur'}
          ],
          mobile: [
            {validator: stringUtils.checkMobile, message: '手机号码格式不正确', trigger: 'blur'}
          ],
          email: [
            {validator: stringUtils.checkEmail, message: 'Email格式不正确', trigger: 'blur'}
          ],
          picUrl: [
            {required: true, message: '请选择头像', trigger: 'blur'}
          ],
        },
        editLoading: false,
        title: '',
        updatePasswordDialogVisible: false,
        password: '',
        newPassword: '',
        showPasswordInput: false,
        authDialogVisible: false,
        showImageDialogVisible: false,
        currentChooseUsers: '',
        showPasswordFormItem: true,
        uploadImageUrl: config.BASE_PATH + config.APP_CONTEXT + config.UPLOAD_FILE,
      }
    },
    methods: {
      formatData(row, column) {
        if (column.property === 'createTime') {
          return dateUtils.formatDate(new Date(row.createTime))
        } else if (column.property === 'enabled') {
          return stringUtils.formatTableRowData(row.enabled, this.ENABLED)
        } else if (column.property === 'gender') {
          return stringUtils.formatTableRowData(row.gender, this.GENDER)
        }
      },
      showAuthoritiesDialog(index, row) {
        this.currentChooseUsers = row.username
        baseAxios.all([this.getUsersAuthorities(), this.getAuthorities(row.username)])
          .then(baseAxios.spread((resJson1, resJson2) => {
            this.usersAuthorities = resJson1.data.map((item) => {
              return {key: item.authority, label: item.authorityName}
            })
            this.authorities = resJson2.data.authorities.map((item) => {
              return this.usersAuthorities.filter((innerItem) => {
                if (innerItem.key === item.authority) {
                  return true
                }
                return false
              })[0].key
            })
            this.authDialogVisible = true
          }))
      },
      getUsersAuthorities() {
        return baseAxios.post(config.FIND_USERS_AUTHORITIES, {
          page: {pageNum: 1, pageSize: 99999},
          authoritiesForm: {}
        })
      },
      getAuthorities(username) {
        return baseAxios.post(config.FIND_AUTHORITIES, {username: username})
      },
      changeAuthorities(current, direction, keys) {
        if (direction === 'right') {
          baseAxios.post(config.AUTH_USERS, {username: this.currentChooseUsers, authorities: keys})
        } else {
          baseAxios.post(config.UN_AUTH_USERS, {username: this.currentChooseUsers, authorities: keys})
        }
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
      showCreateUsersDialog() {
        this.title = '新增用户'
        this.showPasswordFormItem = true
        this.createOrEditUsersForm = {}
        this.uploadShowImageUrl = ''
        this.createOrEditUsersDialogVisible = true
      },
      resetForm(formName) {
        this.$refs[formName].resetFields()
      },
      showEditUsersDialog(index, row) {
        this.title = '编辑用户'
        this.showPasswordFormItem = false
        this.uploadShowImageUrl = row.picUrl
        this.createOrEditUsersForm = Object.assign({}, row)
        this.createOrEditUsersDialogVisible = true
      },
      changeUsersEnabled(enabled, row) {
        let tip = enabled ? '是否确认禁用该账号' : '是否确认启用该账号'
        this.$confirm(tip, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          baseAxios.post(config.CHANGE_USERSE_NABLED, {username: row.username, enabled: !enabled}).then(() => {
            this.$message({message: '操作成功', type: 'success'})
            this.findUsers(this.pageNum)
          })
        }).catch(() => {
        })
      },
      submitUpdateUsersPassword() {
        if (!this.newPassword || this.newPassword.length < 5) {
          this.$message({message: "请正确输入密码信息", type: 'warning'})
          return
        }
        let reqJsonParams = {username: this.currentChooseUsers, newPassword: this.newPassword, password: this.password}
        baseAxios.post(config.UPDATE_USERS_PASSWORD, reqJsonParams).then(() => {
          this.$message({message: '操作成功', type: 'success'})
          this.updatePasswordDialogVisible = false
        })
      },
      showUpdateUsersPasswordDialog(index, row) {
        if (localStorage.getItem(appConsts.LOGIN_USERNAME) === row.username) {
          this.showPasswordInput = true
        } else {
          this.showPasswordInput = false
        }
        this.currentChooseUsers = row.username
        this.newPassword = ''
        this.updatePasswordDialogVisible = true
      },
      delUsers(index, row) {
        this.$confirm('是否确认删除选中的用户?', '提示', {confirmButtonText: '确定', cancelButtonText: '取消', type: 'warning'})
          .then(() => {
            baseAxios.post(config.DELETE_USERS, {username: row.username}, {contentType: 'form'}).then(() => {
              this.$message({message: '操作成功', type: 'success'})
              this.findUsers(this.pageNum)
            })
          }).catch(() => {
        })
      },
      shouldDisabledButton(index, row) {
        if (localStorage.getItem(appConsts.LOGIN_USERNAME) === row.username) {
          return true
        } else {
          return false
        }
      },
      submitCreateOrEditUses() {
        this.$refs.createOrEditUsersFormRef.validate((valid) => {
          if (!valid) {
            this.$message({message: '信息有误', type: 'warning'})
            return
          }
          this.editLoading = true
          let reqJsonParams = {createOrEditUsersForm: this.createOrEditUsersForm}
          let url = this.showPasswordFormItem ? config.CREATE_USERS : config.EDIT_USERS
          baseAxios.post(url, reqJsonParams).then(() => {
            this.$message({message: '操作成功', type: 'success'});
            this.createOrEditUsersDialogVisible = false
            this.findUsers(this.pageNum)
          }).finally(() => {
            this.editLoading = false
          })
        })
      },
      cancelCreateOrEditUsers() {
        this.createOrEditUsersDialogVisible = false
      },
      cancelUpdateUsersPassword() {
        this.newPassword = ''
        this.updatePasswordDialogVisible = false
      },
      handleAvatarSuccess(res, file) {
        if (!res.success) {
          this.$message({message: '上传失败,请稍后再试', type: 'warning'})
          return
        }
        this.$refs['createOrEditUsersFormRef'].clearValidate('picUrl')
        this.uploadShowImageUrl = URL.createObjectURL(file.raw);
        this.createOrEditUsersForm.picUrl = top.location.protocol + config.BASE_PATH + config.APP_CONTEXT +
          config.DOWNLOAD_FILE + '?completePath=' + encodeURIComponent(res.data[0])
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt3M = file.size / 1024 / 1024 < 3;

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt3M) {
          this.$message.error('上传头像图片大小不能超过 3MB!');
        }
        return isJPG && isLt3M;
      },
      clearUploadShowImageUrlAndValidate() {
        this.uploadShowImageUrl = ''
        this.$refs['createOrEditUsersFormRef'].clearValidate()
      },
      handleSizeChange(val) {
        this.pageSize = val
        this.findUsers(this.pageNum)
      },
      handleCurrentChange(val) {
        this.pageNum = val
        this.findUsers(this.pageNum)
      },
    },
    mounted() {
      this.findUsers(1)
      window.vue = this
    }
  }
</script>
<style scoped>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }

  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }

  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
