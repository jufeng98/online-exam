<template>
  <div>
    <el-row>
      <el-col style="text-align: left">
        <el-form :inline="true" :model="authoritiesForm" ref="authoritiesFormRef">
          <el-form-item label="角色" prop="authority">
            <el-input v-model="authoritiesForm.authority" placeholder="支持模糊搜索"></el-input>
          </el-form-item>
          <el-form-item label="角色名称" prop="authorityName">
            <el-input v-model="authoritiesForm.authorityName" placeholder="支持模糊搜索"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="findUsersAuthorities(1)">查询</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-warning" @click="resetForm('authoritiesFormRef')">重置</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-plus" @click="showCreateAuthoritiesDialog">新增</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>

    <el-row>
      <el-col>
        <div>
          <el-table :data="authoritiesList" highlight-current-row v-loading="listLoading" fit>
            <el-table-column type="index" width="60px"></el-table-column>
            <el-table-column prop="authority" label="角色"></el-table-column>
            <el-table-column prop="authorityName" label="角色名称"></el-table-column>
            <el-table-column label="操作" width="180">
              <template slot-scope="scope">
                <el-button type="primary" @click="showEditAuthoritiesDialog(scope.$index, scope.row)">编辑</el-button>
                <el-button type="primary" @click="delAuthorities(scope.$index, scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-col style="text-align: left">
            <el-pagination
              @size-change="handleSizeChange"
              :current-page="pageNum"
              :page-sizes="[10, 20, 30, 40]"
              :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total">
            </el-pagination>
          </el-col>
        </div>
      </el-col>
    </el-row>

    <el-dialog :title="title" :visible.sync="createOrEditAuthoritiesDialogVisible" @closed="clearCheckNodes">
      <el-form :model="createOrEditAuthoritiesForm" label-width="80px" :rules="createOrEditAuthoritiesFormRules"
               ref="createOrEditAuthoritiesFormRef">
        <el-form-item label="角色" prop="authority">
          <el-input :disabled="!createAuthorities" v-model="createOrEditAuthoritiesForm.authority" auto-complete="off"
                    :maxlength="30"></el-input>
          <div style="color:red;text-align: left">角色必须以ROLE_开头,全大写</div>
        </el-form-item>
        <el-form-item label="角色名称" prop="name">
          <el-input v-model="createOrEditAuthoritiesForm.authorityName" auto-complete="off" :maxlength="18"></el-input>
        </el-form-item>
        <el-form-item label="角色授权">
          <el-tree
            :data="menusData"
            show-checkbox
            ref="menusTree"
            node-key="id"
            highlight-current
            :default-expand-all="true"
            :props="defaultProps">
          </el-tree>
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input type="textarea" :rows="3" placeholder="请输入内容"
                    v-model="createOrEditAuthoritiesForm.remark"></el-input>
        </el-form-item>
      </el-form>
      <div>
        <el-button @click.native="createOrEditAuthoritiesDialogVisible = false">取消</el-button>
        <el-button type="primary" @click.native="submitCreateOrEditAuthorities" :loading="addLoading">提交</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import baseAxios from '../../common/baseAxios'
  import config from '../../config'

  export default {
    data() {
      return {
        authoritiesForm: {
          authority: '',
          authorityName: ''
        },
        authoritiesList: [],
        title: '',
        total: 0,
        pageNum: 1,
        pageSize: 10,
        listLoading: false,
        createOrEditAuthoritiesForm: {
          authority: '',
          authorityName: '',
          remark: ''
        },
        createAuthorities: true,
        createOrEditAuthoritiesDialogVisible: false,
        addLoading: false,
        createOrEditAuthoritiesFormRules: {
          authority: [
            {required: true, message: '请输入角色编码', trigger: 'blur'}
          ],
          authorityName: [
            {required: true, message: '请输入角色名称', trigger: 'blur'}
          ],
        },
        menusData: [],
        treeLoading: false,
        defaultProps: {
          children: 'subMenus',
          label: 'name'
        },
      }
    },
    methods: {
      handleSizeChange(val) {
        this.pageSize = val
        this.findUsersAuthorities(this.pageSize)
      },
      findUsersAuthorities(pageNum) {
        let reqJsonParams = {
          page: {pageNum: pageNum, pageSize: this.pageSize},
          authoritiesForm: this.authoritiesForm
        }
        this.listLoading = true
        baseAxios.post(config.FIND_USERS_AUTHORITIES, reqJsonParams).then((resJson) => {
          this.authoritiesList = resJson.data
          this.total = resJson.total
        }).finally(() => {
          this.listLoading = false
        })
      },
      showCreateAuthoritiesDialog() {
        this.title = '新增角色'
        this.createAuthorities = true
        this.createOrEditAuthoritiesDialogVisible = true
        this.createOrEditAuthoritiesForm = {}
        this.getMenusList()
      },

      showEditAuthoritiesDialog(index, row) {
        this.title = '编辑角色'
        this.createAuthorities = false
        this.createOrEditAuthoritiesDialogVisible = true
        this.createOrEditAuthoritiesForm = Object.assign({}, row)
        this.getAuthoritiesMenusList(row.authority)
      },
      submitCreateOrEditAuthorities() {
        this.$refs.createOrEditAuthoritiesFormRef.validate((valid) => {
          if (!valid || !this.createOrEditAuthoritiesForm.authority.startsWith('ROLE_')) {
            this.$message({message: '信息有误', type: 'warning'})
            return
          }
          if (this.$refs.menusTree.getCheckedNodes().length === 0) {
            this.$message({message: '请为角色配置菜单信息', type: 'warning'})
            return
          }
          this.addLoading = true
          this.createOrEditAuthoritiesForm.authority = this.createOrEditAuthoritiesForm.authority.toUpperCase()
          let reqJsonParams = {
            createOrEditAuthoritiesForm: this.createOrEditAuthoritiesForm,
          }
          let url = this.createAuthorities ? config.CREATE_AUTHORITIES : config.EDIT_AUTHORITIES
          baseAxios.post(url, reqJsonParams).then((resJson) => {
            let tmp = this.$refs.menusTree.getCheckedNodes().map((item) => {
              return item.id
            })
            reqJsonParams = {
              authority: resJson.data.authority,
              menusIds: tmp
            }
            baseAxios.post(config.CHANGE_AUTHORITIES_MENUS, reqJsonParams).then(() => {
              this.$message({message: '操作成功', type: 'success'})
            })
          }).finally(() => {
            this.addLoading = false
            this.createOrEditAuthoritiesDialogVisible = false
            this.findUsersAuthorities(this.pageNum)
          })
        })
      },
      getMenusList() {
        this.treeLoading = true
        baseAxios.post(config.GET_MENUS_LIST).then((resJson) => {
          this.menusData = resJson.data.menusEntities
        }).finally(() => {
          this.treeLoading = false
        })
      },
      getAuthoritiesMenusList(authority) {
        this.treeLoading = true
        baseAxios.post(config.GET_MENUS_LIST).then((resJson) => {
          this.menusData = resJson.data.menusEntities
          baseAxios.post(config.GET_AUTHORITIES_MENUS_LIST, {authority: authority}).then((resJson) => {
            this.$refs.menusTree.setCheckedNodes(resJson.data.menusEntities)
          })
        }).finally(() => {
          this.treeLoading = false
        })
      },
      clearCheckNodes() {
        this.menusData = []
        this.$refs.menusTree.setCheckedNodes([])
      },
      delAuthorities(index, row) {
        this.$confirm('确认删除该记录吗?', '提示', {type: 'warning'})
          .then(() => {
            this.listLoading = true
            baseAxios.post(config.DEL_AUTHORITIES, {authority: row.authority}).then(() => {
              this.$message({message: '操作成功', type: 'success'})
              this.findUsersAuthorities(1)
            }).finally(() => {
              this.listLoading = false
            })
          })
          .catch(() => {
          })
      },
      resetForm(formName) {
        this.$refs[formName].resetFields()
      },
    },
    mounted() {
      this.findUsersAuthorities(1)
      window.vue = this
    }
  }

</script>
