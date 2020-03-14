<template>
  <div>
    <el-row>
      <el-col style="text-align: left">
        <el-form :inline="true" :model="topicsForm" ref="topicsFormRef">
          <el-form-item label="主题名称" prop="topicsName">
            <el-input v-model="topicsForm.topicsName" placeholder="支持模糊搜索"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="findTopicsList(1)">查询</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-warning" @click="resetForm('topicsFormRef')">重置</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-plus" @click="showCreateTopicsDialog">新增</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>

    <el-row>
      <el-col>
        <div>
          <el-table :data="topicsList" :height="tableHeight" highlight-current-row v-loading="loading" fit>
            <el-table-column type="index" width="60px"></el-table-column>
            <el-table-column prop="topicsName" label="主题名称"></el-table-column>
            <el-table-column prop="topicsType" :formatter="formatData" label="主题类型"></el-table-column>
            <el-table-column label="主题封面">
              <template slot-scope="scope">
                <img style="width:100px;height:100px" :src="`${base64Prefix}${scope.row.topicsCoverImage}`"/>
              </template>
            </el-table-column>
            <el-table-column prop="examsCode" :formatter="formatData" label="关联考试"></el-table-column>
            <el-table-column label="操作" width="180">
              <template slot-scope="scope">
                <el-button type="primary" size="mini" @click="showEditTopicsDialog(scope.$index, scope.row)">
                  编辑
                </el-button>
                <el-button type="primary" size="mini" @click="delTopics(scope.$index, scope.row)">删除</el-button>
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
  </div>
</template>

<script>
  import baseAxios from '../../common/baseAxios'
  import appConsts from '../../common/appConsts'
  import stringUtils from '../../common/stringUtils'
  import config from '../../config'
  import addOrEditTopic from "./addOrEditTopic"
  import commonUtils from "../../common/commonUtils";

  export default {
    data() {
      return {
        base64Prefix: appConsts.BASE64_JPG_PREFIX,
        topicsTypes: appConsts.TOPICS_TYPE,
        topicsForm: {
          topicsName: ''
        },
        topicsList: [],
        total: 0,
        pageNum: 1,
        pageSize: 10,
        tableHeight:null,
        loading: false,
        createOrEditTopicsForm: {
          topicsName: '',
          topicsCoverImage: null,
          examsCode: '',
          topicsType: '',
        },
        examsList: [],
        examsMap: new Map(),
      }
    },
    methods: {
      formatData(row, column) {
        if (column.property === 'examsCode') {
          return this.examsMap.get(row.examsCode)
        }
        if (column.property === 'topicsType') {
          return stringUtils.formatTableRowData(row.topicsType, this.topicsTypes)
        }
      },
      handleSizeChange(val) {
        this.pageSize = val
        this.findTopicsList(this.pageNum)
      },
      handleCurrentChange(val) {
        this.pageNum = val
        this.findTopicsList(this.pageNum)
      },
      findTopicsList(pageNum) {
        let reqJsonParams = {
          page: {pageNum: pageNum, pageSize: this.pageSize},
          topicsForm: this.topicsForm
        }
        this.loading = true
        baseAxios.post(config.FIND_TOPICS_LIST, reqJsonParams).then((resJson) => {
          this.topicsList = resJson.data
          this.total = resJson.total
        }).finally(() => {
          this.loading = false
        })
      },
      showCreateTopicsDialog() {
        this.openDialog('新增主题', Object.assign({}, {createTopics: true}))
      },
      showEditTopicsDialog(index, row) {
        this.openDialog('编辑主题', Object.assign({}, row, {createTopics: false}))
      },
      openDialog(title, data) {
        this.$store.commit('init', data)
        let dimension = commonUtils.getDimensionFromPercent(60, 80)
        this.$dlg.modal(addOrEditTopic, {
          width: dimension.width,
          height: dimension.height,
          title: title,
          maxButton: false,
          callback: data => {
            if (data.action !== 'success') {
              return
            }
            this.$message({message: '操作成功', type: 'success'})
            this.findTopicsList(1)
          }
        })
      },
      findExamsList() {
        let reqJsonParams = {
          page: {pageNum: 1, pageSize: 99999},
          examsForm: {}
        }
        baseAxios.post(config.FIND_EXAMS_LIST, reqJsonParams).then((resJson) => {
          this.examsList = resJson.data
          for (let i = 0; i < this.examsList.length; i++) {
            this.examsMap.set(this.examsList[i].examsCode, this.examsList[i].examsName)
          }
          this.findTopicsList(1)
        })
      },
      delTopics(index, row) {
        this.$confirm('确认删除该记录吗?', '提示', {type: 'warning'})
          .then(() => {
            this.loading = true
            baseAxios.post(config.DEL_TOPICS, {id: row.id}).then(() => {
              this.$message({message: '操作成功', type: 'success'})
              this.findTopicsList(1)
            }).finally(() => {
              this.loading = false
            })
          })
          .catch(() => {
          })
      },
      resetForm(formName) {
        this.$refs[formName].resetFields()
      },
      recalculateTableHeight() {
        // 表格高度自适应
        let frameHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight
        let otherHeight = 130
        this.tableHeight = frameHeight - otherHeight
      },
    },
    mounted() {
      this.recalculateTableHeight()
      window.onresize = this.recalculateTableHeight
      this.findExamsList()
      window.vue = this
    }
  }
</script>
<style scoped>

</style>
