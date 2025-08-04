package com.java.design.patterns.prototype;

public class KYC implements Cloneable {
    private String name;
    private String address;
    private String idProof;

    public KYC(String name, String address, String idProof) {
        this.name = name;
        this.address = address;
        this.idProof = idProof;
    }

    public void showDetails() {
        System.out.println("Name: " + name);
        System.out.println("Address: " + address);
        System.out.println("ID Proof: " + idProof);
    }

    // Clone method
    @Override
    public KYC clone() {
        try {
            return (KYC) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cloning not supported");
        }
    }
}
