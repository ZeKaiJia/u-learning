package com.ky.ulearning.student.service.impl;

import com.ky.ulearning.common.core.api.service.BaseService;
import com.ky.ulearning.common.core.constant.CommonConstant;
import com.ky.ulearning.common.core.utils.JsonUtil;
import com.ky.ulearning.common.core.utils.StringUtil;
import com.ky.ulearning.spi.common.vo.ExaminationParamVo;
import com.ky.ulearning.spi.common.vo.QuantityVo;
import com.ky.ulearning.spi.student.dto.ExaminationResultDto;
import com.ky.ulearning.spi.student.dto.ExaminationResultSaveDto;
import com.ky.ulearning.spi.student.dto.QuestionAnswerDto;
import com.ky.ulearning.spi.student.vo.CourseQuestionVo;
import com.ky.ulearning.student.dao.ExaminationResultDao;
import com.ky.ulearning.student.dao.StudentExaminationTaskDao;
import com.ky.ulearning.student.service.ExaminationResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

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

    @Autowired
    private StudentExaminationTaskDao studentExaminationTaskDao;

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void batchInsert(Map<Integer, List<CourseQuestionVo>> resMap, Long examiningId) {
        //先清除原有的组卷题目
        examinationResultDao.deleteByExaminingId(examiningId);

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
    public Map<Integer, List<CourseQuestionVo>> getCourseQuestionVoByExaminingId(Long examiningId, List<QuantityVo> quantityVoList) {
        List<CourseQuestionVo> courseQuestionVoList = Optional.ofNullable(examinationResultDao.getCourseQuestionVoByExaminingId(examiningId))
                .orElse(Collections.emptyList());
        Map<Integer, List<CourseQuestionVo>> resMap = new HashMap<>();
        for (QuantityVo quantityVo : quantityVoList) {
            List<CourseQuestionVo> tmpList = resMap.get(quantityVo.getQuestionType());
            if (CollectionUtils.isEmpty(tmpList)) {
                tmpList = new ArrayList<>();
                resMap.put(quantityVo.getQuestionType(), tmpList);
            }
            for (int i = 0; i < courseQuestionVoList.size(); i++) {
                CourseQuestionVo courseQuestionVo = courseQuestionVoList.get(i);
                if (!courseQuestionVo.getQuestionType().equals(quantityVo.getQuestionType())) {
                    continue;
                }
                //设置试题分数
                courseQuestionVo.setGrade(quantityVo.getGrade());
                //若填空题且学生答案为空，则按填空数量补充|#|
                if (StringUtil.isEmpty(courseQuestionVo.getStudentAnswer())
                        && quantityVo.getQuestionType().equals(4)
                        && courseQuestionVo.getQuestionKey().contains(CommonConstant.COURSE_QUESTION_SEPARATE_JUDGE)){
                    String[] split = courseQuestionVo.getQuestionKey().split(CommonConstant.COURSE_QUESTION_SEPARATE);
                    String stuAnswer = "";
                    for(int  j = 0; j < split.length - 1; j++){
                        stuAnswer += CommonConstant.COURSE_QUESTION_SEPARATE_JUDGE;
                    }
                    courseQuestionVo.setStudentAnswer(stuAnswer);
                }
                //加入临时试题集合
                tmpList.add(courseQuestionVo);
                courseQuestionVoList.remove(i--);
            }
        }
        return resMap;
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void batchUpdate(ExaminationResultSaveDto examinationResultSaveDto) {
        List<ExaminationResultDto> examinationResultDtoList = new ArrayList<>();
        for (QuestionAnswerDto tmp : examinationResultSaveDto.getQuestionAnswerDtoList()) {
            ExaminationResultDto examinationResultDto = new ExaminationResultDto();
            examinationResultDto.setQuestionId(tmp.getQuestionId());
            examinationResultDto.setExaminingId(examinationResultSaveDto.getExaminingId());
            examinationResultDto.setStudentAnswer(tmp.getStudentAnswer());
            examinationResultDtoList.add(examinationResultDto);
        }
        if (!CollectionUtils.isEmpty(examinationResultDtoList)) {
            for (ExaminationResultDto examinationResultDto : examinationResultDtoList) {
                examinationResultDao.updateByQuestionIdAndExaminingId(examinationResultDto);
            }
        }
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Throwable.class)
    public void calculationResult(Long examiningId) {
        //查询所有测试结果
        List<ExaminationResultDto> examinationResultDtoList = Optional.ofNullable(examinationResultDao.listByExaminingId(examiningId)).orElse(Collections.emptyList());
        //查询组卷参数
        String examinationParameters = studentExaminationTaskDao.getExaminationParametersById(examiningId);
        ExaminationParamVo examinationParamVo = JsonUtil.parseObject(examinationParameters, ExaminationParamVo.class);
        //抽取类型-分值
        Map<Integer, Double> quantityMap = new HashMap<>();
        for (QuantityVo quantityVo : examinationParamVo.getQuantity()) {
            quantityMap.put(quantityVo.getQuestionType(), quantityVo.getGrade());
        }

        //计算成绩
        List<ExaminationResultDto> resList = new ArrayList<>();
        for (ExaminationResultDto examinationResultDto : examinationResultDtoList) {
            ExaminationResultDto temp = new ExaminationResultDto();
            temp.setId(examinationResultDto.getId());
            //判卷
            if(StringUtil.isEmpty(examinationResultDto.getStudentAnswer())){
                temp.setStudentScore(0.0);
            } else if(CommonConstant.QUESTION_TYPE[3].equals(examinationResultDto.getQuestionType())){
                //填空题需要比较每个空的答案
                List<String> studentAnswerList = StringUtil.strToList(examinationResultDto.getStudentAnswer(), CommonConstant.COURSE_QUESTION_SEPARATE);
                List<String> questionKeyList = StringUtil.strToList(examinationResultDto.getQuestionKey(), CommonConstant.COURSE_QUESTION_SEPARATE);
                //获取分值
                Double questionScore = quantityMap.get(examinationResultDto.getQuestionType());
                Double studentScore = 0.0;
                for(int i = 0; i < studentAnswerList.size(); i++){
                    if(studentAnswerList.get(i).equals(questionKeyList.get(i))){
                        studentScore += questionScore;
                    }
                }
                temp.setStudentScore(studentScore);
            } else {
                temp.setStudentScore(examinationResultDto.getStudentAnswer().equals(examinationResultDto.getQuestionKey()) ? quantityMap.get(examinationResultDto.getQuestionType()) : 0.0);
            }
            resList.add(temp);
        }
        //批量更新测试结果
        if (!CollectionUtils.isEmpty(resList)) {
            for (ExaminationResultDto examinationResultDto : resList) {
                examinationResultDao.update(examinationResultDto);
            }
        }
    }

    @Override
    public Integer getExperimentNumber(Long stuId) {
        return examinationResultDao.getExperimentNumber(stuId);
    }
}
