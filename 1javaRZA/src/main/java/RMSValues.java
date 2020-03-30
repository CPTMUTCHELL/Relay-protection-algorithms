public class RMSValues {
    private double massiv[];

    private double time = 0;

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

