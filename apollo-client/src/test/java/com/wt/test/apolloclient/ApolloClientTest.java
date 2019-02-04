package com.wt.test.apolloclient;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Xljnc
 * @date 2019/2/4 17:15
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableApolloConfig
public class ApolloClientTest {

    @Value("${testKey.stringKey:Holy Shit}")
    private String stringValue;
    @Value("${testKey.intKey:1}")
    private Integer intValue;

    @Test
    public void firstTest() {
        System.out.println("stringValue:" + stringValue);
        System.out.println("intValue:" + intValue);
    }
}
