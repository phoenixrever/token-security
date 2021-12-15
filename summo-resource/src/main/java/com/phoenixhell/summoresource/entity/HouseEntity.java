package com.phoenixhell.summoresource.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 
 * 
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2021-12-07 20:32:53
 */
@Data
@TableName("house")
@JsonIgnoreProperties(value = {"handler"})
public class HouseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 房间名称
	 */
	private String title;
	/**
	 * 租金
	 */
	private String price;
	/**
	 * 礼金
	 */
	private String giftPrice;
	/**
	 * 押金
	 */
	private String deposit;
	/**
	 * 面积
	 */
	private String area;
	/**
	 * 築年数
	 */
	private String age;
	/**
	 * 地点
	 */
	private String location;
	/**
	 * 是否可以
	 */
	private String isEnable;
	/**
	 * 房间具体规格
	 */
	private String detailRoom;
	/**
	 * 建造材料
	 */
	private String buildMaterial;
	/**
	 * 楼层
	 */
	private String floor;
	/**
	 * 建造年份
	 */
	private String buildDate;
	/**
	 * 损保
	 */
	private String depreciation;
	/**
	 * 駐車場费用
	 */
	private String carPark;
	/**
	 * 可以入住时间
	 */
	private String checkIn;
	/**
	 * 総戸数
	 */
	private String totalHouse;
	/**
	 * 情報更新日
	 */
	private String houseUpdate;
	/**
	 * 契約期間
	 */
	private String duration;
	/**
	 * 仲介手数料
	 */
	private String commission;
	/**
	 * 保証会社
	 */
	private String companyPrice;
	/**
	 * ほか初期費用
	 */
	private String totalPrice;
	/**
	 * ほか諸費用
	 */
	private String otherPrice;
	/**
	 * 備考
	 */
	private String remarks;
	/**
	 * 经度
	 */
	private String mapLongitude;
	/**
	 * 纬度 
	 */
	private String mapLatitude;
	/**
	 * 房间设施
	 */
	private String roomDecoration;
	/**
	 * summo 链接
	 */
	private String summoLink;
	/**
	 * 步行时间
	 */
	private String walkTime;
	/**
	 * 管理費・共益費	
	 */
	private String managementPrice;
	/**
	 * 几K
	 */
	private String room;
	/**
	 * 向き	
	 */
	private String direction;
	/**
	 * 建物種別	
	 */
	private String classify;
	/**
	 * 
	 */
	private String vrLink;
	/**
	 * 
	 */
	private String vrImage;
	/**
	 * 条件
	 */
	private String requirement;

	@TableField(exist = false)
	private List<ImageEntity> imageEntities;

	@TableField(exist = false)
	private List<NeighborhoodEntity> neighborhoodEntities;

}
