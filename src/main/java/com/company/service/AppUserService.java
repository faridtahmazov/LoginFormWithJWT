package com.company.service;

import com.company.entity.AppUser;
import com.company.entity.Role;

import java.util.List;
import java.util.Optional;

public interface AppUserService {
    AppUser saveUser(AppUser user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    Optional<AppUser> findUserByUsername(String username);
    Optional<Role> findRoleByRoleName(String roleName);
    List<Role> getRoles();
    List<AppUser> getUsers();
}
