package com.example.demo.dao;


import com.example.demo.domain.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    public List<User> queryAll();

    User doAJAXLogin(User user);

    List<User> pageQuery(Map<String, Object> map);

    int pageQueryCount(Map<String, Object> map);

    void insertUser(User user);

    @Select("select * from t_user where user_id = #{userId}")
    User queryById(Integer userId);

    void update(User user);

    void deleteUserById(Integer userId);

    void deletes(Integer[] userId);

    List<Integer> queryRoleIdsByUserId(Integer userId);

    void insertUserRoles(Map<String, Object> map);

    void deleteUserRoles(Map<String, Object> map);

}
