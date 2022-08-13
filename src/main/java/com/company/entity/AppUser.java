package com.company.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data @NoArgsConstructor
@AllArgsConstructor @Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppUser {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String name;
    String username;
    String password;
    @ManyToMany(fetch = FetchType.EAGER)
    Collection<Role> roles = new ArrayList<>();
}
