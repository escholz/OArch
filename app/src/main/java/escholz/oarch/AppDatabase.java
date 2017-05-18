package escholz.oarch;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Data.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract DataDao dataDao();
}
