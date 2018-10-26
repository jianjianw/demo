package com.example.demo.service.impl;

import com.example.demo.dao.UserMapper;
import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> queryAll() {
        return userMapper.queryAll();
    }

    public List<User> findAll(int id) {
        return userMapper.queryAll();
    }

    public User doAJAXLogin(User user) {
        return userMapper.doAJAXLogin(user);
    }

    public List<User> pageQuery(Map<String, Object> map) {
        return userMapper.pageQuery(map);
    }

    public int pageQueryCount(Map<String, Object> map) {
        return userMapper.pageQueryCount(map);
    }

    public void insertUser(User user) {
        userMapper.insertUser(user);
    }

    public User queryById(Integer userId) {
        return userMapper.queryById(userId);
    }

    public void update(User user) {
        userMapper.update(user);
    }

    public void deleteUserById(Integer userId) {
        userMapper.deleteUserById(userId);
    }

    public void deletes(Integer[] userId) {
        userMapper.deletes(userId);
    }

    public List<Integer> queryRoleIdsByUserId(Integer userId) {
        return userMapper.queryRoleIdsByUserId(userId);
    }

    public void insertUserRoles(Map<String, Object> map) {
        userMapper.insertUserRoles(map);
    }

    public void deleteUserRoles(Map<String, Object> map) {
        userMapper.deleteUserRoles(map);
    }
}
