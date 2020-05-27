public class DPRelay {
    //Класс характеристики блокировки по направлению мощности
    private SymmValues Iscv;
    private SymmValues Uscv;
    private boolean isBlocked=true;
    private double maxSenseAng =360+(-20); //угол макс чувствит, отложенный от U0

    public DPRelay(SymmValues iscv, SymmValues uscv) {
        Iscv = iscv;
        Uscv = uscv;
    }
    boolean block(){
        double angle=Uscv.getAngle0()-Iscv.getAngle0();//при кз U0=-90град, I0=0град
        if (angle<0) angle=360+angle; //чтоб не было отриц.углов и был правильный расчёт
        //проверка на попадание в область срабатывания
        if (maxSenseAng +90>angle&&angle> maxSenseAng -90) isBlocked=false;
        else isBlocked=true;
//        System.out.println("Blocked: "+isBlocked);

        return isBlocked;
    }
}
