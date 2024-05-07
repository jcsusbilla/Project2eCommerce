package com.example.project2ecommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import android.annotation.SuppressLint;
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
import com.example.project2ecommerce.database.entities.StoreItem;
import com.example.project2ecommerce.database.entities.eCommerce;
import com.example.project2ecommerce.databinding.ActivityPurchaseItemsBinding;

import java.util.List;

/**
 *  Author: JC SUSBILLA
 *  Class for populating an items listing page with available shop plants.
 *  Users will type the name of the plant and the quantity they'd like to purchase.
 *  Clicking checkout will purchase the items, send the purchased items to a DAO recording previous purchases, and user is sent back to homescreen.
 */

public class PurchaseItemsActivity extends AppCompatActivity {
    ActivityPurchaseItemsBinding binding;
    eCommerceRepository repository;
    //public final int MAX_ITEMS = 15;
    //private int defaultAmountItems = 5;
    private static final String MAIN_ACTIVITY_USER_ID = "com.example.project2ecommerce.MAIN_ACTIVITY_USER_ID";
    private int userId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_items);
        userId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, -1);

        binding = ActivityPurchaseItemsBinding.inflate(getLayoutInflater());        //reference to xml object, inflate converts xml to java reference
        setContentView(binding.getRoot());                                          //object representation of view

        repository = eCommerceRepository.getRepository(getApplication());
        //eCommerceDatabase db = eCommerceDatabase.getDatabase(this);

        LiveData<List<StoreItem>> storeItemListObserver = repository.getAllItems();
        //LiveData<StoreItem> itemObserver = repository.getItemById();
        storeItemListObserver.observe(this, storeItems -> {
            if(storeItems != null){
                defaultItems(storeItems);
            } else {
                toastMaker("StoreItem DB Error");//testing
            }
        });

        binding.backToPage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), userId));
            }
        });

        binding.viewCartButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(ViewCartActivity.viewCartIntentFactory(getApplicationContext(), userId));
            }
        });
    }

    //methods
    //finish later for Admin Add Items function
    public void addItem(List<StoreItem> products, String name, double price, int quantity) {
//        TableLayout table_layout = new TableLayout(this);
//        TableRow table_row1 = new TableRow(this);
//        TableRow table_row2 = new TableRow(this);
//        TableRow table_row3 = new TableRow(this);
    }

    @SuppressLint("SetTextI18n")
    public void defaultItems(List<StoreItem> StoreItems) {
        //create table column head names
        //int tableId = R.id.itemsTable;
        TableLayout table = (TableLayout) findViewById(R.id.itemsTable);            //Initialize a table
        TableRow tableRow = new TableRow(this);                             //set the TableRow for the whole item within the TableLayout

        //create the table columns that go in each row
        TextView item_name = new TextView(this);                            //Creates new TextView to add to the TableRow
        TextView item_quantity = new TextView(this);
        TextView item_price = new TextView(this);

        //set header names for each column
        item_name.setText("PLANT");
        item_quantity.setText("STOCk");
        item_price.setText("$");

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
        item_price.setPadding(30, 10, 0, 10);
        item_price.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        item_price.setTextColor(Color.GREEN);
        item_price.setTextSize(22);
        tableRow.addView(item_price);                                            //Add the TextView to the TableRow

        //add rows of items to the table
        if(!StoreItems.isEmpty()){
            if(StoreItems.size() < 15){
                for (StoreItem storeItem : StoreItems) {
                    //extract item name
                    String item = storeItem.getName();
                    Log.i(MainActivity.TAG, item);
                    //extract item price
                    double price = storeItem.getPrice();
                    Log.i(MainActivity.TAG, String.valueOf(price));
                    //extract item description
//                String description = storeItem.getDesc();
//                Log.i(MainActivity.TAG, String.valueOf(description));
                    //extract quantity
                    int quantity = storeItem.getQuantity();
                    Log.i(MainActivity.TAG, String.valueOf(quantity));
                    //extract stock
//                Boolean stock = storeItem.isInStock();
//                Log.i(MainActivity.TAG, String.valueOf(quantity));

                    //initialize item row textview for each value
                    TableRow row = new TableRow(this);
                    TextView itemName = new TextView(this);
                    TextView quantityText = new TextView(this);
                    TextView priceText = new TextView(this);

                    //set name content in row
                    itemName.setText(item);
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
                    //table.callOnClick()
                }
            }
        }

        binding.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.plantNameEntry.getText().toString();
                LiveData<StoreItem> itemObserver = repository.getItemByItemName(name);
                try{
                    String enteredQuantity = binding.quantityEntry.getText().toString();
                    int quantity = Integer.parseInt(enteredQuantity);
                    itemObserver.observe(PurchaseItemsActivity.this, item -> {
                        if(verifyQuantity(quantity, item)){
                            if(item != null){
                                eCommerce cartItem = new eCommerce(item.getName(), item.getPrice(), item.isInStock(), userId, item.getId(), quantity);
                                repository.insertECommerce(cartItem);
                                toastMaker("(" + enteredQuantity + ") "+ name + " added to cart");
                            }
                        } else{
                            toastMaker("Invalid Quantity.");
                        }
                        //reset user entry fields
                        binding.plantNameEntry.setText("");
                        binding.quantityEntry.setText("");
                    });
                } catch (NumberFormatException e){
                    toastMaker("Please enter a valid number!");
                }
            }
        });
    }

    public void addItemToPurchaseList(String plant_name, double item_price, int item_quantity){
        TableLayout table = (TableLayout) findViewById(R.id.itemsTable);     //Initialize a table
        TableRow row = new TableRow(this);                           //set the TableRow for the whole item within the TableLayout

        //create the table columns that go in each row
        TextView itemName = new TextView(this);
        TextView quantityText = new TextView(this);
        TextView priceText = new TextView(this);

        Log.i(MainActivity.TAG, plant_name);//extract item name
        Log.i(MainActivity.TAG, String.valueOf(item_price));  //extract item price
        Log.i(MainActivity.TAG, String.valueOf(item_quantity));//extract quantity

        //set name content in row
        itemName.setText(plant_name);
        itemName.setGravity(Gravity.CENTER);
        itemName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        itemName.setTextColor(Color.BLACK);
        itemName.setTextSize(15);
        row.setPadding(15, 10, 30, 10);

        //set quantity content in row
        quantityText.setText(String.valueOf(item_quantity));
        quantityText.setTextColor(Color.BLACK);
        quantityText.setGravity(Gravity.CENTER);
        row.setPadding(30, 10, 0, 10);
        quantityText.setTextColor(Color.BLACK);
        quantityText.setTextSize(12);

        //set price content in row
        priceText.setText(String.valueOf(item_price));
        priceText.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        row.setPadding(30, 10, 0, 10);
        priceText.setTextColor(Color.BLACK);
        priceText.setTextSize(15);

        table.addView(row);
    }

    public boolean verifyQuantity(int quantity, StoreItem item){
        if(quantity == 0){
            toastMaker("Nothing added to cart.");
            return false;
        }
        if(quantity > item.getQuantity()){
            toastMaker("Not enough in stock");
            return false;
        } else {
            return true;
        }
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent purchaseItemsIntentFactory(Context context, int userId){
        Intent intent = new Intent(context, PurchaseItemsActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }
}