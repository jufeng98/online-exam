package org.javamaster.b2c.core;

import org.javamaster.b2c.core.entity.Users;
import org.javamaster.b2c.core.model.vo.CreateUsersForm;
import org.javamaster.b2c.core.model.vo.CreateUsersReqVo;
import org.javamaster.b2c.core.service.UsersService;
import static org.javamaster.b2c.core.utils.TestUtils.mockUserDetails;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * 添加@commit注解则事务不会回滚
 *
 * @author yudong
 * @date 2021/3/24
 */
// 添加事务注解，则默认情况下测试方法执行完后事务会被回滚，结合@commit注解使用可让事务被提交
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = CoreApplication.class
)
public class UsersServiceNormalTests {

    @Autowired
    private UsersService usersService;

    @Test
    // 添加此注解则事务不会回滚
    // @Commit
    // 指定运行测试方法前要先执行sql脚本
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
