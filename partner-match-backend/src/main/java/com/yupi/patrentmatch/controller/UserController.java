package com.yupi.patrentmatch.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.xiaoymin.knife4j.core.util.CollectionUtils;
import com.yupi.patrentmatch.common.BaseResponse;
import com.yupi.patrentmatch.common.ErrorCode;
import com.yupi.patrentmatch.common.ResultUtils;
import com.yupi.patrentmatch.exception.BusinessException;
import com.yupi.patrentmatch.model.domain.User;
import com.yupi.patrentmatch.model.request.UserLoginRequest;
import com.yupi.patrentmatch.model.request.UserRegisterRequest;
import com.yupi.patrentmatch.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.yupi.patrentmatch.contant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户接口
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Slf4j
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"http://localhost:5173"})
@Api(tags = "用户中心")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 用户注册
     *
     * @param userRegisterRequest
     * @return
     */
    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        // 校验
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, planetCode)) {
            return null;
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     *
     * @param userLoginRequest
     * @param request
     * @return
     */
    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    /**
     * 获取当前用户
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "当前用户")
    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        if (loginUser == null) {
            log.info("用户未登录，无法获取当前用户");
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }else {
          log.info("获取当前用户成功：{}---session", loginUser.getId());
          return ResultUtils.success(loginUser);
        }
    }

    @ApiOperation(value = "搜索用户")
    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username, HttpServletRequest request) {
        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH, "缺少管理员权限");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        List<User> userList = userService.list(queryWrapper);
        List<User> list = userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        return ResultUtils.success(list);
    }

    @ApiOperation(value = "推荐用户")
    @GetMapping("/recommend")
    public BaseResponse<List<User>> recommendUsers(HttpServletRequest request) {
        //判断缓存是否已有
        String redisKey = String.format("partner:user:recommend:%s", userService.getLoginUser(request).getId());
        ValueOperations valueOperations = redisTemplate.opsForValue();
        List<User> list = (List<User>) valueOperations.get(redisKey);
        if (list != null) {
            log.info("从Redis缓存中获取推荐信息");
            return ResultUtils.success(list);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        List<User> userList = userService.list(queryWrapper);
        list = userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        //写缓存
        valueOperations.set(redisKey, list, 1, TimeUnit.HOURS);
        log.info("将推荐信息存入Redis");
        return ResultUtils.success(list);
    }


    @PostMapping("/update")
    @ApiOperation(value = "更新用户")
    public BaseResponse<Integer> updateUser(@RequestBody User user, HttpServletRequest request){
        //1.判断用户是否为空
        if (user == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //2.判断用户权限
        User loginUser = userService.getLoginUser(request);
        //3.触发更新
        int res = userService.updateUser(user,loginUser);
        return ResultUtils.success(res);
    }


    @ApiOperation(value = "搜索标签")
    @GetMapping("/search/tags")
    public BaseResponse<List<User>> searchUserByTags(@RequestParam(required = false) List<String> tagsNameList) {
        if (CollectionUtils.isEmpty(tagsNameList)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<User> userList = userService.searchUserByTags(tagsNameList);
        return ResultUtils.success(userList);
    }


    @ApiOperation(value = "删除用户")
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request) {
        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = userService.removeById(id);
        return ResultUtils.success(b);
    }

}
