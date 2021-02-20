package com.karawalaya.alliantbankapp.ACTIVITIES_FRAGMENTS.user_management;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.karawalaya.alliantbankapp.DAO_SERVICE.user_management.UserManagementDAO;
import com.karawalaya.alliantbankapp.POJO_MODEL.transaction_management.Customer;
import com.karawalaya.alliantbankapp.R;

public class UpdateDetails extends Fragment {
    //Bundle Arguments.
    private static final String CUST_VAR_UPDATE_DETAILS = "customer";

    //Special Holding Attributes.
    private Customer customer = null;
    private UserManagementDAO umdao;

    //Views.
    Button update_details_btn01;

    public static UpdateDetails getInstance(Customer cust) {
        UpdateDetails updateDetailsFrag = new UpdateDetails();
        Bundle updateDetailsFragBundle = new Bundle();
        updateDetailsFragBundle.putSerializable(CUST_VAR_UPDATE_DETAILS, cust);
        updateDetailsFrag.setArguments(updateDetailsFragBundle);

        return updateDetailsFrag;
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
        View fragAccountInformationView =  inflater.inflate(R.layout.fragment_update_details, container, false);

        initViews(fragAccountInformationView);
        initObjects();

        if(getArguments() != null) {
            customer = (Customer) getArguments().getSerializable(CUST_VAR_UPDATE_DETAILS);

        }

        return fragAccountInformationView;
    }


    private void initViews(View fragAccountInformationView) {
        update_details_btn01 = (Button) fragAccountInformationView.findViewById(R.id.update_details_btn01);
    }

    private void initObjects() {
        umdao = new UserManagementDAO(getActivity());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        deleteUser();
    }

    public void deleteUser() {
        update_details_btn01.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        removeUser();
                    }
                }
        );
    }

    public void removeUser() {
        if(umdao.removeUser(customer.getCustomerId())) {
            AlertDialog.Builder alertNoTransactionHistory = new AlertDialog.Builder(getActivity());
            alertNoTransactionHistory.setMessage("You have successfully been removed from the system!")
                    .setCancelable(false);
            AlertDialog alert = alertNoTransactionHistory.create();
            alert.setTitle("Notification!");
            alert.show();

            Intent intent = new Intent(getActivity(), Login.class);
            startActivity(intent);
            getActivity().finish();
        } else {
            AlertDialog.Builder alertNoTransactionHistory = new AlertDialog.Builder(getActivity());
            alertNoTransactionHistory.setMessage("Some Error!")
                    .setCancelable(false);
            AlertDialog alert = alertNoTransactionHistory.create();
            alert.setTitle("Notification!");
            alert.show();
        }
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
