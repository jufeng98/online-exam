<template>
  <div>
    <el-row style="text-align: left">
      <el-col>
        <el-form :inline="true" v-model="questionsForm" ref="questionsFormRef">
          <el-form-item label="题目" props="questionsTitle">
            <el-input v-model="questionsForm.questionsTitle" placeholder="支持模糊搜索"></el-input>
          </el-form-item>
          <el-form-item label="题型" props="questionsType">
            <el-select v-model="questionsForm.questionsType" placeholder="全部" clearable>
              <el-option v-for="item in questionsTypes" :key="item.value" :label="item.name"
                         :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="findQuestionsList(1)">查询</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-warning" @click="resetForm('questionsFormRef')">重置</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-plus" @click="showCreateQuestionsDialog">新增</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-eleme" v-on:click="showBatchImportDialog">批量导入试题</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-download" :loading="downloadLoading"
                       v-on:click="downloadQuestionsTemplate">
              {{questionsTemplateTip}}
            </el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>

    <el-row>
      <el-col>
        <div>
          <el-table row-key="id" :data="questionsList" border tooltip-effect="dark" style="width: 100%"
                    v-loading="listLoading" element-loading-text="拼命加载中">
            <el-table-column prop="questionsType" label="题型" width="120" :formatter="formatData"
                             align="center"></el-table-column>
            <el-table-column prop="questionsTitle" label="题目">
              <template slot-scope="scope">
                <div v-html="scope.row.questionsTitle"></div>
              </template>
            </el-table-column>
            <el-table-column prop="answerAnalysis" label="答案解析">
              <template slot-scope="scope">
                <div v-html="scope.row.answerAnalysis"></div>
              </template>
            </el-table-column>
            <el-table-column prop="sortOrder" label="顺序" width="120"></el-table-column>
            <el-table-column label="操作" width="280">
              <template slot-scope="scope">
                <el-button type="primary" size="mini" @click="showEditQuestionsDialog(scope.$index, scope.row)">
                  编辑
                </el-button>
                <el-button type="primary" size="mini" @click="delQuestions(scope.$index, scope.row)">
                  删除
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

    <!-- 选择新增试题类型 -->
    <el-dialog :title="title" :visible.sync="chooseQuestionsTypeFormDialogVisible" width="30%" :append-to-body="true"
               :close-on-click-modal="false">
      <el-row style="margin-left: 8%">
        <el-col style="width: 50%">
          <el-button style="width:180px;height:60px" icon="el-icon-plus" type="primary" @click="handleQuestionsAdd(1)">
            单选题
          </el-button>
        </el-col>
        <el-col style="width: 50%">
          <el-button style="width:180px;height:60px" icon="el-icon-plus" type="primary" @click="handleQuestionsAdd(2)">
            多选题
          </el-button>
        </el-col>
      </el-row>
      <br/>
      <el-row style="margin-left: 8%">
        <el-col style="width: 50%">
          <el-button style="width:180px;height:60px" icon="el-icon-plus" type="primary" @click="handleQuestionsAdd(3)">
            判断题
          </el-button>
        </el-col>
        <el-col style="width: 50%">
          <el-button style="width:180px;height:60px" icon="el-icon-plus" type="primary" @click="handleQuestionsAdd(4)">
            排序题
          </el-button>
        </el-col>
      </el-row>
    </el-dialog>

    <el-dialog :title="title" :visible.sync="importQuestionsFormDialogVisible" width="30%" :append-to-body="true"
               :before-close="handleImportClose" :close-on-click-modal="false">
      <el-row>
        <el-col>
          <el-form label-position="right" :inline="true" ref="importUsersForm" label-width="90px">
            <el-form-item label="选择文件" required>
              <el-upload
                class="upload-demo"
                ref="upload"
                :action="batchImportQuestions"
                :file-list="fileList"
                :on-change="handleChange"
                :on-success="handleSuccess"
                :limit="1"
                accept=".xls,.xlsx"
                :auto-upload="false">
                <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
                <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">
                  上传到服务器
                </el-button>
                <div slot="tip" class="el-upload__tip">只能上传xls/xlsx文件,最多选择一个文件</div>
              </el-upload>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
    </el-dialog>

    <questionsCreateEdit @successCreateOrEditQuestions="successCreateOrEditQuestions"
                         :showCreateOrEditQuestionsDialog.sync="showCreateOrEditQuestionsDialog"
                         ref="createOrEditQuestionsRef"
                         v-if="showCreateOrEditQuestionsDialog"
                         :questionsCode="questionsCode"
                         :questionsType="questionsType" :operationsType="operationsType">
    </questionsCreateEdit>
  </div>
