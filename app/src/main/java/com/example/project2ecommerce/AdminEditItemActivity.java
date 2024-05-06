package com.example.project2ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.project2ecommerce.database.eCommerceRepository;
import com.example.project2ecommerce.databinding.ActivityAdminEditItemBinding;

public class AdminEditItemActivity extends AppCompatActivity {
    ActivityAdminEditItemBinding binding;
    eCommerceRepository repository;
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.project2ecommerce.MAIN_ACTIVITY_USER_ID";
    private int userId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_item);
        binding = ActivityAdminEditItemBinding.inflate(getLayoutInflater());        //reference to xml object, inflate converts xml to java reference
        setContentView(binding.getRoot());                                          //object representation of view
        repository = eCommerceRepository.getRepository(getApplication());
        userId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, -1);

        binding.backToPage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(AdminActivity.adminIntentFactory(getApplicationContext(), userId));
            }
        });

        binding.editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });
    }

    static Intent adminEditItemIntentFactory(Context context, int userId){
        Intent intent = new Intent(context, AdminEditItemActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }
}