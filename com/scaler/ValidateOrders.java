package com.scaler;

public class ValidateOrders {

    int inventory = 100;
    public boolean validateOrders(){
        if(inventory>0){
            inventory--;
            return  true;
        }
        return false;
    }
}
