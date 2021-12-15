package com.phoenixhell.summoresource.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

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
@TableName("image")
//@JsonIgnoreProperties(value = {"handler"})
public class ImageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 轮播图图片地址
	 */
	private String imgSrc;
	/**
	 * 
	 */
	private Long houseId;

}
