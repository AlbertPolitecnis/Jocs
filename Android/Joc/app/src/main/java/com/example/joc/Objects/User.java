package com.example.joc.Objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class User implements Serializable {
    private String nickname;
    private String password;
    private String name;
    private String surnames;
    private String currentAccount;
    private String email;
    private double money;
    private ArrayList<UserGame> userGamesList;

    public User(String nickname, String password, String name, String surnames, String currentAccount, String email, int money, ArrayList<UserGame> userGamesList) {
        this.nickname = nickname;
        this.password = password;
        this.name = name;
        this.surnames = surnames;
        this.currentAccount = currentAccount;
        this.email = email;
        this.money = money;
        this.userGamesList = userGamesList;
    }

    public User ()
    {
    }

    public User(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public String getCurrentAccount() {
        return currentAccount;
    }

    public void setCurrentAccount(String currentAccount) {
        this.currentAccount = currentAccount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public ArrayList<UserGame> getUserGamesList() {
        return userGamesList;
    }

    public void setUserGamesList(ArrayList<UserGame> userGamesList) {
        this.userGamesList = userGamesList;
    }
}
