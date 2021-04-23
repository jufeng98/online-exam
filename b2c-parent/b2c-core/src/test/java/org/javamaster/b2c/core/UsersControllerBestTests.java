package org.javamaster.b2c.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import static org.javamaster.b2c.core.CommonTestCode.PROFILE_UNIT_TEST;
import org.javamaster.b2c.core.config.*;
import org.javamaster.b2c.core.controller.UsersController;
import org.javamaster.b2c.core.service.impl.UsersServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.transaction.annotation.Transactional;

/**
 * 添加@commit注解则事务不会回滚
 *
 * @author yudong
 * @date 2021/3/24
 */
@Transactional
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
        DatasourceTestConfig.class,
        MybatisTestConfig.class,
        RedissonTestConfig.class,
        WebTestConfig.class,
        UsersServiceImpl.class,

        SecurityTestConfig.class,
        UsersController.class
})
@AutoConfigureMockMvc
@AutoConfigureWebMvc
@WebAppConfiguration
@ActiveProfiles(PROFILE_UNIT_TEST)
public class UsersControllerBestTests extends CommonTestCode {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;

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
