package com.phoenixhell.summoresource.entity;

import lombok.Data;

import java.util.Map;

@Data
public class NotificationEntity {
    private String title;
    private String body;
    private Map<String,String> data;
    private String image;

}
