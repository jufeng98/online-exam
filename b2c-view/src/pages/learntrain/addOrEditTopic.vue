<template>
  <div>
    <el-form :model="createOrEditTopicsForm" label-width="120px" :rules="createOrEditTopicsFormRules"
             ref="createOrEditTopicsFormRef">
      <el-form-item label="主题名称" prop="topicsName">
        <el-input v-model="createOrEditTopicsForm.topicsName" auto-complete="off" :maxlength="30"></el-input>
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
        <el-input type="hidden" style="width:0px;height:0px;" v-model="createOrEditTopicsForm.topicsCoverImage">
        </el-input>
      </el-form-item>
      <el-form-item label="主题类型" prop="topicsType" style="text-align: left">
        <el-select v-model="createOrEditTopicsForm.topicsType">
          <el-option
            v-for="item in topicsTypes"
            :key="item.value"
            :label="item.name"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="关联考试" prop="examsCode" style="text-align: left">
        <el-select v-model="createOrEditTopicsForm.examsCode">
          <el-option
            v-for="item in examsList"
            :key="item.examsCode"
            :label="item.examsName"
            :value="item.examsCode">
          </el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <div style="text-align: center">
      <el-button type="primary" @click.native="closeDialog('cancel')">取消</el-button>
      <el-button type="primary" @click.native="submitCreateOrEditTopics" :loading="loading">提交</el-button>
    </div>
  </div>
</template>

<script>
  import appConsts from "../../common/appConsts";
  import config from "../../config";
  import baseAxios from "../../common/baseAxios";
  import store from "../../store/store";

  export default {
    name: 'addOrEditTopic',
    data() {
      return {
        base64Prefix: appConsts.BASE64_JPG_PREFIX,
        topicsTypes: appConsts.TOPICS_TYPE,
        uploadShowImageUrl: '',
        examsList: [],
        examsMap: new Map(),
        loading: false,
        createOrEditTopicsForm: {
          id: null,
          topicsName: '',
          topicsCoverImage: null,
          topicsType: '',
          examsCode: '',
        },
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
      closeDialog(action) {
        this.$emit('close', {action})
      },
      submitCreateOrEditTopics() {
        this.$refs.createOrEditTopicsFormRef.validate((valid) => {
          if (!valid) {
            this.$message({message: '信息有误', type: 'warning'})
            return
          }
          let reqJsonParams = {
            topicsForm: this.createOrEditTopicsForm,
          }
          let url = this.createTopics ? config.CREATE_TOPICS : config.EDIT_TOPICS
          this.loading = true
          baseAxios.post(url, reqJsonParams).then(() => {
            this.closeDialog('success')
          }).finally(() => {
            this.loading = false
          })
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
        })
      },
    },
    mounted() {
      this.createTopics = store.state.createTopics
      this.createOrEditTopicsForm.id = store.state.id
      this.createOrEditTopicsForm.topicsName = store.state.topicsName
      if (store.state.topicsCoverImage) {
        this.createOrEditTopicsForm.topicsCoverImage = this.base64Prefix + store.state.topicsCoverImage
        this.uploadShowImageUrl = this.createOrEditTopicsForm.topicsCoverImage
      }
      this.createOrEditTopicsForm.topicsType = store.state.topicsType
      this.createOrEditTopicsForm.examsCode = store.state.examsCode
      this.findExamsList()
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
