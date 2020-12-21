package com.progressoft.induction.atm;

import java.math.BigDecimal;

public class Account {
    private String accountNmuber;
    private BigDecimal balance;

    public Account(String accountNmuber, BigDecimal balance){

    }

    public String getAccountNmuber() {
        return accountNmuber;
    }

    public void setAccountNmuber(String accountNmuber) {
        this.accountNmuber = accountNmuber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
