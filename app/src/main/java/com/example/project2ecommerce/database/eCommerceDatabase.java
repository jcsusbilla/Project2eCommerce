package com.example.project2ecommerce.database;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.project2ecommerce.database.entities.User;
import com.example.project2ecommerce.database.entities.eCommerce;
import com.example.project2ecommerce.MainActivity;
import com.example.project2ecommerce.database.typeConverters.LocalDateTypeConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@TypeConverters(LocalDateTypeConverter.class)
@Database(entities = {eCommerce.class, User.class}, version = 1, exportSchema = false)
public abstract class eCommerceDatabase extends RoomDatabase {
    //define all table names here
    public static final String USER_TABLE = "usertable";
    private static final String DATABASE_NAME = "eCommerceDatabase";
    public static final String eCommerceTable = "eCommerceTable";

    private static volatile eCommerceDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static eCommerceDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (eCommerceDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            eCommerceDatabase.class,
                                    DATABASE_NAME
                            )
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)//
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);                 //pass in the database
            Log.i(MainActivity.TAG, "Database created");
            databaseWriteExecutor.execute(()->{
                UserDAO dao = INSTANCE.userDAO();
                dao.deleteAll();
                //admin
                User admin = new User("admin1", "admin1", false);
                admin.setAdmin(true);
                dao.insert(admin);
                //user
                User testUser1 = new User("testuser1", "testuser1", true);
                dao.insert(testUser1);
            });
        }
    };

    public abstract eCommerceDAO ecommerceDAO();
    public abstract UserDAO userDAO();
}
