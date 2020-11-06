<!--@author liangyudong-->
<!--@date   2020/11/01-->
<template>
  <div>
    <el-upload drag action="" :multiple="multiple" :http-request="uploadImage" :show-file-list="false"
               accept="*/*" ref="elImageUpload">
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      <div class="el-upload__tip" slot="tip">{{ tip }}</div>
    </el-upload>
    <ul class="el-upload-list el-upload-list--picture">
      <li v-for="(value, key, index) in fileObject" :key="key" :tabindex="index"
          class="el-upload-list__item is-success">
        <a class="el-upload-list__item-name">
          <i class="el-icon-document"></i>
          {{ showThumbName ? value.name : "&nbsp;&nbsp;&nbsp;" }}
        </a>
        <el-progress class="progress" :percentage="value.percentage" v-show="value.percentage < 100"></el-progress>
        <label class="el-upload-list__item-status-label" v-show="value.percentage === 100">
          <i class="el-icon-upload-success el-icon-check"></i>
        </label>
        <i class="el-icon-close" @click="delImage(key)"></i>
        <i class="el-icon-close-tip">点击删除</i>
      </li>
    </ul>
  </div>
</template>
<script>
import "jquery";
import WebUploader from "webuploader";

export default {
  name: "big-file-upload",
  props: {
    /**
     * 上传提示
     */
    tip: {
      type: String,
      default: "文件不超过500M"
    },
    /**
     * 是否支持多选
     */
    multiple: {
      type: Boolean,
      default: true
    },
    /**
     * 文件后缀名限制
     */
    ext: {
      type: String,
      default: "*"
    },
    /**
     * 分片大小设置
     */
    chunkSize: {
      type: Number,
      default: 2 * 1024 * 1024,
    },
    /**
     * 分片上传重试次数
     */
    chunkRetry: {
      type: Number,
      default: 1
    },
    /**
     * 上传文件大小限制
     */
    sizeLimit: {
      type: Number,
      default: 209715200
    },
    /**
     * 上传文件数量限制
     */
    countLimit: {
      type: Number,
      default: 5
    },
    /**
     * 是否显示预览图文件名
     */
    showThumbName: {
      type: Boolean,
      default: true
    },
  },
  data() {
    return {
      fileObject: {},
      wulObj: {},
      webUploader: null,
      showArea: false,
      checkUrl: this.$config.BASE_PATH + this.$config.APP_CONTEXT + this.$config.CHECK_BIG_FILE,
      uploadUrl: this.$config.BASE_PATH + this.$config.APP_CONTEXT + this.$config.UPLOAD_BIG_FILE,
      mergeUrl: this.$config.BASE_PATH + this.$config.APP_CONTEXT + this.$config.MERGE_BIG_FILE,
    };
  },
  methods: {
    uploadImage(obj) {
      this.showArea = true;
      this.webUploader.addFiles(obj.file);
    },
    delImage(key) {
      delete this.fileObject[key];
      this.webUploader.removeFile(key);
      if (Object.keys(this.fileObject).length === 0) {
        this.showArea = false;
      }
      this.$forceUpdate();
    },
    getFileUrls() {
      return Object.values(this.fileObject).map(function (item) {
        return item.url;
      });
    },
    getFiles() {
      let self = this;
      return Object.keys(this.fileObject).map(function (item) {
        let fileInfo = {};
        fileInfo.name = item;
        fileInfo.url = self.fileObject[item].url;
        return fileInfo;
      });
    }
  },
  mounted() {
    let self = this;
    WebUploader.Uploader.register(
      {
        "before-send-file": "beforeSendFile",
        "before-send": "beforeSend",
        "after-send-file": "afterSendFile"
      },
      {
        beforeSendFile: function (file) {
          let deferred = WebUploader.Deferred();
          self.webUploader.md5File(file)
            .then(function (fileMd5) {
                self.wulObj[file.id] = {};
                self.wulObj[file.id].fileMd5 = fileMd5;
                self.wulObj[file.id].fileSize = file.size;
                self.wulObj[file.id].chunkSize = self.chunkSize;
                self.wulObj[file.id].fileName = file.name;
                let reqJsonObj = Object.assign({}, self.wulObj[file.id]);
                jQuery.ajax({
                  type: "POST",
                  url: self.checkUrl,
                  data: reqJsonObj,
                  dataType: "json",
                  error: function (e) {
                    self.$message("上传失败,请联系管理员:" + e);
                    deferred.reject();
                  },
                  success: function (resJsonObj) {
                    if (resJsonObj.success) {
                      if (typeof resJsonObj.data === "string") {
                        self.$message("文件检查完成,状态:已完成上传");
                        self.fileObject[file.id].url = resJsonObj.data;
                      } else {
                        self.$message("文件检查完成,状态:缺失分片");
                        self.fileObject[file.id].missingChunks = resJsonObj.data;
                      }
                      deferred.resolve();
                    } else {
                      self.$message("系统错误");
                      deferred.reject();
                    }
                  }
                });
                return deferred.promise();
              }
            );
          return deferred.promise();
        },
        beforeSend: function (block) {
          let deferred = WebUploader.Deferred();
          if (self.fileObject[block.file.id] === undefined) {
            return deferred.reject();
          }
          if (self.fileObject[block.file.id].missingChunks.indexOf(block.chunk) === -1) {
            return deferred.reject();
          }
          self.webUploader.md5File(block.blob).then(
            function (chunkMd5) {
              self.wulObj[block.file.id][block.chunk] = {};
              self.wulObj[block.file.id][block.chunk].formData = {
                chunk: block.chunk,
                fileMd5: self.wulObj[block.file.id].fileMd5,
                chunkMd5: chunkMd5,
              };
              deferred.resolve();
            }
          );
          return deferred.promise();
        },
        afterSendFile: function (file) {
          self.$message("所有分片上传完成,通知服务器合并文件分片...");
          jQuery.ajax({
            type: "POST",
            url: self.mergeUrl,
            data: {
              fileMd5: self.wulObj[file.id].fileMd5,
              fileName: file.name,
              chunkSize: self.chunkSize,
              size: self.wulObj[file.id].fileSize,
            },
            success: function (resJsonObj) {
              if (resJsonObj.success) {
                self.$message("服务器合并文件分片完成");
                if (self.fileObject[file.id]) {
                  self.fileObject[file.id].status = "success";
                  self.fileObject[file.id].percentage = 100;
                  self.fileObject[file.id].url = resJsonObj.url;
                }
              } else {
                self.$message("文件分片合并失败:" + resJsonObj.msg);
                if (self.fileObject[file.id]) {
                  self.fileObject[file.id].status = "exception";
                }
              }
              self.wulObj[file.id] = {};
            }
          });
        }
      }
    );
    self.webUploader = WebUploader.create({
      // swf文件路径
      swf: "../js/Uploader.swf",
      // 文件接收服务端。
      server: self.uploadUrl,
      // 自动上传
      auto: true,
      // 禁止浏览器打开文件
      disableGlobalDnd: true,
      // 分片上传
      chunked: true,
      // 分片大小
      chunkSize: self.chunkSize,
      // 分片上传失败重试次数
      chunkRetry: 1,
      // 图片不做压缩
      compress: false,
      // 提前准备好下一个文件
      prepareNextFile: true,
      // 限制单个文件大小
      fileSingleSizeLimit: 500 * 1024 * 1024,
      fileNumLimit: 5,
      //线程数
      threads: 5,
      // 限制格式
      accept: {
        title: 'file',
        extensions: '*',
        mimeTypes: '*/*'
      }
    });
    self.webUploader.on(
      "fileQueued",
      function (file) {
        self.fileObject[file.id] = {};
        self.fileObject[file.id].name = file.name;
        self.fileObject[file.id].status = "ready";
        self.fileObject[file.id].blobUrl = URL.createObjectURL(file.source.source);
      }
    );
    self.webUploader.on(
      "uploadBeforeSend",
      function (block, headers) {
        Object.assign(headers, self.wulObj[block.file.id][block.chunk].formData);
      }
    );
    self.webUploader.on(
      "uploadProgress",
      function (file, percentage) {
        self.fileObject[file.id].showPercentage = true;
        self.fileObject[file.id].percentage = Math.ceil(percentage * 100);
        self.fileObject[file.id].status = "uploading";
        self.$forceUpdate();
      }
    );
    self.webUploader.on(
      "uploadComplete",
      function (file) {
        self.fileObject[file.id].showPercentage = false;
        self.fileObject[file.id].status = "complete";
        self.$forceUpdate();
        self.$message("上传完成");
      }
    );
    self.webUploader.on(
      "error",
      function (type) {
        self.$message("错误:" + type);
      }
    );
  }
};
</script>
<style scoped></style>
