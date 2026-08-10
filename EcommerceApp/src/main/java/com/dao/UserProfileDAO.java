package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.entity.UserProfile;

/**
 * Data Access Object for handling User Profile CRUD operations.
 */
public class UserProfileDAO {
    private Connection conn;

    public UserProfileDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Create user profile table if not exists
     */
    public void initTable() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS user_profile ("
                    + "email VARCHAR(100) PRIMARY KEY, "
                    + "fullName VARCHAR(100), "
                    + "phone VARCHAR(30), "
                    + "address VARCHAR(255), "
                    + "city VARCHAR(100), "
                    + "zipCode VARCHAR(20), "
                    + "avatarUrl VARCHAR(255), "
                    + "bio VARCHAR(500)"
                    + ")";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get profile by user email
     */
    public UserProfile getProfileByEmail(String email) {
        initTable();
        UserProfile profile = null;
        try {
            String sql = "SELECT * FROM user_profile WHERE email = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                profile = new UserProfile(
                    rs.getString("email"),
                    rs.getString("fullName"),
                    rs.getString("phone"),
                    rs.getString("address"),
                    rs.getString("city"),
                    rs.getString("zipCode"),
                    rs.getString("avatarUrl"),
                    rs.getString("bio")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return profile;
    }

    /**
     * Insert or update user profile
     */
    public boolean saveOrUpdateProfile(UserProfile profile) {
        initTable();
        boolean success = false;
        try {
            String checkSql = "SELECT email FROM user_profile WHERE email = ?";
            PreparedStatement checkPs = conn.prepareStatement(checkSql);
            checkPs.setString(1, profile.getEmail());
            ResultSet rs = checkPs.executeQuery();

            String sql;
            if (rs.next()) {
                sql = "UPDATE user_profile SET fullName=?, phone=?, address=?, city=?, zipCode=?, avatarUrl=?, bio=? WHERE email=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, profile.getFullName());
                ps.setString(2, profile.getPhone());
                ps.setString(3, profile.getAddress());
                ps.setString(4, profile.getCity());
                ps.setString(5, profile.getZipCode());
                ps.setString(6, profile.getAvatarUrl());
                ps.setString(7, profile.getBio());
                ps.setString(8, profile.getEmail());
                int i = ps.executeUpdate();
                if (i > 0) success = true;
            } else {
                sql = "INSERT INTO user_profile (email, fullName, phone, address, city, zipCode, avatarUrl, bio) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, profile.getEmail());
                ps.setString(2, profile.getFullName());
                ps.setString(3, profile.getPhone());
                ps.setString(4, profile.getAddress());
                ps.setString(5, profile.getCity());
                ps.setString(6, profile.getZipCode());
                ps.setString(7, profile.getAvatarUrl());
                ps.setString(8, profile.getBio());
                int i = ps.executeUpdate();
                if (i > 0) success = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }
}
