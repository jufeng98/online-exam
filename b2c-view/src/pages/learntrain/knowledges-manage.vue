<template>
  <div>
    <el-row>
      <el-col style="text-align: left">
        <el-form :inline="true" :model="knowledgesForm" ref="knowledgesFormRef">
          <el-form-item label="知识名称" prop="knowledgesName">
            <el-input v-model="knowledgesForm.knowledgesName" placeholder="支持模糊搜索"></el-input>
          </el-form-item>
          <el-form-item label="所属章节" prop="sectionsCode">
            <el-select v-model="knowledgesForm.sectionsCode" clearable>
              <el-option
                v-for="item in sectionsList"
                :key="item.sectionsCode"
                :label="item.sectionsName"
                :value="item.sectionsCode">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="findKnowledgesList(1)">查询</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-warning" @click="resetForm('knowledgesFormRef')">重置</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-plus" @click="showCreateKnowledgesDialog">新增</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>

    <el-row>
      <el-col>
        <div>
          <el-table :data="knowledgesList" highlight-current-row v-loading="listLoading" fit>
            <el-table-column type="index" width="60px"></el-table-column>
            <el-table-column prop="knowledgesName" label="知识名称"></el-table-column>
            <el-table-column prop="sectionsCode" :formatter="formatData" label="所属章节"></el-table-column>
            <el-table-column prop="sortOrder" label="顺序"></el-table-column>
            <el-table-column label="操作" width="180">
              <template slot-scope="scope">
                <el-button type="primary" size="mini" @click="showEditKnowledgesDialog(scope.$index, scope.row)">
                  编辑
                </el-button>
                <el-button type="primary" size="mini" @click="delKnowledges(scope.$index, scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
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
        </div>
      </el-col>
    </el-row>

    <el-dialog :title="title" :visible.sync="createOrEditKnowledgesDialogVisible">
      <el-form :model="createOrEditKnowledgesForm" label-width="90px" :rules="createOrEditKnowledgesFormRules"
               ref="createOrEditKnowledgesFormRef">
        <el-form-item label="知识名称" prop="knowledgesName">
          <el-input v-model="createOrEditKnowledgesForm.knowledgesName" auto-complete="off"
                    :maxlength="30"></el-input>
        </el-form-item>
        <el-form-item label="所属章节" prop="sectionsCode" style="text-align: left">
          <el-select v-model="createOrEditKnowledgesForm.sectionsCode">
            <el-option
              v-for="item in sectionsList"
              :key="item.sectionsCode"
              :label="item.sectionsName"
              :value="item.sectionsCode">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="排序" prop="sortOrder" style="text-align: left">
          <el-input-number controls-position="right" v-model="createOrEditKnowledgesForm.sortOrder"></el-input-number>
        </el-form-item>
      </el-form>
      <div>
        <el-button @click.native="createOrEditKnowledgesDialogVisible = false">取消</el-button>
        <el-button type="primary" @click.native="submitCreateOrEditKnowledges" :loading="addLoading">提交</el-button>
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
        knowledgesForm: {
          knowledgesName: '',
          sectionsCode: ''
        },
        knowledgesList: [],
        title: '',
        total: 0,
        pageNum: 1,
        pageSize: 10,
        listLoading: false,
        createOrEditKnowledgesForm: {
          knowledgesName: '',
          sectionsCode: '',
          sortOrder: 1,
        },
        createKnowledges: true,
        createOrEditKnowledgesDialogVisible: false,
        addLoading: false,
        sectionsList: [],
        sectionsMap: new Map(),
        createOrEditKnowledgesFormRules: {
          knowledgesName: [
            {required: true, message: '请输入知识名称', trigger: 'blur'}
          ],
          sectionsCode: [
            {required: true, message: '请选择所属章节', trigger: 'blur'}
          ],
        },
      }
    },
    methods: {
      handleSizeChange(val) {
        this.pageSize = val
        this.findKnowledgesList(this.pageNum)
      },
      handleCurrentChange(val) {
        this.pageNum = val
        this.findKnowledgesList(this.pageNum)
      },
      formatData(row, column) {
        if (column.property === 'sectionsCode') {
          return this.sectionsMap.get(row.sectionsCode)
        }
      },
      findKnowledgesList(pageNum) {
        let reqJsonParams = {
          page: {pageNum: pageNum, pageSize: this.pageSize},
          knowledgesForm: this.knowledgesForm
        }
        this.listLoading = true
        baseAxios.post(config.FIND_KNOWLEDGES_LIST, reqJsonParams).then((resJson) => {
          this.knowledgesList = resJson.data
          this.total = resJson.total
        }).finally(() => {
          this.listLoading = false
        })
      },
      findSectionsList() {
        let reqJsonParams = {
          page: {pageNum: 1, pageSize: 99999},
          sectionsForm: {}
        }
        baseAxios.post(config.FIND_SECTIONS_LIST, reqJsonParams).then((resJson) => {
          this.sectionsList = resJson.data
          for (let i = 0; i < this.sectionsList.length; i++) {
            this.sectionsMap.set(this.sectionsList[i].sectionsCode, this.sectionsList[i].sectionsName)
          }
          this.findKnowledgesList(1)
        })
      },
      showCreateKnowledgesDialog() {
        this.title = '新增知识'
        this.createKnowledges = true
        this.createOrEditKnowledgesDialogVisible = true
        this.createOrEditKnowledgesForm = {}
      },

      showEditKnowledgesDialog(index, row) {
        this.title = '编辑知识'
        this.createKnowledges = false
        this.createOrEditKnowledgesDialogVisible = true
        this.createOrEditKnowledgesForm = Object.assign({}, row)
      },
      submitCreateOrEditKnowledges() {
        this.$refs.createOrEditKnowledgesFormRef.validate((valid) => {
          if (!valid) {
            this.$message({message: '信息有误', type: 'warning'})
            return
          }
          this.addLoading = true
          let reqJsonParams = {
            knowledgesForm: this.createOrEditKnowledgesForm,
          }
          let url = this.createKnowledges ? config.CREATE_KNOWLEDGES : config.EDIT_KNOWLEDGES
          baseAxios.post(url, reqJsonParams).then((resJson) => {
          }).finally(() => {
            this.addLoading = false
            this.createOrEditKnowledgesDialogVisible = false
            this.findKnowledgesList(this.pageNum)
          })
        })
      },
      delKnowledges(index, row) {
        this.$confirm('确认删除该记录吗?', '提示', {type: 'warning'})
          .then(() => {
            this.listLoading = true
            baseAxios.post(config.DEL_KNOWLEDGES, {id: row.id}).then(() => {
              this.$message({message: '操作成功', type: 'success'})
              this.findKnowledgesList(1)
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
      this.findSectionsList()
      window.vue = this
    }
  }
</script>
