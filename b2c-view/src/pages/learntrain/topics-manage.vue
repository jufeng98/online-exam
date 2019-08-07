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
          <el-table :data="topicsList" highlight-current-row v-loading="listLoading" fit>
            <el-table-column type="index" width="60px"></el-table-column>
            <el-table-column prop="topicsName" label="主题名称"></el-table-column>
            <el-table-column label="主题封面">
              <template slot-scope="scope">
                <img style="width:100px;height:100px" :src="`${base64Prefix}${scope.row.topicsCoverImage}`"/>
              </template>
            </el-table-column>
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

    <el-dialog :title="title" :visible.sync="createOrEditTopicsDialogVisible">
      <el-form :model="createOrEditTopicsForm" label-width="80px" :rules="createOrEditTopicsFormRules"
               ref="createOrEditTopicsFormRef">
        <el-form-item label="主题名称" prop="topicsName">
          <el-input v-model="createOrEditTopicsForm.topicsName" auto-complete="off"
                    :maxlength="30"></el-input>
        </el-form-item>
        <el-form-item label="主题封面" prop="topicsCoverImage" style="text-align: left">
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
                    v-model="createOrEditTopicsForm.topicsCoverImage">
          </el-input>
        </el-form-item>
      </el-form>
      <div>
        <el-button @click.native="createOrEditTopicsDialogVisible = false">取消</el-button>
        <el-button type="primary" @click.native="submitCreateOrEditTopics" :loading="addLoading">提交</el-button>
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
        topicsForm: {
          topicsName: ''
        },
        topicsList: [],
        title: '',
        total: 0,
        pageNum: 1,
        pageSize: 10,
        listLoading: false,
        createOrEditTopicsForm: {
          topicsName: '',
          topicsCoverImage: null,
        },
        uploadShowImageUrl: '',
        createTopics: true,
        createOrEditTopicsDialogVisible: false,
        addLoading: false,
        createOrEditTopicsFormRules: {
          topicsName: [
            {required: true, message: '请输入主题名称', trigger: 'blur'}
          ],
          topicsCoverImage: [
            {required: true, message: '请选择主题封面', trigger: 'blur'}
          ],
        },
      }
    },
    methods: {
      handleSizeChange(val) {
        this.pageSize = val
        this.findTopicsList(this.pageSize)
      },
      handleCurrentChange(val) {
        this.pageNum = val
        this.findUsers(this.pageNum)
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
          this.createOrEditTopicsForm.topicsCoverImage = reader.result
        }
      },
      findTopicsList(pageNum) {
        let reqJsonParams = {
          page: {pageNum: pageNum, pageSize: this.pageSize},
          topicsForm: this.topicsForm
        }
        this.listLoading = true
        baseAxios.post(config.FIND_TOPICS_LIST, reqJsonParams).then((resJson) => {
          this.topicsList = resJson.data
          this.total = resJson.total
        }).finally(() => {
          this.listLoading = false
        })
      },
      showCreateTopicsDialog() {
        this.title = '新增主题'
        this.createTopics = true
        this.createOrEditTopicsDialogVisible = true
        this.uploadShowImageUrl = ''
        this.createOrEditTopicsForm = {}
      },

      showEditTopicsDialog(index, row) {
        this.title = '编辑主题'
        this.createTopics = false
        this.createOrEditTopicsDialogVisible = true
        this.createOrEditTopicsForm = Object.assign({}, row)
        this.createOrEditTopicsForm.topicsCoverImage = this.base64Prefix + this.createOrEditTopicsForm.topicsCoverImage
        this.uploadShowImageUrl = this.createOrEditTopicsForm.topicsCoverImage
      },
      submitCreateOrEditTopics() {
        this.$refs.createOrEditTopicsFormRef.validate((valid) => {
          if (!valid) {
            this.$message({message: '信息有误', type: 'warning'})
            return
          }
          this.addLoading = true
          let reqJsonParams = {
            topicsForm: this.createOrEditTopicsForm,
          }
          let url = this.createTopics ? config.CREATE_TOPICS : config.EDIT_TOPICS
          baseAxios.post(url, reqJsonParams).then((resJson) => {
          }).finally(() => {
            this.addLoading = false
            this.createOrEditTopicsDialogVisible = false
            this.findTopicsList(this.pageNum)
          })
        })
      },
      delTopics(index, row) {
        this.$confirm('确认删除该记录吗?', '提示', {type: 'warning'})
          .then(() => {
            this.listLoading = true
            baseAxios.post(config.DEL_TOPICS, {id: row.id}).then(() => {
              this.$message({message: '操作成功', type: 'success'})
              this.findTopicsList(1)
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
      this.findTopicsList(1)
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
