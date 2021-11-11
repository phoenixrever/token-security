package com.phoenixhell.securityuaa.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.phoenixhell.securityuaa.entity.Router;
import com.phoenixhell.securityuaa.service.MenuService;
import com.phoenixhell.securityuaa.utils.SecurityUtils;
import com.phoenixhell.securityuaa.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.phoenixhell.securityuaa.entity.UserEntity;
import com.phoenixhell.securityuaa.service.UserService;
import com.phoenixhell.common.utils.PageUtils;
import com.phoenixhell.common.utils.R;



/**
 * 系统用户
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2021-11-05 21:04:10
 */
@RestController
@RequestMapping("securityuaa/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;

    /**
     * 用户信息 包括侧边栏菜单
     */
    @GetMapping("/authUserInfo")
    public R getAuthUserInfo(){
        UserVo currentUser = SecurityUtils.getCurrentUser(userService);
        List<String> roles = userService.getRoles(currentUser.getUserId());
        currentUser.setRoles(roles);
        List<Router> routers  =  menuService.getRouters();
       currentUser.setRouters(routers);
        return R.ok().put("data",currentUser);
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("securityuaa:user:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{userId}")
    //@RequiresPermissions("securityuaa:user:info")
    public R info(@PathVariable("userId") Long userId){
		UserEntity user = userService.getById(userId);

        return R.ok().put("user", user);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("securityuaa:user:save")
    public R save(@RequestBody UserEntity user){
		userService.save(user);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("securityuaa:user:update")
    public R update(@RequestBody UserEntity user){
		userService.updateById(user);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("securityuaa:user:delete")
    public R delete(@RequestBody Long[] userIds){
		userService.removeByIds(Arrays.asList(userIds));

        return R.ok();
    }

}
