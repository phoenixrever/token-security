package com.phoenixhell.securityuaa.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.phoenixhell.securityuaa.entity.UsersRolesEntity;
import com.phoenixhell.securityuaa.service.UsersRolesService;
import com.phoenixhell.common.utils.PageUtils;
import com.phoenixhell.common.utils.R;



/**
 * 用户角色关联
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2021-11-05 21:04:10
 */
@RestController
@RequestMapping("securityuaa/usersroles")
public class UsersRolesController {
    @Autowired
    private UsersRolesService usersRolesService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("securityuaa:usersroles:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = usersRolesService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{userId}")
    //@RequiresPermissions("securityuaa:usersroles:info")
    public R info(@PathVariable("userId") Long userId){
		UsersRolesEntity usersRoles = usersRolesService.getById(userId);

        return R.ok().put("usersRoles", usersRoles);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("securityuaa:usersroles:save")
    public R save(@RequestBody UsersRolesEntity usersRoles){
		usersRolesService.save(usersRoles);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("securityuaa:usersroles:update")
    public R update(@RequestBody UsersRolesEntity usersRoles){
		usersRolesService.updateById(usersRoles);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("securityuaa:usersroles:delete")
    public R delete(@RequestBody Long[] userIds){
		usersRolesService.removeByIds(Arrays.asList(userIds));

        return R.ok();
    }

}
