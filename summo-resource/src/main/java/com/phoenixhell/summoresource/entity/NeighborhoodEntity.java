package com.phoenixhell.summoresource.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author phoenixhell
 * @email phoenixrever@gmail.com
 * @date 2021-12-07 20:32:53
 */
@Data
@TableName("neighborhood")
public class NeighborhoodEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 附近设施图片
	 */
	private String img;
	/**
	 * 附近设施说明
	 */
	private String title;
	/**
	 * 
	 */
	private Long houseId;

}
