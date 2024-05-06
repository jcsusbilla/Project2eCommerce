package com.example.project2ecommerce.database;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.project2ecommerce.database.entities.StoreItem;
import com.example.project2ecommerce.database.entities.User;
import com.example.project2ecommerce.database.entities.eCommerce;
import com.example.project2ecommerce.MainActivity;
import com.example.project2ecommerce.database.entities.SavedPurchases;
import com.example.project2ecommerce.database.typeConverters.LocalDateTypeConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@TypeConverters(LocalDateTypeConverter.class)
@Database(entities = {eCommerce.class, User.class, StoreItem.class, SavedPurchases.class}, version = 4, exportSchema = false)      //dao
public abstract class eCommerceDatabase extends RoomDatabase {
    //define all table names here
    public static final String USER_TABLE = "usertable";                //dao
    public static final String ITEM_TABLE = "items";                    //dao
    public static final String SAVED_TABLE = "saved";
    private static final String DATABASE_NAME = "eCommerceDatabase";    //dao
    public static final String eCommerceTable = "eCommerceTable";       //dao

    private static volatile eCommerceDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static eCommerceDatabase getDatabase(final Context context){
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
                //DEFAULT USERS
                UserDAO dao = INSTANCE.userDAO();
                dao.deleteAll();
                //admin
                User admin = new User("admin1", "admin1");
                admin.setAdmin(true);
                dao.insert(admin);
                //user
                User testUser1 = new User("testuser1", "testuser1");
                dao.insert(testUser1);

                StoreItemDAO itemDao = INSTANCE.storeItemDao();
                //itemDao.deleteAll();

                //DEFAULT PLANTS
                StoreItem Monstera = new StoreItem("Monstera", "A climbing, evergreen perennial vine that is perhaps most noted for its large perforated leaves on thick plant stems and its long cord-like aerial roots.", 14.99, 50);
                StoreItem MonsteraAlbo = new StoreItem("Monstera Albo", "Unlike the common Monstera, the 'Albo' variety features patches of pure white or light cream on its leaves alongside the traditional deep green.", 75.99, 25);
                StoreItem MoneyTree = new StoreItem("Money Tree", "a braided tree that can grow up to 6-8 feet indoors or be trained as a bonsai.", 15.99, 30);
                StoreItem GoldenPothos = new StoreItem("Golden Pothos", "A climbing vine that produces abundant yellow-marbled foliage.", 9.99, 15);
                StoreItem FiddleLeafFig = new StoreItem("Fiddle Leaf Fig", "A small tropical tree and broadleaf evergreen with large, broad, lyre-shaped, green leaves that can measure up to 18 inches long.", 29.99, 7);
                StoreItem Fern = new StoreItem("Fern", "N/A", 5.99, 0);
                StoreItem SnakePlant = new StoreItem("Snake Plant", "N/A", 10.99, 45);
                StoreItem TreePhilodendron = new StoreItem("Tree Philodendron", "N/A", 14.99, 17);
                StoreItem JadePlant = new StoreItem("Jade Plant", "N/A", 19.99, 5);
                StoreItem StringOfPearls = new StoreItem("String of Pearls", "N/A", 12.99, 20);

                itemDao.insert(Monstera);
                itemDao.insert(MonsteraAlbo);
                itemDao.insert(MoneyTree);
                itemDao.insert(GoldenPothos);
                itemDao.insert(FiddleLeafFig);
                itemDao.insert(Fern);
                itemDao.insert(SnakePlant);
                itemDao.insert(TreePhilodendron);
                itemDao.insert(JadePlant);
                itemDao.insert(StringOfPearls);
            });
        }
    };

    public abstract eCommerceDAO ecommerceDAO();        //dao
    public abstract UserDAO userDAO();                  //dao
    public abstract StoreItemDAO storeItemDao();        //dao
    public abstract SavedPurchasesDAO savedPurchasesDAO();
}
