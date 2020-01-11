package com.ky.ulearning.system.common.constants;

import com.ky.ulearning.common.core.exceptions.enums.BaseEnum;
import org.springframework.http.HttpStatus;

/**
 * @author luyuhao
 * @date 19/12/06 03:13
 */
public enum SystemErrorCodeEnum implements BaseEnum {

    /**
     * 后台管理系统错误状态码
     */
    PARAMETER_EMPTY(HttpStatus.BAD_REQUEST, "参数不可为空!"),
    TEACHER_NOT_EXISTS(HttpStatus.BAD_REQUEST, "教师不存在!"),
    ID_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "ID不可为空!"),
    EMAIL_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "邮箱不可为空!"),
    TEA_NUMBER_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "工号不可为空!"),
    NAME_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "姓名不可为空!"),
    PERMISSION_URL_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "权限url不可为空!"),
    PERMISSION_GROUP_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "权限组不可为空!"),
    PERMISSION_NAME_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "权限名不可为空!"),
    PERMISSION_SOURCE_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "权限码不可为空!"),
    ROLE_NAME_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "角色名不可为空!"),
    ROLE_SOURCE_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "角色资源名不可为空!"),
    IS_ADMIN_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST, "未指定是否是管理员角色"),

    ;

    private Integer code;
    private String message;

    SystemErrorCodeEnum(HttpStatus httpStatus, String message) {
        this.code = httpStatus.value();
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
