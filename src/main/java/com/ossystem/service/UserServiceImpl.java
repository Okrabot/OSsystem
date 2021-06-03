package com.ossystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ossystem.entity.User;
import com.ossystem.mapper.RoleMapper;
import com.ossystem.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final RoleMapper rolesMapper;

    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, RoleMapper rolesMapper, BCryptPasswordEncoder encoder) {
        this.userMapper = userMapper;
        this.rolesMapper = rolesMapper;
        this.encoder = encoder;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Cannot find the user whose username is: "+ username + "");
        }
        return user;
    }

    @Override
    public void addUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        int result = userMapper.insertUser(user);
    }

    @Override
    public User getUserById(int id) {
      return userMapper.selectUserById(id);
    }
}
