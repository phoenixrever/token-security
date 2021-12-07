package com.phoenixhell.summoresource.controller;

import com.phoenixhell.common.utils.PageUtils;
import com.phoenixhell.common.utils.R;
import com.phoenixhell.summoresource.entity.NeighborhoodEntity;
import com.phoenixhell.summoresource.service.NeighborhoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2021-12-07 20:32:53
 */
@RestController
@RequestMapping("summoresource/neighborhood")
public class NeighborhoodController {
    @Autowired
    private NeighborhoodService neighborhoodService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("summoresource:neighborhood:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = neighborhoodService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("summoresource:neighborhood:info")
    public R info(@PathVariable("id") Long id){
		NeighborhoodEntity neighborhood = neighborhoodService.getById(id);

        return R.ok().put("neighborhood", neighborhood);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("summoresource:neighborhood:save")
    public R save(@RequestBody NeighborhoodEntity neighborhood){
		neighborhoodService.save(neighborhood);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("summoresource:neighborhood:update")
    public R update(@RequestBody NeighborhoodEntity neighborhood){
		neighborhoodService.updateById(neighborhood);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("summoresource:neighborhood:delete")
    public R delete(@RequestBody Long[] ids){
		neighborhoodService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
