package az.safekarabakh.renthome.database.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import az.safekarabakh.renthome.database.Place;

@Database(entities = {Place.class}, version = 5)
public abstract class PlaceDatabase extends RoomDatabase {

    public static String DATABASE_NAME = "PlaceDatabse";

    private static PlaceDatabase instance;

    public static PlaceDatabase getDatabase(Context context) {
        synchronized (PlaceDatabase.class) {
            if (instance == null) {
                instance = Room.databaseBuilder(context, PlaceDatabase.class, DATABASE_NAME)
                        .fallbackToDestructiveMigration().allowMainThreadQueries().build();
            }
            return instance;
        }
    }

    public abstract PlaceDao placeDao();
}