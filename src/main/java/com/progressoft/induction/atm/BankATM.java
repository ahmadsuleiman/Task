package com.progressoft.induction.atm;

import com.progressoft.induction.atm.exceptions.AccountNotFoundException;
import com.progressoft.induction.atm.exceptions.InsufficientFundsException;
import com.progressoft.induction.atm.exceptions.NotEnoughMoneyInATMException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public abstract class BankATM implements ATM,BankingSystem{
    private List<Account> accounts;
    private BigDecimal balance;
    public BankATM(){
        setAccounts(new ArrayList<Account>());
    }


    @Override
    public List<Banknote> withdraw(String accountNumber, BigDecimal amount) {
        try {
            if(isValidAccount(accountNumber))
                throw new AccountNotFoundException();
            if(amount.compareTo(balance)==1)
                throw new NotEnoughMoneyInATMException();
            if(amount.compareTo(getAccountBalance(accountNumber))==1)
                throw new InsufficientFundsException();


        }
        catch(AccountNotFoundException e){
            System.out.println("Account Not Found");
        }
        catch (InsufficientFundsException e){
            System.out.println("Not Enough Money In Balance");
        }
        catch (NotEnoughMoneyInATMException e){
            System.out.println("Not Enough Money In ATM");
        }
        return null;
    }

    @Override
    public BigDecimal getAccountBalance(String accountNumber) {
        int index = accountIndex(accountNumber);
        return accounts.get(index).getBalance();
    }

    @Override
    public void debitAccount(String accountNumber, BigDecimal amount) {
        int index = accountIndex(accountNumber);
        accounts.get(index).setBalance(getAccountBalance(accountNumber).subtract(amount));
    }

    private int accountIndex(String accountNumber){
        for(int i = 0; i<accounts.size();i++){
            if(accounts.get(i).getAccountNmuber().equals(accountNumber))
                return i;
        }
        return -1;
    }

    private boolean isValidAccount(String accountNumber){
        return accountIndex(accountNumber)>=0;
    }

    protected List<Account> getAccounts() {
        return accounts;
    }

    protected void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }
}
