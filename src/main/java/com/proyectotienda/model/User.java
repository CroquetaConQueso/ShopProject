package com.proyectotienda.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class User {
    private String userName;
    private String userPass;
    private float userFunds;
    @Builder.Default
    private Cart userCart = new Cart();
}