public class RMSValues {
    private double[] massiv;
    private double[] anglemassiv;
    private double time = 0;

    public double getAngleA() {
        return anglemassiv[0];
    }

    public double getAngleB() {
        return anglemassiv[1];
    }

    public double getAngleC() {
        return anglemassiv[2];
    }


    public void setAnglemassiv(double[] anglemassiv) {
        this.anglemassiv = anglemassiv;
    }

    public void setMassiv(double[] massiv) {
        this.massiv = massiv;
    }

    public double getPhA() {
        return massiv[0];
    }


    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }


    public double getPhB() {
        return massiv[1];
    }

    public double getPhC() {
        return massiv[2];
    }

}
