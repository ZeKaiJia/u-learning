package com.ky.ulearning.student.service;

import com.ky.ulearning.spi.common.dto.PageBean;
import com.ky.ulearning.spi.common.dto.PageParam;
import com.ky.ulearning.spi.system.dto.TeachingTaskDto;
import com.ky.ulearning.spi.system.entity.TeachingTaskEntity;

/**
 * 教学任务service - 接口
 *
 * @author luyuhao
 * @since 20/02/21 22:27
 */
public interface TeachingTaskService {

    /**
     * 分页查询未选的教学任务信息
     *
     * @param teachingTaskDto 筛选参数
     * @param pageParam       分页参数
     * @return 封装教学任务信息的分页对象
     */
    PageBean<TeachingTaskEntity> pageNotSelectedList(TeachingTaskDto teachingTaskDto, PageParam pageParam);

    /**
     * 分页查询已选的教学任务信息
     *
     * @param teachingTaskDto 筛选参数
     * @param pageParam       分页参数
     * @return 封装教学任务信息的分页对象
     */
    PageBean<TeachingTaskEntity> pageSelectedList(TeachingTaskDto teachingTaskDto, PageParam pageParam);
}