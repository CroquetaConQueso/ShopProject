package com.proyectotienda.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private String userName;
    private String userPass;
}