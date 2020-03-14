const assertUtils = {}
assertUtils.notNullOrUndefined = (data) => {
  if (data === null || data === undefined) {
    throw new Error('数据不能为null或undefined')
  }
}
assertUtils.isTrue = (condition, msg) => {
  if (!condition) {
    throw new Error(msg)
  }
}
export default assertUtils
