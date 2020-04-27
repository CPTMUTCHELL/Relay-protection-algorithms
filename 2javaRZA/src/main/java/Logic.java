public class Logic {
    public Logic(DiffValues dv1, DiffValues dv2) {
        this.dv1 = dv1;
        this.dv2 = dv2;
    }
    private DiffValues dv1, dv2;
    private boolean trip=false;
    private double endsection1=1;//конец 1-ого уч
    private double endsection2=5;//конец 2-ого уч
    private double Idmin=0.427; //нач.диф.ток
    private double IdUnre=2; //отсечка
    private double slopeSection2=0.3;//наклон на 2м уч.
    private double slopeSection3=0.3;//наклон на 3м уч.

    public void process()  {
        if (!harmonicblock()){
            trip = brakechar() | cutoff();
        }
        Charts.addDiscreteData(0, trip);

    }
    //Функция диф.защиты с торможением
    private boolean brakechar(){
        trip=false;
        double section1=slopeSection2*(dv1.getDiffbrake()-endsection1)+Idmin;//расчёт хар-ки срабатываний
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
    //Функция отсечки
    private boolean cutoff(){
        trip=false;
        if (dv1.getDiffPhA()>IdUnre| dv1.getDiffPhB()>IdUnre| dv1.getDiffPhC()>IdUnre) trip=true;
        return trip;
    }
    //Функция блокировки по 2й гарм.
    private boolean harmonicblock() {
        trip=false;
        if (dv2.getDiffPhA()/dv1.getDiffPhA()>2|dv2.getDiffPhB()/dv1.getDiffPhB()>2|dv2.getDiffPhC()/dv1.getDiffPhC()>2) trip=true;
        return trip;
    }
}
