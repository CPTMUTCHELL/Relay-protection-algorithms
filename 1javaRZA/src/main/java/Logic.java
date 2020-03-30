public class Logic {
    private double tripPoint = 2.97;
    private RMSValues rms;
    private boolean trip=false;

    public void process()  {
        if(rms.getPhA()   > tripPoint) trip=true;

        if(rms.getPhB() > tripPoint) trip=true;;
        if(rms.getPhC() > tripPoint) trip=true;

        Charts.addAnalogData(0, 2, tripPoint);
        Charts.addAnalogData(1, 2, tripPoint);
        Charts.addAnalogData(2, 2, tripPoint);
        if (rms.getTime()>1424000) Charts.addDiscreteData(0, trip);
    }

    public void setRms(RMSValues rms) {
        this.rms = rms;
    }
}
