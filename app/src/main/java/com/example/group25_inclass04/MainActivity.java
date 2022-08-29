/*
Assignment #: InClass04
File Name: Group25_InClass04 MainActivity.java
Name: Kristin Pflug
 */

package com.example.group25_inclass04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginFragmentListener, AccountFragment.AccountFragmentListener, RegisterFragment.RegisterFragmentListener, UpdateAccountFragment.UpdateAccountFragmentListener {

    DataServices.Account acct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.containerView, new LoginFragment()).commit();
    }

    @Override
    public void sendToUpdateAccount(DataServices.Account account) {
        acct = account;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, UpdateAccountFragment.newInstance(acct))
                .addToBackStack(null).commit();
    }

    @Override
    public void logOut(DataServices.Account account) {
        acct = null;
        getSupportFragmentManager().beginTransaction().replace(R.id.containerView, new LoginFragment()).commit();
    }

    @Override
    public void sendToAccount(DataServices.Account account) {
        acct = account;
        getSupportFragmentManager().beginTransaction().replace(R.id.containerView, AccountFragment.newInstance(acct)).commit();
    }

    @Override
    public void sendToAccountCancelUpdate() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void sendToAccountUpdated(DataServices.Account updatedAccount) {
        acct = updatedAccount;
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().beginTransaction().replace(R.id.containerView, AccountFragment.newInstance(acct)).commit();
    }

    @Override
    public void sendToLogin() {
        getSupportFragmentManager().beginTransaction().replace(R.id.containerView, new LoginFragment()).commit();
    }

    @Override
    public void sendToRegister() {
        getSupportFragmentManager().beginTransaction().replace(R.id.containerView, new RegisterFragment()).commit();
    }
}