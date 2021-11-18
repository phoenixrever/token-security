package com.phoenixhell.securityuaa.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.phoenixhell.securityuaa.entity.RolesMenusEntity;
import com.phoenixhell.securityuaa.entity.UsersRolesEntity;
import com.phoenixhell.securityuaa.service.RolesMenusService;
import com.phoenixhell.securityuaa.service.UsersRolesService;
import com.phoenixhell.securityuaa.vo.RoleTreeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.phoenixhell.securityuaa.entity.RoleEntity;
import com.phoenixhell.securityuaa.service.RoleService;
import com.phoenixhell.common.utils.PageUtils;
import com.phoenixhell.common.utils.R;



/**
 * 角色表
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2021-11-05 21:04:10
 */
@RestController
@RequestMapping("securityuaa/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private UsersRolesService usersRolesService;

    @Autowired
    private RolesMenusService rolesMenusService;
    /**
     * 列表
     */
    @RequestMapping("/tree/{roleId}")
    //@RequiresPermissions("securityuaa:role:list")
    public R tree(@PathVariable Long roleId){
        RoleTreeVo roleTreeVo = roleService.getTreeByRoleId(roleId);
        return R.ok().put("role",roleTreeVo);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("securityuaa:role:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = roleService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{roleId}")
    //@RequiresPermissions("securityuaa:role:info")
    public R info(@PathVariable("roleId") Long roleId){
		//RoleEntity role = roleService.getRoleWithAllPermissionsById(roleId);
		RoleEntity role = roleService.getById(roleId);
        return R.ok().put("role", role);
    }

    /**
     * 保存 role 关联的permission（menu 菜单）
     */
    @RequestMapping("/savePermissions")
    //@RequiresPermissions("securityuaa:role:save")
    public R savePermissions(@RequestBody List<RolesMenusEntity> rolesMenusEntities){
        rolesMenusService.savePermissions(rolesMenusEntities);
        return R.ok();
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("securityuaa:role:save")
    public R save(@RequestBody RoleEntity role){
		roleService.save(role);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("securityuaa:role:update")
    public R update(@RequestBody RoleEntity role){
		roleService.updateById(role);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("securityuaa:role:delete")
    public R delete(@RequestBody Long[] roleIds){
		roleService.removeByIds(Arrays.asList(roleIds));

        return R.ok();
    }

}
