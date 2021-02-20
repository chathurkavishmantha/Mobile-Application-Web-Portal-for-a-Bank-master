package com.karawalaya.alliantbankapp.ACTIVITIES_FRAGMENTS.transaction_management;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.karawalaya.alliantbankapp.ACTIVITIES_FRAGMENTS.user_management.AccountInformation;
import com.karawalaya.alliantbankapp.ACTIVITIES_FRAGMENTS.user_management.UpdateDetails;
import com.karawalaya.alliantbankapp.POJO_MODEL.transaction_management.Customer;
import com.karawalaya.alliantbankapp.R;

/**
 * Here, Interface is used to provide pain free method to implement the 'onNavigationItemSelected' method.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    /**
     * View Attributes that needs to be initialized at first sight.
     */
    private Toolbar mainActionBar;
    private DrawerLayout mainDrawerLayout;
    private TextView navigation_header_Username;
    private TextView navigation_header_Account_Number;
    private TextView navigation_header_Email;

    /**
     * Fragment Attributes
     */
    //Transaction_Management
    private HomePage homePage;
    private AccountBalance accountBalance;
    private TransactionHistory transactionHistory;
    private MakeATransaction makeATransaction;
    private SearchTransactions searchTransactions;
    private RemoveATransaction removeATransaction;

    //User_Management
    private AccountInformation accountInformation;
    private UpdateDetails updateDetails;

    /**
     * Special Attributes
     */
    private Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Kara", "onCreate MainActivity");
        setContentView(R.layout.activity_main);

        /**
         * customer object is filled after the onlineUser successfully logged in to the app.
         */
        customer = (Customer) getIntent().getSerializableExtra("customer");

        /**
         * Here, the action bar is set.
         */
        mainActionBar = findViewById(R.id.main_action_bar);
        setSupportActionBar(mainActionBar);

        /**
         * Here, the DrawerLayout and the NavigationView is initialized for further use.
         */
        mainDrawerLayout = findViewById(R.id.main_drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        /**
         * Due to this activity implementing the interface 'NavigationView.OnNavigationItemSelectedListener',
         *      next line can be used to jump to the method, 'onNavigationItemSelected'.
         */
        navigationView.setNavigationItemSelectedListener(this);

        /**
         * Here, the left upper DrawerToggle is set and synced.
         */
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mainDrawerLayout, mainActionBar, R.string.string_id_navigation_drawer_open, R.string.string_id_navigation_drawer_close);
        mainDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigation_view = (NavigationView) findViewById(R.id.navigation_view);
        View headerView = navigation_view.getHeaderView(0);

        navigation_header_Username = (TextView) headerView.findViewById(R.id.navigation_header_Username);
        navigation_header_Account_Number = (TextView) headerView.findViewById(R.id.navigation_header_Account_Number);
        navigation_header_Email = (TextView) headerView.findViewById(R.id.navigation_header_Email);

        navigation_header_Username.setText(customer.getOnlineUser().getUserName());
        navigation_header_Account_Number.setText(customer.getFirstName() + " " + customer.getLastName());
        navigation_header_Email.setText(customer.getEmail());

        /**
         * Here, If there is no saved instance of the MainActivity, (Basically if the activity is destroyed or opening for the first time),
         *         Homepage is set as the fragment for the initial view.
         */
        if(savedInstanceState == null) {
            /*FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container, new HomePage());
            transaction.addToBackStack(null);
            transaction.commit();*/
            /*TextView navigation_header_Username = (TextView) findViewById(R.id.navigation_header_Username);
            navigation_header_Username.setText(customer.getOnlineUser().getUserName());*/

            homePage = HomePage.getInstance(customer);
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homePage).addToBackStack("Home").commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homePage).commit();
            getSupportActionBar().setSubtitle("HomePage");
            /*FragmentManager fragManager = getSupportFragmentManager();
            FragmentTransaction fragTransact = fragManager.beginTransaction();
            fragTransact.replace(R.id.fragment_container, new HomePage());
            fragTransact.addToBackStack(null);
            fragTransact.commit();*/


        }
    }

    /**
     * Here, if the drawer is open, it will be closed first before doing whatever that should be done when the back button is pressed.
     */
    @Override
    public void onBackPressed() {
        if (mainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mainDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Here, Navigation List is optimized.
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.important_menu_option_01:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homePage, "Homepage").commit();
                getSupportActionBar().setSubtitle("HomePage");
                break;

            case R.id.nav_drawer_menu_id_option_01:
                Toast.makeText(this, R.string.string_id_navigation_menu_option_02, Toast.LENGTH_LONG).show();

                accountInformation = AccountInformation.getInstance(customer);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, accountInformation, "AccountInformation").commit();
                getSupportActionBar().setSubtitle("Account Information");
                menuItem.setCheckable(true);
