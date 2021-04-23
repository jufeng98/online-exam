package org.javamaster.b2c.core;

import lombok.SneakyThrows;
import static org.javamaster.b2c.core.CommonTestCode.PROFILE_UNIT_TEST;
import org.javamaster.b2c.core.config.*;
import org.javamaster.b2c.core.controller.TestController;
import org.javamaster.b2c.core.service.impl.TestServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.web.client.RestTemplate;


/**
 * @author yudong
 * @date 2021/3/24
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {
        DatasourceTestConfig.class,
        SecurityTestConfig.class,
        WebTestConfig.class,
        TestController.class,
        TestServiceImpl.class
})
@AutoConfigureMockMvc
@AutoConfigureWebMvc
@WebAppConfiguration
@ActiveProfiles(PROFILE_UNIT_TEST)
public class MockRestServiceServerTests extends CommonTestCode {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    private RestTemplate restTemplate;

    @Test
    @WithMockUser(
            username = "admin",
            password = "admin",
            authorities = "ROLE_ADMIN"
    )
    @SneakyThrows
    public void test() {
        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();
        server
                .expect(requestTo("http://b2c-cloud-order-service/getOrderDetails?orderCode=C93847639357"))
                .andRespond(withSuccess("{\"orderCode\":\"C93847639357\",\"payType\":\"alipay\"}", MediaType.APPLICATION_JSON_UTF8));

        mockMvc
                .perform(
                        post("/admin/test/getOrderInfo")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content("{\"orderCode\":\"C93847639357\"}")
                                .accept(MediaType.APPLICATION_JSON_UTF8)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.data").value("alipay"));
    }

}
