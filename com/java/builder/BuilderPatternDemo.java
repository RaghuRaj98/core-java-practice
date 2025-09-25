package com.java.builder;

class Customer{
    // Required fields
    private final String name;
    private final String accountNumber;

    // Optional fields
    private final String email;
    private final String phone;
    private final String nominee;

    private Customer(Builder builder) {
        this.name = builder.name;
        this.accountNumber = builder.accountNumber;
        this.email = builder.email;
        this.phone = builder.phone;
        this.nominee = builder.nominee;
    }

    @Override
    public String toString() {
        return "Customer [name=" + name + ", accountNumber=" + accountNumber +
                ", email=" + email + ", phone=" + phone + ", nominee=" + nominee + "]";
    }

    public static class Builder {
        private final String name;
        private final String accountNumber;
        private String email;
        private String phone;
        private String nominee;

        public Builder(String name, String accountNumber) {
            this.name = name;
            this.accountNumber = accountNumber;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder nominee(String nominee) {
            this.nominee = nominee;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }
    }
}

public class BuilderPatternDemo {
    Customer c1 = new Customer.Builder("Alice", "ACC123").build();
}
