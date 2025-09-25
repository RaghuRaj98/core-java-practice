package com.java.factory;


interface LoanTwo{
    int getInterest();
}
class HDFC implements LoanTwo{

    @Override
    public int getInterest() {
        return 9;
    }
}
class ICICI implements LoanTwo{

    @Override
    public int getInterest() {
        return 9;
    }
}

class LoanFactoryTwo{
    public static LoanTwo getLoan(String type){
        if("HDFC".equals(type)){
            return new HDFC();
        }
        else  if("".equals(type)){
            return  new ICICI();
        }
        throw new IllegalArgumentException("Unknown loan type: ");
    }
}


public class LoanDemoTwo {
    LoanTwo car  =  LoanFactoryTwo.getLoan("HDFC");
}
