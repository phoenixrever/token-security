package com.phoenixhell.summoresource.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenixhell.common.utils.PageUtils;
import com.phoenixhell.summoresource.entity.ImageEntity;

import java.util.Map;

/**
 * 
 *
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2021-12-07 20:32:53
 */
public interface ImageService extends IService<ImageEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

