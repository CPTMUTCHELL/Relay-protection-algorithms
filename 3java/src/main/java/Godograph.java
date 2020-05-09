public class Godograph {
    private ImpedanceValues iv;
    private Logic logic;
    private double[] settingX1 = new double[361];
    private double[] settingY1= new double[361];
    private double[] settingX2 = new double[361];
    private double[] settingY2= new double[361];
    private double[] settingX3 = new double[361];
    private double[] settingY3= new double[361];
    public Godograph(Logic logic,ImpedanceValues iv) {
        this.iv = iv;
        this.logic=logic;
    }
    public void drawsetting() {

        ChartsXY.createAnalogChart("Godograph", 0);
        ChartsXY.addSeries("Фаза А", 0, 0);
        ChartsXY.addSeries("Фаза B", 0, 1);
        ChartsXY.addSeries("Фаза С", 0, 2);
        ChartsXY.addSeries("setting1", 0, 3);
        ChartsXY.addSeries("setting2", 0, 4);
        ChartsXY.addSeries("setting3", 0, 5);
        for (int i = 0; i <= 360; i ++) { //Движение по окружности
            settingX1[i]=logic.getXcenter1()+(logic.getZ1()/2)*Math.cos(i);
            settingY1[i]=logic.getYcenter1()+(logic.getZ1()/2)*Math.sin(i);
            settingX2[i]=logic.getXcenter2()+(logic.getZ2()/2)*Math.cos(i);
            settingY2[i]=logic.getYcenter2()+(logic.getZ2()/2)*Math.sin(i);
            settingX3[i]=logic.getXcenter3()+(logic.getZ3()/2)*Math.cos(i);
            settingY3[i]=logic.getYcenter3()+(logic.getZ3()/2)*Math.sin(i);
        }
        for (int i=0;i<settingX1.length;i++){ //вывод характеристик на график
            ChartsXY.addAnalogData(0, 3, settingX1[i], settingY1[i]);
            ChartsXY.addAnalogData(0, 4, settingX2[i], settingY2[i]);
            ChartsXY.addAnalogData(0, 5, settingX3[i], settingY3[i]);
        }
    }
    public void showGodograph() {//вывод годографа сопротивлений
            ChartsXY.addAnalogData(0, 0, iv.getZAX(), iv.getZAY());
            ChartsXY.addAnalogData(0, 1, iv.getZBX(), iv.getZBY());
            ChartsXY.addAnalogData(0, 2, iv.getZCX(), iv.getZCY());
    }
}
