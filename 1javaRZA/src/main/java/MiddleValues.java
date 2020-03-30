public class MiddleValues {
    public MiddleValues(SimpleValues sv, RMSValues rms) {
        this.sv = sv;
        this.rms = rms;

    }
    private double buffer[][] = new double[20][3];
    private double buffer1[] = new double[20];
    private double buffer2[] = new double[20];
    private double sum [] = new double[3];
    private double k = 1.11/20;
    private SimpleValues sv;
    private RMSValues rms;
    private int count = 0;
    private double[] rmsmassiv =new double[3];

    public void calculate (double ph, int i) {

        sum[i] += Math.abs(ph) - buffer[count][i]; //вычитаем точку, чтоб добавить новую

        buffer[count][i] = Math.abs(ph); //записываем 20 выборок

        rmsmassiv[i]=(sum[i]*k); // средневыпрямленное
        rms.setMassiv(rmsmassiv);

        if(++count >= 20) count = 0;
        rms.setTime(sv.getTime());
    }
}
