package com.wt.test.apolloclient.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Xljnc
 * @date 2019/2/7 22:33
 * @description
 */
@RestController
@RequestMapping("/apollo/config")
@Slf4j
public class ClientController {

    @Value("${testKey.stringKey:Holy Shit}")
    private String stringValue;
    @Value("${testKey.intKey:1}")
    private Integer intValue;

    @GetMapping("/get")
    public String getConfig() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("stringValue", stringValue);
        jsonObject.put("intValue", intValue);
        log.error("config:"+jsonObject.toJSONString());
        return jsonObject.toJSONString();
    }
}
