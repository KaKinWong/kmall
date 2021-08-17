package com.wong.kmall.demo.dao;

import com.wong.kmall.entity.UmsPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author KaKinWong
 * @crate_time 2021/8/5 0:10
 * @description 后台用户与角色管理自定义Dao
 */
public interface UmsAdminRoleRelationDAO {
    /**
     * 获取用户所有权限
     * @param adminId
     * @return
     */
    List<UmsPermission> getPermissionList(@Param("adminId") Long adminId);
}
