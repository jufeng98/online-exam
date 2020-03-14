<template>
  <div>
    <el-row>
      <el-col style="text-align: left">
        <el-form :inline="true" :model="sectionsForm" ref="sectionsFormRef">
          <el-form-item label="章节名称" prop="sectionsName">
            <el-input v-model="sectionsForm.sectionsName" placeholder="支持模糊搜索"></el-input>
          </el-form-item>
          <el-form-item label="所属主题" prop="topicsCode">
            <el-select v-model="sectionsForm.topicsCode" clearable>
              <el-option
                v-for="item in topicsList"
                :key="item.topicsCode"
                :label="item.topicsName"
                :value="item.topicsCode">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="findSectionsList(1)">查询</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-warning" @click="resetForm('sectionsFormRef')">重置</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-plus" @click="showCreateSectionsDialog">新增</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>

    <el-row>
      <el-col>
        <div>
          <el-table :data="sectionsList" highlight-current-row v-loading="loading" fit>
            <el-table-column type="index" width="60px"></el-table-column>
            <el-table-column prop="sectionsName" label="章节名称"></el-table-column>
            <el-table-column prop="topicsCode" :formatter="formatData" label="所属主题"></el-table-column>
            <el-table-column label="章节封面">
              <template slot-scope="scope">
                <img style="width:100px;height:100px" :src="`${base64Prefix}${scope.row.sectionsCoverImage}`"/>
              </template>
            </el-table-column>
            <el-table-column prop="sortOrder" label="顺序"></el-table-column>
            <el-table-column label="操作" width="180">
              <template slot-scope="scope">
                <el-button type="primary" size="mini" @click="showEditSectionsDialog(scope.$index, scope.row)">
                  编辑
                </el-button>
                <el-button type="primary" size="mini" @click="delSections(scope.$index, scope.row)">删除</el-button>
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
  import config from '../../config'
  import commonUtils from '../../common/commonUtils'
  import addOrEditSections from './addOrEditSections'

  export default {
    data() {
      return {
        base64Prefix: appConsts.BASE64_JPG_PREFIX,
        sectionsForm: {
          sectionsName: '',
          topicsCode: ''
        },
        sectionsList: [],
        title: '',
        total: 0,
        pageNum: 1,
        pageSize: 10,
        loading: false,
        topicsList: [],
        topicsMap: new Map(),
      }
    },
    methods: {
      handleSizeChange(val) {
        this.pageSize = val
        this.findSectionsList(this.pageNum)
      },
      handleCurrentChange(val) {
        this.pageNum = val
        this.findSectionsList(this.pageNum)
      },
      formatData(row, column) {
        if (column.property === 'topicsCode') {
          return this.topicsMap.get(row.topicsCode)
        }
      },
      findSectionsList(pageNum) {
        let reqJsonParams = {
          page: {pageNum: pageNum, pageSize: this.pageSize},
          sectionsForm: this.sectionsForm
        }
        this.loading = true
        baseAxios.post(config.FIND_SECTIONS_LIST, reqJsonParams).then((resJson) => {
          this.sectionsList = resJson.data
          this.total = resJson.total
        }).finally(() => {
          this.loading = false
        })
      },
      findTopicsList() {
        let reqJsonParams = {
          page: {pageNum: 1, pageSize: 99999, orderBy: 'create_time desc'},
          topicsForm: {}
        }
        baseAxios.post(config.FIND_TOPICS_LIST, reqJsonParams).then((resJson) => {
          this.topicsList = resJson.data
          for (let i = 0; i < this.topicsList.length; i++) {
            this.topicsMap.set(this.topicsList[i].topicsCode, this.topicsList[i].topicsName)
          }
          this.findSectionsList(1)
        })
      },
      showCreateSectionsDialog() {
        this.openDialog('新增章节', Object.assign({}, {createSections: true}))
      },

      showEditSectionsDialog(index, row) {
        this.openDialog('编辑章节', Object.assign({}, row, {createSections: false}))
      },
      openDialog(title, data) {
        let dimension = commonUtils.getDimensionFromPercent(60, 80)
        this.$dlg.modal(addOrEditSections, {
          width: dimension.width,
          height: dimension.height,
          title: title,
          params: {
            sections: data
          },
          maxButton: false,
          callback: data => {
            if (data.action !== 'success') {
              return
            }
            this.$message({message: '操作成功', type: 'success'})
            this.findTopicsList()
          }
        })
      },
      delSections(index, row) {
        this.$confirm('确认删除该记录吗?', '提示', {type: 'warning'})
          .then(() => {
            this.loading = true
            baseAxios.post(config.DEL_SECTIONS, {id: row.id}).then(() => {
              this.$message({message: '操作成功', type: 'success'})
              this.findSectionsList(1)
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
    },
    mounted() {
      window.vue = this
      this.findTopicsList()
    }
  }
</script>
<style scoped>

</style>
