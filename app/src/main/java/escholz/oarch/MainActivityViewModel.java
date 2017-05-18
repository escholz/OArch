package escholz.oarch;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.persistence.room.Room;

import java.util.List;
import java.util.Locale;

public class MainActivityViewModel extends AndroidViewModel
        implements LoadTask.OnLoadCompleteListener, InsertTask.OnInsertCompleteListener {

    private CharSequence csvColumnTitle;
    private CharSequence csvColumnDuration;
    private MutableLiveData<List<Data>> list = new MutableLiveData<>();
    private LoadTask loadTask;
    private InsertTask insertTask;
    private AppDatabase appDatabase;
    private int lastIndex = 0;

    public final LiveData<String> csv = Transformations.map(list, list -> {
        final StringBuilder builder = new StringBuilder();
        if (list != null && !list.isEmpty()) {
            builder
                .append(csvColumnTitle)
                .append(",")
                .append(csvColumnDuration)
                .append("\n");
            for (Data record : list)
                builder
                    .append(record.getTitle())
                    .append(",")
                    .append(record.getDurationInMs())
                    .append("\n");
        }
        return builder.toString();
    });

    public MainActivityViewModel(Application application) {
        super(application);

        final Application context = getApplication();
        csvColumnTitle = context.getString(R.string.csv_column_title);
        csvColumnDuration = context.getString(R.string.csv_column_duration);

        appDatabase = Room
                .inMemoryDatabaseBuilder(context, AppDatabase.class)
                .build();
    }

    public void loadDataAsync() {
        if (loadTask == null) {
            loadTask = new LoadTask(appDatabase, this);
            loadTask.execute();
        }
    }

    public void addRecord() {
        if (insertTask == null) {
            insertTask = new InsertTask(appDatabase, this);
            Data newData = new Data();
            newData.setId(lastIndex);
            newData.setTitle(String.format(Locale.US, "Item #%d", lastIndex));
            newData.setDurationInMs(lastIndex*1000);
            insertTask.execute(newData);
            lastIndex++;
        }
    }

    @Override
    protected void onCleared() {
        if (loadTask != null) {
            loadTask.cancel(true);
            loadTask = null;
        }
        if (insertTask != null) {
            insertTask.cancel(true);
            insertTask = null;
        }

        super.onCleared();
    }

    @Override
    public void onLoadComplete(LoadTask loadTask, LiveData<List<Data>> data) {
        if (this.loadTask == loadTask)
            this.loadTask = null;

        data.observeForever(items -> {
            if (list != null && items != null)
                list.setValue(items);
        });
    }

    @Override
    public void onInsertComplete(InsertTask insertTask) {
        if (this.insertTask == insertTask)
            this.insertTask = null;
    }
}
