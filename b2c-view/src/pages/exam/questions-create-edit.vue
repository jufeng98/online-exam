<template>
  <div>
    <el-dialog :title="title" v-loading="editLoading" :visible.sync="createOrEditQuestionsFormVisible"
               :close-on-click-modal="false" :fullscreen="true">
      <el-form :model="createOrEditQuestionsForm" :rules="createOrEditQuestionsFormRules"
               ref="createOrEditQuestionsFormRef" label-width="120px"
               class="demo-ruleForm">
        <el-row>
          <el-col style="text-align: left">
            <el-form-item label="题型" prop="questionsType">
              <el-select :disabled="true" v-model="createOrEditQuestionsForm.questionsType" placeholder="全部">
                <el-option v-for="item in questionsTypes" :key="item.value" :label="item.name"
                           :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col style="text-align: left">
            <el-form-item label="题目" prop="questionsTitle" :required="true">
              <ueditor id="questionsTitleUeditor" :config="config" ref="questionsTitleUeditorRef"></ueditor>
              <el-input v-model="createOrEditQuestionsForm.questionsTitle" type="hidden"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-form-item label="分数" prop="questionsScore" style="text-align: left">
              <el-input-number controls-position="right"
                               v-model.number="createOrEditQuestionsForm.questionsScore"></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-form-item label="排序" prop="sortOrder" style="text-align: left">
              <el-input-number controls-position="right"
                               v-model="createOrEditQuestionsForm.sortOrder"></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="text-align: left">
          <el-col>
            <el-form-item
              v-for="(option, index) in optionsValidateForm.options"
              :label="'选项' + String.fromCharCode(index + 65)"
              :key="option.key"
              :prop="'options' + index" :rules="optionRule">
              <el-radio v-if="questionsType === 1 || questionsType === 3"
                        v-model="createOrEditQuestionsForm.radio" :label="index">
                {{''}}
              </el-radio>
              <el-checkbox v-else-if="questionsType === 2"
                           v-model="createOrEditQuestionsForm.selects[index]">
              </el-checkbox>
              <el-input
                v-model="createOrEditQuestionsForm.options[index]"
                style="width: 500px" @blur="setOptionValue(index)"
                :disabled="shouldDisabledOption"></el-input>
              <el-button @click.prevent="removeOption(option)" :disabled="shouldDisabledOption">删除选项</el-button>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col style="width: 800px">
            <el-button @click="addOption" style="margin-bottom: 10px" :disabled="shouldDisabledOption">新增选项</el-button>
          </el-col>
        </el-row>
        <el-row style="text-align: left">
          <el-col>
            <el-form-item label="答案解析" prop="answerAnalysis" :required="true">
              <ueditor id="answerAnalysisUeditor" :config="config" ref="answerAnalysisUeditorRef"></ueditor>
              <el-input v-model="createOrEditQuestionsForm.answerAnalysis" type="hidden"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <div>
              <el-button @click="cancelCreateOrEditQuestions">取消</el-button>
              <el-button type="primary" @click="submitCreateOrEditQuestions" :loading="editLoading">保存</el-button>
            </div>
          </el-col>
        </el-row>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
  import ueditor from '../../components/ueditor'
  import appConsts from '../../common/appConsts'
  import baseAxios from '../../common/baseAxios'
  import config from '../../config'

  export default {
    components: {ueditor},
    name: 'questionsAddEdit',
    props: {
      questionsType: {
        type: Number,
      },
      operationsType: {
        type: String,
        default() {
          return "create"
        },
      },
      questionsCode: {
        type: String,
        default() {
          return ""
        },
      }
    },
    data() {
      return {
        SINGLE_QUESTION_TYPE: 1,
        MULTI_QUESTION_TYPE: 2,
        JUDGE_QUESTION_TYPE: 3,
        shouldDisabledOption: false,
        questionsTypes: appConsts.QUESTIONS_TYPE,
        config: {
          //可以在此处定义工具栏的内容
          // toolbars: [
          //   ['fullscreen', 'undo', 'redo', '|', 'bold', 'italic', 'underline',
          //     '|', 'superscript', 'subscript', '|', 'insertorderedlist', 'insertunorderedlist',
          //     '|', 'fontfamily', 'fontsize', 'justifyleft', 'justifyright', 'justifycenter', 'justifyjustify']
          // ],
          autoHeightEnabled: false,
          autoFloatEnabled: true,
          //初始化编辑器的内容,也可以通过textarea/script给值，看官网例子
          initialContent: '',
          //是否自动清除编辑器初始内容，注意：如果focus属性设置为true,这个也为真，那么编辑器一上来就会触发导致初始化的内容看不到了
          autoClearinitialContent: true,
          initialFrameWidth: null,
          initialFrameHeight: 250,
          BaseUrl: '',
        },
        title: '',
        editLoading: false,
        createOrEditQuestionsFormVisible: true,
        optionRule: {
          required: true, message: '选项不能为空', trigger: 'blur'
        },
        createOrEditQuestionsForm: {
          id: '',
          questionsType: this.questionsType,
          questionsTitle: '',
          answerAnalysis: '',
          questionsScore: 0,
          sortOrder: 0,
          radio: -1,
          selects: [],
          options: [],
        },
        createOrEditQuestionsFormRules: {
          questionsTitle: [
            {required: true, max: 100, message: '请填写题目，字数不超过100个字', trigger: 'blur'}
          ],
          questionsScore: [
            {min: 1, message: '分数不能小于1', type: 'number', trigger: 'change'}
          ],
          answerAnalysis: [
            {required: true, max: 100, message: '请填写答案解析，字数不超过100个字', trigger: 'blur'}
          ],
        },
        optionsValidateForm: {
          options: [],
        },
      }
    },
    methods: {
      removeOption(item) {
        let index = this.optionsValidateForm.options.indexOf(item)
        if (index !== -1) {
          this.optionsValidateForm.options.splice(index, 1)
        }
      },
      addOption() {
        this.optionsValidateForm.options.push({
          value: '',
          key: Math.random()
        })
      },
      resetForm(formName) {
        this.$refs[formName].resetFields()
      },
      cancelCreateOrEditQuestions() {
        this.$confirm('确定取消？', '提示', {}).then(() => {
          this.$emit("update:showCreateOrEditQuestionsDialog", false)
        }).catch(() => {
        })
      },
      async findQuestionsInfo() {
        let reqJsonParams = {
          page: {pageNum: 1, pageSize: 9999},
          questionsForm: {questionsCode: this.questionsCode}
        }
        this.editLoading = true
        let resPromise = await baseAxios.post(config.FIND_QUESTIONS_LIST, reqJsonParams)
        this.editLoading = false
        return resPromise
      },
      async getOptionsList() {
        let reqJsonParams = {
          questionsCode: this.questionsCode,
          questionsType: this.questionsType,
        }
        let resPromise = await baseAxios.post(config.FIND_OPTIONS_LIST, reqJsonParams)
        return resPromise
      },
      submitCreateOrEditQuestions() {
        this.createOrEditQuestionsForm.questionsTitle = this.$refs.questionsTitleUeditorRef.getContentTxt()
        this.createOrEditQuestionsForm.answerAnalysis = this.$refs.answerAnalysisUeditorRef.getContentTxt()
        this.$refs.createOrEditQuestionsFormRef.validate((valid) => {
          if (!valid) {
            this.$message({message: '信息有误', type: 'warning'})
            return
          }
          if (this.questionsType === this.SINGLE_QUESTION_TYPE || this.questionsType === this.JUDGE_QUESTION_TYPE) {
            if (this.createOrEditQuestionsForm.radio === -1) {
              this.$message({message: '请为试题选择选项答案', type: 'warning'})
              return
            }
          }
          if (this.questionsType === this.MULTI_QUESTION_TYPE) {
            let length = this.createOrEditQuestionsForm.selects.filter((item) => {
              return item
            }).length
            if (length < 2) {
              this.$message({message: '请至少选择两个以上选项答案', type: 'warning'})
              return
            }
          }
          this.createOrEditQuestionsForm.questionsTitle = this.$refs.questionsTitleUeditorRef.getContent()
          this.createOrEditQuestionsForm.answerAnalysis = this.$refs.answerAnalysisUeditorRef.getContent()
          this.editLoading = true
          let reqJsonParams = {
            questionsForm: this.createOrEditQuestionsForm,
          }
          let url = this.operationsType === 'create' ? config.CREATE_QUESTIONS : config.EDIT_QUESTIONS
          baseAxios.post(url, reqJsonParams).then(() => {
            this.$refs.questionsTitleUeditorRef.destroy()
            this.$refs.answerAnalysisUeditorRef.destroy()
            this.$emit("successCreateOrEditQuestions")
          }).finally(() => {
            this.editLoading = false
          })
        })
      },
      setOptionValue(index) {
        this.$set(this.createOrEditQuestionsForm, 'options' + index, this.createOrEditQuestionsForm.options[index])
      },
      initOptions(optionNum) {
        if (this.questionsType === this.JUDGE_QUESTION_TYPE) {
          // 判断题
          optionNum = 2
          this.shouldDisabledOption = true
        }
        this.optionsValidateForm.options = []
        this.createOrEditQuestionsForm.selects = []
        for (let i = 0; i < optionNum; i++) {
          this.optionsValidateForm.options.push({
            value: '',
            radio: '',
            key: Math.random(),
          })
          this.$set(this.createOrEditQuestionsForm.selects, i, false)
          this.$set(this.createOrEditQuestionsFormRules, 'options' + i, [
            {
              validator: (rule, value, callback) => {
                if (!value) {
                  return callback(new Error('选项不能为空'))
                }
                if (value.length >= 100) {
                  return callback(new Error('每个选项字数不超过100个字'))
                }
                return callback()
              },
              trigger: 'blur'
            }
          ])
          this.$set(this.createOrEditQuestionsForm, 'options' + i, '')
        }
        if (this.questionsType === this.JUDGE_QUESTION_TYPE) {
          this.$set(this.createOrEditQuestionsForm.options, 0, '对')
          this.$set(this.createOrEditQuestionsForm.options, 1, '错')
          this.$set(this.createOrEditQuestionsForm, 'options0', '对')
          this.$set(this.createOrEditQuestionsForm, 'options1', '错')
        }
      }
    },
    mounted() {
      window.vue = this
      if (this.operationsType === "create") {
        this.title = "新增试题"
        let optionNum = 4
        this.initOptions(optionNum)
      } else if (this.operationsType === "edit") {
        this.title = "编辑试题"
        let resPromise = this.findQuestionsInfo()
        resPromise.then((resJson) => {
          this.createOrEditQuestionsForm = Object.assign({}, resJson.data[0])
          setTimeout(() => {
            this.$refs.questionsTitleUeditorRef.setContent(this.createOrEditQuestionsForm.questionsTitle)
            this.$refs.answerAnalysisUeditorRef.setContent(this.createOrEditQuestionsForm.answerAnalysis)
          }, 2000)
        })
        resPromise = this.getOptionsList()
        resPromise.then((resJson) => {
          this.createOrEditQuestionsForm.radio = resJson.data.radio
          this.createOrEditQuestionsForm.selects = resJson.data.selects
          this.createOrEditQuestionsForm.options = resJson.data.options
          let optionNum = resJson.data.options.length
          for (let i = 0; i < resJson.data.options.length; i++) {
            this.$set(this.createOrEditQuestionsForm, 'options' + i, resJson.data.options[i])
          }
          this.initOptions(optionNum)
        })
      }
    }

  }
</script>
