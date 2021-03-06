package com.ky.ulearning.student.service;

import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.student.dto.StudentExaminationTaskDto;
import com.ky.ulearning.spi.student.entity.StudentExaminationTaskEntity;
import com.ky.ulearning.spi.student.vo.ExaminationResultViewVo;
import com.ky.ulearning.spi.student.vo.StudentExaminationTaskBaseInfoVo;

import java.util.Date;
import java.util.List;

/**
 * 学生测试service - 接口
 *
 * @author luyuhao
 * @since 20/03/08 00:51
 */
public interface StudentExaminationTaskService {

    /**
     * 添加学生测试
     *
     * @param studentExaminationTaskDto 学生测试对象
     */
    void add(StudentExaminationTaskDto studentExaminationTaskDto);

    /**
     * 根据测试任务id和学生id查询学生测试信息
     *
     * @param examinationTaskId 测试任务id
     * @param stuId             学生id
     * @return 学生测试信息
     */
    StudentExaminationTaskEntity getByExaminationTaskIdAndStuId(Long examinationTaskId, Long stuId);

    /**
     * 更新学生测试信息
     *
     * @param studentExaminationTaskDto 待更新的对象
     */
    void update(StudentExaminationTaskDto studentExaminationTaskDto);

    /**
     * 根据测试任务id查询学生测试基本信息
     *
     * @param examinationTaskId 测试任务id
     * @return 学生基本信息
     */
    List<StudentExaminationTaskBaseInfoVo> getBaseInfoByExaminationTaskId(Long examinationTaskId);

    /**
     * 分页查询学生测试结果
     *
     * @param pageParam       分页条件
     * @param submitTime      提交时间
     * @param stuId           学生id
     * @param teachingTaskId  教学任务id
     * @param examinationName 测试任务名
     * @return 测试结果分页对象
     */
    PageBean<ExaminationResultViewVo> pageList(PageParam pageParam, Date submitTime, Long stuId, Long teachingTaskId, String examinationName);
}
