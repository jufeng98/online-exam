package org.javamaster.b2c.core.model.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * @author yudong
 * @date 2019/7/28
 */
@Data
public class EditUsersForm {
    @NotBlank
    private String username;
    private String gender;
    private String nickname;
    private String mobile;
    private String remark;
    private String picUrl;
}
