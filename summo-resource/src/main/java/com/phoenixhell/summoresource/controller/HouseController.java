package com.phoenixhell.summoresource.controller;

import com.phoenixhell.common.utils.PageUtils;
import com.phoenixhell.common.utils.R;
import com.phoenixhell.summoresource.entity.HouseEntity;
import com.phoenixhell.summoresource.entity.ImageEntity;
import com.phoenixhell.summoresource.entity.NeighborhoodEntity;
import com.phoenixhell.summoresource.service.HouseService;
import com.phoenixhell.summoresource.service.ImageService;
import com.phoenixhell.summoresource.service.NeighborhoodService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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

    @Autowired
    private ImageService imageService;
    @Autowired
    private NeighborhoodService neighborhoodService;

    /**
     * favorite 列表
     */
    @RequestMapping("/list/favorite")
    public R list(){
        List<HouseEntity> houseEntityList = houseService.query().eq("is_favorite",1).list();
        houseEntityList.forEach(houseEntity -> {
            List<ImageEntity> imageEntities = imageService.query().eq("house_id", houseEntity.getId()).list();
            List<NeighborhoodEntity> neighborhoodEntities = neighborhoodService.query().eq("house_id", houseEntity.getId()).list();
            houseEntity.setImageEntities(imageEntities);
            houseEntity.setNeighborhoodEntities(neighborhoodEntities);
        });
        return R.ok().put("list", houseEntityList);
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

    /**
     * 删除
     */
    @RequestMapping("/addToFavorite/{houseId}")
    public R addToFavorite(@PathVariable("houseId") Long houseId){
        HouseEntity houseEntity = new HouseEntity();
        houseEntity.setId(houseId);
        houseEntity.setIsFavorite(1);
        houseService.updateById(houseEntity);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/removeFavorite/{houseId}")
    public R removeFavorite(@PathVariable("houseId") Long houseId){
        HouseEntity houseEntity = new HouseEntity();
        houseEntity.setId(houseId);
        houseEntity.setIsFavorite(0);
        houseService.updateById(houseEntity);
        return R.ok();
    }
}
