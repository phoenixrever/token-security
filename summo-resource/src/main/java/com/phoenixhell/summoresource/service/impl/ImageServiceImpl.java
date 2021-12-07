package com.phoenixhell.summoresource.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenixhell.common.utils.PageUtils;
import com.phoenixhell.common.utils.Query;

import com.phoenixhell.summoresource.mapper.ImageMapper;
import com.phoenixhell.summoresource.entity.ImageEntity;
import com.phoenixhell.summoresource.service.ImageService;


@Service("imageService")
public class ImageServiceImpl extends ServiceImpl<ImageMapper, ImageEntity> implements ImageService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ImageEntity> page = this.page(
                new Query<ImageEntity>().getPage(params),
                new QueryWrapper<ImageEntity>()
        );

        return new PageUtils(page);
    }

}