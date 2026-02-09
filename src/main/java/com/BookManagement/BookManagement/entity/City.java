package com.BookManagement.BookManagement.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name="cities")
@Data
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(name = "schema_name")
    private String schemaName;
    @Column(name ="active")
    private Boolean isActive;
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

}
