package com.example.springboot.model;

import com.example.springboot.model.AuthorityModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class UserModel {

   private String id;
   private String username;
   private String email;
   @JsonIgnore
   private String password;
   @JsonIgnore
   private boolean activated;
   private Set<AuthorityModel> authorities = new HashSet<>();

   public UserModel() { }

   public UserModel(String id, String username, String password, String authorities) {
      this.id = id;
      this.username = username;
      this.password = password;
      if(authorities != null) this.setAuthorities(authorities);
      this.activated = true;
   }

   public String getId() {
      return id;
   }
   public void setId(String id) {
      this.id = id;
   }

   public String getUsername() {
      return username;
   }
   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }
   public void setPassword(String password) {
      this.password = password;
   }

   public String getEmail() {
      return email;
   }
   public void setEmail(String email) {
      this.email = email;
   }

   public boolean isActivated() {
      return activated;
   }
   public void setActivated(boolean activated) {
      this.activated = activated;
   }

   public Set<AuthorityModel> getAuthorities() {
      return authorities;
   }
   public void setAuthorities(Set<AuthorityModel> authorities) {
      this.authorities = authorities;
   }

   public void setAuthorities(String authorities) {
      String[] roles = authorities.split(",");
      for(String role : roles) {
         String authority = role.contains("ROLE_") ? role : "ROLE_" + role;
         this.authorities.add(new AuthorityModel(authority));
      }
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      UserModel user = (UserModel) o;
      return id == user.id &&
              activated == user.activated &&
              Objects.equals(username, user.username) &&
              Objects.equals(password, user.password) &&
              Objects.equals(authorities, user.authorities);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, username, password, activated, authorities);
   }

   @Override
   public String toString() {
      return "User{" +
              "id=" + id +
              ", username='" + username + '\'' +
              ", activated=" + activated +
              ", authorities=" + authorities +
              '}';
   }
}
