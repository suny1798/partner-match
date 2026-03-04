package com.yupi.patrentmatch.common;


import lombok.Data;

import java.io.Serializable;

@Data
public class PageRequest implements Serializable {

    private static final long serialVersionUID = 1234567898765436543L;

    /**
     * 页码
     */
    protected Integer pageNum = 1;

    /**
     * 页大小
     */
    protected Integer pageSize = 10;
}
