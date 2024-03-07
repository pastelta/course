package ru.practice;

import static ru.practice.CurrencyCode.*;

public class Demo {
    public static void main(String[] args) throws OperationAttemptException {
        DepositAccount depositAccount = new DepositAccount("Tatiana Pastel");
        depositAccount.changeCurrency(RUB,100);
        System.out.println(depositAccount);
        depositAccount.setOwner("Vasiliy Ivanov");
        System.out.println(depositAccount);
        depositAccount.changeCurrency(RUB, 300);
        System.out.println(depositAccount);
        depositAccount.changeCurrency(EUR, 5);
        System.out.println(depositAccount);
        //undo
        depositAccount.undo().undo();
        System.out.println(depositAccount);
        //save
        Loadable saveFirst = depositAccount.save();
        depositAccount.setOwner("Ivan Petrov");
        depositAccount.changeCurrency(USD, 10);
        System.out.println(depositAccount);
        //load
        saveFirst.load();
        System.out.println(depositAccount);
    }
}
