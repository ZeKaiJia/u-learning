package com.ky.ulearning.gateway.common.security;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ky.ulearning.common.core.message.JsonResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 自定义返回的错误信息
 *
 * @author luyuhao
 * @date 2019/12/10 9:26
 */
@Component
public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        JsonResult jsonResult = new JsonResult<>(HttpStatus.UNAUTHORIZED, exception.getMessage());
        String jsonString = JSONObject.toJSONString(jsonResult, SerializerFeature.WriteMapNullValue);
        response.setContentType("text/json;charset=utf-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        PrintWriter out = response.getWriter();
        out.write(jsonString);
        out.close();
    }
}
