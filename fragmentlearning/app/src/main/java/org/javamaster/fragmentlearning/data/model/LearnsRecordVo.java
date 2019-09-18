package org.javamaster.fragmentlearning.data.model;

/**
 * @author yudong
 */
public class LearnsRecordVo {

    /**
     * 用户名
     */
    private String username;

    /**
     * 关联主题表topics_code
     */
    private String topicsCode;

    /**
     * 关联章节表sections_code
     */
    private String sectionsCode;

    /**
     * 关联知识表knowledges_code
     */
    private String knowledgesCode;

    /**
     * 关联知识点表knowledge_points_code
     */
    private String knowledgePointsCode;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTopicsCode() {
        return topicsCode;
    }

    public void setTopicsCode(String topicsCode) {
        this.topicsCode = topicsCode;
    }

    public String getSectionsCode() {
        return sectionsCode;
    }

    public void setSectionsCode(String sectionsCode) {
        this.sectionsCode = sectionsCode;
    }

    public String getKnowledgesCode() {
        return knowledgesCode;
    }

    public void setKnowledgesCode(String knowledgesCode) {
        this.knowledgesCode = knowledgesCode;
    }

    public String getKnowledgePointsCode() {
        return knowledgePointsCode;
    }

    public void setKnowledgePointsCode(String knowledgePointsCode) {
        this.knowledgePointsCode = knowledgePointsCode;
    }
}