package org.javamaster.b2c.core.model.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author yudong
 * @date 2019/7/28
 */
@Data
public class CreateOrEditUsersForm {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private String gender;
    private String nickname;
    private String mobile;
    private String email;
    private String remark;
    private String picUrl;
}
