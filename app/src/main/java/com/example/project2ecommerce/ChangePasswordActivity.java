package com.example.project2ecommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.project2ecommerce.database.eCommerceRepository;
import com.example.project2ecommerce.database.entities.User;
import com.example.project2ecommerce.databinding.ActivityChangePasswordBinding;

public class ChangePasswordActivity extends AppCompatActivity {

    private ActivityChangePasswordBinding binding;

    private static eCommerceRepository repository;
    final int requiredPasswordLength = 8;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = eCommerceRepository.getRepository(getApplication());

        binding.changePasswordConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeUserPassword();
            }
        });

        binding.changePasswordCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelPasswordChange();
            }
        });


    }

    /*
    Main Method used for changing the users password. After taking in all information it then checks the
    Database for the current password of the user and compares it to the input entered by the user
    on the enter current password line. If the password does match then the password is changed.
    if the password does not match a message is shown and nothing is done.
     */
    private void changeUserPassword(){
        int userId = getCurrentLoggedInUserId();
        String currentPassword = binding.changePasswordEditTextPasswordTop.getText().toString();
        String newPassword = binding.changePasswordEditTextPasswordMid.getText().toString();
        String confirmedPassword = binding.changePasswordEditTextPasswordBot.getText().toString();

        //Username is not entered
        if (currentPassword.isEmpty()) {
            binding.changePasswordEditTextPasswordTop.setError("Username is required");

        }
        //Password is empty
        if (newPassword.isEmpty()) {
            binding.changePasswordEditTextPasswordMid.setError("Password can not be empty");

        }

        //Passwords do not match
        if (!newPassword.equals(confirmedPassword)) {
            binding.changePasswordEditTextPasswordBot.setError("Passwords do not match");


        }

        if (newPassword.length() < requiredPasswordLength){
            binding.changePasswordEditTextPasswordMid.setError("Password is too short");

        }

        LiveData<User> userObserver = repository.getUserByUserId(userId);
        userObserver.observe(this, user -> {
            this.user = user;
            if(this.user != null) {
                boolean isAdmin = user.isAdmin();
                if (currentPassword.equals(user.getPassword())) {
                    repository.updateUserPassword(userId, newPassword);
                    if (isAdmin){
                        //Change to admin view
                        Intent intent =  MainActivity.mainActivityIntentFactory(getApplicationContext(), userId);
                        startActivity(intent);
                    }else {
                        Intent intent =  MainActivity.mainActivityIntentFactory(getApplicationContext(), userId);
                        startActivity(intent);
                    }
                }else {
                    //print error
                    toastMaker("Error Current Password is Incorrect");
                    binding.changePasswordEditTextPasswordTop.setSelection(0);
                }
            }else {
                //print user is not found
                toastMaker("User Id not found try again later");
                binding.changePasswordEditTextPasswordTop.setSelection(0);
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

    //Method used for cancelling the password change. All it looks for is if the current logged in user is an admin
    //or not. It will then return the user to its proper Main landing page. - Miguel
    private void cancelPasswordChange(){
        int userId = getCurrentLoggedInUserId();
        LiveData<User> userObserver = repository.getUserByUserId(userId);
        userObserver.observe(this, user -> {
            this.user = user;
            if(this.user != null) {
                boolean isAdmin = user.isAdmin();
                if (isAdmin){

                }else {
                    Intent intent =  MainActivity.mainActivityIntentFactory(getApplicationContext(), userId);
                    startActivity(intent);
                }
            }
        });
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent changePasswordIntentFactory(Context context){
        return new Intent(context, ChangePasswordActivity.class);
    }
}