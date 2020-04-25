public class RMSValues {
    private double[] massiv1;
    private double[] anglemassiv1;
    private double[] anglemassiv2;
    private double[] massiv2;

    private double time = 0;


    public void setMassiv2(double[] massiv2) {
        this.massiv2 = massiv2;
    }

    public void setAnglemassiv2(double[] anglemassiv2) {
        this.anglemassiv2 = anglemassiv2;
    }

    public double getAngleA1() {
        return anglemassiv1[0];
    }

    public double getAngleB1() {
        return anglemassiv1[1];
    }

    public double getAngleC1() {
        return anglemassiv1[2];
    }
    public double getAngleA2() {
        return anglemassiv2[0];
    }

    public double getAngleB2() {
        return anglemassiv2[1];
    }

    public double getAngleC2() {
        return anglemassiv2[2];
    }

    public void setAnglemassiv1(double[] anglemassiv1) {
        this.anglemassiv1 = anglemassiv1;
    }

    public void setMassiv1(double[] massiv1) {
        this.massiv1 = massiv1;
    }

    public double getPhA1() {
        return massiv1[0];
    }


    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }


    public double getPhB1() {
        return massiv1[1];
    }

    public double getPhC1() {
        return massiv1[2];
    }
    public double getPhB2() {
        return massiv2[1];
    }
    public double getPhA2() {
        return massiv2[0];
    }
    public double getPhC2() {
        return massiv2[2];
    }
}
