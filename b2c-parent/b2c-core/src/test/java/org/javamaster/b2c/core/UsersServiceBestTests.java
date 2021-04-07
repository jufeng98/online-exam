package org.javamaster.b2c.core;

import org.javamaster.b2c.core.config.*;
import org.javamaster.b2c.core.entity.Users;
import org.javamaster.b2c.core.model.vo.CreateUsersForm;
import org.javamaster.b2c.core.model.vo.CreateUsersReqVo;
import org.javamaster.b2c.core.service.UsersService;
import org.javamaster.b2c.core.service.impl.UsersServiceImpl;
import static org.javamaster.b2c.core.utils.TestUtils.mockUserDetails;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

/**
 * 添加@commit注解则事务不会回滚
 *
 * @author yudong
 * @date 2021/3/24
 */
@Transactional
@ContextConfiguration(classes = {
        DatasourceTestConfig.class,
        MybatisTestConfig.class,
        RedissonTestConfig.class,
        WebTestConfig.class,
        UsersServiceImpl.class
})
public class UsersServiceBestTests extends CommonTestCode {

    @Autowired
    private UsersService usersService;

    @Test
    // @Commit
    @Sql("classpath:sql-script/users.sql")
    public void createUsersTest() {
        CreateUsersReqVo reqVo = new CreateUsersReqVo();
        CreateUsersForm form = new CreateUsersForm();
        form.setUsername("jufeng98");
        form.setPassword("admin");
        form.setEmail("jufeng98@qq.com");
        form.setGender("M");
        reqVo.setCreateOrEditUsersForm(form);
        Users users = usersService.createUsers(reqVo, mockUserDetails());
        Assert.assertEquals(users.getUsername(), "jufeng98");
    }
}
