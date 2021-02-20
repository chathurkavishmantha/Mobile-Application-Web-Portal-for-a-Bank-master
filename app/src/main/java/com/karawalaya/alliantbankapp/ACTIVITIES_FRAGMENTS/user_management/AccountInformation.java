package com.karawalaya.alliantbankapp.ACTIVITIES_FRAGMENTS.user_management;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.karawalaya.alliantbankapp.ACTIVITIES_FRAGMENTS.transaction_management.RemoveATransaction;
import com.karawalaya.alliantbankapp.DAO_SERVICE.transaction_management.TransactionManagementDAO;
import com.karawalaya.alliantbankapp.POJO_MODEL.transaction_management.Account;
import com.karawalaya.alliantbankapp.POJO_MODEL.transaction_management.Customer;
import com.karawalaya.alliantbankapp.POJO_MODEL.transaction_management.TMValidator;
import com.karawalaya.alliantbankapp.R;

public class AccountInformation extends Fragment {
    //Bundle Arguments.
    private static final String CUST_VAR_ACCOUNT_INFORMATION = "customer";

    //Special Holding Attributes.
    private Customer customer = null;

    //Views.
    private TextView account_info_TV08;
    private TextView account_info_TV09;
    private TextView account_info_TV10;
    private TextView account_info_TV11;
    private TextView account_info_tv13;
    private TextView account_info_tv15;

    public static AccountInformation getInstance(Customer cust) {
        AccountInformation accountInformationFrag = new AccountInformation();
        Bundle accountInformationFragBundle = new Bundle();
        accountInformationFragBundle.putSerializable(CUST_VAR_ACCOUNT_INFORMATION, cust);
        accountInformationFrag.setArguments(accountInformationFragBundle);

        return accountInformationFrag;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragAccountInformationView =  inflater.inflate(R.layout.fragment_account_information, container, false);

        initializeViews(fragAccountInformationView);

        if(getArguments() != null) {
            customer = (Customer) getArguments().getSerializable(CUST_VAR_ACCOUNT_INFORMATION);

            account_info_TV08.setText(customer.getFirstName() + " " + customer.getLastName());
            account_info_tv13.setText(Integer.toString(customer.getAge()));
            account_info_tv15.setText(customer.getGender());
            account_info_TV09.setText(customer.getAddressStreet() + " " + customer.getAddressCity());
            account_info_TV10.setText(customer.getContactNumber());
            account_info_TV11.setText(customer.getEmail());
        }

        return fragAccountInformationView;
    }


    private void initializeViews(View fragAccountInformationView) {
        account_info_TV08 = (TextView) fragAccountInformationView.findViewById(R.id.account_info_tv08);
        account_info_TV09 = (TextView) fragAccountInformationView.findViewById(R.id.account_info_tv09);
        account_info_TV10 = (TextView) fragAccountInformationView.findViewById(R.id.account_info_tv10);
        account_info_TV11 = (TextView) fragAccountInformationView.findViewById(R.id.account_info_tv11);
        account_info_tv13 = (TextView) fragAccountInformationView.findViewById(R.id.account_info_tv13);
        account_info_tv15 = (TextView) fragAccountInformationView.findViewById(R.id.account_info_tv15);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
