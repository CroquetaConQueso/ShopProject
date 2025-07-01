package com.proyectotienda.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private int userId;
    private String userName;
    private String userPass;
    private float userFunds;
    @Builder.Default
    private Cart userCart = new Cart();
}