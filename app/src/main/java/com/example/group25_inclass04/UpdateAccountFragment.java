/*
Assignment #: InClass04
File Name: Group25_InClass04 UpdateAccountFragment.java
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
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateAccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_ACCOUNT = "ARG_PARAM_ACCOUNT";

    // TODO: Rename and change types of parameters
    private DataServices.Account account;

    public UpdateAccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param account Parameter 1.
     * @return A new instance of fragment UpdateAccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateAccountFragment newInstance(DataServices.Account account) {
        UpdateAccountFragment fragment = new UpdateAccountFragment();
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
        getActivity().setTitle(R.string.update_fragment_title);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_account, container, false);
    }

    UpdateAccountFragment.UpdateAccountFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mListener = (UpdateAccountFragment.UpdateAccountFragmentListener) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.update_fragment_title);

        TextView emailDisplayText = view.findViewById(R.id.update_email_display);
        EditText updateNameText = view.findViewById(R.id.editTextUpdateName);
        EditText updatePasswordText = view.findViewById(R.id.editTextUpdatePassword);
        Button submitButton = view.findViewById(R.id.button_submit_update);
        Button cancelButton = view.findViewById(R.id.button_cancel_update);

        emailDisplayText.setText(account.getEmail());

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updatedName = updateNameText.getText().toString();
                String updatedPassword = updatePasswordText.getText().toString();

                if(updatedName.isEmpty()){
                    Toast.makeText(view.getContext(), getString(R.string.name_not_entered_toast), Toast.LENGTH_SHORT).show();
                } else {
                    if(updatedPassword.isEmpty()){
                        Toast.makeText(view.getContext(), getString(R.string.password_not_entered_toast), Toast.LENGTH_SHORT).show();
                    } else {
                        DataServices.AccountRequestTask task = DataServices.update(account, updatedName, updatedPassword);
                        if(task.isSuccessful()){
                            DataServices.Account account = task.getAccount();
                            mListener.sendToAccountUpdated(account);
                        } else {
                            Toast.makeText(view.getContext(), getString(R.string.update_not_successful_toast), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.sendToAccountCancelUpdate();
            }
        });
    }

    interface UpdateAccountFragmentListener {
        void sendToAccountCancelUpdate();
        void sendToAccountUpdated(DataServices.Account updatedAccount);
    }
}