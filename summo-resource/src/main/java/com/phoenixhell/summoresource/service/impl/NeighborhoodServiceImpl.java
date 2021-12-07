package com.phoenixhell.summoresource.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenixhell.common.utils.PageUtils;
import com.phoenixhell.common.utils.Query;

import com.phoenixhell.summoresource.mapper.NeighborhoodMapper;
import com.phoenixhell.summoresource.entity.NeighborhoodEntity;
import com.phoenixhell.summoresource.service.NeighborhoodService;


@Service("neighborhoodService")
public class NeighborhoodServiceImpl extends ServiceImpl<NeighborhoodMapper, NeighborhoodEntity> implements NeighborhoodService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<NeighborhoodEntity> page = this.page(
                new Query<NeighborhoodEntity>().getPage(params),
                new QueryWrapper<NeighborhoodEntity>()
        );

        return new PageUtils(page);
    }

}