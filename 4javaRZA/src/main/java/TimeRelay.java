public class TimeRelay {
    private double time;
    private boolean trip;
    private double timesetting;

    public TimeRelay(double timesetting) {
        this.timesetting = timesetting;
    }

    boolean isOperated(boolean operated){
        if (operated)time+=0.001;
        if (time>timesetting) trip=true;
        return trip;
    }
}
