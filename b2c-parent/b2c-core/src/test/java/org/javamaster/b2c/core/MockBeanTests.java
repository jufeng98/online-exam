package org.javamaster.b2c.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import static org.javamaster.b2c.core.CommonTestCode.PROFILE_UNIT_TEST;
import org.javamaster.b2c.core.config.*;
import org.javamaster.b2c.core.controller.UsersController;
import org.javamaster.b2c.core.entity.Users;
import org.javamaster.b2c.core.service.UsersService;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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
 * @author yudong
 * @date 2021/3/24
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
        WebTestConfig.class,
        DatasourceTestConfig.class,
        SecurityTestConfig.class,
        UsersController.class
})
@AutoConfigureMockMvc
@AutoConfigureWebMvc
@WebAppConfiguration
@ActiveProfiles(PROFILE_UNIT_TEST)
public class MockBeanTests extends CommonTestCode {

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
        // @MockBean注解会代理bean的所有方法,对于未mock的方法调用均是返回null,这里的意思是针对调用createUsers方法
        // 的任意入参，均返回指定的结果
        given(usersService.createUsers(any(), any())).willReturn(users);

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
