package com.ky.ulearning.common.core.constant;

/**
 * 业务通用常量
 *
 * @author luyuhao
 * @since 2020/03/12 23:17
 */
public class CommonConstant {

    public static final String CHARSET = "UTF-8";

    /**
     * 测试任务状态，1：未发布 2：未开始 3：进行中 4：已结束
     */
    public static final Integer[] EXAMINATION_STATE = {1, 2, 3, 4};

    /**
     * 答案分割符-split使用
     */
    public static final String COURSE_QUESTION_SEPARATE = "\\|#\\|";

    /**
     * 答案分割符-判断使用
     */
    public static final String COURSE_QUESTION_SEPARATE_JUDGE = "|#|";

    public static final String DRUID_STAT_WEB_URI = "/weburi.json";

    /**
     * rocketmq日志队列主题
     */
    public static final String ROCKET_LOG_MONITOR_TOPIC = "LogMonitor";

    /**
     * rocketmq教师动态队列主题
     */
    public static final String ROCKET_LOG_TEACHER_ACTIVITY_TOPIC = "TeacherActivity";

    /**
     * 插入操作
     */
    public static final int INSERT_OPERATION = 1;

    /**
     * 删除操作
     */
    public static final int DELETE_OPERATION = 2;

    /**
     * 更新操作
     */
    public static final int UPDATE_OPERATION = 3;

    /**
     * 查询操作
     */
    public static final int GET_OPERATION = 4;

    /**
     * 试题类型 1：选择题，2：判断题，3：多选题，4：填空题，5：简答题
     */
    public static final Integer[] QUESTION_TYPE = {1, 2, 3, 4, 5};
}
