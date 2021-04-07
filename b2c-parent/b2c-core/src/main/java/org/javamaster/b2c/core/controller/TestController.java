package org.javamaster.b2c.core.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.NullNode;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.javamaster.b2c.core.model.Result;
import org.javamaster.b2c.core.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yudong
 * @date 2020/9/3
 */
@Validated
@RestController
@RequestMapping("/admin/test")
public class TestController {


    @Autowired
    private TestService testService;

    private static final List<Map<String, Object>> USER_PRODUCTS = new ArrayList<>();

    static {
        int num = 36;
        for (int i = 0; i < num; i++) {
            Map<String, Object> map = new HashMap<>(8, 1);
            map.put("orderCode", "C" + RandomStringUtils.randomNumeric(10));
            map.put("proTotalNum", RandomUtils.nextInt(1, 10));
            map.put("orderPayAmt", RandomUtils.nextInt(2000, 80000));
            map.put("platStartTime", System.currentTimeMillis());
            map.put("payTime", System.currentTimeMillis());
            USER_PRODUCTS.add(map);
        }
    }

    @PostMapping("/findUserDetails")
    public Result<Object> findUserDetails(@RequestBody JsonNode jsonNode) {
        Map<String, Object> map = new HashMap<>(5, 1);
        map.put("username", jsonNode.get("username").asText());
        map.put("memberId", "200088452133");
        map.put("phone", "13800138000");
        map.put("sex", "M");
        map.put("autoaym", true);
        return new Result<>(map);
    }

    @PostMapping("/findHonorAccount")
    public Result<Object> findHonorAccount(@RequestBody JsonNode jsonNode) {
        Map<String, Object> map = new HashMap<>(8, 1);
        map.put("zzUserId", jsonNode.get("username").asText());
        map.put("zzRegisterTime", System.currentTimeMillis());
        map.put("zzRegsterSource", "APP");
        map.put("zzRegisterReffer", "张三");
        map.put("zzNickName", "张三");
        map.put("zzStatus", true);
        map.put("zzLastLogTime", System.currentTimeMillis());
        map.put("zzLastLogApp", "微信");
        return new Result<>(map);
    }

    @PostMapping("/findUserProductSummary")
    public Result<Object> searchUserProductSummary(@RequestBody JsonNode jsonNode) {
        Map<String, Object> map = new HashMap<>(3, 1);
        map.put("zzUserId", jsonNode.get("username").asText());
        map.put("totalReceivedAmt", RandomUtils.nextInt(100000, 1000000));
        map.put("totalNum", RandomUtils.nextInt(10, 100));
        return new Result<>(map);
    }

    @PostMapping("/findUserProducts")
    public Result<Object> searchUserProducts(@RequestBody JsonNode jsonNode) {
        List<Map<String, Object>> list = USER_PRODUCTS.stream()
                .peek(map -> {
                    if (!(jsonNode.get("startTime") instanceof NullNode)) {
                        map.put("platStartTime", jsonNode.get("startTime").asLong());
                    }
                    if (!(jsonNode.get("endTime") instanceof NullNode)) {
                        map.put("payTime", jsonNode.get("endTime").asLong());
                    }
                })
                .skip(jsonNode.get("pageNum").asInt() - 1)
                .limit(jsonNode.get("pageSize").asInt())
                .collect(Collectors.toList());
        return new Result<>(list, Long.valueOf(USER_PRODUCTS.size()));
    }

    @PostMapping("/getOrderInfo")
    public Result<String> getOrderInfo(@RequestBody JsonNode jsonNode) {
        return new Result<>(testService.getOrderInfo(jsonNode.get("orderCode").asText()));
    }

}