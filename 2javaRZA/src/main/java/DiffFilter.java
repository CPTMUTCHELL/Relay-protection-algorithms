import java.util.Arrays;

public class DiffFilter {
    public DiffFilter(DiffValues diffValues) {
        this.diffValues=diffValues;
    }
    private DiffValues diffValues;
    private double[] diffX=new double[3];//X вектор
    private double[] diffY=new double[3];//Y вектор
    private double[] diffSum=new double[3];//Векторная сумма
    private double maxBrake =0;//Торм.ток
    private double[] Hbrake =new double[3];//Токи rms с выс.стороны
    private double[] Lbrake =new double[3];//Токи rms с низ.стороны
    private double[] diffMassiv =new double[3];//массив диф.токов пофазно

    void calcDiffSum(double Hph, double Lph, int i, double Hangle, double Langle){
        Hbrake[i]=Hph;
        Lbrake[i]=Lph;
        //Расчёт диф.тока
        diffX[i]+=Hph*Math.cos(Math.toRadians(Hangle))+Lph*Math.cos(Math.toRadians(Langle));
        diffY[i]+=Hph*Math.sin(Math.toRadians(Hangle))+Lph*Math.sin(Math.toRadians(Langle));
        diffSum[i]=Math.sqrt(Math.pow(diffX[i],2)+Math.pow(diffY[i],2));
        diffMassiv[i]=diffSum[i];
        diffValues.setDiffsum(diffMassiv);
        diffX[i]=0;
        diffY[i]=0;

        maxBrake = Math.abs(maxBrake(Hbrake,Lbrake));
        diffValues.setDiffbrake(maxBrake);

    }
    //Функция выбора макс.тока из всех фаз и всех сторон
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
