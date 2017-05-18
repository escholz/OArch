package escholz.oarch;

public class Data {
    private String title;
    private long duration;

    public Data(String title, long duration) {
        this.title = title;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public long getDuration() {
        return duration;
    }
}
