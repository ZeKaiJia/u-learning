package com.ky.ulearning.system;

import com.ky.ulearning.common.core.utils.EnvironmentAwareUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author luyuhao
 * @date 19/12/04 01:17
 */
@SpringBootApplication(scanBasePackages = {
        "com.ky.ulearning.system",
        "com.ky.ulearning.common.core.component"})
@EnableEurekaClient
@EnableCaching
@EnableAsync
@EnableFeignClients
public class SystemManageApplication {
    public static void main(String[] args) {
        EnvironmentAwareUtil.adjust();
        SpringApplication.run(SystemManageApplication.class, args);
    }
}
