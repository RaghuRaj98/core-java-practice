package com.scaler;

public class CalculateOrderPrice {
    int calculateOrderPrice(Order order){
        int totalcost = 0;
        for(String item: order.items){
            totalcost+=10;
        }
        return totalcost+(int)(totalcost*0.18);
    }
}
