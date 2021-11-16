package com.phoenixhell.securityuaa.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.phoenixhell.securityuaa.entity.UserEntity;
import com.phoenixhell.securityuaa.utils.SecurityUtils;
import com.phoenixhell.securityuaa.vo.MenuTreeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.phoenixhell.securityuaa.entity.MenuEntity;
import com.phoenixhell.securityuaa.service.MenuService;
import com.phoenixhell.common.utils.PageUtils;
import com.phoenixhell.common.utils.R;



/**
 * 系统菜单
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2021-11-05 21:04:10
 */
@RestController
@RequestMapping("securityuaa/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;


    /**
     *  tree 列表
     */
    @GetMapping("/tree")
    private R getMenuTree(){
        List<MenuTreeVo> menuTreeVos =  menuService.buildMenuTree();
        return R.ok().put("data",menuTreeVos);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("securityuaa:menu:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = menuService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{menuId}")
    //@RequiresPermissions("securityuaa:menu:info")
    public R info(@PathVariable("menuId") Long menuId){
		MenuEntity menu = menuService.getById(menuId);
        return R.ok().put("data", menu);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("securityuaa:menu:save")
    public R save(@RequestBody MenuEntity menu){
		menuService.save(menu);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("securityuaa:menu:update")
    public R update(@RequestBody MenuEntity menu){
        //不允许修改组件name component 隐藏
		menuService.updateById(menu);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/updateDrag")
    //@RequiresPermissions("securityuaa:menu:update")
    public R update(@RequestBody List <MenuEntity> menus){
        menus.forEach(menu ->menuService.updateById(menu));
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("securityuaa:menu:delete")
    //@RequestBody 接收json  前端如果是数组的话 需要JSON.stringify(ids)
    //数组的话 @RequestParam
    public R delete(@RequestBody Long[] menuIds){
		menuService.removeByIds(Arrays.asList(menuIds));

        return R.ok();
    }

}
