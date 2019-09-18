package org.javamaster.b2c.core.model.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author yudong
 */
@Data
public class LearnsRecordVo {

    /**
     * 用户名
     */
    @NotBlank
    private String username;

    /**
     * 关联主题表topics_code
     */
    @NotBlank
    private String topicsCode;

    /**
     * 关联章节表sections_code
     */
    @NotBlank
    private String sectionsCode;

    /**
     * 关联知识表knowledges_code
     */
    @NotBlank
    private String knowledgesCode;

    /**
     * 关联知识点表knowledge_points_code
     */
    @NotBlank
    private String knowledgePointsCode;


}