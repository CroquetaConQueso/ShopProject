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

    public String buyCart(){
        if(userFunds < userCart.calculateTotal()){
            return "You do not have enough funds";
        }else{
            userFunds = userFunds - userCart.calculateTotal();
            userCart.emptyCart();
            return "Enjoy your new products and have a nice day!";
        }

    }
}