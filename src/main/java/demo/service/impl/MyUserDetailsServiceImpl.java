package demo.service.impl;

import demo.dao.UserMapper;
import demo.model.User;
import demo.service.MyUserDetailsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashSet;
import java.util.Set;

/**
 * @author admin
 * 2022/7/13 16:14
 **/
@Service
public class MyUserDetailsServiceImpl implements MyUserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("name", username);
        User user = userMapper.selectOneByExample(example);
        Set<GrantedAuthority> authoritySet = new HashSet<>();
        return null;
    }
}
