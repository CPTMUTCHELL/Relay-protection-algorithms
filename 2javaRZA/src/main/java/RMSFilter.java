import java.util.Arrays;

public class RMSFilter {
    public RMSFilter(SampleValues sv, RMSValues rms) {
        this.sv = sv;
        this.rms = rms;

    }
    private double[][] sinbuffer = new double[20][3];
    private double[][] cosbuffer = new double[20][3];
    int count=0;
    int count1=0;
    int count2=0;
    double Kf=2/(20*Math.sqrt(2));
    private SampleValues sv;
    private RMSValues rms;
    double[] Fx =new double[3];
    double[] Fy =new double[3];
    double[] F =new double[3];
    double[] angle=new double[3];
    private double[] rmsmassiv1 =new double[3];
    private double[] rmsmassiv2 =new double[3];
    private double[] rmsanglemasiv1 =new double[3];
    private double[] rmsanglemasiv2 =new double[3];
    double []sin=new double[20];
    double []cos=new double[20];

    public void calculate (double ph, int i, int k) {
        if (k==1) {
            count = count1;
            count1++;
        }
        else if (k==2){
            count=count2;
            count2++;
        }
        sin[count] =Math.sin(count*k*2*Math.PI/20);
        cos[count] =Math.cos(count*k*2*Math.PI/20);
        Fx[i]+=sin[count]* Math.abs(ph) - sinbuffer[count][i];
        Fy[i]+=cos[count]* Math.abs(ph) - cosbuffer[count][i];
        sinbuffer[count][i]= Math.abs(ph)*sin[count];
        cosbuffer[count][i]= Math.abs(ph)*cos[count];
        F[i]=Kf*Math.sqrt(Math.pow(Fx[i],2)+Math.pow(Fy[i],2));
        if (k==1) {
            rmsmassiv1[i] = F[i];
            rms.setMassiv1(rmsmassiv1);
            angle[i] = Math.toDegrees(Math.atan(Fy[i] / Fx[i]));
            if (Fx[i]<0 && Fy[i]>0) angle[i] = 180 + angle[i];
            else if (Fy[i]<0 && Fx[i]<0) angle[i] = -180 + angle[i];
            rmsanglemasiv1[i] = angle[i];
            rms.setAnglemassiv1(rmsanglemasiv1);
            System.out.println("1 при к=1 "+ Arrays.toString(rmsmassiv1));
            System.out.println("2 при к=1 "+ Arrays.toString(rmsmassiv2));
        }
        else if (k==2){
            rmsmassiv2[i]=F[i];
            rms.setMassiv2(rmsmassiv2);
            angle[i] = Math.toDegrees(Math.atan(Fy[i] / Fx[i]));
            if (Fx[i]<0 && Fy[i]>0) angle[i] = 180 + angle[i];
            else if (Fy[i]<0 && Fx[i]<0) angle[i] = -180 + angle[i];
            rmsanglemasiv2[i] = angle[i];
            rms.setAnglemassiv2(rmsanglemasiv2);
            System.out.println("1 при к=2 "+ Arrays.toString(rmsmassiv1));
            System.out.println("2 при к=2 "+ Arrays.toString(rmsmassiv2));

        }
        if(count1 >= 20 &&count2>=20) {
            count1 = 0;
            count2=0;
        }
        rms.setTime(sv.getTime());
    }
}