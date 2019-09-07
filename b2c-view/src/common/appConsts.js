const appConsts = {}
appConsts.LOGIN_USER_PIC_URL = 'loginUserPicUrl'
appConsts.LOGIN_USERNAME = 'loginUsername'
appConsts.LOGIN_USER_AUTHORITIES = 'loginUserAuthorities'
appConsts.BASE64_JPG_PREFIX = 'data:image/jpeg;base64,'
appConsts.ENABLED = [
  {name: '启用', value: true},
  {name: '禁用', value: false},
]
appConsts.SHOWLD_SHOW = [
  {name: '是', value: true},
  {name: '否', value: false},
]
appConsts.GENDER = [
  {name: '男', value: 'M'},
  {name: '女', value: 'F'},
]
appConsts.QUESTIONS_TYPE = [
  {name: '单选题', value: 1},
  {name: '多选题', value: 2},
  {name: '判断题', value: 3},
  {name: '排序题', value: 4},
]
appConsts.TOPICS_TYPE = [
  {name: '编码挑战', value: 1},
  {name: '网页开发', value: 2},
  {name: '编程语言', value: 3},
  {name: '数据科学', value: 4},
  {name: '开发基础', value: 5},
]
export default appConsts
