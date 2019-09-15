package org.javamaster.b2c.core.model.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author yudong
 * @date 2019/7/28
 */
@Data
public class CreateUsersForm {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private String gender;
    private String nickname;
    private String mobile;
    @NotBlank
    @Email(regexp = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$")
    private String email;
    private String remark;
    private String picUrl;
}
