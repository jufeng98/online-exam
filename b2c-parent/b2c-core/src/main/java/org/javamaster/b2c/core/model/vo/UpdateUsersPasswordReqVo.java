package org.javamaster.b2c.core.model.vo;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;

/**
 * Created on 2018/12/10.<br/>
 *
 * @author yudong
 */
@Data
public class UpdateUsersPasswordReqVo {
    @NotBlank
    private String username;
    @NotBlank
    private String newPassword;
    private String password;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(ToStringStyle.JSON_STYLE);
    }
}
