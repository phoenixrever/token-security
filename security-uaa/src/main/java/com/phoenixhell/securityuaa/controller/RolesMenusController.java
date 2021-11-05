package com.phoenixhell.securityuaa.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.phoenixhell.securityuaa.entity.RolesMenusEntity;
import com.phoenixhell.securityuaa.service.RolesMenusService;
import com.phoenixhell.common.utils.PageUtils;
import com.phoenixhell.common.utils.R;



/**
 * 角色菜单关联
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2021-11-05 21:04:10
 */
@RestController
@RequestMapping("securityuaa/rolesmenus")
public class RolesMenusController {
    @Autowired
    private RolesMenusService rolesMenusService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("securityuaa:rolesmenus:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = rolesMenusService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{menuId}")
    //@RequiresPermissions("securityuaa:rolesmenus:info")
    public R info(@PathVariable("menuId") Long menuId){
		RolesMenusEntity rolesMenus = rolesMenusService.getById(menuId);

        return R.ok().put("rolesMenus", rolesMenus);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("securityuaa:rolesmenus:save")
    public R save(@RequestBody RolesMenusEntity rolesMenus){
		rolesMenusService.save(rolesMenus);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("securityuaa:rolesmenus:update")
    public R update(@RequestBody RolesMenusEntity rolesMenus){
		rolesMenusService.updateById(rolesMenus);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("securityuaa:rolesmenus:delete")
    public R delete(@RequestBody Long[] menuIds){
		rolesMenusService.removeByIds(Arrays.asList(menuIds));

        return R.ok();
    }

}
