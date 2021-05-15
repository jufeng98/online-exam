package org.javamaster.b2c.core;

import org.javamaster.spring.test.GeneralTestCode;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;

import javax.servlet.*;
import java.io.IOException;

/**
 * 公共测试基类
 *
 * @author yudong
 * @date 2021/5/15
 */
public class CommonSuperTestCode extends GeneralTestCode {
    static {
        System.setProperty("env", "dev");
    }

    @Override
    public void customizedMockMvc(DefaultMockMvcBuilder mockMvcBuilder) {
        mockMvcBuilder.addFilter(new GenericFilter() {
            @Override
            public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                    throws IOException, ServletException {
                // mock 请求公参
                MockHttpServletRequest servletRequest = (MockHttpServletRequest) request;
                servletRequest.setParameter("clientType", "android");
                servletRequest.setParameter("version", "5.19.0");
                chain.doFilter(request, response);
            }
        }, "/*");
    }

}
