package org.javamaster.b2c.core.model;

import lombok.Data;

/**
 * @author yudong
 * @date 2019/8/8
 */
@Data
public class UeditorUploadState {
    private String state;
    private String original;
    private String size;
    private String title;
    private String type;
    private String url;
}
