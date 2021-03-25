/**
 * 单元测试类不建议使用@SpringBootTest注解,因为该注解相当于完整启动了整个应用,这会让执行测试类耗时过长,无法接受.
 * <p>
 * 因此在测试类里,根据实际情况,自己组装Spring应用的上下文,虽然麻烦了点,但这是值得的,避免了组装无关的bean,让测试类能快速启动执行.
 * <p>
 * 其中在测试类里,需继承CommonTestCode,其包含了测试类必须引入的公共代码,如PropertyTestConfig和SetEnvApplicationContextInitializer,
 * 用于设定测试类环境配置,修改SetEnvApplicationContextInitializer可实现使用不同的环境配置.
 * <p>
 * 测试类编写参考{@link org.javamaster.b2c.core.UsersServiceBestTests}或者{@link org.javamaster.b2c.core.UsersControllerBestTests}
 *
 * @author yudong
 * @date 2021/3/24
 * @see org.javamaster.b2c.core.CommonTestCode
 * @see org.javamaster.b2c.core.config.PropertyTestConfig
 * @see org.javamaster.b2c.core.initializer.SetEnvApplicationContextInitializer
 */
package org.javamaster.b2c.core;