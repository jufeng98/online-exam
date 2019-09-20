package org.javamaster.b2c.core.entity;

import java.util.Date;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 答题记录表,请勿手工改动此文件,请使用 mybatis generator
 * 
 * @author mybatis generator
 */
public class AnswersRecord {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 关联考试表exams__code
     */
    private String examsCode;

    /**
     * 关联题目表questions_code
     */
    private String questionsCode;

    /**
     * 本题得分
     */
    private Byte score;

    /**
     * 用户答案的选项id,多选或者排序用英文逗号隔开
     */
    private String answer;

    /**
     * 创建时间
     */
    private Date createTime;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * 获取主键id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取关联考试表exams__code
     */
    public String getExamsCode() {
        return examsCode;
    }

    /**
     * 设置关联考试表exams__code
     */
    public void setExamsCode(String examsCode) {
        this.examsCode = examsCode;
    }

    /**
     * 获取关联题目表questions_code
     */
    public String getQuestionsCode() {
        return questionsCode;
    }

    /**
     * 设置关联题目表questions_code
     */
    public void setQuestionsCode(String questionsCode) {
        this.questionsCode = questionsCode;
    }

    /**
     * 获取本题得分
     */
    public Byte getScore() {
        return score;
    }

    /**
     * 设置本题得分
     */
    public void setScore(Byte score) {
        this.score = score;
    }

    /**
     * 获取用户答案的选项id,多选或者排序用英文逗号隔开
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * 设置用户答案的选项id,多选或者排序用英文逗号隔开
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * 获取创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}