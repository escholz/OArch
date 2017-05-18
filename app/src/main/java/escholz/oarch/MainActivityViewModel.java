package escholz.oarch;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import java.util.List;

public class MainActivityViewModel extends ViewModel implements LoadTask.OnLoadCompleteListener {

    private MutableLiveData<List<Data>> list = new MutableLiveData<>();
    public final LiveData<String> csv = Transformations.map(list, list -> {
        final StringBuilder builder = new StringBuilder();
        if (list != null && !list.isEmpty()) {
            // TODO: String resources
            builder
                .append("title")
                .append(",")
                .append("duration")
                .append("\n");
            for (Data record : list)
                builder
                    .append(record.getTitle())
                    .append(",")
                    .append(record.getDuration())
                    .append("\n");
        }
        return builder.toString();
    });
    private AsyncTask<String, Void, List<Data>> asyncTask;

    public void loadDataAsync() {
        if (asyncTask == null)
            asyncTask = new LoadTask(this).execute();
    }

    @Override
    protected void onCleared() {
        list = null;
        if (asyncTask != null) {
            asyncTask.cancel(true);
            asyncTask = null;
        }

        super.onCleared();
    }

    @Override
    public void onLoadComplete(LoadTask loadTask, List<Data> data) {
        if (asyncTask == loadTask)
            asyncTask = null;

        if (list != null) {
            list.setValue(data);
        }
    }
}
