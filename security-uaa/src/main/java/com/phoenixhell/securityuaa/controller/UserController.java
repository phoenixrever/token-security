package com.phoenixhell.securityuaa.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.phoenixhell.securityuaa.entity.Router;
import com.phoenixhell.securityuaa.entity.UsersRoles;
import com.phoenixhell.securityuaa.service.MenuService;
import com.phoenixhell.securityuaa.service.impl.UserServiceImpl;
import com.phoenixhell.securityuaa.utils.SecurityUtils;
import com.phoenixhell.securityuaa.vo.UserVo;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    private UserServiceImpl userServiceImpl;

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
     * 改变激活状态
     */
    @RequestMapping("/{userId}/{status}")
    //@RequiresPermissions("securityuaa:user:list")
    public R changeStaus(@PathVariable Long userId, @PathVariable Boolean status){
        if(userId.equals(1L)){
            return R.error();
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userId);
        userEntity.setEnabled(status);
        userService.updateById(userEntity);
        return R.ok();
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{userId}")
    //@RequiresPermissions("securityuaa:user:info")
    public R info(@PathVariable("userId") Long userId){
		UserVo userVo = userService.getUserVoById(userId);
        
        return R.ok().put("user", userVo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("securityuaa:user:save")
    public R save(@RequestBody UserVo userVo){
		userService.saveUserVo(userVo);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("securityuaa:user:update")
    public R update(@RequestBody UserVo userVo){
      userService.updateByUserVo(userVo);
      return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("securityuaa:user:delete")
    public R delete(@RequestBody Long[] userIds){
        List<Long> list = Arrays.asList(userIds);
        // 1  admin  id
        int index = list.indexOf(1L);
        if(index>-1) {
            return R.error("不能删除admin");
        }
        userService.removeByIds(list);
        return R.ok();
    }

    /**
     * 修改
     */
    @GetMapping("/test")
    //@RequiresPermissions("securityuaa:user:update")
    public R update(){
        ArrayList<String> list = new ArrayList<>();
        list.add("getter");
//        List<String> authoritiesByRoles = userServiceImpl.getAuthoritiesByRoles(list);
//        return R.ok().put("data",authoritiesByRoles);
        userServiceImpl.setRolesByUserId(list,1L);
        return R.ok();
    }
}
