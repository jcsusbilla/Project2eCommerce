package com.example.project2ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.project2ecommerce.database.eCommerceRepository;
import com.example.project2ecommerce.database.entities.User;
import com.example.project2ecommerce.database.entities.eCommerce;
import com.example.project2ecommerce.databinding.ActivityMainBinding;

/**
 *  Author: JC SUSBILLA
 *  Main Landing Page for the app.
 *  Admin button only works for admin.
 *  3 options to view available items to purchase, go to view previously purchased items, and to delete account.
 */

public class MainActivity extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.project2ecommerce.MAIN_ACTIVITY_USER_ID"; //change package name when pushed to github
    static final String SHARED_PREFERENCE_USERID_KEY = "com.example.project2ecommerce.SHARED_PREFERENCE_USERID_KEY"; //change package name when pushed to github
    static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.project2ecommerce.SAVED_INSTANCE_STATE_USERID_KEY";
    private static final int LOGGED_OUT = -1;

    private ActivityMainBinding binding;
    eCommerceRepository repository;

    public static final String TAG = "CST_ECOMMERCE";

    String itemName = "";
    String desc = "";
    Double price = 0.0;
    Boolean stock = true;
    private int loggedInUserId = -1;
    int itemId = 0; //db
    private User user;
    private int quantity;

    //Button adminButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = eCommerceRepository.getRepository(getApplication());
        loginUser(savedInstanceState);
        updateSharedPreference();

        //user isn't logged in at this point. send to login screen
        if(loggedInUserId == -1){
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
            startActivity(intent);
        }



        //---------------------------------------------------------------------------------------------------------------------
        //BUTTONS

        binding.purchaseItemsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(PurchaseItemsActivity.purchaseItemsIntentFactory(getApplicationContext(), loggedInUserId));
            }
        });

        binding.viewPurchasesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(ViewPurchasesActivity.viewPurchasesIntentFactory(getApplicationContext(), loggedInUserId));
            }
        });

        binding.adminButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(AdminActivity.adminIntentFactory(getApplicationContext(), loggedInUserId));
            }
        });

        binding.changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  ChangePasswordActivity.changePasswordIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        binding.deleteAccountButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent =  DeleteOwnAccountActivity.deleteOwnAccountIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });


//        TextView welcome = findViewById(R.id.displayName);
//        String username = repository.getNameByUserId(loggedInUserId).toString();
        //welcome.setText(user.getUsername().toString());
        //---------------------------------------------------------------------------------------------------------------------
    }

    private void loginUser(Bundle savedInstanceState) {

        //adminButton = findViewById(R.id.adminButton);
        //check shared preference for logged in user
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE); //preferences only applicable to this program

        loggedInUserId = sharedPreferences.getInt(getString(R.string.preference_userId_key), LOGGED_OUT);

        if(loggedInUserId == LOGGED_OUT & savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_STATE_USERID_KEY)){
            loggedInUserId = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY, LOGGED_OUT);
        }
        if(loggedInUserId == LOGGED_OUT){
            loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
        }
        if(loggedInUserId == LOGGED_OUT){
            return;
        }
        LiveData<User> userObserver = repository.getUserByUserId(loggedInUserId);
        userObserver.observe(this, user -> {
            this.user = user;
            if(this.user != null) {
//                boolean isAdmin = user.isAdmin();
//                if (isAdmin){
//                    adminButton.setVisibility(View.VISIBLE);
//                }else {
//                    adminButton.setVisibility(View.GONE);
//                }
                invalidateOptionsMenu();
            }
        });
    }

    //@Override
    protected void onSavedInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_INSTANCE_STATE_USERID_KEY, loggedInUserId);
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_USERID_KEY, Context.MODE_PRIVATE);
        updateSharedPreference();
    }

    //for nav burger dropdown
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.logoutMenuItem);
        item.setVisible(true);
//        if(user == null){
//            return false;
//        }
        //get user
        item.setTitle("Logout");
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                //logout();                                                                                     // *** LOGOUT ***
                showLogoutDialogue();                                                                           //show logout confirmation
                return false;
            }
        });
        //return true;
        return super.onPrepareOptionsMenu(menu);//
    }

    //validate if user wants to logout --> send them to logout screen
    private void showLogoutDialogue(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog alertDialog = alertBuilder.create();                                                   //instantiate memory for alert dialogue

        alertBuilder.setMessage("Logout?");
        alertBuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });

        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertBuilder.create().show();
    }

    //logout function
    private void logout() {
        loggedInUserId = LOGGED_OUT;
        updateSharedPreference();
        getIntent().putExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    }

    private void updateSharedPreference() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(getString(R.string.preference_userId_key), loggedInUserId);
        sharedPrefEditor.apply();
    }

    static Intent mainActivityIntentFactory(Context context, int userId){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }

    static Intent mainActivityIntentFactory(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }


    private void insertECommerceRecord(){
        //eCommerce ecommerce = new eCommerce(itemName,desc, price, stock, loggedInUserId, itemId); //db
        eCommerce ecommerce = new eCommerce(itemName, price, stock, loggedInUserId, itemId, quantity); //db
        repository.insertECommerce(ecommerce);

    }
}