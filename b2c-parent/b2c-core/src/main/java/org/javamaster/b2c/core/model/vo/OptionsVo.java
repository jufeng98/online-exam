package org.javamaster.b2c.core.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yudong
 * @date 2019/9/17
 */
@Data
@NoArgsConstructor
public class OptionsVo {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 选项名称
     */
    private String optionName;

    /**
     * 选项排序,仅针对排序题
     */
    private Integer sort;
}
