package com.phoenixhell.summoresource.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.phoenixhell.common.utils.Constant;
import com.phoenixhell.summoresource.entity.ImageEntity;
import com.phoenixhell.summoresource.entity.NeighborhoodEntity;
import com.phoenixhell.summoresource.service.ImageService;
import com.phoenixhell.summoresource.service.NeighborhoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenixhell.common.utils.PageUtils;
import com.phoenixhell.common.utils.Query;

import com.phoenixhell.summoresource.mapper.HouseMapper;
import com.phoenixhell.summoresource.entity.HouseEntity;
import com.phoenixhell.summoresource.service.HouseService;
import org.springframework.util.StringUtils;


@Service("houseService")
public class HouseServiceImpl extends ServiceImpl<HouseMapper, HouseEntity> implements HouseService {
    @Autowired
    private  ImageService imageService;
    @Autowired
    private NeighborhoodService neighborhoodService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HouseEntity> page = this.page(
                new Query<HouseEntity>().getPage(params),
                new QueryWrapper<HouseEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryFullPage(Map<String, Object> params) {
        //
        //IPage<HouseEntity> page = this.page(
        //        new Query<HouseEntity>().getPage(params),
        //        new QueryWrapper<HouseEntity>()
        //);
        //
        //List<HouseEntity> list = page.getRecords().stream().map(item -> {
        //    List<ImageEntity> imageEntities = imageService.query().eq("house_id", item.getId()).list();
        //    item.setImageEntities(imageEntities);
        //    List<NeighborhoodEntity> neighborhoodEntities = neighborhoodService.query().eq("house_id", item.getId()).list();
        //    item.setNeighborhoodEntities(neighborhoodEntities);
        //    return item;
        //}).collect(Collectors.toList());
        //
        //page.setRecords(list);
        //
        //return new PageUtils(page);


        //#需求：每页显示pageSize条记录，此时显示第pageNo页：
        //#公式：LIMIT (pageNo-1) * pageSize,pageSize;
        int pageSize=10;
        int pageNo=1;
        String order="price";
        String giftPrice=null;
        String deposit=null;
        String vrLink=null;
        List<String> classify=null;
        List<String> buildMaterial=null;
        if(params.get("pageSize")!=null){
             pageSize = Integer.parseInt((String) params.get("pageSize"));
        }
        if(params.get("pageNo")!=null){
            pageNo = Integer.parseInt((String) params.get("pageNo"));
        }
        if(params.get("order")!=null){
            order = (String) params.get("order");
        }

        if(!StringUtils.isEmpty(params.get("classify"))){
            classify = Arrays.asList(params.get("classify").toString().split(","));
        }

        if(!StringUtils.isEmpty(params.get("build_material"))){
            buildMaterial = Arrays.asList(params.get("build_material").toString().split(","));
        }

        if(params.get("giftPrice")!=null){
            giftPrice= (String) params.get("giftPrice");
        }

        if(params.get("deposit")!=null){
            deposit= (String) params.get("deposit");
        }

        if(params.get("vr_link")!=null){
            vrLink= (String) params.get("vr_link");
        }
        System.out.println(StringUtils.isEmpty(params.get("build_material")));
        List<HouseEntity> houseEntities =  baseMapper.getOrderPage(order,pageSize,pageNo,classify,buildMaterial,giftPrice,deposit,vrLink);
        PageUtils pageUtils = new PageUtils(houseEntities, houseEntities.size(), pageSize, pageNo);
        return pageUtils;
    }

    @Override
    public List<HouseEntity> search(String query) {
        List<HouseEntity> houseEntities = baseMapper.search(query);
        return houseEntities;
    }
}