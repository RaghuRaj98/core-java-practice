package com.java.abstractFactory;

interface Loan{
    void getInterestRate();
}
interface Card{
    void getCardType();
}

class USLoan implements Loan{

    @Override
    public void getInterestRate() {
        System.out.println("US Loan rate 8%");
    }
}
class USCards implements Card{

    @Override
    public void getCardType() {
        System.out.println("US Amex card");
    }
}


class IndiaLoan implements Loan{

    @Override
    public void getInterestRate() {
        System.out.println("India Loan rate 8%");
    }
}
class IndiaCards implements Card{

    @Override
    public void getCardType() {
        System.out.println("India Amex card");
    }
}

interface BankFactory{
    Loan getLoan();
    Card getCard();
}

class USBankFactory implements BankFactory {
    @Override
    public Loan getLoan() {
        return new USLoan();
    }

    @Override
    public Card getCard() {
        return new USCards();
    }
}


public class AbstractFactoryDemo {
}
