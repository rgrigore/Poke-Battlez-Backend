package com.example.pokebattlez.model.entity;

import com.example.pokebattlez.model.request.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    @ElementCollection
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Transient
    public User generateUser() {
        return new User(this.id, this.username);
    }
}
