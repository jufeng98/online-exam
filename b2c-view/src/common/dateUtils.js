import assertUtils from './assertUtils'

const dateUtils = {}
dateUtils.formatDate = (date, pattern = 'yyyy-MM-dd HH:mm:ss') => {
  assertUtils.notNullOrUndefined(date)
  let model = {
    "M+": date.getMonth() + 1,     //月份
    "d+": date.getDate(),     //日
    "H+": date.getHours(),     //小时
    "m+": date.getMinutes(),     //分
    "s+": date.getSeconds(),     //秒
    "q+": Math.floor((date.getMonth() + 3) / 3), //季度
    "S": date.getMilliseconds()    //毫秒
  }
  if (/(y+)/.test(pattern))
    pattern = pattern.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length))
  for (let k in model)
    if (new RegExp("(" + k + ")").test(pattern))
      pattern = pattern.replace(RegExp.$1, (RegExp.$1.length == 1) ? (model[k]) : (("00" + model[k]).substr(("" + model[k]).length)))
  return pattern
}
export default dateUtils
