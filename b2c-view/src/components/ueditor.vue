<template>
  <div>
    <script :id="id" type="text/plain"></script>
  </div>
</template>

<script>
  import '../../static/ueditor/ueditor.config.js'
  import '../../static/ueditor/ueditor.all.js'
  import '../../static/ueditor/lang/zh-cn/zh-cn.js'

  export default {
    name: 'ueditor',
    props: {
      id: {
        type: String,
        default() {
          return 'editor'
        }
      },
      config: {
        type: Object
      }
    },
    data() {
      return {
        editor: null
      }
    },
    methods: {
      initUeditor() {
        this.editor = UE.delEditor(this.id);
        this.editor = UE.getEditor(this.id, this.config);
      },
      destory() {
        this.editor.destory()
      },
      getContentTxt() {
        return this.editor.getContentTxt()
      },
      getContent() {
        return this.editor.getContent()
      },
      //设置编辑器内容
      setContent(val) {
        this.editor.setContent(val)
      },
      //追加编辑器内容
      appendContent(val) {
        this.editor.setContent(val, true)
      }
    },
    mounted() {
      this.initUeditor()
    },
  }
</script>

