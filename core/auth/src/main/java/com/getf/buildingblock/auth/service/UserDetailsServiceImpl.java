package com.getf.buildingblock.auth.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(!username.equals("admin")){
            //throw new UsernameNotFoundException("the user is not found");
            return null;
        }else{
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//            // 用户角色也应在数据库中获取
//            String role = "ROLE_ADMIN";
//            authorities.add(new SimpleGrantedAuthority(role));
            // 线上环境应该通过用户名查询数据库获取加密后的密码
            String password = new BCryptPasswordEncoder().encode("123456");
            return new org.springframework.security.core.userdetails.User(username,password, authorities);
        }
    }
}
