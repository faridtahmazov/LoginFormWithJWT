package com.company;

import com.company.entity.AppUser;
import com.company.entity.Role;
import com.company.entity.util.RoleUtil;
import com.company.service.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class JsonWebTokenApplication {

    public static void main(String[] args) {
        SpringApplication.run(JsonWebTokenApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner runner(AppUserService appUserService){
        return args -> {
            appUserService.saveRole(new Role(null, RoleUtil.ROLE_USER.toString()));
            appUserService.saveRole(new Role(null, RoleUtil.ROLE_MANAGER.toString()));
            appUserService.saveRole(new Role(null, RoleUtil.ROLE_ADMIN.toString()));
            appUserService.saveRole(new Role(null, RoleUtil.ROLE_SUPER_ADMIN.toString()));

            appUserService.saveUser(new AppUser(null, "Farid", "tahmazovfarid",
                    "12345", new ArrayList<>()));
            appUserService.saveUser(new AppUser(null, "Fidan", "suleymanlifidan",
                    "12345", new ArrayList<>()));
            appUserService.saveUser(new AppUser(null, "John", "johncaberg",
                    "12345", new ArrayList<>()));

            appUserService.addRoleToUser("tahmazovfarid", RoleUtil.ROLE_SUPER_ADMIN.toString());
            appUserService.addRoleToUser("suleymanlifidan", RoleUtil.ROLE_ADMIN.toString());
            appUserService.addRoleToUser("johncaberg", RoleUtil.ROLE_USER.toString());
        };
    }

}
