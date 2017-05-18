package escholz.oarch;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

public class LoadTask extends AsyncTask<Void, Void, LiveData<List<Data>>> {
    private final OnLoadCompleteListener listener;
    private AppDatabase appDatabase;

    public LoadTask(@NonNull AppDatabase appDatabase, @Nullable OnLoadCompleteListener listener) {
        this.listener = listener;
        this.appDatabase = appDatabase;
    }

    @Override
    protected LiveData<List<Data>> doInBackground(Void... voids) {
        return appDatabase.dataDao().getAll();
    }

    @Override
    protected void onPostExecute(LiveData<List<Data>> data) {
        if (listener != null)
            listener.onLoadComplete(this, data);
    }

    public interface OnLoadCompleteListener {
        void onLoadComplete(LoadTask loadTask, LiveData<List<Data>> data);
    }
}
