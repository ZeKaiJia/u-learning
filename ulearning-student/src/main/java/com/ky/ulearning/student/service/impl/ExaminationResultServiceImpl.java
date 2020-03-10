package com.ky.ulearning.student.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.spi.common.vo.CourseQuestionVo;
import com.ky.ulearning.spi.student.dto.ExaminationResultDto;
import com.ky.ulearning.spi.student.dto.ExperimentResultDto;
import com.ky.ulearning.student.dao.ExaminationResultDao;
import com.ky.ulearning.student.service.ExaminationResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 测试结果service - 实现
 *
 * @author luyuhao
 * @since 2020/03/11 01:13
 */
@Service
@CacheConfig(cacheNames = "examinationResult")
@Transactional(rollbackFor = Throwable.class, readOnly = true)
public class ExaminationResultServiceImpl extends BaseService implements ExaminationResultService {

    @Autowired
    private ExaminationResultDao examinationResultDao;

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void batchInsert(Map<Integer, List<CourseQuestionVo>> resMap, Long examiningId) {
        List<ExaminationResultDto> examinationResultDtoList = new ArrayList<>();
        for (List<CourseQuestionVo> courseQuestionVoList : resMap.values()) {
            for (CourseQuestionVo courseQuestionVo : courseQuestionVoList) {
                ExaminationResultDto tmp = new ExaminationResultDto();
                tmp.setExaminingId(examiningId);
                tmp.setQuestionId(courseQuestionVo.getId());
                tmp.setCreateBy("system");
                tmp.setUpdateBy("system");
                examinationResultDtoList.add(tmp);
            }
        }
        if (!CollectionUtils.isEmpty(examinationResultDtoList)) {
            examinationResultDao.batchInsert(examinationResultDtoList);
        }
    }

    @Override
    public Map<Integer, List<CourseQuestionVo>> getCourseQuestionVoByExaminingId(Long examiningId) {
        List<CourseQuestionVo> courseQuestionVoList = Optional.ofNullable(examinationResultDao.getCourseQuestionVoByExaminingId(examiningId))
                .orElse(Collections.emptyList());
        Map<Integer, List<CourseQuestionVo>> resMap = new HashMap<>();
        for (CourseQuestionVo courseQuestionVo : courseQuestionVoList) {
            List<CourseQuestionVo> tmpList = resMap.get(courseQuestionVo.getQuestionType());
            if(CollectionUtils.isEmpty(tmpList)){
                tmpList = new ArrayList<>();
                resMap.put(courseQuestionVo.getQuestionType(), tmpList);
            }
            tmpList.add(courseQuestionVo);
        }
        return resMap;
    }
}