import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class InputData {
    private File comtrCfg, comtrDat;
    private BufferedReader br;
    private String line;
    private String[] lineData;
    private double k1[], k2[];
    private int rmscounter=0; //Счётчик для обработки 80-ти выборок в ФФ
    private String comtrName = "KZ1";
    private String path = "C:\\Users\\1\\Downloads\\Опыты\\";
    private String cfgName = path+comtrName+".cfg";
    private String datName = path+comtrName+".dat";
    public InputData() {
        comtrCfg = new File(cfgName);
        comtrDat = new File(datName);
    }
    private SampleValues sv = new SampleValues();
    private RMSValues Irms = new RMSValues(); //экз.класса rmsvalues на высокой стороне 1й гарм
    private RMSValues Urms = new RMSValues();//экз.класса rmsvalues на низкой стороне 1й гарм
    private RMSFilter IRMSFilter = new RMSFilter(sv, Irms);// экз.класса ФФ на выс. стороне 1й гарм
    private RMSFilter URMSFilter = new RMSFilter(sv, Urms);// экз.класса ФФ на низ. стороне 1й гарм
    private ImpedanceValues iv = new ImpedanceValues();//класс хранения полных сопр
    private ImpedanceFilter impfilter = new ImpedanceFilter(Irms,Urms,iv); //класс расчёта полных сопр.
    private Logic logic = new Logic(sv,Irms,Urms, iv); //Класс логики
    private Godograph godograph = new Godograph( logic,iv); //класс построения годографа

    public void start() throws IOException {
        godograph.drawsetting();//рисуем характеристику срабатывания
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
            if(!(count > 1500 && count < 6000)) continue;//чтоб не смотреть весь график
            lineData = line.split(",");
//            System.out.println("LINE DATA1 "+Arrays.toString(lineData));
//            System.out.println("K1 "+Arrays.toString(k1));
//            System.out.println("K2 "+Arrays.toString(k2));
            //получаем мгн.значения
            sv.setTime(Double.parseDouble(lineData[1]));
            sv.setUa(Double.parseDouble(lineData[2])*k1[0]+k2[0]);//35638*1.19011E-4+-4.01058
            sv.setUb(Double.parseDouble(lineData[3])*k1[1]+k2[1]);
            sv.setUc(Double.parseDouble(lineData[4])*k1[2]+k2[2]);
            sv.setPhA(Double.parseDouble(lineData[5])*k1[3]+k2[3]);
            sv.setPhB(Double.parseDouble(lineData[6])*k1[4]+k2[4]);
            sv.setPhC(Double.parseDouble(lineData[7])*k1[5]+k2[5]);
            sv.setPh0(sv.getPhA()+sv.getPhB()+sv.getPhC());
            sv.setU0(sv.getUa()+sv.getUb()+sv.getUc());

            for (int i = 0; i <4 ; i++) { //i=phase(A,B,C,0)
                    switch (i){
                        case 0:
                            URMSFilter.calculate(sv.getUa(),i,rmscounter);
                            IRMSFilter.calculate(sv.getPhA(),i,rmscounter);
                            break;
                        case 1:
                            URMSFilter.calculate(sv.getUb(),i,rmscounter);
                            IRMSFilter.calculate(sv.getPhB(),i,rmscounter);
                            break;
                        case 2:
                            URMSFilter.calculate(sv.getUc(),i,rmscounter);
                            IRMSFilter.calculate(sv.getPhC(),i,rmscounter);
                            break;
                        case 3:
                            URMSFilter.calculate(sv.getU0(),i,rmscounter);
                            IRMSFilter.calculate(sv.getPh0(),i,rmscounter);
                            if(++rmscounter >= 80) rmscounter = 0;
                            for (int j = 0; j <3 ; j++) {
                                switch (j){
                                    case 0:
                                        impfilter.calcImp(Irms.getPhAX(),Irms.getPhAY(),Urms.getPhAX(),Urms.getPhAY(),j);
                                        break;
                                    case 1:
                                        impfilter.calcImp(Irms.getPhBX(),Irms.getPhBY(),Urms.getPhBX(),Urms.getPhBY(),j);
                                        break;
                                    case 2:
                                        impfilter.calcImp(Irms.getPhCX(),Irms.getPhCY(),Urms.getPhCX(),Urms.getPhCY(),j);
                                        break;
                                }
                            }
                            break;
                    }
            }
            logic.process();

            godograph.showGodograph();
            Charts.addAnalogData(0, 0, sv.getPhA());
            Charts.addAnalogData(0, 1, iv.getZA());

            Charts.addAnalogData(1, 0, sv.getPhB());
            Charts.addAnalogData(1, 1, iv.getZB());

            Charts.addAnalogData(2, 0, sv.getPhC());
            Charts.addAnalogData(2, 1, iv.getZC());

            ChartsXY.addAnalogData(0, 0, iv.getZAX(), iv.getZAY());
            ChartsXY.addAnalogData(0, 1, iv.getZBX(), iv.getZBY());
            ChartsXY.addAnalogData(0, 2, iv.getZCX(), iv.getZCY());

        }
    }
}
