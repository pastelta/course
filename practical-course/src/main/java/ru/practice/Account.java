package ru.practice;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Objects;

public class Account {
    private String owner;
    private HashMap<CurrencyCode, Integer> currencyHashMap;
    private Deque<Manageable> deque = new ArrayDeque<>();

    public Account(String owner) {
        if(owner == null || owner.trim().isEmpty()) throw new IllegalArgumentException();
        this.owner = owner;
        this.currencyHashMap = new HashMap<>();
    }
    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        if(owner == null || owner.trim().isEmpty()) throw new IllegalArgumentException();
        String ownerCopy = this.owner;
        this.deque.push(()->this.owner=ownerCopy);
        this.owner = owner;
    }
    public HashMap<CurrencyCode, Integer> getCurrencyHashMap() {
        return new HashMap<>(currencyHashMap);
    }
    public void changeCurrency(CurrencyCode currencyCode, Integer cnt){
        if(cnt<0) throw new IllegalArgumentException();

        if(currencyHashMap.containsKey(currencyCode)){
            Integer cntCopy = this.currencyHashMap.get(currencyCode);
            this.deque.push(()->{this.currencyHashMap.put(currencyCode,cntCopy);});
        } else {
            this.deque.push(()->{this.currencyHashMap.remove(currencyCode);});
        }
        this.currencyHashMap.put(currencyCode, cnt);
    }
    public Account undo() throws OperationAttemptException {
        if(deque.isEmpty()) throw new OperationAttemptException("Error when trying to undo changes");
        deque.pop().perform();
        return this;
    }
    public Loadable save(){
        return new Snapshot();
    }
    private class Snapshot implements Loadable{
        private String owner;
        private HashMap<CurrencyCode, Integer> currencyHashMap;

        public Snapshot() {
            this.owner = Account.this.owner;
            this.currencyHashMap = new HashMap<>(Account.this.currencyHashMap);
        }
        @Override
        public void load() {
            Account.this.owner = this.owner;
            Account.this.currencyHashMap = new HashMap<>(this.currencyHashMap);
        }
    }

    @Override
    public String toString() {
        return "Account{" +
                "owner='" + owner + '\'' +
                ", currencyHashMap=" + currencyHashMap +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        final Account other = (Account) obj;
        return Objects.equals(this.owner, other.owner)
                && Objects.equals(this.currencyHashMap, other.currencyHashMap);
    }
}
