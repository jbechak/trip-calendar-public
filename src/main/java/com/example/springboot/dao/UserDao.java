package com.example.springboot.dao;

import com.example.springboot.model.UserModel;

import java.util.List;

public interface UserDao {

    List<UserModel> findAll();

    UserModel getUserById(String userId);

    UserModel findByUsername(String username);

    String findIdByUsername(String username);

    boolean create(String username, String password, String role);

    String getRoleById(String id);

    UserModel getUserByIdAndRole(String id, String role);
}
