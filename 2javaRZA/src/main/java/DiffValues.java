public class DiffValues {
    private double[] diffsum;
    private double diffbrake=0;
    private double high=1;

    public double getDiffPhB() {
        return diffsum[1]/high;
    }
    public double getDiffPhA() {
        return diffsum[0]/high;
    }
    public double getDiffPhC() {
        return diffsum[2]/high;
    }

    public void setDiffsum(double[] diffsum) {
        this.diffsum = diffsum;
    }

    public double getDiffbrake() {
        return diffbrake/high;
    }

    public void setDiffbrake(double diffbrake) {
        this.diffbrake = diffbrake;
    }
}
