package escholz.oarch;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DataDao {
    @Query("SELECT * FROM data")
    LiveData<List<Data>> getAll();

    @Query("SELECT * FROM data WHERE id IN (:ids)")
    List<Data> findAllById(int[] ids);

    @Query("SELECT * FROM data WHERE title LIKE :title LIMIT 1")
    Data findByTitle(String title);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Data... data);

    @Update
    public void updateData(Data... data);

    @Delete
    void delete(Data data);
}
