package escholz.oarch;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Data {
    @PrimaryKey
    private int id;
    private String title;
    @ColumnInfo(name = "duration-ms")
    private long durationInMs;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public long getDurationInMs() {
        return durationInMs;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDurationInMs(long durationInMs) {
        this.durationInMs = durationInMs;
    }
}
