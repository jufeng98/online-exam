package org.javamaster.fragmentlearning.data.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.litepal.crud.LitePalSupport;

/**
 * @author yudong
 * @date 2019/09/18
 */
public class SectionsProgressVo extends LitePalSupport {
    private String username;
    private String topicsCode;
    private String sectionsCode;
    private Integer progress;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

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

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }
}
