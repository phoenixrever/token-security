package com.phoenixhell.securityuaa.service.impl;

import com.phoenixhell.securityuaa.entity.Router;
import com.phoenixhell.securityuaa.entity.UserEntity;
import com.phoenixhell.securityuaa.service.UserService;
import com.phoenixhell.securityuaa.utils.SecurityUtils;
import com.phoenixhell.securityuaa.vo.MenuTreeVo;
import com.phoenixhell.securityuaa.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenixhell.common.utils.PageUtils;
import com.phoenixhell.common.utils.Query;

import com.phoenixhell.securityuaa.mapper.MenuMapper;
import com.phoenixhell.securityuaa.entity.MenuEntity;
import com.phoenixhell.securityuaa.service.MenuService;
import org.springframework.util.StringUtils;


@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuEntity> implements MenuService {

    @Autowired
    private UserService userService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MenuEntity> page = this.page(
                new Query<MenuEntity>().getPage(params),
                new QueryWrapper<MenuEntity>()
        );

        return new PageUtils(page);
    }


    @Override
    public List<MenuTreeVo> buildMenuTree() {
       List<MenuEntity> menuEntities = this.list();
        List<MenuTreeVo> menuTreeVos = menuEntities.stream().filter(menuEntity -> menuEntity.getPid() == 0)
                .sorted((menu1, menu2) -> (menu1.getMenuId() == null ? 0 : menu1.getMenuSort()) - (menu2.getMenuSort() == null ? 0 : menu2.getMenuSort()))
                .map(menu -> {
                    MenuTreeVo menuTreeVo = new MenuTreeVo();
                    BeanUtils.copyProperties(menu,menuTreeVo);
                    menuTreeVo.setLabel(menu.getTitle());
                    menuTreeVo.setChildren(getMenuTreeChildren(menu, menuEntities));
                    return menuTreeVo;
                })
                .collect(Collectors.toList());
        return menuTreeVos;
    }

    private List<MenuTreeVo> getMenuTreeChildren(MenuEntity menu, List<MenuEntity> menuEntities) {
        List<MenuTreeVo> childMenuTreeVos = menuEntities.stream().filter(menuEntity -> menuEntity.getPid().equals(menu.getMenuId()))
                .sorted((menu1, menu2) -> (menu1.getMenuSort() == null ? 0 : menu1.getMenuSort()) - (menu2.getMenuSort() == null ? 0 : menu2.getMenuSort()))
                .map(childMenu -> {
                    MenuTreeVo childMenuTreeVo = new MenuTreeVo();
                    BeanUtils.copyProperties(childMenu,childMenuTreeVo);
                    childMenuTreeVo.setLabel(childMenu.getTitle());
                    childMenuTreeVo.setChildren(getMenuTreeChildren(childMenu, menuEntities));
                    return childMenuTreeVo;
                }).collect(Collectors.toList());
        return childMenuTreeVos;
    }


    @Override
    public List<Router> getRouters() {
        //List<String> authorities = userService.getStringAuthorities("admin");
        List<String> authorities = SecurityUtils.getAuthorities();
        List<MenuEntity> menuEntities = this.list();
        List<Router> routers = menuEntities.stream().filter(menuEntity -> menuEntity.getPid() == 0)
                .filter(menuItem-> StringUtils.isEmpty(menuItem.getPermission()) || authorities.contains(menuItem.getPermission()))
                .map(menu -> {
                    Router router = new Router();
                    router.setName(menu.getName());
                    router.setPath(menu.getPath());
                    router.setComponent(menu.getComponent());
                    router.setHidden(menu.getHidden());
                    HashMap<String, String> map = new HashMap<>();
                    map.put("title", menu.getTitle());
                    router.setMeta(map);
                    router.setChildren(getChildren(menu, menuEntities));
                    return router;
                })
                .collect(Collectors.toList());
        return routers;
    }


    /*
        private String name;
        private String path;
        private String component;
        private Boolean hidden;
        private Boolean isAlwaysShow;
        private Map<String,String> meta;
        private Router children;
     */
    //递归查找所有子分类  最后一步list为空  menu自然也为null  所欲空指针异常
    private List<Router> getChildren(MenuEntity root, List<MenuEntity> list) {
        //List<String> authorities = userService.getStringAuthorities("admin");
        List<String> authorities = SecurityUtils.getAuthorities();
        List<Router> childRouters = list.stream().filter(menuEntity -> root.getMenuId().equals(menuEntity.getPid()))
                .filter(menuItem-> StringUtils.isEmpty(menuItem.getPermission()) || authorities.contains(menuItem.getPermission()))
                .sorted((menu1, menu2) -> (menu1.getMenuSort() == null ? 0 : menu1.getMenuSort()) - (menu2.getMenuSort() == null ? 0 : menu2.getMenuSort()))
                .map(menu -> {
                    Router childRouter = new Router();
                    childRouter.setName(menu.getName());
                    childRouter.setPath(menu.getPath());
                    childRouter.setComponent(menu.getComponent());
                    childRouter.setHidden(menu.getHidden());
                    HashMap<String, String> map = new HashMap<>();
                    map.put("title", menu.getTitle());
                    childRouter.setMeta(map);
                    childRouter.setChildren(getChildren(menu, list));
                    return childRouter;
                }).collect(Collectors.toList());
            return childRouters;
    }
}