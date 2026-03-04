package com.yupi.patrentmatch.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.patrentmatch.model.domain.Team;
import com.yupi.patrentmatch.model.domain.User;
import com.yupi.patrentmatch.model.dto.TeamQuery;
import com.yupi.patrentmatch.model.request.TeamJoinRequest;
import com.yupi.patrentmatch.model.request.TeamQuitRequest;
import com.yupi.patrentmatch.model.vo.TeamUserVO;

import java.util.List;

/**
* @author sun
* @description 针对表【team(队伍)】的数据库操作Service
* @createDate 2026-03-03 14:31:41
*/
public interface TeamService extends IService<Team> {

    /**
     * 创建队伍
     * @param team
     * @param currentUser
     * @return
     */
    long addTeam(Team team, User currentUser);

    /**
     * 查询队伍列表
     * @param teamQuery
     * @return
     */
    List<TeamUserVO> listTeam(TeamQuery teamQuery);


    /**
     * 更新队伍
     * @param team
     * @return
     */
    boolean updateTeam(Team team);

    /**
     * 用户加入队伍
     * @param teamJoinRequest
     * @param loginUser
     * @return
     */
    boolean joinTeam(TeamJoinRequest teamJoinRequest, User loginUser);

    /**
     * 用户退出队伍
     * @param teamquitRequest
     * @param loginUser
     * @return
     */
    boolean quitTeam(TeamQuitRequest teamquitRequest, User loginUser);

    /**
     * 删除队伍
     * @param id
     * @return
     */
    Boolean deleteTeam(long id, User loginUser);

    /**
     * 模糊查询队伍
     * @param teamQuery
     * @return
     */
    List<TeamUserVO> listTeamByKey(TeamQuery teamQuery);
}
