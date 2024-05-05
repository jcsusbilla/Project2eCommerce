package com.example.project2ecommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.example.project2ecommerce.database.eCommerceRepository;
import com.example.project2ecommerce.database.entities.User;
import com.example.project2ecommerce.databinding.ActivityLoginBinding;

/**
 * Authors: Miguel Santiago & JC Susbilla
 */
public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    private eCommerceRepository repository;
    public static final String TAG = "CST_ECOMMERCE";//
    private static final String LOGIN_PAGE_USER_ID = "com.example.project2ecommerce.LOGIN_PAGE_USER_ID";//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = eCommerceRepository.getRepository(getApplication());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyUser();
            }
        });

        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = SignUpActivity.signUpIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    private void verifyUser(){
        String username = binding.userNameLoginEditText.getText().toString();
        if(username.isEmpty()){
            toastMaker("Username can't be blank");
            return;
        }
        LiveData<User> userObserver = repository.getUserByUserName(username);
        userObserver.observe(this, user -> {
            //verify if user is in database
            if(user != null){
                String password = binding.passwordLoginEditText.getText().toString();
                if(password.equals(user.getPassword())){
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                    SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
                    sharedPrefEditor.putInt(getString(R.string.preference_userId_key), user.getId());
                    sharedPrefEditor.apply();
                    Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId());
                    startActivity(intent);
                } else {
                    toastMaker("Invalid password");
                    binding.passwordLoginEditText.setSelection(0);
                }
            } else { //if user is null
                toastMaker(String.format("%s is not a valid username", username));
                binding.userNameLoginEditText.setSelection(0);
            }
        });
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent loginIntentFactory(Context context){  //new (userId))
        return new Intent(context, LoginActivity.class);
    }
}