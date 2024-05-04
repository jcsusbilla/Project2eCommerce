package com.example.project2ecommerce.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.project2ecommerce.database.entities.StoreItem;
import com.example.project2ecommerce.database.entities.User;
import com.example.project2ecommerce.database.entities.eCommerce;
import com.example.project2ecommerce.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class eCommerceRepository {
    //attributes
    private final eCommerceDAO ecommerceDAO;        //dao
    private final UserDAO userDAO;                  //dao
    private final StoreItemDAO storeItemDAO;
    private ArrayList<eCommerce> allCarts;
    private static eCommerceRepository repository;

    //constructor
    public eCommerceRepository(Application application){
        eCommerceDatabase db = eCommerceDatabase.getDatabase(application);
        this.ecommerceDAO = db.ecommerceDAO();          //dao
        this.userDAO = db.userDAO();                    //dao
        this.storeItemDAO = db.storeItemDao();
        this.allCarts = (ArrayList<eCommerce>) this.ecommerceDAO.getAllRecords(); //cast into ArrayList
    }

    //methods
    public static eCommerceRepository getRepository(Application application){
        if(repository != null){
            return repository;
        }
        Future<eCommerceRepository> future = eCommerceDatabase.databaseWriteExecutor.submit(
                new Callable<eCommerceRepository>() {
                    @Override
                    public eCommerceRepository call() throws Exception {
                        return new eCommerceRepository(application);
                    }
                }
        );
        try{
            return future.get();
        } catch (InterruptedException | ExecutionException e){
            Log.d(MainActivity.TAG, "Problem getting eCommerceRepository, thread error");
        }
        return null;
    }

    public ArrayList<eCommerce> getAllCarts() {
        Future<ArrayList<eCommerce>> future = eCommerceDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<eCommerce>>() {
                    @Override
                    public ArrayList<eCommerce> call() throws Exception {
                        return (ArrayList<eCommerce>) ecommerceDAO.getAllRecords();
                    }
                }
            );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e){
            Log.i(MainActivity.TAG, "Problem when getting all in repository");
        }
        return null;
    }

    public void insertECommerce(eCommerce ecommerce){
        eCommerceDatabase.databaseWriteExecutor.execute(()->
        {
            ecommerceDAO.insert(ecommerce);
        });
    }
    //------------------------------------------------------------------------------------------------------------
    //LiveData for User
    public void insertUser(User... user){
        eCommerceDatabase.databaseWriteExecutor.execute(()->
        {
            userDAO.insert(user);
        });
    }

    public LiveData<User> getUserByUserName(String username) {          //LiveData is automatically muiltithreaded
        return userDAO.getUserByUserName(username);
    }

    public LiveData<User> getUserByUserId(int userId) {          //LiveData is automatically muiltithreaded
        return userDAO.getUserByUserId(userId);
    }


    //Dont think this is working properly yet
    public LiveData<List<User>> getAllUsers(){
        return userDAO.getAllUsers();
    }

//    public LiveData<List<eCommerce>> getAllCartsByUserId(int userId){
//        return eCommerce.getAllCartsByUserId(userId);
//    }

    public void updateUserPassword(int userId, String newPassword){
        eCommerceDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.updateUserPassword(userId, newPassword);
        });
    }
    //------------------------------------------------------------------------------------------------------------
    public void insertUser(StoreItem... storeItem){
        eCommerceDatabase.databaseWriteExecutor.execute(()->
        {
            storeItemDAO.insert(storeItem);
        });
    }

    public LiveData<List<StoreItem>> getAllItems(){
        return storeItemDAO.getAllItems();
    }
}
