package com.phoenixhell.securityuaa.config;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @author phoenixhell
 * @since 2021/11/11 0011-下午 3:10
 */

public class UserPage<T> extends Page<T> {
    @Override
    public Page setRecords(List<T> records) {
        return super.setRecords(records);
    }
}
