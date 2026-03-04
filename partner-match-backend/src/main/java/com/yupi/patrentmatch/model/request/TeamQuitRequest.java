package com.yupi.patrentmatch.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户退出队伍请求体
 *
 * @author shaosao
 */
@Data
public class TeamQuitRequest implements Serializable {

    private static final long serialVersionUID = -24663018187059425L;

    /**
     * id
     */
    private Long teamId;
}