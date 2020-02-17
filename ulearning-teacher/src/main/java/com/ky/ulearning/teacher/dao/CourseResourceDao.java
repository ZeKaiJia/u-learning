package com.ky.ulearning.teacher.dao;

import com.ky.ulearning.spi.teacher.dto.CourseFileResourceDto;
import com.ky.ulearning.spi.teacher.dto.CourseResourceDto;
import com.ky.ulearning.spi.teacher.entity.CourseResourceEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 教学资源dao
 *
 * @author luyuhao
 * @since 2020/02/17 19:14
 */
@Mapper
@Repository
public interface CourseResourceDao {

    /**
     * 插入教学资源信息
     *
     * @param courseResourceDto 待插入的教学资源
     */
    void insert(CourseResourceDto courseResourceDto);

    /**
     * 根据id查询教学资源
     *
     * @param id 教学资源id
     * @return 教学资源对象
     */
    CourseResourceEntity getById(Long id);

    /**
     * 更新教学资源
     *
     * @param courseResourceDto 待更新的教学资源对象
     */
    void update(CourseResourceDto courseResourceDto);

    /**
     * 根据文件id查询课程文件资料对象
     *
     * @param fileId 文件id
     * @return 课程文件资料对象
     */
    CourseFileResourceDto getByFileId(Long fileId);
}