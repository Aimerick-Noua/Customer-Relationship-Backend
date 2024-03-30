package com.example.crm.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "_user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "phone"),
                @UniqueConstraint(columnNames = "email")
        })

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    @JsonIgnore
    private String password;
    private String address;
    private String phone;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER) // Ensure profile image is retrieved eagerly
    @JoinColumn(name = "profile_image_id")
    private ImageModel profilePicture;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private List<Command> commands=new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Note> notes;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Services> services;

    private LocalDate joinedDate;
    public User(String firstname, String lastname, String phone, String address,String email, String password) {
        this.firstname = firstname;
        this.lastname =lastname;
        this.phone = phone;
        this.address=address;
        this.email = email;
        this.username =email;
        this.password = password;
    }

}
