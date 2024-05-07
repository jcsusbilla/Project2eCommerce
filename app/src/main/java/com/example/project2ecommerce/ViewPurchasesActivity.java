package com.example.project2ecommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.example.project2ecommerce.database.eCommerceRepository;
import com.example.project2ecommerce.database.entities.SavedPurchases;
import com.example.project2ecommerce.databinding.ActivityViewPurchasesBinding;

import java.util.List;

public class ViewPurchasesActivity extends AppCompatActivity {
    ActivityViewPurchasesBinding binding;
    eCommerceRepository repository;
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.project2ecommerce.MAIN_ACTIVITY_USER_ID";
    private int userId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_purchases);
        binding = ActivityViewPurchasesBinding.inflate(getLayoutInflater());        //reference to xml object, inflate converts xml to java reference
        setContentView(binding.getRoot());                                          //object representation of view
        repository = eCommerceRepository.getRepository(getApplication());
        userId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, -1);

        LiveData<List<SavedPurchases>> savedListObserver = repository.getAllSavedPurchases();
        savedListObserver.observe(this, savedItems -> {
            if(savedItems.isEmpty()){
                toastMaker("Cart is Empty!");
            } else {
                populateScreen(savedItems);
            }
        });

        binding.backToPage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), userId));
            }
        });
    }

    public void populateScreen(List<SavedPurchases> savedItems){
        //create table column head names
        TableLayout table = (TableLayout) findViewById(R.id.itemsTable);            //Initialize a table
        TableRow tableRow = new TableRow(this);                             //set the TableRow for the whole item within the TableLayout

        //create the table columns that go in each row
        TextView item_name = new TextView(this);                            //Creates new TextView to add to the TableRow
        TextView item_quantity = new TextView(this);
        TextView item_total_price = new TextView(this);

        //set header names for each column
        item_name.setText("PLANT");
        item_quantity.setText("QUANTITY");
        item_total_price.setText("$");

        //set the item name textview
        item_name.setPadding(15, 10, 30, 10);
        item_name.setTextColor(Color.GREEN);
        item_name.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        item_name.setTextSize(22);
        tableRow.addView(item_name);

        //set the quantity textview
        item_quantity.setPadding(30, 10, 0, 10);
        item_quantity.setGravity(Gravity.CENTER);
        item_quantity.setTextColor(Color.GREEN);
        item_quantity.setTextSize(22);
        tableRow.addView(item_quantity);                                           //Add the TextView to the TableRow
        table.addView(tableRow);                                                   //Add the TextRow to the TableLayout

        //set the price textview
        item_total_price.setPadding(30, 10, 0, 10);
        item_total_price.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        item_total_price.setTextColor(Color.GREEN);
        item_total_price.setTextSize(22);
        for(SavedPurchases item : savedItems){
            if(item.getUserId() == userId){
                //send item to cart display if userid attached to the item matches current userid

                //get item name
                String name = item.getPlant_name();
                Log.i(MainActivity.TAG, name);
                //get quantity of each item
                int quantity = item.getQuantity();
                Log.i(MainActivity.TAG, String.valueOf(quantity));
                //get price of each item
                double price = item.getPrice();
                price = price * quantity;                                   //if multiple
                Log.i(MainActivity.TAG, String.valueOf(price));

                //initialize item row textview for each value
                TableRow row = new TableRow(this);
                TextView itemName = new TextView(this);
                TextView quantityText = new TextView(this);
                TextView priceText = new TextView(this);

                //set name content in row
                itemName.setText(name);
                itemName.setGravity(Gravity.CENTER);
                itemName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                itemName.setTextColor(Color.BLACK);
                itemName.setTextSize(15);
                row.setPadding(15, 10, 30, 10);

                //set quantity content in row
                quantityText.setText(String.valueOf(quantity));
                quantityText.setTextColor(Color.BLACK);
                quantityText.setGravity(Gravity.CENTER);
                row.setPadding(30, 10, 0, 10);
                quantityText.setTextColor(Color.BLACK);
                quantityText.setTextSize(12);

                //set price content in row
                priceText.setText(String.valueOf(price));
                priceText.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                row.setPadding(30, 10, 0, 10);
                priceText.setTextColor(Color.BLACK);
                priceText.setTextSize(15);

                //add each attribute to the row
                row.addView(itemName);
                row.addView(quantityText);
                row.addView(priceText);

                //add row to the table_layout
                table.addView(row);
                table.setGravity(Gravity.CENTER_HORIZONTAL);                //center the table to screen horizontally
            }
        }
    }

    static Intent viewPurchasesIntentFactory(Context context, int userId){
        Intent intent = new Intent(context, ViewPurchasesActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }
    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}