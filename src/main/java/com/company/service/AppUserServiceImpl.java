package com.company.service;

import com.company.entity.AppUser;
import com.company.entity.Role;
import com.company.repo.AppUserRepo;
import com.company.repo.RoleRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor @Slf4j @Transactional
public class AppUserServiceImpl implements AppUserService, UserDetailsService {
    private final AppUserRepo appUserRepo;
    private final RoleRepo roleRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    private final static String USER_NOT_FOUND_MSG =
            "Username: %s not found in the database!";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> myUserOptional = findUserByUsername(username);

        if (myUserOptional.isEmpty()){
            log.error("Username: {} not found in the database!", username);
            throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username));
        } else{
            log.info("Username: {} found in the database!", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        myUserOptional.get().getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });

        return new User(myUserOptional.get().getUsername(), myUserOptional.get().getPassword(), authorities);
    }

    @Override
    public AppUser saveUser(AppUser user) {
        log.info("Saving new user {} to the database!", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return appUserRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database!", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {} !", roleName, username);
        Optional<AppUser> user = findUserByUsername(username);
        Optional<Role> role = Optional.ofNullable(roleRepo.findByName(roleName));
        System.out.println(user.get());
        System.out.println(role.get());
        if (user.isPresent() && role.isPresent()) {
            user.get().getRoles().add(role.get());
        }
    }

    @Override
    public Optional<AppUser> findUserByUsername(String username) {
        log.info("Fetching user {}", username);
        return Optional.ofNullable(appUserRepo.findByUsername(username));
    }

    @Override
    public Optional<Role> findRoleByRoleName(String roleName) {
        log.info("Fetching role {}", roleName);
        return Optional.ofNullable(roleRepo.findByName(roleName));
    }

    @Override
    public List<Role> getRoles(){
        log.info("Fetching all role!");
        return roleRepo.findAll();
    }

    @Override
    public List<AppUser> getUsers() {
        log.info("Fetching all user!");
        return appUserRepo.findAll();
    }
}
