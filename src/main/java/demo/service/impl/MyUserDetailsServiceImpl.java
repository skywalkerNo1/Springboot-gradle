package demo.service.impl;

import demo.dao.SysUserMapper;
import demo.model.SysPermission;
import demo.model.SysUser;
import demo.service.MyUserDetailsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author admin
 * 2022/7/13 16:14
 **/
@Service
public class MyUserDetailsServiceImpl implements MyUserDetailsService {

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        Example example = new Example(SysUser.class);
        example.createCriteria().andEqualTo("username", username);
        SysUser sysUser = userMapper.selectOneByExample(example);
        if (sysUser == null) {
            return null;
        }
        List<GrantedAuthority> authorityList = new ArrayList<>();
        List<SysPermission> permissionList = userMapper.permissionList(username);
        permissionList.forEach(p -> {
            authorityList.add(new SimpleGrantedAuthority(p.getPermtag()));
        });
        sysUser.setAuthorityList(authorityList);
        return sysUser;
    }
}
