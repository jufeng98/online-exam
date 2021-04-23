package org.javamaster.b2c.core.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import org.javamaster.b2c.core.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

/**
 * @author yudong
 * @date 2021/4/7
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String getOrderPayType(String orderCode) {
        JsonNode jsonNode = restTemplate.getForObject("http://b2c-cloud-order-service/getOrderPayType?orderCode={1}", JsonNode.class, orderCode);
        return Objects.requireNonNull(jsonNode).get("payType").asText();
    }
}
