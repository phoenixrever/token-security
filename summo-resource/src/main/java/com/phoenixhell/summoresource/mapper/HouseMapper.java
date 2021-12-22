package com.phoenixhell.summoresource.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.phoenixhell.summoresource.entity.HouseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2021-12-07 20:32:53
 */
@Mapper
public interface HouseMapper extends BaseMapper<HouseEntity> {
    List<HouseEntity> search(@Param("q") String query);

    List<HouseEntity> getOrderPage(@Param("order") String order,@Param("pageSize") int pageSize,@Param("pageNo") int pageNo);
}
