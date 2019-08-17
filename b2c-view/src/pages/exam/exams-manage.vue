<template>
  <div>
    <el-row>
      <el-col style="text-align: left">
        <el-form :inline="true" :model="examsForm" ref="examsFormRef">
          <el-form-item label="考试名称" prop="examsName">
            <el-input v-model="examsForm.examsName" placeholder="支持模糊搜索"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="findExamsList(1)">查询</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-warning" @click="resetForm('examsFormRef')">重置</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-plus" @click="showCreateExamsDialog">新增</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>

    <el-row>
      <el-col>
        <div>
          <el-table :data="examsList" highlight-current-row v-loading="listLoading" fit>
            <el-table-column type="index" width="60px"></el-table-column>
            <el-table-column prop="examsName" label="考试名称"></el-table-column>
            <el-table-column prop="examsDesc" label="考试描述"></el-table-column>
            <el-table-column prop="certsId" :formatter="formatData" label="关联证书"></el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button type="primary" size="mini"
                           @click="showAddOrEditAssociateQuestionsDialog(scope.$index, scope.row)">
                  添加/修改关联题目
                </el-button>
                <el-button type="primary" size="mini" @click="showEditExamsDialog(scope.$index, scope.row)">
                  编辑
                </el-button>
                <el-button type="primary" size="mini" @click="delExams(scope.$index, scope.row)">删除</el-button>
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

    <el-dialog :title="title" :visible.sync="createOrEditExamsDialogVisible">
      <el-form :model="createOrEditExamsForm" label-width="90px" :rules="createOrEditExamsFormRules"
               ref="createOrEditExamsFormRef">
        <el-form-item label="考试名称" prop="examsName">
          <el-input v-model="createOrEditExamsForm.examsName" auto-complete="off"
                    :maxlength="32"></el-input>
        </el-form-item>
        <el-form-item label="考试描述" prop="examsDesc">
          <el-input type="textarea" v-model="createOrEditExamsForm.examsDesc" auto-complete="off"
                    :maxlength="100"></el-input>
        </el-form-item>
        <el-form-item label="关联证书" prop="certsId" style="text-align: left">
          <el-select v-model="createOrEditExamsForm.certsId">
            <el-option
              v-for="item in certsList"
              :key="item.id"
              :label="item.certsName"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div>
        <el-button @click.native="createOrEditExamsDialogVisible = false">取消</el-button>
        <el-button type="primary" @click.native="submitCreateOrEditExams" :loading="addLoading">提交</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="title" :visible.sync="addOrEditAssociateQuestionsDialogVisible">
      <el-table :data="questionsList" @selection-change="questionsSelectionChange" highlight-current-row
                v-loading="listLoading" fit ref="addOrEditAssociateQuestionsTableRef">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="questionsType" label="题型" width="120" :formatter="formatData"
                         align="center"></el-table-column>
        <el-table-column prop="questionsTitle" label="题目">
          <template slot-scope="scope">
            <div v-html="scope.row.questionsTitle"></div>
          </template>
        </el-table-column>
      </el-table>
      <br/>
      <div>
        <el-button @click.native="addOrEditAssociateQuestionsDialogVisible = false">取消</el-button>
        <el-button type="primary" @click.native="submitAddOrEditAssociateQuestions" :loading="addLoading">提交</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import baseAxios from '../../common/baseAxios'
  import config from '../../config'
  import stringUtils from '../../common/stringUtils'
  import appConsts from '../../common/appConsts'

  export default {
    data() {
      return {
        questionsTypes: appConsts.QUESTIONS_TYPE,
        examsForm: {
          examsName: '',
        },
        examsList: [],
        title: '',
        total: 0,
        pageNum: 1,
        pageSize: 10,
        listLoading: false,
        createOrEditExamsForm: {
          id: '',
          examsName: '',
          examsDesc: '',
          certsId: null,
        },
        createExams: true,
        createOrEditExamsDialogVisible: false,
        addOrEditAssociateQuestionsDialogVisible: false,
        addLoading: false,
        certsList: [],
        certsMap: new Map(),
        createOrEditExamsFormRules: {
          examsName: [
            {required: true, message: '请输入考试名称', trigger: 'blur'}
          ],
          certsId: [
            {required: true, message: '请选择关联证书', trigger: 'blur'}
          ],
        },
        questionsList: [],
        currentChooseExams: {
          examsCode: ''
        },
        currentChooseQuestions: [],
      }
    },
    methods: {
      handleSizeChange(val) {
        this.pageSize = val
        this.findExamsList(this.pageNum)
      },
      handleCurrentChange(val) {
        this.pageNum = val
        this.findExamsList(this.pageNum)
      },
      formatData(row, column) {
        if (column.property === 'certsId') {
          return this.certsMap.get(row.certsId)
        } else if (column.property === 'questionsType') {
          return stringUtils.formatTableRowData(row.questionsType, this.questionsTypes)
        }
      },
      findExamsList(pageNum) {
        let reqJsonParams = {
          page: {pageNum: pageNum, pageSize: this.pageSize},
          examsForm: this.examsForm
        }
        this.listLoading = true
        baseAxios.post(config.FIND_EXAMS_LIST, reqJsonParams).then((resJson) => {
          this.examsList = resJson.data
          this.total = resJson.total
        }).finally(() => {
          this.listLoading = false
        })
      },
      findCertsList() {
        let reqJsonParams = {
          page: {pageNum: 1, pageSize: 99999},
          certsForm: {}
        }
        baseAxios.post(config.FIND_CERTS_LIST, reqJsonParams).then((resJson) => {
          this.certsList = resJson.data
          for (let i = 0; i < this.certsList.length; i++) {
            this.certsMap.set(this.certsList[i].id, this.certsList[i].certsName)
          }
          this.findExamsList(1)
        })
      },
      findQuestionsList() {
        let reqJsonParams = {
          page: {pageNum: 1, pageSize: 99999},
          questionsForm: {}
        }
        this.listLoading = true
        baseAxios.post(config.FIND_QUESTIONS_LIST, reqJsonParams).then((resJson) => {
          this.questionsList = resJson.data
          this.total = resJson.total
          this.findAssociateQuestions()
        }).finally(() => {
          this.listLoading = false
        })
      },
      findAssociateQuestions() {
        let examsCode = this.currentChooseExams.examsCode
        baseAxios.post(config.FIND_ASSOCIATE_QUESTIONS, {examsCode}).then((resJson) => {
          let examsQuestionsCodes = resJson.data.examsQuestionsCodes
          let set = new Set([...examsQuestionsCodes])
          let rows = this.$refs.addOrEditAssociateQuestionsTableRef.data
          for (let i = 0; i < rows.length; i++) {
            if (set.has(rows[i].questionsCode)) {
              this.$refs.addOrEditAssociateQuestionsTableRef.toggleRowSelection(rows[i], true)
            }
          }
        })
      },
      questionsSelectionChange(select) {
        this.currentChooseQuestions = select
      },
      showCreateExamsDialog() {
        this.title = '新增考试'
        this.createExams = true
        this.createOrEditExamsDialogVisible = true
        this.createOrEditExamsForm = {}
      },
      showAddOrEditAssociateQuestionsDialog(index, row) {
        this.addOrEditAssociateQuestionsDialogVisible = true
        this.currentChooseExams = row
        this.findQuestionsList()
      },
      showEditExamsDialog(index, row) {
        this.title = '编辑考试'
        this.createExams = false
        this.createOrEditExamsDialogVisible = true
        this.createOrEditExamsForm = Object.assign({}, row)
      },
      submitCreateOrEditExams() {
        this.$refs.createOrEditExamsFormRef.validate((valid) => {
          if (!valid) {
            this.$message({message: '信息有误', type: 'warning'})
            return
          }
          this.addLoading = true
          let reqJsonParams = {
            examsForm: this.createOrEditExamsForm,
          }
          let url = this.createExams ? config.CREATE_EXAMS : config.EDIT_EXAMS
          baseAxios.post(url, reqJsonParams).then(() => {
            this.$message({message: '操作成功', type: 'success'})
          }).finally(() => {
            this.addLoading = false
            this.createOrEditExamsDialogVisible = false
            this.findExamsList(this.pageNum)
          })
        })
      },
      submitAddOrEditAssociateQuestions() {
        let examsCode = this.currentChooseExams.examsCode
        let questionsCodes = this.currentChooseQuestions.map((item) => {
          return item.questionsCode
        })
        let reqJsonParams = {
          examsCode, questionsCodes
        }
        if (questionsCodes.length === 0) {
          this.$message.warning('请至少选择一条题目')
          return
        }
        baseAxios.post(config.ADD_OR_EDIT_ASSOCIATE_QUESTIONS, reqJsonParams).then(() => {
          this.$message({message: '操作成功', type: 'success'})
          this.addOrEditAssociateQuestionsDialogVisible = false
        });
      },
      delExams(index, row) {
        this.$confirm('确认删除该记录吗?', '提示', {type: 'warning'})
          .then(() => {
            this.listLoading = true
            baseAxios.post(config.DEL_EXAMS, {id: row.id}).then(() => {
              this.$message({message: '操作成功', type: 'success'})
              this.findExamsList(1)
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
      this.findCertsList()
      window.vue = this
    }
  }
</script>
