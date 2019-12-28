package com.ky.ulearning.system.common.constants;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author luyuhao
 * @date 19/12/11 23:19
 */
@Component
@Getter
public class SystemManageConfigParameters {

    @Value("${server.servlet.context-path}")
    private String contextPath;
}
