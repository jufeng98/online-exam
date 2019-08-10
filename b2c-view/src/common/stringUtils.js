const stringUtils = {}
stringUtils.checkMobile = (rule, value, callback) => {
  let pattern = /^(13[0-9]|14[0-9]|15[0-9]|166|17[0-9]|18[0-9]|19[8|9])\d{8}$/;
  check(rule, value, callback, pattern)
}
stringUtils.checkEmail = (rule, value, callback) => {
  let pattern = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
  check(rule, value, callback, pattern)
}

stringUtils.checkAlphabetNumber = (rule, value, callback) => {
  let pattern = /^[0-9a-zA-Z]+$/
  check(rule, value, callback, pattern)
}
stringUtils.formatTableRowData = (rowValue, array) => {
  for (let i = 0; i < array.length; i++) {
    if (array[i].value === rowValue) {
      return array[i].name
    }
  }
  return ''
}

function check(rule, value, callback, pattern) {
  if (!value) {
    callback()
  }
  if (pattern.test(value)) {
    callback()
  }
  return callback(new Error(rule.message))
}

export default stringUtils
