package com.yupi.patrentmatch.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.patrentmatch.common.BaseResponse;
import com.yupi.patrentmatch.common.ErrorCode;
import com.yupi.patrentmatch.common.ResultUtils;
import com.yupi.patrentmatch.exception.BusinessException;
import com.yupi.patrentmatch.model.domain.Team;
import com.yupi.patrentmatch.model.domain.User;
import com.yupi.patrentmatch.model.dto.TeamQuery;
import com.yupi.patrentmatch.model.request.TeamJoinRequest;
import com.yupi.patrentmatch.model.request.TeamQuitRequest;
import com.yupi.patrentmatch.model.vo.TeamUserVO;
import com.yupi.patrentmatch.service.TeamService;
import com.yupi.patrentmatch.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 队伍接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Slf4j
@RestController
@RequestMapping("/team")
@CrossOrigin(origins = {"http://localhost:5173"})
@Api(tags = "队伍接口")
public class TeamController {

    @Resource
    private UserService userService;

    @Resource
    private TeamService teamService;


    @PostMapping("/add")
    @ApiOperation(value = "创建队伍")
    public BaseResponse<Long> addTeam(@RequestBody Team team, HttpServletRequest request) {
        if (team == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        long addedTeamId = teamService.addTeam(team, loginUser);
//      boolean save = teamService.save(team);
        if (addedTeamId <= 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "创建队伍失败");
        }
        return ResultUtils.success(addedTeamId);
    }


    @PostMapping("/update")
    @ApiOperation(value = "修改队伍")
    public BaseResponse<Boolean> updateTeam(@RequestBody Team team) {
        if (team == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = teamService.updateTeam(team);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新队伍失败");
        }
        log.info("修改队伍成功");
        return ResultUtils.success(true);
    }

    @PostMapping("/get")
    @ApiOperation(value = "查询队伍")
    public BaseResponse<Team> getTeam(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Team teamById = teamService.getById(id);
        if (teamById == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        return ResultUtils.success(teamById);
    }

    @PostMapping("/join")
    @ApiOperation(value = "加入队伍")
    public BaseResponse<Boolean> getTeam(@RequestBody TeamJoinRequest teamJoinRequest, HttpServletRequest request) {
        if (teamJoinRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        boolean result = teamService.joinTeam(teamJoinRequest, loginUser);
        log.info("用户：{}加入队伍成功", loginUser.getUsername());
        return ResultUtils.success(result);
    }

    @PostMapping("/quit")
    @ApiOperation(value = "退出队伍")
    public BaseResponse<Boolean> quitTeam(@RequestBody TeamQuitRequest teamquitRequest, HttpServletRequest request) {
        if (teamquitRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        boolean result = teamService.quitTeam(teamquitRequest, loginUser);
        log.info("用户：{}退出队伍成功", loginUser.getId());
        return ResultUtils.success(result);
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除队伍")
    public BaseResponse<Boolean> quitTeam(@RequestBody long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        Boolean result = teamService.deleteTeam(id, loginUser);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "删除队伍失败");
        }
        log.info("删除队伍成功");
        return ResultUtils.success(true);
    }


    @PostMapping("/list")
    @ApiOperation(value = "查询所有队伍")
    public BaseResponse<List<TeamUserVO>> listAllTeam(TeamQuery teamQuery) {
        if (teamQuery == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<TeamUserVO> teamList = teamService.listTeam(teamQuery);
        log.info("查询所有队伍信息");
        return ResultUtils.success(teamList);
    }

    @PostMapping("/list/team")
    @ApiOperation(value = "模糊查询队伍")
    public BaseResponse<List<TeamUserVO>> listTeamByKeys(@RequestBody TeamQuery teamQuery) {
        if (teamQuery == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<TeamUserVO> teamList = teamService.listTeamByKey(teamQuery);
        log.info("模糊查询队伍信息");
        return ResultUtils.success(teamList);
    }



    @GetMapping("/list/page")
    @ApiOperation(value = "分页查询所有队伍")
    public BaseResponse<Page<Team>> listPageTeam(TeamQuery teamQuery) {
        if (teamQuery == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Team team = new Team();
        BeanUtils.copyProperties(teamQuery, team);

        Page<Team> page = new Page<>(teamQuery.getPageNum(), teamQuery.getPageSize());
        QueryWrapper<Team> queryWrapper = new QueryWrapper<>(team);
        Page<Team> pageRes = teamService.page(page, queryWrapper);
        return ResultUtils.success(pageRes);
    }


}
