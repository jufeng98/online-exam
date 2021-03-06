package org.javamaster.b2c.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import static org.javamaster.b2c.core.CommonTestCode.PROFILE_UNIT_TEST;
import org.javamaster.b2c.core.config.DatasourceTestConfig;
import org.javamaster.b2c.core.config.SecurityTestConfig;
import org.javamaster.b2c.core.controller.UsersController;
import org.javamaster.b2c.core.entity.Users;
import org.javamaster.b2c.core.service.UsersService;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 添加@commit注解则事务不会回滚
 *
 * @author yudong
 * @date 2021/3/25
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(
        classes = {
                DatasourceTestConfig.class,
                SecurityTestConfig.class,
                UsersController.class
        }
)
@AutoConfigureMockMvc
@AutoConfigureWebMvc
@WebAppConfiguration
@ActiveProfiles(PROFILE_UNIT_TEST)
public class UsersControllerBetterTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsersService usersService;

    @Test
    @SneakyThrows
    @WithMockUser(
            username = "admin",
            password = "admin",
            authorities = "ROLE_ADMIN"
    )
    public void createUsersTest() {
        Users users = new Users();
        users.setUsername("jufeng98");

        doReturn(users).when(usersService).createUsers(any(), any());

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
