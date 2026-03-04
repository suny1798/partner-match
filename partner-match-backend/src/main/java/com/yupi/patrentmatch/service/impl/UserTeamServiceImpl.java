package com.yupi.patrentmatch.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.patrentmatch.mapper.UserTeamMapper;
import com.yupi.patrentmatch.service.UserTeamService;
import com.yupi.patrentmatch.model.domain.UserTeam;
import org.springframework.stereotype.Service;

/**
* @author sun
* @description 针对表【user_team(用户_队伍关系)】的数据库操作Service实现
* @createDate 2026-03-03 14:34:03
*/
@Service
public class UserTeamServiceImpl extends ServiceImpl<UserTeamMapper, UserTeam>
    implements UserTeamService {

}




