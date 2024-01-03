package com.example.crm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Command {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Status status;
    private float totalAmount;
    private LocalDate dateCommand;
    @ManyToOne // Many commands can be associated with one user
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany
    private List<Product> products;
}
