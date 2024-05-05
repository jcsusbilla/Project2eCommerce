package com.example.project2ecommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.project2ecommerce.database.eCommerceRepository;
import com.example.project2ecommerce.database.entities.User;
import com.example.project2ecommerce.databinding.ActivityAdminDeleteUserBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Miguel Santiago
 * This class will allow the Administrator to delete a user in the database. upon entering
 * the activity the Admin will be shown a recycler view with the current users in the
 * database and their ID. From there the user is asked to enter the ID of the user he
 * wishes to delete.
 */
public class AdminDeleteUserActivity extends AppCompatActivity {
    ActivityAdminDeleteUserBinding binding;
    eCommerceRepository repository;
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.project2ecommerce.MAIN_ACTIVITY_USER_ID";
    private int userId = -1;

    public RecyclerView recyclerView;
    private List<User> userList;

    private UserAdapter userAdapter;
    private User user;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminDeleteUserBinding.inflate(getLayoutInflater());        //reference to xml object, inflate converts xml to java reference
        setContentView(binding.getRoot());

        //object representation of view
        repository = eCommerceRepository.getRepository(getApplication());
        userId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, -1);

        /*
        Recycler view setup start. this is what lists the users in the view. - Miguel
         */
        recyclerView = findViewById(R.id.adminDeleteUserRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


        userAdapter = new UserAdapter(new ArrayList<>());
        recyclerView.setAdapter(userAdapter);

        // Observe the LiveData and update adapter's data - Miguel
        repository.getAllUsers().observe(this, users -> {
            if (users != null) {
                List<String> usernames = new ArrayList<>();
                for (User user : users) {
                    usernames.add("User ID: " + user.getId() + "     " + user.getUsername());
                }
                userAdapter.setData(usernames);
            }
        });

        binding.deleteUserButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                deleteUser();
            }
        });

        binding.backToPage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(AdminActivity.adminIntentFactory(getApplicationContext(), userId));
            }
        });



    }

    /*
    Method to delete a user when the button is pressed and there is a number entered. - Miguel
     */
    public void deleteUser(){
        int userId = Integer.parseInt(binding.adminDeleteUserEditTextNumber.getText().toString());
        LiveData<User> userObserver = repository.getUserByUserId(userId);
        userObserver.observe(this, user -> {
            this.user = user;
            if (this.user != null){
                repository.deleteUser(user);
                Intent intent =  AdminActivity.adminIntentFactory(getApplicationContext(), userId);
                startActivity(intent);
            }
        });
    }


    static Intent adminDeleteUserIntentFactory(Context context, int userId){
        Intent intent = new Intent(context, AdminDeleteUserActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }
}