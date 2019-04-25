package com.heo.exam.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author 刘康
 * @create 2019-04-16 16:09
 * @desc 题目
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionVO {

    private Integer id;

    private Integer type;

    private String title;
    private String titleImage;

    private String answer0;
    private String answerImage0;

    private String answer1;
    private String answerImage1;

    private String answer2;
    private String answerImage2;

    private String answer3;
    private String answerImage3;

    private String answer;

    public QuestionVO(Integer id, Integer type, String title, String titleImage, String answer0, String answerImage0, String answer1, String answerImage1, String answer2, String answerImage2, String answer3, String answerImage3, String answer) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.titleImage = titleImage;
        this.answer = answer;

        if (type == 0) {
            List<String[]> list = new ArrayList<>();
            list.add(new String[]{answer0, answerImage0});
            list.add(new String[]{answer1, answerImage1});
            list.add(new String[]{answer2, answerImage2});
            list.add(new String[]{answer3, answerImage3});
            Collections.shuffle(list);
            this.answer0 = list.get(0)[0];
            this.answerImage0 = list.get(0)[1];
            this.answer1 = list.get(1)[0];
            this.answerImage1 = list.get(1)[1];
            this.answer2 = list.get(2)[0];
            this.answerImage2 = list.get(2)[1];
            this.answer3 = list.get(3)[0];
            this.answerImage3 = list.get(3)[1];
        } else if (type == 1) {
            if (Math.random() <= 0.5) {
                this.answer0 = "对";
                this.answer1 = "错";
            } else {
                this.answer0 = "错";
                this.answer1 = "对";
            }
        }
    }
}
