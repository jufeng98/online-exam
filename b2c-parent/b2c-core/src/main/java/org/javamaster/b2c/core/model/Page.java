package org.javamaster.b2c.core.model;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

/**
 * Created on 2018/12/12.<br/>
 *
 * @author yudong
 */
@Data
public class Page implements Serializable {

    private static final long serialVersionUID = 2775511245499344608L;
    /**
     * 页码，从1开始
     */
    @Range(min = 1)
    private Integer pageNum;
    /**
     * 页数
     */
    @Range(min = 1)
    private Integer pageSize;
    /**
     * 排序
     */
    private String orderBy;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(ToStringStyle.JSON_STYLE);
    }

}
