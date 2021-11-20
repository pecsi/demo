package com.example.demo.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
public class City {

        @Id
        @GeneratedValue(strategy = IDENTITY)
        private Integer id;

        @Column(nullable = false)
        private String name;

        @Column(nullable = false)
        private String principal;
}
