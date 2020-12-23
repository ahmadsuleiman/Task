package com.progressoft.induction.atm;

import com.progressoft.induction.atm.exceptions.AccountNotFoundException;
import com.progressoft.induction.atm.exceptions.InsufficientFundsException;
import com.progressoft.induction.atm.exceptions.NotEnoughMoneyInATMException;

import java.math.BigDecimal;
import java.util.*;

public class BankATM implements ATM,BankingSystem{
    private List<Account> accounts;
    private BigDecimal balance;
    private Map<Banknote,Integer> note;

    public BankATM(){
        accounts = new ArrayList<Account>();
        addAccount(new Account("123456789",new BigDecimal(1000)));
        addAccount(new Account("111111111",new BigDecimal(1000)));
        addAccount(new Account("222222222",new BigDecimal(1000)));
        addAccount(new Account("333333333",new BigDecimal(1000)));
        addAccount(new Account("444444444",new BigDecimal(1000)));

        balance = new BigDecimal(2400);
        note = new LinkedHashMap<Banknote,Integer>();
        note.put(Banknote.FIFTY_JOD,10);
        note.put(Banknote.TWENTY_JOD,20);
        note.put(Banknote.TEN_JOD,100);
        note.put(Banknote.FIVE_JOD,100);
    }


    @Override
    public List<Banknote> withdraw(String accountNumber, BigDecimal amount) {

            if(!isValidAccount(accountNumber))
                throw new AccountNotFoundException();
            if(amount.compareTo(getAccountBalance(accountNumber))==1)
                throw new InsufficientFundsException();
            if(amount.compareTo(balance)==1)
                throw new NotEnoughMoneyInATMException();
            List<Banknote> list= new ArrayList<Banknote>();
            BigDecimal tempAmount = new BigDecimal(amount.intValue());

            for(Banknote banknote : note.keySet()){
                int counter = tempAmount.divide(banknote.getValue()).intValue();
                if(counter > note.get(banknote))
                    counter = note.get(banknote);
                note.put(banknote,note.get(banknote)-counter);
                tempAmount = tempAmount.subtract((new BigDecimal(counter)).multiply(banknote.getValue()));
                while (counter--!=0){
                    list.add(banknote);
                }
                if(tempAmount.intValue()<=0)
                    break;
            }
            if(tempAmount.intValue()>0){
                for(int i=0;i<list.size();i++){
                    note.put(list.get(i),note.get(list.get(i))+1);
                }
                throw new NotEnoughMoneyInATMException();
            }
            debitAccount(accountNumber,amount);
            return list;


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
