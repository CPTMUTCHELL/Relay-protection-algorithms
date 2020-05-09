public class Logic {
    public Logic(SampleValues sv,RMSValues Irms, RMSValues Urms,ImpedanceValues iv) {
        this.Irms = Irms;
        this.Urms = Urms;
        this.iv=iv;
        this.sv=sv;
    }
    boolean trip1=false; boolean trip2=false;  boolean trip3=false; //срабатывание защиты
    private SampleValues sv;
    private RMSValues Irms;
    private RMSValues Urms;
    private ImpedanceValues iv;
    private double blocktrip=50;//уставка блокировки, если выше, то КЗ, иначе качание
    private double time1=0; private double time2=0;private double time3=0;//время срабатывания
    private double blocktime=0;//время разрешенной работы защиты
    public double dt=0.001;
    private boolean block=false;
    double dIa = 0; double dIb = 0; double dIc = 0;//производные по току
    double dUa = 0; double dUb = 0; double dUc = 0;//производные по напряжению
    double lastIa = 0; double lastIb = 0; double lastIc = 0; double lastUa = 0; double lastUb = 0; double lastUc = 0;
    double Z1=76; double Z2=146; double Z3=275; //расчитанные уставки ступеней
    double angle=81;// угол линии arctg(Xl/Rl)
    //координаты центра окружности
    double Xcenter1=Z1*Math.cos(Math.toRadians(angle))/2; double Ycenter1=Z1*Math.sin(Math.toRadians(angle))/2;
    double Xcenter2=Z2*Math.cos(Math.toRadians(angle))/2; double Ycenter2=Z2*Math.sin(Math.toRadians(angle))/2;
    double Xcenter3=Z3*Math.cos(Math.toRadians(angle))/2; double Ycenter3=Z3*Math.sin(Math.toRadians(angle))/2;

    public double getZ1() {
        return Z1;
    }

    public double getXcenter1() {
        return Xcenter1;
    }

    public double getYcenter1() {
        return Ycenter1;
    }

    public double getZ2() {
        return Z2;
    }

    public double getZ3() {
        return Z3;
    }

    public double getXcenter2() {
        return Xcenter2;
    }

    public double getYcenter2() {
        return Ycenter2;
    }

    public double getXcenter3() {
        return Xcenter3;
    }

    public double getYcenter3() {
        return Ycenter3;
    }

    public void process()  {
       if (blocked()) blocktime+=0.001;//считаем разрешенное блокировкой время
            if (blocktime<=1.1) {//если не превышает, то защита работает
                //1я ст
                if (operate(iv.getZAX(), iv.getZAY(), Xcenter1, Ycenter1, Z1 / 2) ||
                        operate(iv.getZBX(), iv.getZBY(), Xcenter1, Ycenter1, Z1 / 2) ||
                        operate(iv.getZCX(), iv.getZCY(), Xcenter1, Ycenter1, Z1 / 2)){
                    time1+=0.001;
                    if (time1>=0.02) trip1=true; //1я ст.
                }
                if (!trip1) {  //вторая ступень
                    if (operate(iv.getZAX(), iv.getZAY(), Xcenter2, Ycenter2, Z2 / 2) |
                            operate(iv.getZBX(), iv.getZBY(), Xcenter2, Ycenter2, Z2 / 2)|
                            operate(iv.getZCX(), iv.getZCY(), Xcenter2, Ycenter2, Z2 / 2)) time2+=0.001;
                    if (time2>0.5) trip2=true;
                }
                if (!trip1&&!trip2){ //третья ст.
                    if (operate(iv.getZAX(), iv.getZAY(), Xcenter3, Ycenter3, Z3 / 2) |
                            operate(iv.getZBX(), iv.getZBY(), Xcenter3, Ycenter3, Z3 / 2)|
                            operate(iv.getZCX(), iv.getZCY(), Xcenter3, Ycenter3, Z3 / 2)) time3+=0.001;
                    if (time3>1) trip3=true;
            }
        }
        Charts.addDiscreteData(0, trip1);
        Charts.addDiscreteData(1, trip2);
        Charts.addDiscreteData(2, trip3);
    }
    private boolean blocked(){//функция блокировки по производной.
        //если произв.маленькая, то качания, если большое, то КЗ
        dIa=Math.abs(Irms.getPhA()-lastIa)/dt;
        dIb =Math.abs(Irms.getPhB()-lastIb)/dt;
        dIc =Math.abs(Irms.getPhC()-lastIc)/dt;
        lastIa=Irms.getPhA();
        lastIb=Irms.getPhB();
        lastIc=Irms.getPhC();
        dUb=Math.abs(Urms.getPhA()-lastUa)/dt;
        dIb =Math.abs(Urms.getPhB()-lastUb)/dt;
        dIc =Math.abs(Urms.getPhC()-lastUc)/dt;
        lastUa=Urms.getPhA();
        lastUb=Urms.getPhB();
        lastUc=Urms.getPhC();
        block=((dIa>blocktrip)|(dIb >blocktrip)|(dIc >blocktrip))|((dUa>blocktrip)|(dUb>blocktrip)|(dUc>blocktrip));

        return block;
    }
    //Функция срабатывания реле
    private boolean operate(double ZX, double ZY, double Xcenter, double Ycenter, double radius){
        boolean trip;
        trip = Math.pow( (ZX - Xcenter), 2) + Math.pow((ZY - Ycenter), 2) <= Math.pow(radius, 2);
        return trip;
    }
}