//                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                break;

            case R.id.nav_drawer_menu_id_option_02:
                Toast.makeText(this, R.string.string_id_navigation_menu_option_01, Toast.LENGTH_LONG).show();

                updateDetails = UpdateDetails.getInstance(customer);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, updateDetails, "UpdateDetails").commit();
                getSupportActionBar().setSubtitle("Delete Account");
                menuItem.setCheckable(true);
//                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                break;

            case R.id.nav_drawer_menu_id_option_03:
                Toast.makeText(this, R.string.string_id_navigation_menu_option_03, Toast.LENGTH_LONG).show();

                accountBalance = AccountBalance.getInstance(customer);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, accountBalance, "AccountBalance").commit();
                getSupportActionBar().setSubtitle(R.string.string_id_navigation_menu_option_03);
                menuItem.setCheckable(true);

                break;

            case R.id.nav_drawer_menu_id_option_04:
                Toast.makeText(this, R.string.string_id_navigation_menu_option_04, Toast.LENGTH_LONG).show();

                transactionHistory = TransactionHistory.getInstance(customer);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, transactionHistory, "TransactionHistory").commit();
                getSupportActionBar().setSubtitle(R.string.string_id_navigation_menu_option_04);
                menuItem.setCheckable(true);
                break;

            case R.id.nav_drawer_menu_id_option_05:
                Toast.makeText(this, R.string.string_id_navigation_menu_option_05, Toast.LENGTH_LONG).show();

                makeATransaction = MakeATransaction.getInstance(customer);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, makeATransaction, "MakeATransaction").commit();
                getSupportActionBar().setSubtitle(R.string.string_id_navigation_menu_option_05);
                menuItem.setCheckable(true);
                break;

            case R.id.nav_drawer_menu_id_option_05_01:
                Toast.makeText(this, R.string.string_id_navigation_menu_option_05_01, Toast.LENGTH_LONG).show();

                searchTransactions = SearchTransactions.getInstance(customer);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, searchTransactions, "SearchTransactions").commit();
                getSupportActionBar().setSubtitle(R.string.string_id_navigation_menu_option_05_01);
                menuItem.setCheckable(true);
                break;

            case R.id.nav_drawer_menu_id_option_05_02:
                Toast.makeText(this, R.string.string_id_navigation_menu_option_05_02, Toast.LENGTH_LONG).show();

                removeATransaction = RemoveATransaction.getInstance(customer);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, removeATransaction, "RemoveTransaction").commit();
                getSupportActionBar().setSubtitle(R.string.string_id_navigation_menu_option_05_02);
                menuItem.setCheckable(true);
                break;

            case R.id.nav_drawer_menu_id_option_06:
                Toast.makeText(this, "Apply For Loan", Toast.LENGTH_LONG).show();
                getSupportActionBar().setSubtitle("Apply for Loan");
                menuItem.setCheckable(true);
                break;

            case R.id.nav_drawer_menu_id_option_07:
                Toast.makeText(this, "Lodge A Complaint", Toast.LENGTH_LONG).show();
                getSupportActionBar().setSubtitle("Lodge a Complaint");
                menuItem.setCheckable(true);
                break;
        }
        mainDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*switch (item.getItemId()) {
            case R.id.important_menu_option_01:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomePage()).commit();
                break;
        }
        mainDrawerLayout.closeDrawer(GravityCompat.START);
        return true;*/

        onNavigationItemSelected(item);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("Kara", "onStart MainActivity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Kara", "onResume MainActivity");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("Kara", "onRestart MainActivity");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("Kara", "onPause MainActivity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("Kara", "onStop MainActivity");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("Kara", "onSaveInstanceState MainActivity");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e("Kara", "onRestoreInstanceState MainActivity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Kara", "onDestroy MainActivity");
    }
}
