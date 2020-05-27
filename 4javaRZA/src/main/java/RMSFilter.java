
public class RMSFilter {
    public RMSFilter(SampleValues sv, RMSValues rms) {
        this.sv = sv;
        this.rms = rms;
    }
    private double[][] sinbuffer = new double[80][4];
    private double[][] cosbuffer = new double[80][4];
    private SampleValues sv;
    private RMSValues rms;
    private double[] Fx =new double[4]; //синус.составляющая
    private double[] Fy =new double[4];//косинус.составляющая
    private double[] F =new double[4];//rms
    private double Kf=2/(80*Math.sqrt(2));
    private double[] angle=new double[4];//угол
    private double[] rmsmassivX =new double[4];
    private double[] rmsmassivY =new double[4];
    private double[] rmsmassiv =new double[4];
    private double[] rmsanglemasiv =new double[4];
    private  double[][]sin=new double[80][4];
    private double[][]cos=new double[80][4];

    public void calculate (double ph,int i,int count) {
        //расчёт тока rms
        //расчёт sin и cos для 80-ти выборок
        sin[count][i] =Math.sin(count*2*Math.PI/80);
        cos[count][i] =Math.cos(count*2*Math.PI/80);
        //расчёт ортогональных составляющих
        Fx[i]+=sin[count][i]* ph - sinbuffer[count][i];
        Fy[i]+=cos[count][i]* ph - cosbuffer[count][i];
        //запись в буфер(окно осциллографа по времени), так как на осциллографе орг.число точек
        sinbuffer[count][i]= ph*sin[count][i];
        cosbuffer[count][i]= (ph)*cos[count][i];
        rmsmassivX[i]=Fx[i]*Kf;
        rmsmassivY[i]=Fy[i]*Kf;

        rms.setMassivX(rmsmassivX);
        rms.setMassivY(rmsmassivY);
        F[i]=Kf*Math.sqrt(Math.pow(Fx[i],2)+Math.pow(Fy[i],2));
        rmsmassiv[i]=F[i];
        rms.setMassiv(rmsmassiv);
        //расчёт угла
        angle[i]=Math.toDegrees(Math.atan(Fy[i]/Fx[i]));
        if (Fx[i]<0 && Fy[i]>0) angle[i] = 180 + angle[i];
        else if (Fy[i]<0 && Fx[i]<0) angle[i] = -180 + angle[i];
    }
}