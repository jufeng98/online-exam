import baseAxios from '../common/baseAxios'

const downloadUtils = {}
downloadUtils.download = (url, fileName, beforeDown = () => {
}, afterDownload = () => {
}, failDownload = (error) => {
}) => {
  beforeDown()
  baseAxios({
    method: 'get',
    url: url,
    responseType: 'blob'
  }).then(resData => {
    let blob = new Blob([resData], {type: resData.type})
    let downloadElement = document.createElement('a')
    // 创建下载的链接
    let href = window.URL.createObjectURL(blob);
    downloadElement.href = href;
    // 下载后文件名
    downloadElement.download = fileName;
    document.body.appendChild(downloadElement);
    // 点击下载
    downloadElement.click();
    // 下载完成移除元素
    document.body.removeChild(downloadElement);
    // 释放blob对象
    window.URL.revokeObjectURL(href);
  }).catch((error) => {
    failDownload(error)
  }).finally(() => {
    afterDownload()
  })
}
export default downloadUtils
