package com.wong.kmall.demo.service;

import com.wong.kmall.common.util.JwtTokenUtil;
import com.wong.kmall.demo.dao.UmsAdminRoleRelationDao;
import com.wong.kmall.entity.UmsAdmin;
import com.wong.kmall.entity.UmsAdminExample;
import com.wong.kmall.entity.UmsPermission;
import com.wong.kmall.mapper.UmsAdminMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author KaKinWong
 * @crate_time 2021/8/4 20:52
 * @description
 */
@Service
public class UmsAdminService {
    private static final Logger logger = LoggerFactory.getLogger(UmsAdminService.class);
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UmsAdminMapper umsAdminMapper;
    @Resource
    private UmsAdminRoleRelationDao umsAdminRoleRelationDao;

    /**
     * 根据用户名获取后台管理员
     *
     * @param username
     * @return
     */
    public UmsAdmin getAdminByUsername(String username) {
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<UmsAdmin> umsAdminList = umsAdminMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(umsAdminList)) {
            return umsAdminList.get(0);
        }
        return null;
    }

    public UmsAdmin register(UmsAdmin umsAdminParam) {
        UmsAdmin umsAdmin = new UmsAdmin();
        BeanUtils.copyProperties(umsAdminParam, umsAdmin);
        // 查询是否有相同用户名的用户
        UmsAdminExample example = new UmsAdminExample();
        example.createCriteria().andUsernameEqualTo(umsAdmin.getUsername());
        List<UmsAdmin> umsAdminList = umsAdminMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(umsAdminList)) {
            return null;
        }
        umsAdmin.setCreateTime(LocalDateTime.now());
        umsAdmin.setStatus(1);
        // 将密码进行加密操作
        String encodePassword = passwordEncoder.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(encodePassword);
        umsAdminMapper.insertSelective(umsAdmin);
        return umsAdmin;
    }

    /**
     * 登录功能
     *
     * @param username
     * @param password
     * @return 生成的 JWT 的 token
     */
    public String login(String username, String password) {
        String token = null;
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("密码不正确");
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (Exception e) {
            logger.warn("登录异常", e.getMessage());
        }
        return token;
    }

    /**
     * 获取用户的所有权限（包括角色+-权限）
     *
     * @param adminId
     * @return
     */
    public List<UmsPermission> getPermissionList(Long adminId) {
        return umsAdminRoleRelationDao.getPermissionList(adminId);
    }
}
