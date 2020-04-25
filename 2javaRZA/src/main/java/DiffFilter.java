import java.util.Arrays;

public class DiffFilter {


    public DiffFilter(RMSValues Hrms, RMSValues Lrms,DiffValues diffValues) {
        this.Hrms = Hrms;
        this.Lrms=Lrms;
        this.diffValues=diffValues;
    }
    private RMSValues Hrms;
    private RMSValues Lrms;
    private DiffValues diffValues;
    double[] diffX=new double[3];
    double[] diffY=new double[3];
    double[] diffSum=new double[3];
    double maxBrake =0;
    double[] Hbrake =new double[3];
    double[] Lbrake =new double[3];
    double[] diffMassiv =new double[3];

    void calcDiffSum(double Hph, double Lph, int i, double Hangle, double Langle){
        Hbrake[i]+=Hph;
        Lbrake[i]+=Lph;
        diffX[i]+=Hph*Math.cos(Math.toRadians(Hangle))+Lph*Math.cos(Math.toRadians(Langle));
        diffY[i]+=Hph*Math.sin(Math.toRadians(Hangle))+Lph*Math.sin(Math.toRadians(Langle));
        diffSum[i]=Math.sqrt(Math.pow(diffX[i],2)+Math.pow(diffY[i],2));

        diffMassiv[i]=Math.abs(diffSum[i]);
        diffValues.setDiffsum(diffMassiv);
        diffX[i]=0;
        diffY[i]=0;

        maxBrake += Math.abs(maxBrake(Hbrake,Lbrake));
        diffValues.setDiffbrake(maxBrake);
        Hbrake[i]=0;
        Lbrake[i]=0;

    }
    double maxBrake(double[]Hbrake,double[]Lbrake){
        double Hmax=Hbrake[0];
        double Lmax=Lbrake[0];
        for (int i = 0; i <Hbrake.length ; i++) {
            if (Hbrake[i]>Hmax) Hmax=Hbrake[i];
            if (Lbrake[i]>Lmax) Lmax=Lbrake[i];
        }
        return Math.max(Hmax, Lmax);
    }
}
