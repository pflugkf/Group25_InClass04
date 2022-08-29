/*
Assignment #: InClass04
File Name: Group25_InClass04 RegisterFragment.java
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
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_ACCOUNT = "ARG_PARAM_ACCOUNT";

    // TODO: Rename and change types of parameters
    private DataServices.Account account;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param account Parameter 1.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(DataServices.Account account) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_ACCOUNT, account);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.account = (DataServices.Account) getArguments().getSerializable(ARG_PARAM_ACCOUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    RegisterFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mListener = (RegisterFragmentListener) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.register_fragment_title);

        Button submitButton = view.findViewById(R.id.button_submit);
        Button cancelButton = view.findViewById(R.id.button_cancel);
        EditText nameTextbox = view.findViewById(R.id.editTextPersonName);
        EditText emailTextbox = view.findViewById(R.id.editTextPersonEmail);
        EditText passwordTextbox = view.findViewById(R.id.editTextPersonPassword);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameEntered = nameTextbox.getText().toString();
                String emailEntered = emailTextbox.getText().toString();
                String passwordEntered = passwordTextbox.getText().toString();

                if(nameEntered.isEmpty()){
                    Toast.makeText(view.getContext(), getString(R.string.name_not_entered_toast), Toast.LENGTH_SHORT).show();
                } else {
                    if(emailEntered.isEmpty()){
                        Toast.makeText(view.getContext(), getString(R.string.email_not_entered_toast), Toast.LENGTH_SHORT).show();
                    } else {
                        if(passwordEntered.isEmpty()) {
                            Toast.makeText(view.getContext(), getString(R.string.password_not_entered_toast), Toast.LENGTH_SHORT).show();
                        } else {
                            DataServices.AccountRequestTask task = DataServices.register(nameEntered, emailEntered, passwordEntered);
                            if (task.isSuccessful()) {
                                DataServices.Account account = task.getAccount();
                                mListener.sendToAccount(account);
                            } else {
                                Toast.makeText(view.getContext(), getString(R.string.registration_not_successful_toast), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.sendToLogin();
            }
        });
    }

    interface RegisterFragmentListener {
        void sendToAccount(DataServices.Account account);
        void sendToLogin();
    }
}