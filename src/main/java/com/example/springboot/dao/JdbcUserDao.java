package com.example.springboot.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.springboot.utility.General;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.springboot.model.UserModel;

@Component
public class JdbcUserDao implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String findIdByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username cannot be null");

        String userId;
        try {
            userId = jdbcTemplate.queryForObject("select id from users where username = ?", String.class, username);
        } catch (EmptyResultDataAccessException e) {
            throw new UsernameNotFoundException("User " + username + " was not found.");
        }

        return userId;
    }

	@Override
	public UserModel getUserById(String userId) {
		String sql = "SELECT * FROM users WHERE id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
		if (results.next()) {
			return mapRowToUser(results);
		} else {
			return null;
		}
	}

    @Override
    public List<UserModel> findAll() {
        List<UserModel> users = new ArrayList<>();
        String sql = "select * from users";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            UserModel user = mapRowToUser(results);
            users.add(user);
        }

        return users;
    }

    @Override
    public UserModel findByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username cannot be null");

        for (UserModel user : this.findAll()) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        throw new UsernameNotFoundException("User " + username + " was not found.");
    }

    @Override
    public boolean create(String username, String password, String email) {
        String id = General.getGuid();
        String insertUserSql = "insert into users (id,username,password_hash,email,role) values (?,?,?,?,?);";
        String password_hash = new BCryptPasswordEncoder().encode(password);
        //String ssRole = role.toUpperCase().startsWith("ROLE_") ? role.toUpperCase() : "ROLE_" + role.toUpperCase();
        String role = "ROLE_USER";
        return jdbcTemplate.update(insertUserSql, id, username, password_hash, email, role) == 1;
    }

    private UserModel mapRowToUser(SqlRowSet rs) {
        UserModel user = new UserModel();
        user.setId(rs.getString("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password_hash"));
        user.setAuthorities(Objects.requireNonNull(rs.getString("role")));
        user.setActivated(true);
        return user;
    }

    @Override
    public String getRoleById(String id) {
        String sql = "SELECT role FROM users WHERE id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if (results.next()) {
            return results.getString("role");
        } else {
            return null;
        }
    }

    @Override
    public UserModel getUserByIdAndRole(String id, String role) {

        //TO DO
        String sql = "SELECT * FROM users WHERE user_id = ? AND role = ?;";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id, role);

        if (rowSet.next()) {
            return mapRowToUser(rowSet);
        }

        return null;
    }

}
