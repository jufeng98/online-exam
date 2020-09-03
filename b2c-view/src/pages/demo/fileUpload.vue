<template>
  <div>

    <v-uploader :preview-height="300" :preview-width="400" @done="uploadDone" button-text="请选择文件"></v-uploader>

    <v-uploader :multiple="true" @done="uploadDone"></v-uploader>

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
