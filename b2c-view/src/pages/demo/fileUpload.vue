<template>
  <div>

    <v-uploader @done="uploadDone" :preview-width="400" :preview-height="300" button-text="请选择文件"></v-uploader>

    <v-uploader @done="uploadDone" :multiple="true"></v-uploader>

  </div>
</template>

<script>
  export default {
    methods: {
      uploadDone(files) {
        if (files && Array.isArray(files) && files.length) {
          console.log(...files)
        }
      }
    }
  }
</script>

<style scoped>

</style>
