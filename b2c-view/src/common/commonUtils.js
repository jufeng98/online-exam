import assertUtils from './assertUtils'

const commonUtils = {}
commonUtils.getDimensionFromPercent = (widthPercent, heightPercent) => {
  assertUtils.isTrue(widthPercent >= 0 && widthPercent <= 100, '须位于0-100之间')
  assertUtils.isTrue(heightPercent >= 0 && heightPercent <= 100, '须位于0-100之间')
  let frameWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth
  let frameHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight
  let width = (widthPercent / 100) * frameWidth
  let height = (heightPercent / 100) * frameHeight
  return {width, height}
}
export default commonUtils
