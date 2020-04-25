public class Logic {
    private double tripPoint = 2.97;

    public Logic(DiffValues dv1, DiffValues dv2, RMSValues rms) {
        this.dv1 = dv1;
        this.dv2 = dv2;
        this.rms = rms;
    }

    private DiffValues dv1, dv2;
    private RMSValues rms;
    private boolean trip=false;
    double endsection1=1;
    double endsection2=5;
    double Idmin=0.427;
    double IdUnre=2;

    double slopeSection2=0.3;
    double slopeSection3=0.3;



    public void process()  {
        trip= (cutoff()|brakechar()) ;
        Charts.addAnalogData(0, 2, Idmin);
        Charts.addAnalogData(1, 2, Idmin);
        Charts.addAnalogData(2, 2, Idmin);
        Charts.addDiscreteData(0, trip);
       // if (rms.getTime()>1424000) Charts.addDiscreteData(0, trip);
    }


    boolean brakechar(){
        trip=false;
        double section1=slopeSection2*(dv1.getDiffbrake()-endsection1)+Idmin;
        double section2=slopeSection3*(dv1.getDiffbrake()-endsection2)+Idmin;
        if (dv1.getDiffbrake()<endsection2 && dv1.getDiffbrake()>endsection1){
            if (dv1.getDiffPhA()>section1| dv1.getDiffPhB()>section1| dv1.getDiffPhC()>section1) trip=true;
        }
        else if (dv1.getDiffbrake()>=endsection2){
            if (dv1.getDiffPhA()>section2| dv1.getDiffPhB()>section2| dv1.getDiffPhC()>section2) trip=true;
        }
        else if (dv1.getDiffPhA()>Idmin| dv1.getDiffPhB()>Idmin| dv1.getDiffPhC()>Idmin) trip=true;
        return trip;
    }
    boolean cutoff(){
        trip=false;
        if (dv1.getDiffPhA()>IdUnre| dv1.getDiffPhB()>IdUnre| dv1.getDiffPhC()>IdUnre) trip=true;
        return trip;
    }
    boolean harmonicblock() {
        trip=false;
        if (dv2.getDiffPhA()/dv1.getDiffPhA()>0.4|dv2.getDiffPhB()/dv1.getDiffPhB()>0.4|dv2.getDiffPhC()/dv1.getDiffPhC()>0.4) trip=true;
        System.out.println(trip);
        return trip;
    }
}
