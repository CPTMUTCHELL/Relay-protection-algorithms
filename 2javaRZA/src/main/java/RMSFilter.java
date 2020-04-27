public class RMSFilter {
    public RMSFilter(SampleValues sv, RMSValues rms, int k) {
        this.sv = sv;
        this.rms = rms;
        this.k=k;

    }
    private double[][] sinbuffer = new double[20][3];
    private double[][] cosbuffer = new double[20][3];
    private double Kf=2/(20*Math.sqrt(2)); //Коэф.Фурье
    private SampleValues sv;
    private RMSValues rms;
    double[] Fx =new double[3]; //синус.составляющая
    double[] Fy =new double[3];//косинус.составляющая
    double[] F =new double[3];//rms
    double[] angle=new double[3];//угол
    private double[] rmsmassiv =new double[3];
    private double[] rmsanglemasiv =new double[3];
    private  double[][]sin=new double[20][3];
    private double[][]cos=new double[20][3];
    int k=0;

    public void calculate (double ph,int i,int count) {
        //расчёт тока rms
        sin[count][i] =Math.sin(k*count*2*Math.PI/20);
        cos[count][i] =Math.cos(k*count*2*Math.PI/20);
        Fx[i]+=sin[count][i]* Math.abs(ph) - sinbuffer[count][i];
        Fy[i]+=cos[count][i]* Math.abs(ph) - cosbuffer[count][i];
        sinbuffer[count][i]= Math.abs(ph)*sin[count][i];
        cosbuffer[count][i]= Math.abs(ph)*cos[count][i];
        F[i]=Kf*Math.sqrt(Math.pow(Fx[i],2)+Math.pow(Fy[i],2));
        rmsmassiv[i]=F[i];
        rms.setMassiv(rmsmassiv);
        //расчёт угла
        angle[i]=Math.toDegrees(Math.atan(Fy[i]/Fx[i]));
        if (Fx[i]<0 && Fy[i]>0) angle[i] = 180 + angle[i];
        else if (Fy[i]<0 && Fx[i]<0) angle[i] = -180 + angle[i];
        rmsanglemasiv[i] = angle[i];
        rms.setAnglemassiv(rmsanglemasiv);
    }
}