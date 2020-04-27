import java.io.*;
import java.util.Arrays;
import java.util.List;

public class InputData {
    private File comtrCfg, comtrDat;
    private BufferedReader br;
    private String line;
    private String[] lineData;
    private double k1[], k2[], time;
    private int rmscounter; //Счётчик для обработки 20-ти выборок в ФФ
    private String comtrName = "VnutB";
    private String path = "C:\\Users\\1\\Downloads\\ОпытыComtrade\\DPR\\";
    private String cfgName = path+comtrName+".cfg";
    private String datName = path+comtrName+".dat";
    public InputData() {
        comtrCfg = new File(cfgName);
        comtrDat = new File(datName);
    }
    private SampleValues sv = new SampleValues();
    private RMSValues Hrms1 = new RMSValues(); //экз.класса rmsvalues на высокой стороне 1й гарм
    private RMSValues Lrms1 = new RMSValues();//экз.класса rmsvalues на низкой стороне 1й гарм
    private RMSValues Hrms2 = new RMSValues(); //экз.класса rmsvalues на высокой стороне 2й гарм
    private RMSValues Lrms2 = new RMSValues();// экз.класса rmsvalues на низкой стороне 2й гарм
    private RMSFilter HRMSFilter1 = new RMSFilter(sv, Hrms1,1);// экз.класса ФФ на выс. стороне 1й гарм
    private RMSFilter LRMSFilter1 = new RMSFilter(sv, Lrms1,1);// экз.класса ФФ на низ. стороне 1й гарм
    private RMSFilter HRMSFilter2 = new RMSFilter(sv, Hrms2,2);// экз.класса ФФ на выс. стороне 2й гарм
    private RMSFilter LRMSFilter2 = new RMSFilter(sv, Lrms2,2);// экз.класса ФФ на низ. стороне 2й гарм
    private DiffValues dv1 =new DiffValues();// экз.класса diffvalues 1й гарм
    private DiffValues dv2 =new DiffValues();// экз.класса diffvalues 2й гарм
    private DiffFilter diff1 = new DiffFilter(dv1);// экз.класса расчёта диф и торм.токов 1й гарм
    private DiffFilter diff2 = new DiffFilter(dv2);// экз.класса расчёта диф токов 2й гарм
    private Logic logic = new Logic(dv1,dv2); //Класс логики диф.защиты

