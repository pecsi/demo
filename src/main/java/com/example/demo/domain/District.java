package com.example.demo.domain;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
public class District {

        @Id
        @GeneratedValue(strategy = IDENTITY)
        private Integer id;

        @Column(nullable = false)
        private String name;

        @ManyToOne
        private City city;
}
