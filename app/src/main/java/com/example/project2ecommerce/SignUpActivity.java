package com.example.project2ecommerce;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;


import androidx.appcompat.app.AppCompatActivity;



import com.example.project2ecommerce.database.eCommerceRepository;
import com.example.project2ecommerce.database.entities.User;
import com.example.project2ecommerce.databinding.ActivitySignUpBinding;



/**
 * Author: Miguel Santiago
 * This class will handle when a new user wants to sign up to the application. The
 * user will be prompted to enter a username, a password and confirm the password.
 */
public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private static eCommerceRepository repository;

    final int requiredPasswordLength = 8;
//    private UserDAO userDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = eCommerceRepository.getRepository(getApplication());


        binding.signUpConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (addUser()){
                    Intent intent =  LoginActivity.loginIntentFactory(getApplicationContext());
                    startActivity(intent);
                }


            }
        });

        binding.signUpReturnTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  LoginActivity.loginIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }


    private boolean addUser(){
        String username = binding.signUpUsernameEditText.getText().toString();
        String password = binding.signUpEditTextEnterPassword.getText().toString();
        String confirmPassword = binding.signUpEditTextConfirmPassword.getText().toString();

        //Username is not entered
        if (username.isEmpty()){
            binding.signUpUsernameEditText.setError("Username is required");
            return false;
        }
        //Password is empty
        if (password.isEmpty()){
            binding.signUpEditTextEnterPassword.setError("Password can not be empty");
            return false;
        }

        //Passwords do not match
        if (!password.equals(confirmPassword)) {
            binding.signUpEditTextConfirmPassword.setError("Passwords do not match");
            return false;
        }

        //Username is taken check
//                if (repository.getUserByUserName(username) != null){
//                    binding.signUpUsernameEditText.setError("Username is taken");
//                    return;
//                }

        //Password is too short
        if (password.length() < requiredPasswordLength){
            binding.signUpEditTextEnterPassword.setError("Password is too short");
            return false;
        }


        User user = new User(username, confirmPassword);
        repository.insertUser(user);
        return true;
    }

    static Intent signUpIntentFactory(Context context){
        return new Intent(context, SignUpActivity.class);
    }
}