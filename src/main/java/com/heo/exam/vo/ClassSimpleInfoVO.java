package com.heo.exam.vo;

import com.heo.exam.utils.DateUtil;
import lombok.Data;

import java.util.Date;
import java.util.Objects;

/**
 * @author 刘康
 * @create 2019-03-27 11:29
 * @desc 班级简要信息
 **/
@Data
public class ClassSimpleInfoVO {

    /** 班级id */
    private String id;

    /** 班级名称 */
    private String name;

    /**  班级头像 */
    private String avatar;

    /** 是否是创建者 */
    private boolean isCreator;

    /** 加入日期 */
    private String joinDate;

    public ClassSimpleInfoVO(){}

    public ClassSimpleInfoVO(String id, String name, String avatar, String userId, String creatorId, Date joinDate){
        this.id=id;
        this.name = name;
        this.avatar = avatar;
        this.isCreator = Objects.equals(userId,creatorId);
        this.joinDate = DateUtil.formatter(joinDate,"yyyy-MM-dd HH:mm");
    }

}
