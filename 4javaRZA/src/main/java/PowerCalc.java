public class PowerCalc {
    private double q0,s0,p0;
    private SymmValues Iscv, Uscv;

    public double getS0() {
        return s0;
    }

    public PowerCalc(SymmValues iscv, SymmValues uscv) {
        Iscv = iscv;
        Uscv = uscv;
    }

    void powerCalc(){
        //расчёт мощности нул.послед
        p0=Math.sqrt(3)*Uscv.getF0()*Iscv.getF0()*Math.cos(Math.toRadians(Uscv.getAngle0()-Iscv.getAngle0()));
        q0=Math.sqrt(3)*Uscv.getF0()*Iscv.getF0()*Math.sin(Math.toRadians(Uscv.getAngle0()-Iscv.getAngle0()));
        s0=Math.sqrt(Math.pow(p0,2)+Math.pow(q0,2));

    }
}
