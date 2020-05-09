import java.util.Arrays;

public class ImpedanceFilter {
    public ImpedanceFilter(RMSValues Irms, RMSValues Urms, ImpedanceValues iv) {
        this.Irms = Irms;
        this.Urms = Urms;
        this.iv = iv;
    }
    private double k0x=3.126; //коэф.компенсации тока 0-послед
    private double k0y=2.5;
    private RMSValues Irms;
    private RMSValues Urms;
    private ImpedanceValues iv;
    private double []X=new double[3];
    private double []Y=new double[3];
    private double [] Zx=new double[3];
    private double [] Zy=new double[3];
    private double [] Z=new double[3];
    private double [] Xmassiv=new double[3];
    private double [] Ymassiv=new double[3];
    private double [] Zmassiv=new double[3];
// Функция расчёта полного активного и реактивного сопр.
    public void calcImp(double iX, double iY,double uX,double uY,int i ){
        X[i]=iX+k0x*Irms.getPh0X()-k0y*Irms.getPh0Y();
        Y[i]=iY+k0x*Irms.getPh0Y()+k0y*Irms.getPh0X();
        Zx[i]=(uX*X[i]+uY*Y[i])/(Math.pow(X[i],2)+Math.pow(Y[i],2));
        Xmassiv[i]=Zx[i];
        Zy[i]=(uY*X[i]-uX*Y[i])/(Math.pow(X[i],2)+Math.pow(Y[i],2));
        Ymassiv[i]=Zy[i];
        Z[i]=Math.sqrt(Math.pow(Zx[i],2)+Math.pow(Zy[i],2));
        Zmassiv[i]=Z[i];
        iv.setImpmassivX(Xmassiv);
        iv.setImpmassivY(Ymassiv);
        iv.setImpmassivZ(Zmassiv);
    }
}
