package org.javamaster.b2c.core.model.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author yudong
 * @date 2019/7/31
 */
@Data
public class CreateOrEditAuthoritiesVo {
    @NotBlank
    private String authority;
    @NotBlank
    private String authorityName;
    private String remark;
}
