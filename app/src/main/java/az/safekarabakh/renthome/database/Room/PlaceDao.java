package az.safekarabakh.renthome.database.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import az.safekarabakh.renthome.database.Place;
import io.reactivex.rxjava3.core.Completable;

@Dao
public interface PlaceDao {

    @Query("Select * FROM Place")
    List<Place> getAll();

    @Insert
    void insert(Place place);

    @Delete
    Completable delete(Place place);

}
