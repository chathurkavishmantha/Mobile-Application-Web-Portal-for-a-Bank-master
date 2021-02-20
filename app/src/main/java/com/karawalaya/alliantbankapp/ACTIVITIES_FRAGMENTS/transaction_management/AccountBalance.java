package com.karawalaya.alliantbankapp.ACTIVITIES_FRAGMENTS.transaction_management;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.karawalaya.alliantbankapp.DAO_SERVICE.transaction_management.TransactionManagementDAO;
import com.karawalaya.alliantbankapp.POJO_MODEL.transaction_management.Account;
import com.karawalaya.alliantbankapp.POJO_MODEL.transaction_management.Customer;
import com.karawalaya.alliantbankapp.R;

import java.text.DecimalFormat;

public class AccountBalance extends Fragment {
    //Bundle Arguments.
    private static final String CUST_VAR_ACCOUNT_BALANCE = "customer";

    //Special Holding Attributes.
    private Customer customer = null;
    private TransactionManagementDAO tmdao;

    //Views.
    private TextView Account_Balance_TV01;
    private TextView Account_Balance_TV03;
    private TextView Account_Balance_TV04;

    /**
     * This was used to create a communication between the MainActivity and this fragment.
     */
    public static AccountBalance getInstance(Customer cust) {
        AccountBalance accountBalanceFrag = new AccountBalance();
        Bundle accountBalanceBundle = new Bundle();
        accountBalanceBundle.putSerializable(CUST_VAR_ACCOUNT_BALANCE, cust);
        accountBalanceFrag.setArguments(accountBalanceBundle);

        return accountBalanceFrag;
    }

    @Override
    public void onAttach(Context context) {
        Log.i("Kara", "FRAGMENT =========================== AccountBalance =========================== onAttach ===========================");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i("Kara", "FRAGMENT =========================== AccountBalance =========================== onCreate ===========================");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("Kara", "FRAGMENT =========================== AccountBalance =========================== onCreateView ===========================");

        View fragAccountBalanceView = inflater.inflate(R.layout.fragment_account_balance, container, false);

        initializeObjects();
        initializeViews(fragAccountBalanceView);

        if(getArguments() != null) {
            customer = (Customer) getArguments().getSerializable(CUST_VAR_ACCOUNT_BALANCE);
            Account account = tmdao.getAccountDetails(customer);

            if(account != null) {
                Account_Balance_TV01.setText(customer.getFirstName() + " " + customer.getLastName());
                Account_Balance_TV03.setText(account.getAccountType());
                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(2);
                Account_Balance_TV04.setText(df.format(account.getBalance()));
            } else {

            }
        }

        return fragAccountBalanceView;
    }

    private void initializeObjects() {
        tmdao = new TransactionManagementDAO(getActivity());
    }

    private void initializeViews(View fragHomepageView) {
        Account_Balance_TV01 = fragHomepageView.findViewById(R.id.Account_Balance_TV01);
        Account_Balance_TV03 = fragHomepageView.findViewById(R.id.Account_Balance_TV03);
        Account_Balance_TV04 = fragHomepageView.findViewById(R.id.Account_Balance_TV04);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i("Kara", "FRAGMENT =========================== AccountBalance =========================== onActivityCreated ===========================");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.i("Kara", "FRAGMENT =========================== AccountBalance =========================== onStart ===========================");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i("Kara", "FRAGMENT =========================== AccountBalance =========================== onResume  ===========================");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i("Kara", "FRAGMENT =========================== AccountBalance =========================== onPause ===========================");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i("Kara", "FRAGMENT =========================== AccountBalance =========================== onStop ===========================");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.i("Kara", "FRAGMENT =========================== AccountBalance =========================== onDestroyView ===========================");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.i("Kara", "FRAGMENT =========================== AccountBalance =========================== onDestroy ===========================");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.i("Kara", "FRAGMENT =========================== AccountBalance =========================== onDetach ===========================");
        super.onDetach();
    }
}
