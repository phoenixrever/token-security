package com.phoenixhell.summoresource.mapper;

import com.phoenixhell.summoresource.entity.ImageEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2021-12-07 20:32:53
 */
@Mapper
public interface ImageMapper extends BaseMapper<ImageEntity> {
        List<ImageEntity> getImageByHouseId(@Param("houseId") Long houseId);
}
