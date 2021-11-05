package com.phoenixhell.securityuaa.service.impl;

import com.phoenixhell.securityuaa.entity.Role;
import com.phoenixhell.securityuaa.mapper.RoleMapper;
import com.phoenixhell.securityuaa.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author phoenixhell
 * @since 2021-11-05
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
