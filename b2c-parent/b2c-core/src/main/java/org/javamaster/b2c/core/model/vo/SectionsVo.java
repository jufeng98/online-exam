package org.javamaster.b2c.core.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.javamaster.b2c.core.jackson.Base64Deserializer;

/**
 * @author yudong
 * @date 2019/8/7
 */
@Data
public class SectionsVo {
    private Integer id;
    private String sectionsName;
    @JsonDeserialize(using = Base64Deserializer.class)
    private byte[] sectionsCoverImage;
    private String topicsCode;
    private Integer sortOrder;
}
