package com.karawalaya.alliantbankapp.ACTIVITIES_FRAGMENTS.transaction_management;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.TextView;
import android.widget.Toast;

import com.karawalaya.alliantbankapp.ACTIVITIES_FRAGMENTS.user_management.Register;
import com.karawalaya.alliantbankapp.DAO_SERVICE.transaction_management.TransactionManagementDAO;
import com.karawalaya.alliantbankapp.DAO_SERVICE.user_management.UserManagementDAO;
import com.karawalaya.alliantbankapp.POJO_MODEL.transaction_management.Account;
import com.karawalaya.alliantbankapp.POJO_MODEL.transaction_management.Customer;
import com.karawalaya.alliantbankapp.POJO_MODEL.transaction_management.ErrorGives;
import com.karawalaya.alliantbankapp.POJO_MODEL.transaction_management.TMValidator;
import com.karawalaya.alliantbankapp.POJO_MODEL.transaction_management.Transaction;
import com.karawalaya.alliantbankapp.POJO_MODEL.user_management.UMValidator;
import com.karawalaya.alliantbankapp.R;

import java.util.Collection;

public class SearchTransactions extends Fragment implements View.OnClickListener{
    //Bundle Arguments.
    private static final String CUST_VAR_SEARCH_TRANSACTIONS = "customer";

    //Special Holding Attributes.
    private Customer customer = null;
    private Account account = null;

    private TransactionManagementDAO tmdao;
    private TMValidator tmv;

    private UserManagementDAO umdao;
    private UMValidator umv;

    //Views --> TM
    private EditText search_transactions_ET01;
    private Button search_transactions_BTN01;
    //Views --> UM
    private EditText search_transactions_ET02;
    private Button search_transactions_BTN02;

    public static SearchTransactions getInstance(Customer cust) {
        SearchTransactions searchTransactionsFrag = new SearchTransactions();
        Bundle searchTransactionsBundle = new Bundle();
        searchTransactionsBundle.putSerializable(CUST_VAR_SEARCH_TRANSACTIONS, cust);
        searchTransactionsFrag.setArguments(searchTransactionsBundle);

        return searchTransactionsFrag;
    }

    @Override
    public void onAttach(Context context) {
        Log.i("Kara", "FRAGMENT =========================== SearchTransactions =========================== onAttach ===========================");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i("Kara", "FRAGMENT =========================== SearchTransactions =========================== onCreate ===========================");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("Kara", "FRAGMENT =========================== SearchTransactions =========================== onCreateView ===========================");
        View fragSearchTransactionView = inflater.inflate(R.layout.fragment_search_transactions, container, false);

        initializeObjects();
        initializeViews(fragSearchTransactionView);
        initListeners();

        if(getArguments() != null) {
            customer = (Customer) getArguments().getSerializable(CUST_VAR_SEARCH_TRANSACTIONS);
            account = tmdao.getAccountDetails(customer);
        }

        return fragSearchTransactionView;
    }

    private void initializeObjects() {
        tmdao = new TransactionManagementDAO(getActivity());
        tmv = new TMValidator(getActivity());

        umdao = new UserManagementDAO(getActivity());
        umv = new UMValidator(getActivity());
    }

    private void initListeners() {
        search_transactions_BTN01.setOnClickListener(this);
        search_transactions_BTN02.setOnClickListener(this);
    }

