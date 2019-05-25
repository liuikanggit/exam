package com.heo.exam.service.impl;

import com.heo.exam.entity.Question;
import com.heo.exam.enums.ExcelInputEnum;
import com.heo.exam.enums.GradeEnum;
import com.heo.exam.enums.QuestionTypeEnum;
import com.heo.exam.enums.ResultEnum;
import com.heo.exam.exception.ExamException;
import com.heo.exam.exception.ExcelException;
import com.heo.exam.repository.QuestionRepository;
import com.heo.exam.repository.SubjectRepository;
import com.heo.exam.service.QuestionService;
import com.heo.exam.utils.EnumUtil;
import com.heo.exam.utils.ExcelUtil;
import com.heo.exam.utils.ResultVOUtil;
import com.heo.exam.vo.BatchVO;
import com.heo.exam.vo.ErrorDetail;
import com.heo.exam.vo.ResultVO;
import org.apache.logging.log4j.util.Strings;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.*;

/**
 * @author 刘康
 * @create 2019-02-21 13:35
 * @desc 题目项目业务逻辑的实现
 **/
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Value("${system.tempPath}")
    private String systemTempPath;

    @Override
    public ResultVO excelInput(String userId, MultipartFile file) {
        String filename = file.getOriginalFilename();
        Workbook wb;
        try {
            if (ExcelUtil.isExcel2007(filename)) {
                wb = new XSSFWorkbook(file.getInputStream());
            } else if (ExcelUtil.isExcel2003(filename)) {
                wb = new HSSFWorkbook(file.getInputStream());
            } else {
                throw new ExamException(ResultEnum.FILE_TYPE_ERROR);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ExamException(ResultEnum.FILE_NULL);
        }
        Sheet sheet = wb.getSheetAt(0);//获取第一张表

        BatchVO batchVO = new BatchVO();
        List<ErrorDetail> errorDetailList = new ArrayList<>();
        int success = 0;
        for (int i = 2; i <= sheet.getLastRowNum(); i++) {
            // 从第三行开始 第一行是备注，第二行是表头
            try {
                Question question = readRowData(sheet.getRow(i));
                question.setCreatorId(userId);
                if (!questionRepository.existsQuestionByTitle(question.getTitle())) {
                    questionRepository.save(question);
                    success++;
                } else {
                    errorDetailList.add(new ErrorDetail(i, ExcelInputEnum.QUESTION_EXIST));
                }
            } catch (ExcelException e) {
                errorDetailList.add(new ErrorDetail(i, e.getCode(), e.getMessage()));
            } catch (Exception e) {
                e.printStackTrace();
                errorDetailList.add(new ErrorDetail(i, ExcelInputEnum.SQL_ERROR));
            }
        }

        batchVO.setCmdNumber(sheet.getLastRowNum() - 1);
        batchVO.setSuccess(success);
        batchVO.setError(errorDetailList.size());
        batchVO.setErrorDetailList(errorDetailList);

        try {
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (errorDetailList.size() == 0) {
            return ResultVOUtil.success(batchVO);
        } else {
            return ResultVOUtil.error(1, "数据导入有错误", batchVO);
        }
    }

    private Question readRowData(Row row) throws ExcelException {
        Question question = new Question();
        /** 读取科目 */
        Cell cell = row.getCell(0);
        if (cell == null) {
            throw new ExcelException(ExcelInputEnum.SUBJECT_EMPTY);
        }
        String subjectName = cell.getStringCellValue();
        if (Strings.isEmpty(subjectName)) {
            throw new ExcelException(ExcelInputEnum.SUBJECT_EMPTY);
        }
        Integer subjectId = subjectRepository.getOneIdByName(subjectName);
        if (subjectId == -1) {
            throw new ExcelException(ExcelInputEnum.SUBJECT_NO_EXIST);
        }
        question.setSubjectId(subjectId);

        /** 读取年级 */
        cell = row.getCell(1);
        if (cell == null) {
            throw new ExcelException(ExcelInputEnum.GRADE_EMPTY);
        }
        String gradeName = cell.getStringCellValue();
        Integer gradeCode = EnumUtil.getCode(GradeEnum.class, gradeName);
        if (gradeCode == -1) {
            throw new ExcelException(ExcelInputEnum.GRADE_NO_EXIST);
        }
        question.setGrade(gradeCode);

        /** 读取题目类别 */
        cell = row.getCell(2);
        if (cell == null) {
            throw new ExcelException(ExcelInputEnum.QUESTION_TYPE_EMPTY);
        }
        String questionTypeName = cell.getStringCellValue();
        if (Strings.isEmpty(questionTypeName)) {
            throw new ExcelException(ExcelInputEnum.QUESTION_TYPE_EMPTY);
        }
        Integer questionTypeCode = EnumUtil.getCode(QuestionTypeEnum.class, questionTypeName);
        if (questionTypeCode == -1) {
            throw new ExcelException(ExcelInputEnum.QUESTION_TYPE_NO_EXIST);
        }
        question.setType(questionTypeCode);

        /** 题目标题 */
        cell = row.getCell(3);
        String title = cell.getStringCellValue();
        if (Strings.isEmpty(title)) {
            throw new ExcelException(ExcelInputEnum.TITLE_EMPTY);
        }
        question.setTitle(title);

        /** 题目标题图片url  */
        cell = row.getCell(4);
        if (cell != null) {
            String titleImage = cell.getStringCellValue();
            question.setTitle(titleImage);
        }


        /** 答案 */
        cell = row.getCell(5);
        if (cell == null) {
            throw new ExcelException(ExcelInputEnum.ANSWER_EMPTY);
        }
        String answer0 = cell.getStringCellValue();
        if (Strings.isEmpty(answer0)) {
            throw new ExcelException(ExcelInputEnum.ANSWER_EMPTY);
        }
        question.setAnswer0(answer0);

        /** 答案图片 */
        cell = row.getCell(6);
        if (cell != null) {
            String answerImage0 = cell.getStringCellValue();
            question.setAnswerImage0(answerImage0);
        }


        if (questionTypeCode.equals(QuestionTypeEnum.CHOICE_QUESTION) || questionTypeCode.equals(QuestionTypeEnum.JUDGE_QUESTION)) {
            /** 错误答案1 */
            cell = row.getCell(7);
            String answer1 = cell.getStringCellValue();
            question.setAnswer1(answer1);

            /** 错误答案1图片 */
            cell = row.getCell(8);
            if (cell != null) {
                String answerImage1 = cell.getStringCellValue();
                question.setAnswerImage1(answerImage1);
            }

            if (questionTypeCode.equals(QuestionTypeEnum.CHOICE_QUESTION)) {
                /** 错误答案2 */
                cell = row.getCell(9);
                String answer2 = cell.getStringCellValue();
                question.setAnswer2(answer2);

                /** 错误答案2图片 */
                cell = row.getCell(10);
                if (cell != null) {
                    String answerImage2 = cell.getStringCellValue();
                    question.setAnswerImage2(answerImage2);
                }

                /** 错误答案3 */
                cell = row.getCell(11);
                String answer3 = cell.getStringCellValue();
                question.setAnswer2(answer3);

                /** 错误答案3图片 */
                cell = row.getCell(12);
                if (cell != null) {
                    String answerImage3 = cell.getStringCellValue();
                    question.setAnswerImage3(answerImage3);
                }
            }
        }

        cell = row.getCell(13);
        if (cell != null) {
            String analysis = cell.getStringCellValue();
            question.setAnalysis(analysis);
        }

        return question;
    }

    @Override
    public synchronized File getExcelTemplate() {
        try {
            ClassPathResource resource = new ClassPathResource("input.xlsx");
            Path path = Paths.get(systemTempPath, "input.xlsx");

            if (!Files.deleteIfExists(path)) {
                Files.createDirectories(path);
                Set<PosixFilePermission> permissionSet = new HashSet<>();
                permissionSet.add(PosixFilePermission.GROUP_WRITE);
                permissionSet.add(PosixFilePermission.OWNER_EXECUTE);
                Files.setPosixFilePermissions(path, permissionSet);
            }

            Workbook wb = new XSSFWorkbook(resource.getInputStream());

            String password = UUID.randomUUID().toString();
            /** 第二个sheet，放科目*/
            Sheet sheet = wb.getSheetAt(1);
            setRowData(sheet.createRow(0), subjectRepository.findAllName());
            sheet.protectSheet(password);
            /** 第三个sheet，放年级 */
            sheet = wb.getSheetAt(2);
            setRowData(sheet.createRow(0), EnumUtil.getNameList(GradeEnum.class));
            sheet.protectSheet(password);
            /** 第四sheet，放题目类别 */
            sheet = wb.getSheetAt(3);
            sheet.protectSheet(password);
            setRowData(sheet.createRow(0), EnumUtil.getNameList(QuestionTypeEnum.class));

            wb.write(new FileOutputStream(path.toFile()));
            wb.close();
            return path.toFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new ExamException(ResultEnum.FILE_NULL);
    }

    @Override
    public ResultVO inputChoiceQuestion(Integer subjectId, String creatorId, int grade, String title, String answer0, String answer1, String answer2, String answer3, String analysis) {
        Question question = new Question(subjectId, creatorId, grade, title, answer0, answer1, answer2, answer3, analysis);
        questionRepository.save(question);


        return ResultVOUtil.success();
    }

    @Override
    public ResultVO inputJudgementQuestion(Integer subjectId, String creatorId, int grade, String title, boolean answer, String analysis) {
        Question question = new Question(subjectId, creatorId, grade, title, answer, analysis);
        questionRepository.save(question);
        return ResultVOUtil.success();
    }

    @Override
    public ResultVO getAllQuestion() {
        return ResultVOUtil.success(questionRepository.findAllQuestionSimpleVO());
    }


    private void setRowData(Row row, List<String> valueList) {
        if (valueList != null) {
            for (int i = 0; i < valueList.size(); i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(valueList.get(i));
            }
        }
    }


}
