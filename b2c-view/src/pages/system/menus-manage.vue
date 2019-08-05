<template>
  <div>
    <div style="text-align: left;margin-bottom: 20px">
      <el-button size="small" type="primary" icon="el-icon-refresh" @click="getMenusList">
        重新加载
      </el-button>
      <el-button size="small" type="primary" icon="el-icon-plus" @click="showCreateOrEditMenusDialog('top')">
        新建顶级菜单
      </el-button>
    </div>
    <el-table :data="menusData" border v-loading="listLoading">
      <el-table-column prop="name" label="菜单">
        <template slot-scope="scope">
          <div>
            <span v-if="scope.row.parentId !== 0" v-html="indentSpace[scope.row.parentId]"></span>
            <span v-if="scope.row.subMenus !== null" @click="expandOrElapseMenus(scope.$index,scope.row)">
              <i v-if="arrowExpanded[scope.row.id]" class="el-icon-arrow-down" aria-hidden="true"></i>
              <i v-else class="el-icon-arrow-right" aria-hidden="true"></i>
            </span>
            <span v-else>&nbsp;&nbsp;&nbsp;</span>
            {{scope.row.name}}
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="path" label="链接"></el-table-column>
      <el-table-column prop="icon" label="图标"></el-table-column>
      <el-table-column prop="shouldShow" :formatter="formatData" label="是否展示"></el-table-column>
      <el-table-column label="操作" width="350">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="showCreateOrEditMenusDialog('edit', scope.row)">
            编辑
          </el-button>
          <el-button v-if="scope.row.subMenus === null" size="mini" type="primary"
                     @click="delMenus(scope.$index, scope.row)">
            删除
          </el-button>
          <el-button size="mini" type="primary" @click="showCreateOrEditMenusDialog('sub', scope.row)">
            添加子菜单
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :title="title" :visible.sync="createOrEditMenusFormVisible" :close-on-click-modal="false">
      <el-form :model="createOrEditMenusForm" label-width="120px" :rules="createOrEditMenusFormRules"
               ref="createOrEditMenusForm">
        <el-form-item label="父菜单名称" prop="parentName">
          <el-input v-model="createOrEditMenusForm.parentName" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="菜单名称" prop="name">
          <el-input v-model="createOrEditMenusForm.name" auto-complete="off" :maxlength="20"></el-input>
        </el-form-item>
        <el-form-item label="链接" prop="path">
          <el-input v-model="createOrEditMenusForm.path" auto-complete="off" :maxlength="200"></el-input>
        </el-form-item>
        <el-form-item label="图标" prop="icon">
          <el-input v-model="createOrEditMenusForm.icon" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="是否显示" prop="shouldShow" style="text-align: left">
          <el-switch v-model="createOrEditMenusForm.shouldShow"></el-switch>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="createOrEditMenusFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="submitCreateOrEditMenusForm" :loading="addLoading">保存</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import baseAxios from '../../common/baseAxios'
  import appConsts from '../../common/appConsts'
  import stringUtils from '../../common/stringUtils'
  import config from '../../config'

  export default {
    data() {
      return {
        listLoading: false,
        createOrEditMenusForm: {
          parentId: '',
          parentName: '',
          name: '',
          path: '',
          icon: '',
          shouldShow: false,
        },
        createOrEditMenusFormVisible: false,
        addLoading: false,
        createOrEditMenusFormRules: {
          name: [
            {required: true, message: '请输入名称', trigger: 'blur'}
          ],
        },
        SHOULD_SHOW: appConsts.SHOWLD_SHOW,
        menusData: [],
        arrowExpanded: [],
        indentSpace: [],
        recordInsertMenusId: [],
        title: '',
        editMenus: false,
      }
    },
    methods: {
      formatData(row, column) {
        if (column.property === 'shouldShow') {
          return stringUtils.formatTableRowData(row.shouldShow, this.SHOULD_SHOW)
        }
      },
      getMenusList() {
        this.listLoading = true
        baseAxios.post(config.GET_MENUS_LIST, {shouldFilterNullSubMenus: false}).then((resJson) => {
          this.menusData = resJson.data.menusEntities
          this.menusData.forEach((item) => {
            this.arrowExpanded[item.id] = false
            this.indentSpace[item.parentId] = '&nbsp;&nbsp;&nbsp;'
            this.indentSpace[item.id] = '&nbsp;&nbsp;&nbsp;'
          })
        }).finally(() => {
          this.listLoading = false
        })
      },
      recursiveIterateMenus(array, rowId) {
        let insertDataIds = this.recordInsertMenusId[rowId]
        if (insertDataIds === undefined) {
          return
        }
        array.push(...insertDataIds)
        for (let i = 0; i < insertDataIds.length; i++) {
          this.recursiveIterateMenus(array, insertDataIds[i])
        }
      },
      expandOrElapseMenus(index, row) {
        if (this.arrowExpanded[row.id] === undefined) {
          this.arrowExpanded[row.id] = false
        }
        this.arrowExpanded[row.id] = !this.arrowExpanded[row.id]
        if (!this.arrowExpanded[row.id]) {
          this.indentSpace[row.id] = this.indentSpace[row.id].replace(this.indentSpace[row.parentId], '')
          // 收起箭头时,删掉插入的菜单记录
          let array = []
          this.recursiveIterateMenus(array, row.id)
          this.menusData = this.menusData.filter((item) => {
            return array.indexOf(item.id) === -1
          })
          // 重置展开标志
          for (let i = 0; i < array.length; i++) {
            this.arrowExpanded[array[i]] = false
          }
          return
        }
        if (this.indentSpace[row.id] === undefined) {
          this.indentSpace[row.id] = '&nbsp;&nbsp;&nbsp;'
        }
        this.indentSpace[row.id] += this.indentSpace[row.parentId]
        let tmp = this.menusData.filter((item) => {
          if (item.id === row.id) {
            return true
          }
          return false
        })
        let subMenus = tmp[0].subMenus
        // 箭头展开,将子菜单记录插入到menusData
        this.recordInsertMenusId[row.id] = []
        subMenus.forEach((item) => {
          this.recordInsertMenusId[row.id].push(item.id)
        })
        this.menusData.splice(index + 1, 0, ...subMenus)
      },
      delMenus(index, row) {
        this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          baseAxios.post(config.DEL_MENUS, {id: row.id}).then(() => {
            this.$message({message: '操作成功', type: 'success'})
            this.getMenusList()
          })
        }).catch(() => {
        })
      },
      showCreateOrEditMenusDialog(operationType, row) {
        this.createOrEditMenusFormVisible = true
        if (operationType === 'top') {
          this.title = '新建菜单'
          this.editMenus = false
          this.createOrEditMenusForm = {parentName: '', shouldShow: true}
          return
        } else if (operationType === 'sub') {
          this.title = '新建菜单'
          this.editMenus = false
          this.createOrEditMenusForm = {parentName: row.name, parentId: row.id, shouldShow: true}
        } else {
          this.title = '编辑菜单'
          this.editMenus = true
          this.createOrEditMenusForm = JSON.parse(JSON.stringify(row))
        }
      },
      submitCreateOrEditMenusForm() {
        this.$refs.createOrEditMenusForm.validate((valid) => {
          if (!valid) {
            this.$message({message: '信息有误', type: 'warning'})
            return
          }
          this.addLoading = true
          let reqJsonParams = {createOrEditMenusForm: this.createOrEditMenusForm}
          let url = this.editMenus ? config.EDIT_MENUS : config.CREATE_MENUS
          baseAxios.post(url, reqJsonParams).then(() => {
            this.$message({message: '操作成功', type: 'success'})
            this.getMenusList()
            this.createOrEditMenusFormVisible = false
          }).finally(() => {
            this.addLoading = false
          })
        })
      },
    },
    mounted() {
      this.getMenusList()
      window.vue = this
    }
  }
</script>
