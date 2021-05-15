package org.javamaster.b2c.core;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import org.javamaster.b2c.core.config.SecurityTestConfig;
import org.javamaster.b2c.core.controller.UsersController;
import org.javamaster.spring.test.annos.ScanTestedDependencies;
import org.javamaster.spring.test.config.MybatisTestConfig;
import org.javamaster.spring.test.config.RedisTestConfig;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.transaction.annotation.Transactional;

/**
 * 添加@commit注解则事务不会回滚
 *
 * @author yudong
 * @date 2021/5/15
 */
@Transactional
@ScanTestedDependencies(UsersController.class)
@ContextConfiguration(classes = {
        UsersController.class,
        MybatisTestConfig.class,
        RedisTestConfig.class,
        SecurityTestConfig.class,
})
public class UsersControllerSuperBestTests extends CommonSuperTestCode {

    @Test
    @SneakyThrows
    @WithMockUser(
            username = "admin",
            password = "admin",
            authorities = "ROLE_ADMIN"
    )
    // @Commit
    @Sql("classpath:sql-script/users.sql")
    public void createUsersTest() {
        ObjectNode reqVo = objectMapper.createObjectNode();
        ObjectNode createOrEditUsersForm = reqVo.putObject("createOrEditUsersForm");

        createOrEditUsersForm.put("username", "jufeng98");
        createOrEditUsersForm.put("password", "admin");
        createOrEditUsersForm.put("email", "jufeng98@qq.com");
        createOrEditUsersForm.put("gender", "M");

        mockMvc
                .perform(
                        post("/admin/users/createUsers")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(objectMapper.writeValueAsString(reqVo))
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.data.username").value("jufeng98"));
    }
}
