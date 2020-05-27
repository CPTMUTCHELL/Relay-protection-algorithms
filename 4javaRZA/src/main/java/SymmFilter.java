public class SymmFilter {

    private RMSValues rms;
    private SymmValues scv;
    private double F0;
    private double F0x;
    private double F0y;

    public SymmFilter(RMSValues rms, SymmValues scv) {
        this.rms = rms;
        this.scv = scv;
    }
    public  void calculate(){
        //расчёт токов и напр 0 послед
        F0x=(rms.getPhAX()+rms.getPhBX()+rms.getPhCX())/3;
        F0y=(rms.getPhAY()+rms.getPhBY()+rms.getPhCY())/3;
        F0=Math.sqrt(Math.pow(F0x,2)+Math.pow(F0y,2));
        scv.setF0(F0);
        scv.setAngle0(angle_calc(F0x,F0y));
    }
    private double angle_calc(double x, double y){
        double angle=Math.toDegrees(Math.atan(y/x));
        if (x<0 && y>0) {
            angle = 180+angle;
        }
        else if (y<0 && x<0) angle = -180+angle;
        return angle;
    }

}
