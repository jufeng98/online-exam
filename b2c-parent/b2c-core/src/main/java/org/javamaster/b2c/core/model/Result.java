package org.javamaster.b2c.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author yudong
 * @date 2019/6/10
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {
    private Boolean success;
    private Integer errorCode;
    private String errorMsg;
    private T data;
    private Long total;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    public Result(T data) {
        this(true, null, null, data, null);
    }

    public Result(Integer errorCode, String errorMsg) {
        this(false, errorCode, errorMsg, null, null);
    }

    public Result(T data, Long total) {
        this(true, null, null, data, total);
    }

    public Result(Boolean success, Integer errorCode, String errorMsg, T data, Long total) {
        this.success = success;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.data = data;
        this.total = total;
    }
}
