package ru.practice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static ru.practice.CurrencyCode.*;

public class AccountTest {

    //@org.junit.jupiter.api.Test
    @ParameterizedTest
    @NullSource
    @EmptySource
    @ValueSource(strings = {"", "  ", "\n", "\t"})
    public void setOwner(String owner) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new DepositAccount(owner);
        });
    }

    @org.junit.jupiter.api.Test
    public void changeCurrencyNegative() {
        DepositAccount depositAccount = new DepositAccount("Tatiana Pastel");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            depositAccount.changeCurrency(RUB, -100);
        });
    }
    @org.junit.jupiter.api.Test
    public void changeCurrencyCorrect() {
        DepositAccount depositAccount = new DepositAccount("Tatiana Pastel");
        depositAccount.changeCurrency(USD,10);
        depositAccount.changeCurrency(USD,20);
        depositAccount.changeCurrency(RUB, 50);
        HashMap<CurrencyCode, Integer> exp = new HashMap<>();
        exp.put(USD,20);
        exp.put(RUB,50);
        Assertions.assertEquals(exp, depositAccount.getCurrencyHashMap());
    }

    @org.junit.jupiter.api.Test
    public void undo() throws OperationAttemptException {
        DepositAccount expDepositAccount = new DepositAccount("Tatiana Pastel");
        expDepositAccount.changeCurrency(RUB, 1000);

        DepositAccount depositAccount = new DepositAccount("Tatiana Pastel");
        depositAccount.changeCurrency(RUB, 1000);
        depositAccount.setOwner("Ivan Ivanov");
        depositAccount.changeCurrency(RUB, 500);
        depositAccount.undo().undo();

        Assertions.assertEquals(true, depositAccount.equals(expDepositAccount));
    }

    @org.junit.jupiter.api.Test
    public void save() {
        DepositAccount expDepositAccount = new DepositAccount("Tatiana Pastel");
        expDepositAccount.changeCurrency(USD, 200);

        DepositAccount depositAccount = new DepositAccount("Tatiana Pastel");
        depositAccount.changeCurrency(USD, 200);
        Loadable saveFirst = depositAccount.save();
        depositAccount.changeCurrency(EUR, 30);
        depositAccount.setOwner("Ivan Ivanov");
        saveFirst.load();

        Assertions.assertEquals(true, depositAccount.equals(expDepositAccount));
    }
}