package com.ky.ulearning.spi.teacher.entity;

import com.ky.ulearning.spi.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 试题实体类
 *
 * @author luyuhao
 * @since 2020/02/03 19:41
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("试题实体类")
public class CourseQuestionEntity extends BaseEntity {

    /**
     * 课程ID
     */
    @ApiModelProperty("课程ID")
    private Long courseId;

    /**
     * 试题内容
     */
    @ApiModelProperty("试题内容")
    private String questionText;

    /**
     * 图片URL
     */
    @ApiModelProperty("图片URL")
    private String questionUrl;

    /**
     * 参考答案
     */
    @ApiModelProperty("参考答案")
    private String questionKey;

    /**
     * 知识模块
     */
    @ApiModelProperty("知识模块")
    private String questionKnowledge;

    /**
     * 试题类型 1：选择题，2：判断题，3：多选题，4：填空题，5：简答题
     */
    @ApiModelProperty("试题类型 1：选择题，2：判断题，3：多选题，4：填空题，5：简答题")
    private Integer questionType;

    /**
     * 试题选项，"|#|"分隔
     */
    @ApiModelProperty("试题选项，'|#|'分隔")
    private String questionOption;

    /**
     * 试题难度 0：无级别，1：容易，2：较易，3：一般，4：较难，5：困难
     */
    @ApiModelProperty("试题难度 0：无级别，1：容易，2：较易，3：一般，4：较难，5：困难")
    private Integer questionDifficulty;
}
