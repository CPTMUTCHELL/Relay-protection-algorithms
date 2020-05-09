public class RMSValues {
    private double[] massivX;
    private double[] massivY;
    private double[] massiv;

    private double time = 0;

    public void setMassiv(double[] massiv) {
        this.massiv = massiv;
    }

    public void setMassivY(double[] massivY) {
        this.massivY = massivY;
    }



    public void setMassivX(double[] massivX) {
        this.massivX = massivX;
    }

    public double getPhAX() {
        return massivX[0];
    }


    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }


    public double getPhBX() {
        return massivX[1];
    }

    public double getPhCX() {
        return massivX[2];
    }
    public double getPh0X() {return massivX[3];}

    public double getPhAY() {
        return massivY[0];
    }
    public double getPhBY() {
        return massivY[1];
    }
    public double getPhCY() {
        return massivY[2];
    }
    public double getPh0Y() {
        return massivY[3];
    }
    public double getPhA() {
        return massiv[0];
    }

    public double getPhB() {
        return massiv[1];
    }

    public double getPhC() {
        return massiv[2];
    }
}
