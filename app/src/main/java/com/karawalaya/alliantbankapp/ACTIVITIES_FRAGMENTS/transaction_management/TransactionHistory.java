package com.karawalaya.alliantbankapp.ACTIVITIES_FRAGMENTS.transaction_management;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.karawalaya.alliantbankapp.DAO_SERVICE.transaction_management.TransactionManagementDAO;
import com.karawalaya.alliantbankapp.POJO_MODEL.transaction_management.Account;
import com.karawalaya.alliantbankapp.POJO_MODEL.transaction_management.Customer;
import com.karawalaya.alliantbankapp.POJO_MODEL.transaction_management.Transaction;
import com.karawalaya.alliantbankapp.R;

import org.w3c.dom.Text;

import java.util.Collection;

public class TransactionHistory extends Fragment {
    //Bundle Arguments.
    private static final String CUST_VAR_TRANSACTION_HISTORY = "customer";

    //Special Holding Attributes.
    private Customer customer = null;
    private TransactionManagementDAO tmdao;

    //Views.
    private TableLayout Transaction_History_Table_Layout;

    private TextView Transaction_History_Table_Row02_Col01;
    private TextView Transaction_History_Table_Row02_Col02;
    private TextView Transaction_History_Table_Row02_Col03;
    private TextView Transaction_History_Table_Row02_Col04;

    private TextView Transaction_History_Table_Row03_Col01;
    private TextView Transaction_History_Table_Row03_Col02;
    private TextView Transaction_History_Table_Row03_Col03;
    private TextView Transaction_History_Table_Row03_Col04;

    private TextView Transaction_History_Table_Row04_Col01;
    private TextView Transaction_History_Table_Row04_Col02;
    private TextView Transaction_History_Table_Row04_Col03;
    private TextView Transaction_History_Table_Row04_Col04;

    private TextView Transaction_History_Table_Row05_Col01;
    private TextView Transaction_History_Table_Row05_Col02;
    private TextView Transaction_History_Table_Row05_Col03;
    private TextView Transaction_History_Table_Row05_Col04;

    private TextView Transaction_History_Table_Row06_Col01;
    private TextView Transaction_History_Table_Row06_Col02;
    private TextView Transaction_History_Table_Row06_Col03;
    private TextView Transaction_History_Table_Row06_Col04;
    /**
     * This was used to create a communication between the MainActivity and this fragment.
     */
    public static TransactionHistory getInstance(Customer cust) {
        TransactionHistory transactionHistoryFrag = new TransactionHistory();
        Bundle transactionHistoryBundle = new Bundle();
        transactionHistoryBundle.putSerializable(CUST_VAR_TRANSACTION_HISTORY, cust);
        transactionHistoryFrag.setArguments(transactionHistoryBundle);

        return transactionHistoryFrag;
    }

    @Override
    public void onAttach(Context context) {
        Log.i("Kara", "FRAGMENT =========================== TransactionHistory =========================== onAttach ===========================");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i("Kara", "FRAGMENT =========================== TransactionHistory =========================== onCreate ===========================");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("Kara", "FRAGMENT =========================== TransactionHistory =========================== onCreateView ===========================");

        View fragTransactionHistoryView = inflater.inflate(R.layout.fragment_transaction_history, container, false);

        initializeObjects();
        initializeViews(fragTransactionHistoryView);

        if(getArguments() != null) {
            customer = (Customer) getArguments().getSerializable(CUST_VAR_TRANSACTION_HISTORY);
            Account account = tmdao.getAccountDetails(customer);

            if(account != null) {
                Collection<Transaction> transactionList = tmdao.getTransactionHistory(account.getAccountNo());
                if(transactionList != null) {
                    int[] textViews = {R.id.Transaction_History_Table_Row02_Col01, R.id.Transaction_History_Table_Row02_Col02, R.id.Transaction_History_Table_Row02_Col03, R.id.Transaction_History_Table_Row02_Col04,
                            R.id.Transaction_History_Table_Row03_Col01, R.id.Transaction_History_Table_Row03_Col02, R.id.Transaction_History_Table_Row03_Col03, R.id.Transaction_History_Table_Row03_Col04,
                            R.id.Transaction_History_Table_Row04_Col01, R.id.Transaction_History_Table_Row04_Col02, R.id.Transaction_History_Table_Row04_Col03, R.id.Transaction_History_Table_Row04_Col04,
                            R.id.Transaction_History_Table_Row05_Col01, R.id.Transaction_History_Table_Row05_Col02, R.id.Transaction_History_Table_Row05_Col03, R.id.Transaction_History_Table_Row05_Col04,
                            R.id.Transaction_History_Table_Row06_Col01, R.id.Transaction_History_Table_Row06_Col02, R.id.Transaction_History_Table_Row06_Col03, R.id.Transaction_History_Table_Row06_Col04,};

                    int i = 0;
                    for(Transaction transaction: transactionList) {
                        TextView tv01 = (TextView) fragTransactionHistoryView.findViewById(textViews[i]);
                        tv01.setText(Integer.toString(transaction.getCreditAccount()));
                        i++;
                        TextView tv02 = (TextView) fragTransactionHistoryView.findViewById(textViews[i]);
                        tv02.setText(transaction.getTransactionDate().toString());
                        i++;
                        TextView tv03 = (TextView) fragTransactionHistoryView.findViewById(textViews[i]);
                        tv03.setText(Double.toString(transaction.getTransactionAmount()));
                        i++;
                        TextView tv04 = (TextView) fragTransactionHistoryView.findViewById(textViews[i]);
                        tv04.setText(Integer.toString(transaction.getDebitAccount()));
                        i++;
                    }
                } else {
                    Transaction_History_Table_Layout.setVisibility(View.INVISIBLE);

                    AlertDialog.Builder alertNoTransactionHistory = new AlertDialog.Builder(getActivity());
                    alertNoTransactionHistory.setMessage("There are no transactions to show as of now!")
                            .setCancelable(true);
                    AlertDialog alert = alertNoTransactionHistory.create();
                    alert.setTitle("Transaction History");
                    alert.show();
                }
            } else {

            }
        }

        return fragTransactionHistoryView;
    }

    private void initializeObjects() {
        tmdao = new TransactionManagementDAO(getActivity());
    }

    private void initializeViews(View fragTransactionHistoryView) {
        Transaction_History_Table_Layout = fragTransactionHistoryView.findViewById(R.id.Transaction_History_Table_Layout);

//        Transaction_History_Table_Row02_Col01 = fragTransactionHistoryView.findViewById(R.id.Transaction_History_Table_Row02_Col01);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i("Kara", "FRAGMENT =========================== TransactionHistory =========================== onActivityCreated ===========================");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.i("Kara", "FRAGMENT =========================== TransactionHistory =========================== onStart ===========================");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i("Kara", "FRAGMENT =========================== TransactionHistory =========================== onResume  ===========================");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i("Kara", "FRAGMENT =========================== TransactionHistory =========================== onPause ===========================");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i("Kara", "FRAGMENT =========================== TransactionHistory =========================== onStop ===========================");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.i("Kara", "FRAGMENT =========================== TransactionHistory =========================== onDestroyView ===========================");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.i("Kara", "FRAGMENT =========================== TransactionHistory =========================== onDestroy ===========================");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.i("Kara", "FRAGMENT =========================== TransactionHistory =========================== onDetach ===========================");
        super.onDetach();
    }
}
