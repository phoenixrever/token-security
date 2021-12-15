package com.phoenixhell.summoresource.controller;

import com.phoenixhell.common.utils.PageUtils;
import com.phoenixhell.common.utils.R;
import com.phoenixhell.summoresource.entity.HouseEntity;
import com.phoenixhell.summoresource.service.HouseService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;



/**
 * 
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2021-12-07 20:32:53
 */
@RestController
@RequestMapping("summoresource/house")
public class HouseController {
    @Autowired
    private HouseService houseService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("summoresource:house:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = houseService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 自定义联合列表查询
     */
    @RequestMapping("/fullList")
    //@RequiresPermissions("summoresource:house:list")
    public R fullList(@RequestParam Map<String, Object> params){
        PageUtils page = houseService.queryFullPage(params);

        return R.ok().put("page", page);
    }

    /**
     * serach
     */

    @GetMapping("/search")
    public R search(@RequestParam("q") String query){
      List<HouseEntity> houseEntityList =  houseService.search(query);
      return R.ok().put("data",houseEntityList);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("summoresource:house:info")
    public R info(@PathVariable("id") Long id){
		HouseEntity house = houseService.getById(id);

        return R.ok().put("house", house);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("summoresource:house:save")
    public R save(@RequestBody HouseEntity house){
		houseService.save(house);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("summoresource:house:update")
    public R update(@RequestBody HouseEntity house){
		houseService.updateById(house);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("summoresource:house:delete")
    public R delete(@RequestBody Long[] ids){
		houseService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