    public void start() throws IOException {

        br = new BufferedReader(new FileReader(comtrCfg)); // поток, который подключается к текстовому файлу+его чтение
        int lineNumber = 0;
        int count = 0;
        int numberData = 100;
        //Начало считывания файла конфигурационного файла cfg
        while((line = br.readLine()) != null) {//читаем строчки
            lineNumber++;
            //System.out.println("LINE ~~~ "+line);
            if(lineNumber==2) {
                numberData = Integer.parseInt(line.split(",")[1].replaceAll("A", "")); //берем 4,3A,1D

                System.out.println("Number Signals: " + numberData);
                //массивы коэффициентов для получения мгн.знач
                k1 = new double [numberData];
                k2 = new double[numberData];

                System.out.println("Number Data: " + numberData);

            }
            if(lineNumber > 2 && lineNumber < numberData+3) {
                lineData = line.split(",");//перевод строки в массив
                //запись коэф. в массивы
                k1[count] = Double.parseDouble(line.split(",")[5]);
                k2[count] = Double.parseDouble(line.split(",")[6]);
                count++;
//                System.out.println("LINE DATA "+ Arrays.toString(lineData));
//                            System.out.println("K1 "+Arrays.toString(k1));
//            System.out.println("K2 "+Arrays.toString(k2));

            }
        }
        count = 0;
        //Читаем dat файл
        br = new BufferedReader(new FileReader(comtrDat));
        while((line = br.readLine()) != null) {
            count++;
            if(!(count > 330 && count < 2000)) continue;//чтоб не смотреть весь график
            lineData = line.split(",");
//            System.out.println("LINE DATA1 "+Arrays.toString(lineData));
//            System.out.println("K1 "+Arrays.toString(k1));
//            System.out.println("K2 "+Arrays.toString(k2));
            //получаем мгн.значения
            sv.setTime(Double.parseDouble(lineData[1]));
            sv.setHphA(Double.parseDouble(lineData[2])*k1[0]+k2[0]);//35638*1.19011E-4+-4.01058
            sv.setHphB(Double.parseDouble(lineData[3])*k1[1]+k2[1]);
            sv.setHphC(Double.parseDouble(lineData[4])*k1[2]+k2[2]);
            sv.setLphA(Double.parseDouble(lineData[5])*k1[3]+k2[3]);
            sv.setLphB(Double.parseDouble(lineData[6])*k1[4]+k2[4]);
            sv.setLphC(Double.parseDouble(lineData[7])*k1[5]+k2[5]);

            List<RMSFilter> Hcalc=Arrays.asList(HRMSFilter1,HRMSFilter2);
            List<RMSFilter> Lcalc=Arrays.asList(LRMSFilter1,LRMSFilter2);
            for (int i = 0; i <3 ; i++) { //i=phase(A,B,C)
                    switch (i){
                        case 0:
                            for (RMSFilter filter:Hcalc)filter.calculate(sv.getHphA(),i, rmscounter);
                            for (RMSFilter filter:Lcalc)filter.calculate(sv.getLphA(),i, rmscounter);
                            diff1.calcDiffSum(Hrms1.getPhA(),Lrms1.getPhA(),i, Hrms1.getAngleA(),Lrms1.getAngleA());
                            diff2.calcDiffSum(Hrms2.getPhA(),Lrms2.getPhA(),i, Hrms2.getAngleA(),Lrms2.getAngleA());
                            break;
                        case 1:
                            for (RMSFilter filter:Hcalc)filter.calculate(sv.getHphB(),i, rmscounter);
                            for (RMSFilter filter:Lcalc)filter.calculate(sv.getLphB(),i, rmscounter);
                            diff1.calcDiffSum(Hrms1.getPhB(),Lrms1.getPhB(),i, Hrms1.getAngleB(),Lrms1.getAngleB());
                            diff2.calcDiffSum(Hrms2.getPhB(),Lrms2.getPhB(),i, Hrms2.getAngleB(),Lrms2.getAngleB());
                            break;
                        case 2:
                            for (RMSFilter filter:Hcalc)filter.calculate(sv.getHphC(),i, rmscounter);
                            for (RMSFilter filter:Lcalc)filter.calculate(sv.getLphC(),i, rmscounter);
                            diff1.calcDiffSum(Hrms1.getPhC(),Lrms1.getPhC(),i, Hrms1.getAngleC(),Lrms1.getAngleC());
                            diff2.calcDiffSum(Hrms2.getPhC(),Lrms2.getPhC(),i, Hrms2.getAngleC(),Lrms2.getAngleC());
                            if(++rmscounter >= 20) rmscounter = 0;
                            break;
                    }
            }
            logic.process();
            //Вывод графиков
            Charts.addAnalogData(0, 0, Hrms1.getPhA());
            Charts.addAnalogData(0, 1, dv1.getDiffPhA());
            Charts.addAnalogData(0, 2, dv2.getDiffPhA());
            Charts.addAnalogData(0, 3, dv1.getDiffbrake());
            //Charts.addAnalogData(0, 4, sv.getHphA());
            //Charts.addAnalogData(0, 5, sv.getLphA());

            Charts.addAnalogData(1, 0, Hrms1.getPhB());
            Charts.addAnalogData(1, 1, dv1.getDiffPhB());
            Charts.addAnalogData(1, 2, dv2.getDiffPhB());
            Charts.addAnalogData(1, 3, dv1.getDiffbrake());
            //Charts.addAnalogData(1, 4, sv.getHphB());
            //Charts.addAnalogData(1, 5, sv.getLphB());

            Charts.addAnalogData(2, 0, Hrms1.getPhC());
            Charts.addAnalogData(2, 1, dv1.getDiffPhC());
            Charts.addAnalogData(2, 2, dv2.getDiffPhC());
            Charts.addAnalogData(2, 3, dv1.getDiffbrake());
            //Charts.addAnalogData(2, 4, sv.getHphC());
            //Charts.addAnalogData(2, 5, sv.getLphC());
        }
    }
}
