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
import com.example.project2ecommerce.databinding.ActivityAdminEditUserBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Author :Miguel Santiago
 * This class allows the administrator to made a regular user an administrator.
 */

public class AdminEditUserActivity extends AppCompatActivity {
    ActivityAdminEditUserBinding binding;
    eCommerceRepository repository;
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.project2ecommerce.MAIN_ACTIVITY_USER_ID";
    private int userId = -1;
    public RecyclerView recyclerView;

    private AdminEditUserAdapter adminEditUserAdapter;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_admin_edit_user);
        binding = ActivityAdminEditUserBinding.inflate(getLayoutInflater());        //reference to xml object, inflate converts xml to java reference
        setContentView(binding.getRoot());                                          //object representation of view
        repository = eCommerceRepository.getRepository(getApplication());
        userId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, -1);

        /*
        Recycler view setup start. this is what lists the users in the view. - Miguel
         */
        recyclerView = findViewById(R.id.adminEditUserRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


        adminEditUserAdapter = new AdminEditUserAdapter(new ArrayList<>());
        recyclerView.setAdapter(adminEditUserAdapter);

        // Observe the LiveData and update adapter's data - Miguel
        repository.getAllUsers().observe(this, users -> {
            if (users != null) {
                List<String> usernames = new ArrayList<>();
                for (User user : users) {
                    usernames.add("User ID: " + user.getId() + "    Admin: " + user.isAdmin() + "     User: " + user.getUsername());
                }
                adminEditUserAdapter.setData(usernames);
            }
        });

        binding.adminEditUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAdminStatus();
            }
        });

        binding.adminEditUserCancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(AdminActivity.adminIntentFactory(getApplicationContext()));
            }
        });


    }

    /*
    Change admin status used for making a regular user an administrator.
     */
    private void changeAdminStatus() {
        String userIdString = binding.adminEditUserEditTextNumber.getText().toString();
        if (userIdString.isEmpty()){
            binding.adminEditUserEditTextNumber.setError("ID Can not be empty");
            return;
        }

        userId = Integer.parseInt(userIdString);

        LiveData<User> userObserver = repository.getUserByUserId(userId);
        userObserver.observe(this, user -> {
            this.user = user;
            boolean isAdmin = user.isAdmin();
            if (this.user != null){
                if (isAdmin){
                    binding.adminEditUserEditTextNumber.setError("User is already an Admin");
                }else {
                    repository.updateUserAdminStatus(userId, 1);
                    Intent intent =  AdminActivity.adminIntentFactory(getApplicationContext());
                    startActivity(intent);
                }

            }
        });
    }

    static Intent adminEditUserIntentFactory(Context context, int userId){
        Intent intent = new Intent(context, AdminEditUserActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }
}