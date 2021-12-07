package com.phoenixhell.summoresource.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenixhell.common.utils.PageUtils;
import com.phoenixhell.common.utils.Query;

import com.phoenixhell.summoresource.mapper.HouseMapper;
import com.phoenixhell.summoresource.entity.HouseEntity;
import com.phoenixhell.summoresource.service.HouseService;


@Service("houseService")
public class HouseServiceImpl extends ServiceImpl<HouseMapper, HouseEntity> implements HouseService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HouseEntity> page = this.page(
                new Query<HouseEntity>().getPage(params),
                new QueryWrapper<HouseEntity>()
        );

        return new PageUtils(page);
    }

}