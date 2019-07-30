import * as baseConfig from './base.config'
import * as devConfig from './dev.config'
import * as mockConfig from './mock.config'
import * as testConfig from './test.config'
import * as prodConfig from './prod.config'

let config
switch (process.env.NODE_ENV) {
  case 'develop':
    config = devConfig
    break
  case 'mock':
    config = mockConfig
    break
  case 'testing':
    config = testConfig
    break
  default:
    config = prodConfig
}
// base.config优先级最低
export default Object.assign({}, baseConfig, config)
