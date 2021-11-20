package com.example.demo.domain;

import lombok.Data;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
public class Measurement {

        @Id
        @GeneratedValue(strategy = IDENTITY)
        private Integer id;

        @Column
        private double co2level;

        private LocalDateTime created;

        @ManyToOne
        private District district;
}
