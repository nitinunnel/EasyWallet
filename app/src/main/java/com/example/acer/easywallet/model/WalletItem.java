package com.example.acer.easywallet.model;

/**
 * Created by acer on 12/10/2017.
 */

public class WalletItem {
    public final int id;
    public final String detail;
    public final int money;

    public WalletItem(int id, String detail, int money) {
        this.id = id;
        this.detail = detail;
        this.money = money;
    }

    @Override
    public String toString() {
        return detail;
    }
}
