package com.karawalaya.alliantbankapp.ACTIVITIES_FRAGMENTS.transaction_management;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.karawalaya.alliantbankapp.DAO_SERVICE.transaction_management.TransactionManagementDAO;
import com.karawalaya.alliantbankapp.POJO_MODEL.transaction_management.Account;
import com.karawalaya.alliantbankapp.POJO_MODEL.transaction_management.Customer;
import com.karawalaya.alliantbankapp.POJO_MODEL.transaction_management.ErrorGives;
import com.karawalaya.alliantbankapp.POJO_MODEL.transaction_management.TMValidator;
import com.karawalaya.alliantbankapp.R;


public class RemoveATransaction extends Fragment {
    //Bundle Arguments.
    private static final String CUST_VAR_REMOVE_A_TRANSACTION = "customer";

    //Special Holding Attributes.
    private Customer customer = null;
    private Account account = null;
    private TransactionManagementDAO tmdao;
    private TMValidator tmv;

    //Views.
    private EditText remove_a_transaction_ET01;
    private Button remove_a_transaction_BTN01;

    public static RemoveATransaction getInstance(Customer cust) {
        RemoveATransaction removeATransactionFrag = new RemoveATransaction();
        Bundle removeATransactionFragBundle = new Bundle();
        removeATransactionFragBundle.putSerializable(CUST_VAR_REMOVE_A_TRANSACTION, cust);
        removeATransactionFrag.setArguments(removeATransactionFragBundle);

        return removeATransactionFrag;
    }

    @Override
    public void onAttach(Context context) {
        Log.i("Kara", "FRAGMENT =========================== RemoveATransaction =========================== onAttach ===========================");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i("Kara", "FRAGMENT =========================== RemoveATransaction =========================== onCreate ===========================");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("Kara", "FRAGMENT =========================== RemoveATransaction =========================== onCreateView ===========================");
        View fragRemoveATransactionView = inflater.inflate(R.layout.fragment_remove_atransaction, container, false);

        initializeObjects();
        initializeViews(fragRemoveATransactionView);

        if(getArguments() != null) {
            customer = (Customer) getArguments().getSerializable(CUST_VAR_REMOVE_A_TRANSACTION);
            account = tmdao.getAccountDetails(customer);
        }

        return fragRemoveATransactionView;
    }


    private void initializeObjects() {
        tmdao = new TransactionManagementDAO(getActivity());
        tmv = new TMValidator(getActivity());
    }

    private void initializeViews(View fragRemoveATransactionView) {
        remove_a_transaction_ET01 = (EditText) fragRemoveATransactionView.findViewById(R.id.remove_a_transaction_ET01);
        remove_a_transaction_BTN01 = (Button) fragRemoveATransactionView.findViewById(R.id.remove_a_transaction_BTN01);
    }

    public void deleteTransaction() {
        remove_a_transaction_BTN01.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        doStuff();
                    }
                }
        );
    }

    public void doStuff() {
        ErrorGives eg = new ErrorGives(getActivity());

        if(!tmv.isFieldFilled(remove_a_transaction_ET01)) {
            remove_a_transaction_ET01.setError(eg.getFieldError(getString(R.string.string_id_error_field_cannot_be_empty)));
            emptyInputEditText();
            return;
        }

        if(!tmv.isNumericOnly(remove_a_transaction_ET01)) {
            remove_a_transaction_ET01.setError(eg.getFieldError(getString(R.string.string_id_error_field_should_only_contain_numeric)));
            emptyInputEditText();
            return;
        }

        if(!tmv.isPosByInt(remove_a_transaction_ET01)) {
            remove_a_transaction_ET01.setError(eg.getFieldError(getString(R.string.string_id_error_field_should_be_positive)));
            emptyInputEditText();
            return;
        }

        if(tmdao.removeTransaction(Integer.parseInt(remove_a_transaction_ET01.getText().toString()), account)) {
            AlertDialog.Builder alertTransactionRemoved = new AlertDialog.Builder(getActivity());
            alertTransactionRemoved.setMessage(getString(R.string.string_id_remove_a_transaction_success))
                    .setCancelable(true);

            AlertDialog alert = alertTransactionRemoved.create();
            alert.setTitle(getString(R.string.string_id_remove_a_transaction_alert_title));
            alert.show();
        } else {
            remove_a_transaction_ET01.setError(eg.getFieldError(getString(R.string.string_id_remove_a_transaction_error)));
            emptyInputEditText();
            return;
        }

    }

    private void emptyInputEditText() {
        remove_a_transaction_ET01.setText(null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i("Kara", "FRAGMENT =========================== RemoveATransaction =========================== onActivityCreated ===========================");
        super.onActivityCreated(savedInstanceState);
        deleteTransaction();
    }

    @Override
    public void onStart() {
        Log.i("Kara", "FRAGMENT =========================== RemoveATransaction =========================== onStart ===========================");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i("Kara", "FRAGMENT =========================== RemoveATransaction =========================== onResume  ===========================");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i("Kara", "FRAGMENT =========================== RemoveATransaction =========================== onPause ===========================");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i("Kara", "FRAGMENT =========================== RemoveATransaction =========================== onStop ===========================");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.i("Kara", "FRAGMENT =========================== RemoveATransaction =========================== onDestroyView ===========================");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.i("Kara", "FRAGMENT =========================== RemoveATransaction =========================== onDestroy ===========================");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.i("Kara", "FRAGMENT =========================== RemoveATransaction =========================== onDetach ===========================");
        super.onDetach();
    }
}
