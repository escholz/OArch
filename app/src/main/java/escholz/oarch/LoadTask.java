package escholz.oarch;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LoadTask extends AsyncTask<String, Void, List<Data>> {
    private final OnLoadCompleteListener listener;

    public LoadTask(OnLoadCompleteListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<Data> doInBackground(String... strings) {
        List<Data> list = new ArrayList<>(10);
        for (int index = 0; index < 10; index++)
            list.add(new Data(String.format(Locale.US, "Item #%d", index),
                    100*index));
        return list;
    }

    @Override
    protected void onPostExecute(List<Data> data) {
        if (listener != null)
            listener.onLoadComplete(this, data);
    }

    public interface OnLoadCompleteListener {
        void onLoadComplete(LoadTask loadTask, List<Data> data);
    }
}
