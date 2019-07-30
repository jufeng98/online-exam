const assertUtils = {}
assertUtils.notNullOrUndefined = (data) => {
  if (data === null || data === undefined) {
    throw new Error('数据不能为null或undefined')
  }
}
export default assertUtils
