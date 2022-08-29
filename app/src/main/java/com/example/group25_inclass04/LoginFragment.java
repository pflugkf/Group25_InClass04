/*
Assignment #: InClass04
File Name: Group25_InClass04 LoginFragment.java
Name: Kristin Pflug
 */

package com.example.group25_inclass04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    LoginFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mListener = (LoginFragmentListener) context;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.login_fragment_title);

        EditText emailEditText = view.findViewById(R.id.editTextEmail);
        EditText passwordEditText = view.findViewById(R.id.editTextPassword);
        Button loginButton = view.findViewById(R.id.button_login);
        Button registerButton = view.findViewById(R.id.button_create_new_account);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailEntered = emailEditText.getText().toString();
                String passwordEntered = passwordEditText.getText().toString();

                if(emailEntered.isEmpty()){
                    Toast.makeText(view.getContext(), getString(R.string.email_not_entered_toast), Toast.LENGTH_SHORT).show();
                } else {
                    if(passwordEntered.isEmpty()){
                        Toast.makeText(view.getContext(), getString(R.string.password_not_entered_toast), Toast.LENGTH_SHORT).show();
                    } else {
                        DataServices.AccountRequestTask task = DataServices.login(emailEntered, passwordEntered);
                        if(task.isSuccessful()){
                            DataServices.Account account = task.getAccount();
                            mListener.sendToAccount(account);
                        } else {
                            Toast.makeText(view.getContext(), getString(R.string.login_not_successful_toast), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.sendToRegister();
            }
        });
    }

    interface LoginFragmentListener {
        void sendToAccount(DataServices.Account account);
        void sendToRegister();
    }
}