</template>
<script>
  import appConsts from '../../common/appConsts'
  import baseAxios from '../../common/baseAxios'
  import downloadUtils from '../../common/downloadUtils'
  import stringUtils from '../../common/stringUtils'
  import config from '../../config'
  import questionsCreateEdit from './questions-create-edit'

  export default {
    components: {questionsCreateEdit},
    name: 'questions-manage',
    data() {
      return {
        showCreateOrEditQuestionsDialog: false,
        questionsTypes: appConsts.QUESTIONS_TYPE,
        questionsForm: {
          questionsCode: '',
          questionsTitle: '',
          questionsType: null,
          sortOrder: 0,
          answerAnalysis: ''
        },
        questionsList: [],
        questionsType: 1,
        operationsType: 'add',
        questionsCode: '',
        total: 0,
        pageNum: 1,
        pageSize: 10,
        listLoading: false,
        title: '',
        chooseQuestionsTypeFormDialogVisible: false,
        importQuestionsFormDialogVisible: false,
        questionsCreateOrEditDialogVisible: false,
        fileList: [],
        questionsTemplateTip: '导入模板下载',
        downloadLoading: false,
        batchImportQuestions: config.BASE_PATH + config.APP_CONTEXT + config.BATCH_IMPORT_QUESTIONS
      }
    },
    methods: {
      submitUpload() {
        this.$refs.upload.submit()
      },
      handleChange(file, fileList) {
        console.log(file)
      },
      handleSuccess(res, file, fileList) {
        console.log(res)
        this.importQuestionsFormDialogVisible = false
        this.fileList = []
        this.$alert(`本次共成功导入${res.data.questionsList.length}条记录`, '导入结果', {
          confirmButtonText: '确定',
          callback: () => {
            this.findQuestionsList(1)
          }
        })
      },
      formatData(row, column) {
        if (column.property === 'questionsType') {
          return stringUtils.formatTableRowData(row.questionsType, this.questionsTypes)
        }
      },
      showBatchImportDialog() {
        this.title = '批量导入试题'
        this.importQuestionsFormDialogVisible = true
      },
      handleImportClose() {
        this.importQuestionsFormDialogVisible = false
        this.fileList = []
      },
      downloadQuestionsTemplate() {
        let tmp = this.questionsTemplateTip
        downloadUtils.download(config.DOWNLOAD_QUESTIONS_TEMPLATE, "试题导入模板.xls", () => {
          this.questionsTemplateTip = '下载中,请稍候'
          this.downloadLoading = true
        }, () => {
          this.questionsTemplateTip = tmp
          this.downloadLoading = false
        }, (error) => {
          console.error(config.DOWNLOAD_QUESTIONS_TEMPLATE, error)
          this.$message('下载失败,请稍后再试')
        })
      },
      showCreateQuestionsDialog() {
        this.chooseQuestionsTypeFormDialogVisible = true
      },
      showEditQuestionsDialog(index, row) {
        this.handleQuestionsEdit(index, row)
      },
      handleQuestionsAdd(questionsType) {
        this.questionsType = questionsType
        this.operationsType = 'create'
        this.chooseQuestionsTypeFormDialogVisible = false
        this.showCreateOrEditQuestionsDialog = true
      },
      handleQuestionsEdit(index, row) {
        this.questionsType = row.questionsType
        this.operationsType = 'edit'
        this.questionsCode = row.questionsCode
        this.showCreateOrEditQuestionsDialog = true
      },
      successCreateOrEditQuestions() {
        this.$message.success('操作成功')
        this.showCreateOrEditQuestionsDialog = false
        this.findQuestionsList(1)
      },
      findQuestionsList(pageNum) {
        let reqJsonParams = {
          page: {pageNum: pageNum, pageSize: this.pageSize},
          questionsForm: this.questionsForm
        }
        this.listLoading = true
        baseAxios.post(config.FIND_QUESTIONS_LIST, reqJsonParams).then((resJson) => {
          this.questionsList = resJson.data
          this.total = resJson.total
        }).finally(() => {
          this.listLoading = false
        })
      },
      delQuestions(index, row) {
        this.$confirm('确认删除该记录吗?', '提示', {type: 'warning'})
          .then(() => {
            this.listLoading = true
            baseAxios.post(config.DEL_QUESTIONS, {id: row.id}).then(() => {
              this.$message({message: '操作成功', type: 'success'})
              this.findQuestionsList(1)
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
      handleSizeChange(val) {
        this.pageSize = val
        this.findQuestionsList(this.pageNum)
      },
      handleCurrentChange(val) {
        this.pageNum = val
        this.findQuestionsList(this.pageNum)
      },
    },
    mounted() {
      this.findQuestionsList(1)
    }
  }
</script>
