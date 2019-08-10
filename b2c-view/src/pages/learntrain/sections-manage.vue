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
          <el-table :data="sectionsList" highlight-current-row v-loading="listLoading" fit>
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

    <el-dialog :title="title" :visible.sync="createOrEditSectionsDialogVisible">
      <el-form :model="createOrEditSectionsForm" label-width="80px" :rules="createOrEditSectionsFormRules"
               ref="createOrEditSectionsFormRef">
        <el-form-item label="章节名称" prop="sectionsName">
          <el-input v-model="createOrEditSectionsForm.sectionsName" auto-complete="off"
                    :maxlength="30"></el-input>
        </el-form-item>
        <el-form-item label="章节封面" prop="sectionsCoverImage" style="text-align: left">
          <el-upload
            style="margin:0px;height:200px;"
            class="avatar-uploader"
            action=""
            :http-request="showImage"
            :show-file-list="false"
            :before-upload="beforeAvatarUpload">
            <img v-if="uploadShowImageUrl" :src="uploadShowImageUrl" class="avatar">
            <i v-else class="el-upload el-icon-plus avatar-uploader-icon"></i>
            <div slot="tip" style="color: red" class="el-upload__tip">
              只能上传jpg文件，且不超过3MB
            </div>
          </el-upload>
          <el-input type="hidden" style="width:0px;height:0px;"
                    v-model="createOrEditSectionsForm.sectionsCoverImage">
          </el-input>
        </el-form-item>
        <el-form-item label="所属主题" prop="topicsCode" style="text-align: left">
          <el-select v-model="createOrEditSectionsForm.topicsCode">
            <el-option
              v-for="item in topicsList"
              :key="item.topicsCode"
              :label="item.topicsName"
              :value="item.topicsCode">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="排序" prop="sortOrder" style="text-align: left">
          <el-input-number controls-position="right" v-model="createOrEditSectionsForm.sortOrder"></el-input-number>
        </el-form-item>
      </el-form>
      <div>
        <el-button @click.native="createOrEditSectionsDialogVisible = false">取消</el-button>
        <el-button type="primary" @click.native="submitCreateOrEditSections" :loading="addLoading">提交</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import baseAxios from '../../common/baseAxios'
  import appConsts from '../../common/appConsts'
  import config from '../../config'

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
        listLoading: false,
        createOrEditSectionsForm: {
          sectionsName: '',
          sectionsCoverImage: null,
          topicsCode: '',
          sortOrder: 1,
        },
        uploadShowImageUrl: '',
        createSections: true,
        createOrEditSectionsDialogVisible: false,
        addLoading: false,
        topicsList: [],
        topicsMap: new Map(),
        createOrEditSectionsFormRules: {
          sectionsName: [
            {required: true, message: '请输入章节名称', trigger: 'blur'}
          ],
          sectionsCoverImage: [
            {required: true, message: '请选择章节封面', trigger: 'blur'}
          ],
          topicsCode: [
            {required: true, message: '请选择所属主题', trigger: 'blur'}
          ],
        },
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
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg'
        const isLt3M = file.size / 1024 / 1024 < 3

        if (!isJPG) {
          this.$message.error('上传图片只能是 JPG 格式!')
        }
        if (!isLt3M) {
          this.$message.error('上传图片大小不能超过 3MB!')
        }
        return isJPG && isLt3M
      },
      showImage(obj) {
        let file = obj.file
        this.uploadShowImageUrl = top.URL.createObjectURL(file)
        let reader = new FileReader()
        reader.readAsDataURL(file)
        reader.onload = () => {
          this.createOrEditSectionsForm.sectionsCoverImage = reader.result
        }
      },
      findSectionsList(pageNum) {
        let reqJsonParams = {
          page: {pageNum: pageNum, pageSize: this.pageSize},
          sectionsForm: this.sectionsForm
        }
        this.listLoading = true
        baseAxios.post(config.FIND_SECTIONS_LIST, reqJsonParams).then((resJson) => {
          this.sectionsList = resJson.data
          this.total = resJson.total
        }).finally(() => {
          this.listLoading = false
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
        this.title = '新增章节'
        this.createSections = true
        this.createOrEditSectionsDialogVisible = true
        this.uploadShowImageUrl = ''
        this.createOrEditSectionsForm = {}
      },

      showEditSectionsDialog(index, row) {
        this.title = '编辑章节'
        this.createSections = false
        this.createOrEditSectionsDialogVisible = true
        this.createOrEditSectionsForm = Object.assign({}, row)
        this.createOrEditSectionsForm.sectionsCoverImage = this.base64Prefix + this.createOrEditSectionsForm.sectionsCoverImage
        this.uploadShowImageUrl = this.createOrEditSectionsForm.sectionsCoverImage
      },
      submitCreateOrEditSections() {
        this.$refs.createOrEditSectionsFormRef.validate((valid) => {
          if (!valid) {
            this.$message({message: '信息有误', type: 'warning'})
            return
          }
          this.addLoading = true
          let reqJsonParams = {
            sectionsForm: this.createOrEditSectionsForm,
          }
          let url = this.createSections ? config.CREATE_SECTIONS : config.EDIT_SECTIONS
          baseAxios.post(url, reqJsonParams).then((resJson) => {
          }).finally(() => {
            this.addLoading = false
            this.createOrEditSectionsDialogVisible = false
            this.findSectionsList(this.pageNum)
          })
        })
      },
      delSections(index, row) {
        this.$confirm('确认删除该记录吗?', '提示', {type: 'warning'})
          .then(() => {
            this.listLoading = true
            baseAxios.post(config.DEL_SECTIONS, {id: row.id}).then(() => {
              this.$message({message: '操作成功', type: 'success'})
              this.findSectionsList(1)
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
      this.findTopicsList()
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
