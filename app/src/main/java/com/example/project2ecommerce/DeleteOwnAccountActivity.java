package com.example.project2ecommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.project2ecommerce.database.eCommerceRepository;
import com.example.project2ecommerce.database.entities.User;
import com.example.project2ecommerce.databinding.ActivityDeleteOwnAccountBinding;

/**
 * Author: Miguel Santiago
 * This class handles the ability to delete your own account in the app. The method checks
 * to make sure that the user enters their current password and that they also select
 * the I Understand checkbox otherwise the account is not deleted.
 */
public class DeleteOwnAccountActivity extends AppCompatActivity {

    private ActivityDeleteOwnAccountBinding binding;

    private static eCommerceRepository repository;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeleteOwnAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = eCommerceRepository.getRepository(getApplication());


        binding.deleteUserDeleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAccount();

            }
        });


        binding.deleteUserCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  MainActivity.mainActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    /*
    This is the method to delete own account. the method uses the userID to get the current logged
    in user (getCurrentLoggedInUser()) and checks to make sure that the username matches
    the current username in the database. This will also check that the I understand check box
    is selected otherwise it will not delete the account. - Miguel
     */
    private void deleteAccount() {
        int userId = getCurrentLoggedInUserId();
        String currentPassword = binding.deleteUserEditTextCurrentPassword.getText().toString();
        boolean isChecked = binding.deleteUserCheckBox.isChecked();


        if (currentPassword.isEmpty()){
            binding.deleteUserEditTextCurrentPassword.setError("Can not be empty");
        }
        LiveData<User> userObserver = repository.getUserByUserId(userId);
        userObserver.observe(this, user -> {
            this.user = user;
            if (this.user != null){
                if (currentPassword.equals(user.getPassword())){
                    if (isChecked){
                        repository.deleteUser(user);
                        Intent intent =  LoginActivity.loginIntentFactory(getApplicationContext());
                        startActivity(intent);
                    }else {
                        //print that the check box was not checked
                    }
                }else {
                    //print error saying password was wrong
                }
            }

        });


    }

    // This Method is used to retrieve the current logged in user ID. - Miguel
    private int getCurrentLoggedInUserId(){
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE); //preferences only applicable to this program
        int loggedInUserId = sharedPreferences.getInt(getString(R.string.preference_userId_key), -1);
        return loggedInUserId;
    }

    static Intent deleteOwnAccountIntentFactory(Context context){
        return new Intent(context, DeleteOwnAccountActivity.class);
    }
}