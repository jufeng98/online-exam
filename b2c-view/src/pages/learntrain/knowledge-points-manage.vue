<template>
  <div>
    <el-row>
      <el-col style="text-align: left">
        <el-form :inline="true" :model="knowledgePointsForm" ref="knowledgePointsFormRef">
          <el-form-item label="所属知识" prop="knowledgesCode">
            <el-select v-model="knowledgePointsForm.knowledgesCode" clearable>
              <el-option
                v-for="item in knowledgesList"
                :key="item.knowledgesCode"
                :label="item.knowledgesName"
                :value="item.knowledgesCode">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="findKnowledgePointsList(1)">查询</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-warning" @click="resetForm('knowledgePointsFormRef')">重置</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-plus" @click="showCreateKnowledgePointsDialog">新增</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>

    <el-row>
      <el-col>
        <div>
          <el-table :data="knowledgePointsList" highlight-current-row v-loading="listLoading" fit>
            <el-table-column type="index" width="60px"></el-table-column>
            <el-table-column prop="knowledgesCode" :formatter="formatData" label="所属知识" width="180px"></el-table-column>
            <el-table-column prop="knowledgePointsContent" label="知识点内容">
              <template slot-scope="scope">
                <div v-html="scope.row.knowledgePointsContent"></div>
              </template>
            </el-table-column>
            <el-table-column prop="questionsCode" label="关联试题" width="180px">
              <template slot-scope="scope">
                <div v-html="formatQuestionsCode(scope.row.questionsCode)"></div>
              </template>
            </el-table-column>
            <el-table-column prop="sortOrder" label="顺序" width="120px"></el-table-column>
            <el-table-column label="操作" width="180">
              <template slot-scope="scope">
                <el-button type="primary" size="mini" @click="showEditKnowledgePointsDialog(scope.$index, scope.row)">
                  编辑
                </el-button>
                <el-button type="primary" size="mini" @click="delKnowledgePoints(scope.$index, scope.row)">
                  删除
                </el-button>
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

    <el-dialog :fullscreen="true" :title="title" :close-on-click-modal="false" @opened="fillUeditorContent"
               :visible.sync="createOrEditKnowledgePointsDialogVisible" @close="resetUeditorAndClearValidate">
      <el-form :model="createOrEditKnowledgePointsForm" label-width="150px"
               :rules="createOrEditKnowledgePointsFormRules"
               ref="createOrEditKnowledgePointsFormRef">
        <el-form-item label="所属知识" prop="knowledgesCode" style="text-align: left">
          <el-select v-model="createOrEditKnowledgePointsForm.knowledgesCode">
            <el-option
              v-for="item in knowledgesList"
              :key="item.knowledgesCode"
              :label="item.knowledgesName"
              :value="item.knowledgesCode">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="关联题目" prop="questionsCode" style="text-align: left">
          <el-select v-model="createOrEditKnowledgePointsForm.questionsCode">
            <el-option
              v-for="item in questionsList"
              :key="item.questionsCode"
              :label="item.questionsTitle"
              :value="item.questionsCode">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="排序" prop="sortOrder" style="text-align: left">
          <el-input-number controls-position="right"
                           v-model="createOrEditKnowledgePointsForm.sortOrder"></el-input-number>
        </el-form-item>
        <el-form-item style="text-align:left" label="知识点内容" prop="knowledgePointsContent">
          <ueditor id="knowledgePointsUeditor" :config="config" ref="knowledgePointsUeditorRef"></ueditor>
          <el-input v-model="createOrEditKnowledgePointsForm.knowledgePointsContent" type="hidden"></el-input>
        </el-form-item>
      </el-form>
      <div>
        <el-button @click.native="createOrEditKnowledgePointsDialogVisible = false">取消</el-button>
        <el-button type="primary" @click.native="submitCreateOrEditKnowledgePoints" :loading="addLoading">提交</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import baseAxios from '../../common/baseAxios'
  import config from '../../config'
  import ueditor from '../../components/ueditor'

  export default {
    name: 'knowledgePointsManage',
    components: {ueditor},
    data() {
      return {
        config: {
          //可以在此处定义工具栏的内容
          // toolbars: [
          //  ['fullscreen', 'undo', 'redo','|','bold', 'italic', 'underline',
          //  '|','superscript','subscript','|', 'insertorderedlist', 'insertunorderedlist',
          //  '|','fontfamily','fontsize','justifyleft','justifyright','justifycenter','justifyjustify']
          // ],
          autoHeightEnabled: false,
          autoFloatEnabled: true,
          //初始化编辑器的内容,也可以通过textarea/script给值，看官网例子
          initialContent: '',
          //是否自动清除编辑器初始内容，注意：如果focus属性设置为true,这个也为真，那么编辑器一上来就会触发导致初始化的内容看不到了
          autoClearinitialContent: true,
          initialFrameWidth: null,
          initialFrameHeight: 450,
          BaseUrl: '',
        },
        knowledgePointsForm: {
          knowledgesCode: ''
        },
        knowledgePointsList: [],
        title: '',
        total: 0,
        pageNum: 1,
        pageSize: 10,
        listLoading: false,
        createOrEditKnowledgePointsForm: {
          knowledgesCode: '',
          questionsCode: '',
          sortOrder: 1,
          knowledgePointsContent: ''
        },
        createKnowledgePoints: true,
        createOrEditKnowledgePointsDialogVisible: false,
        addLoading: false,
        knowledgesList: [],
        questionsList: [],
        knowledgesMap: new Map(),
        questionsMap: new Map(),
        createOrEditKnowledgePointsFormRules: {
          knowledgesCode: [
            {required: true, message: '请选择所属知识', trigger: 'blur'}
          ],
          knowledgePointsContent: [
            {required: true, message: '知识点内容不能为空', trigger: 'blur'}
          ],
        },
      }
    },
    methods: {
      handleSizeChange(val) {
        this.pageSize = val
        this.findKnowledgePointsList(this.pageNum)
      },
      handleCurrentChange(val) {
        this.pageNum = val
        this.findKnowledgePointsList(this.pageNum)
      },
      formatData(row, column) {
        if (column.property === 'knowledgesCode') {
          return this.knowledgesMap.get(row.knowledgesCode)
        }
      },
      formatQuestionsCode(questionsCode) {
        return this.questionsMap.get(questionsCode)
      },
      findKnowledgePointsList(pageNum) {
        let reqJsonParams = {
          page: {pageNum: pageNum, pageSize: this.pageSize},
          knowledgePointsForm: this.knowledgePointsForm
        }
        this.listLoading = true
        baseAxios.post(config.FIND_KNOWLEDGE_POINTS_LIST, reqJsonParams).then((resJson) => {
          this.knowledgePointsList = resJson.data
          this.total = resJson.total
        }).finally(() => {
          this.listLoading = false
        })
      },
      findKnowledgesList() {
        let reqJsonParams = {
          page: {pageNum: 1, pageSize: 99999},
          knowledgesForm: {}
        }
        return baseAxios.post(config.FIND_KNOWLEDGES_LIST, reqJsonParams)
      },
      findQuestionsList() {
        let reqJsonParams = {
          page: {pageNum: 1, pageSize: 99999},
          questionsForm: {}
        }
        return baseAxios.post(config.FIND_QUESTIONS_LIST, reqJsonParams)
      },
      findNecessaryList() {
        baseAxios.all([this.findKnowledgesList(), this.findQuestionsList()])
          .then(baseAxios.spread((res1, res2) => {
            this.knowledgesList = res1.data
            for (let i = 0; i < this.knowledgesList.length; i++) {
              this.knowledgesMap.set(this.knowledgesList[i].knowledgesCode, this.knowledgesList[i].knowledgesName)
            }
            this.questionsList = res2.data
            for (let i = 0; i < this.questionsList.length; i++) {
              this.questionsMap.set(this.questionsList[i].questionsCode, this.questionsList[i].questionsTitle)
            }
            this.findKnowledgePointsList(1)
          }))
      },
      resetUeditorAndClearValidate() {
        this.$refs.createOrEditKnowledgePointsFormRef.clearValidate()
        this.$refs.knowledgePointsUeditorRef.setContent('')
      },
      showCreateKnowledgePointsDialog() {
        this.title = '新增知识点'
        this.createKnowledgePoints = true
        this.createOrEditKnowledgePointsDialogVisible = true
        this.createOrEditKnowledgePointsForm = {}
      },
      showEditKnowledgePointsDialog(index, row) {
        this.title = '编辑知识点'
        this.createKnowledgePoints = false
        this.createOrEditKnowledgePointsDialogVisible = true
        this.createOrEditKnowledgePointsForm = Object.assign({}, row)
      },
      fillUeditorContent() {
        if (!this.createKnowledgePoints) {
          this.$refs.knowledgePointsUeditorRef.setContent(this.createOrEditKnowledgePointsForm.knowledgePointsContent);
        }
      },
      submitCreateOrEditKnowledgePoints() {
        this.createOrEditKnowledgePointsForm.knowledgePointsContent = this.$refs.knowledgePointsUeditorRef.getContentTxt()
        this.$refs.createOrEditKnowledgePointsFormRef.validate((valid) => {
          if (!valid) {
            this.$message({message: '信息有误', type: 'warning'})
            return
          }
          this.createOrEditKnowledgePointsForm.knowledgePointsContent = this.$refs.knowledgePointsUeditorRef.getContent()
          this.addLoading = true
          let reqJsonParams = {
            knowledgePointsForm: this.createOrEditKnowledgePointsForm,
          }
          let url = this.createKnowledgePoints ? config.CREATE_KNOWLEDGE_POINTS : config.EDIT_KNOWLEDGE_POINTS
          baseAxios.post(url, reqJsonParams).then((resJson) => {
          }).finally(() => {
            this.addLoading = false
            this.createOrEditKnowledgePointsDialogVisible = false
            this.findKnowledgePointsList(this.pageNum)
          })
        })
      },
      delKnowledgePoints(index, row) {
        this.$confirm('确认删除该记录吗?', '提示', {type: 'warning'})
          .then(() => {
            this.listLoading = true
            baseAxios.post(config.DEL_KNOWLEDGE_POINTS, {id: row.id}).then(() => {
              this.$message({message: '操作成功', type: 'success'})
              this.findKnowledgePointsList(1)
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
      this.findNecessaryList()
      window.vue = this
    }
  }
</script>
