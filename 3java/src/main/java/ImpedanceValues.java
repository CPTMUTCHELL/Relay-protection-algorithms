public class ImpedanceValues {
    private double[] impmassivX;
    private double[] impmassivY;
    private double[] impmassivZ;

    public void setImpmassivZ(double[] impmassivZ) {
        this.impmassivZ = impmassivZ;
    }

    public void setImpmassivX(double[] impmassivX) {
        this.impmassivX = impmassivX;
    }

    public void setImpmassivY(double[] impmassivY) {
        this.impmassivY = impmassivY;
    }
    public double getZAY() {
        return impmassivY[0];
    }
    public double getZBY() {
        return impmassivY[1];
    }
    public double getZCY() {
        return impmassivY[2];
    }
    public double getZAX() {
        return impmassivX[0];
    }
    public double getZBX() {
        return impmassivX[1];
    }
    public double getZCX() {
        return impmassivX[2];
    }
    public double getZA() {
        return impmassivZ[0];
    }
    public double getZB() {
        return impmassivZ[1];
    }
    public double getZC() {
        return impmassivZ[2];
    }
}
