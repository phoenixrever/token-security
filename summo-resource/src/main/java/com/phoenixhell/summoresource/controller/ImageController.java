package com.phoenixhell.summoresource.controller;

import com.phoenixhell.common.utils.PageUtils;
import com.phoenixhell.common.utils.R;
import com.phoenixhell.summoresource.entity.ImageEntity;
import com.phoenixhell.summoresource.service.ImageService;
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
@RequestMapping("summoresource/image")
public class ImageController {
    @Autowired
    private ImageService imageService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("summoresource:image:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = imageService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("summoresource:image:info")
    public R info(@PathVariable("id") Long id){
		ImageEntity image = imageService.getById(id);

        return R.ok().put("image", image);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("summoresource:image:save")
    public R save(@RequestBody ImageEntity image){
		imageService.save(image);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("summoresource:image:update")
    public R update(@RequestBody ImageEntity image){
		imageService.updateById(image);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("summoresource:image:delete")
    public R delete(@RequestBody Long[] ids){
		imageService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