    private void initializeViews(View fragSearchTransactionView) {
        search_transactions_ET01 = (EditText) fragSearchTransactionView.findViewById(R.id.search_transactions_ET01);
        search_transactions_BTN01 = (Button) fragSearchTransactionView.findViewById(R.id.search_transactions_BTN01);

        search_transactions_ET02 = (EditText) fragSearchTransactionView.findViewById(R.id.search_transactions_ET02);
        search_transactions_BTN02 = (Button) fragSearchTransactionView.findViewById(R.id.search_transactions_BTN02);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_transactions_BTN01:
                showTransactionsUsingDebitAccount();
                break;
            case R.id.search_transactions_BTN02:
                showTransactionsUsingTransactionId();
                break;
        }
    }

    public void showTransactionsUsingDebitAccount() {

        ErrorGives eg = new ErrorGives(getActivity());

        if(!tmv.isFieldFilled(search_transactions_ET01)) {
            search_transactions_ET01.setError(eg.getFieldError(getString(R.string.string_id_error_field_cannot_be_empty)));
            emptyInputEditTextF1();
            return;
        }

        if(!tmv.isNumericOnly(search_transactions_ET01)) {
            search_transactions_ET01.setError(eg.getFieldError(getString(R.string.string_id_error_field_should_only_contain_numeric)));
            emptyInputEditTextF1();
            return;
        }

        if(!tmv.isNineDigit(search_transactions_ET01)) {
            search_transactions_ET01.setError(eg.getFieldError(getString(R.string.string_id_error_field_should_be_nine_digit)));
            return;
        }

        if(Integer.parseInt(search_transactions_ET01.getText().toString().trim()) == account.getAccountNo()) {
            search_transactions_ET01.setError(eg.getFieldError(getString(R.string.string_id_error_search_transactions)));
            emptyInputEditTextF1();
            return;
        }

        Collection<Transaction> transactionList = tmdao.getTransactionDetails(account.getAccountNo(), Integer.parseInt(search_transactions_ET01.getText().toString()));

        if(transactionList != null) {
            StringBuffer buffer = new StringBuffer();
            for(Transaction tran: transactionList) {
                buffer.append("\tTransaction " + tran.getTransactionId() + "\n");
                buffer.append("\t\t\t\tDate: " + tran.getTransactionDate() + "\n");
                buffer.append("\t\t\t\tAmount: " + tran.getCreditAccount() + "\n");
                buffer.append("\t\t\t\tDebit Account: " + tran.getDebitAccount() + "\n\n");
            }

            showTransactionListByDebitAccount(getString(R.string.string_id_search_transactions_alert_title), buffer.toString());
        } else {
            showTransactionListByDebitAccount(getString(R.string.string_id_search_transactions_alert_box_error), getString(R.string.string_id_search_transactions_alert_box_error));
            return;
        }
    }

    public void showTransactionListByDebitAccount(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    private void emptyInputEditTextF1() {
        search_transactions_ET01.setText(null);
    }

    /**
     * @Author Bogahawatte W.W.M.K.A
     */
    public void showTransactionsUsingTransactionId() {
        ErrorGives eg = new ErrorGives(getActivity()); //Samarasekara S.A.M.I.D.

        if(!umv.filled(search_transactions_ET02)) {
            search_transactions_ET02.setError(eg.getFieldError(getString(R.string.search_by_transaction_error_not_filled)));
            emptyInputEditTextF1();
            return;
        }

        if(!umv.integer(search_transactions_ET02)) {
            search_transactions_ET02.setError(eg.getFieldError(getString(R.string.search_by_transaction_error_not_int)));
            emptyInputEditTextF1();
            return;
        }

        if(!umv.notNegativeOrZero(search_transactions_ET02)) {
            search_transactions_ET02.setError(eg.getFieldError(getString(R.string.search_by_transaction_error_neg_or_zero)));
            emptyInputEditTextF1();
            return;
        }

        Transaction transaction = umdao.getTransaction(Integer.parseInt(search_transactions_ET02.getText().toString()), account);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);

        if(transaction != null) {
            StringBuffer buffer = new StringBuffer();

            buffer.append("\tTransaction " + transaction.getTransactionId() + "\n");
            buffer.append("\t\t\t\tDate: " + transaction.getTransactionDate() + "\n");
            buffer.append("\t\t\t\tAmount: " + transaction.getCreditAccount() + "\n");
            buffer.append("\t\t\t\tDebit Account: " + transaction.getDebitAccount() + "\n\n");

            builder.setTitle(getString(R.string.search_by_transaction_alert_title01));
            builder.setMessage(buffer.toString());
            builder.show();
        } else {
            builder.setTitle(getString(R.string.search_by_transaction_alert_title02));
            builder.setMessage(getString(R.string.search_by_transaction_alert_error) + search_transactions_ET02.getText().toString());
            builder.show();
        }
    }

    private void emptyInputEditTextF2() {
        search_transactions_ET02.setText(null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i("Kara", "FRAGMENT =========================== SearchTransactions =========================== onActivityCreated ===========================");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.i("Kara", "FRAGMENT =========================== SearchTransactions =========================== onStart ===========================");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i("Kara", "FRAGMENT =========================== SearchTransactions =========================== onResume  ===========================");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i("Kara", "FRAGMENT =========================== SearchTransactions =========================== onPause ===========================");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i("Kara", "FRAGMENT =========================== SearchTransactions =========================== onStop ===========================");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.i("Kara", "FRAGMENT =========================== SearchTransactions =========================== onDestroyView ===========================");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.i("Kara", "FRAGMENT =========================== SearchTransactions =========================== onDestroy ===========================");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.i("Kara", "FRAGMENT =========================== SearchTransactions =========================== onDetach ===========================");
        super.onDetach();
    }
}
