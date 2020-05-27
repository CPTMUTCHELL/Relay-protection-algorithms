public class Logic {

    public Logic(SymmValues iscv, DPRelay dpr) {
        this.Iscv = iscv;
        this.dpr = dpr;
    }
    private OutputData od = new OutputData();
    boolean trip1, trip2, trip3, trip4,trip5; //срабатывание защиты
    private SymmValues Iscv;
    private DPRelay dpr;
    private double setting1=2.3;
    private double setting2=1.6;
    private double setting3= 1.39;
    private double setting4=0.61;
    private double setting5=0.19;
    private double time1=0;
    private double time2=0.5;
    private double time3=1;
    private double time4=1.5;
    private double time5=2;
    private TimeRelay stage1=new TimeRelay(time1);
    private TimeRelay stage2=new TimeRelay(time2);
    private TimeRelay stage3=new TimeRelay(time3);
    private TimeRelay stage4=new TimeRelay(time4);
    private TimeRelay stage5=new TimeRelay(time5);


    public void process()  {
       if (!dpr.block()) {
          // System.out.println(Iscv.getF0());
           if (trip1 = stage1.isOperated(3*Iscv.getF0()>setting1)) {
               od.trip1(trip1);
           }
           if (!trip1) {
               if  (trip2 = stage2.isOperated(3*Iscv.getF0()>setting2)) od.trip2(trip2);
           }
           if (!trip1 && !trip2) {

              if (trip3 = stage3.isOperated(3*Iscv.getF0()>setting3)) od.trip3(trip3);
           }
           if (!trip1 && !trip2&&!trip3){
               if (trip4 = stage4.isOperated(3*Iscv.getF0()>setting4)) od.trip4(trip4);
           }
           if (!trip1 && !trip2&&!trip3&&!trip4){
               if  (trip5 = stage5.isOperated(3*Iscv.getF0()>setting5)) od.trip5(trip5);
           }

       }
    }
}
