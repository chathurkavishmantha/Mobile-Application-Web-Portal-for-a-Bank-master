package com.karawalaya.alliantbankapp.POJO_MODEL.transaction_management;

public class Populate {
    private Customer customer;
    private Account account;

    public Populate() {}

    public Populate(Customer customer, Account account) {
        this.customer = customer;
        this.account = account;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}