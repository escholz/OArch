package escholz.oarch;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

public class InsertTask extends AsyncTask<Data, Void, Void> {

    private final AppDatabase appDatabase;
    private final OnInsertCompleteListener listener;

    public InsertTask(@NonNull AppDatabase appDatabase, OnInsertCompleteListener listener) {
        this.appDatabase = appDatabase;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Data... data) {
        appDatabase.dataDao().insertAll(data);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if (listener != null)
            listener.onInsertComplete(this);
    }

    public interface OnInsertCompleteListener {
        void onInsertComplete(InsertTask insertTask);
    }
}
