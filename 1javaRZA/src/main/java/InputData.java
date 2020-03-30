import java.io.*;
import java.util.Arrays;
import java.util.List;

public class InputData {
    private File comtrCfg, comtrDat;
    private BufferedReader br;
    private String line,A,B,C;
    private String[] lineData;
    private double k1[], k2[], time;

    private String comtrName = "PhC20";
    private String path = "C:\\Users\\1\\Downloads\\Опыты(1)\\Конец линии\\";
    private String cfgName = path+comtrName+".cfg";
    private String datName = path+comtrName+".dat";

    public InputData() {
        comtrCfg = new File(cfgName);
        comtrDat = new File(datName);
    }

    private SimpleValues sv = new SimpleValues();
    private RMSValues rms = new RMSValues();
    private MiddleValues filter = new MiddleValues(sv,rms);

    private Logic logic = new Logic();

    public void start() throws IOException, InterruptedException {

        logic.setRms(rms);
        br = new BufferedReader(new FileReader(comtrCfg)); // поток, который подключается к текстовому файлу+его чтение

        int lineNumber = 0;
        int count = 0;

        int numberData = 100;

        while((line = br.readLine()) != null) {//читаем строчки
            lineNumber++;
            System.out.println("LINE ~~~ "+line);
            if(lineNumber==2) {
                numberData = Integer.parseInt(line.split(",")[1].replaceAll("A", "")); //берем 4,3A,1D

                System.out.println("Number Signals: " + numberData);

                k1 = new double [numberData];
                k2 = new double[numberData];
                System.out.println("Number Data: " + numberData);

            }

            if(lineNumber > 2 && lineNumber < numberData+3) {
                lineData = line.split(",");//перевод строки в массив
                k1[count] = Double.parseDouble(line.split(",")[5]); //записали Ia Ib Ic
                k2[count] = Double.parseDouble(line.split(",")[6]); //записали Ia Ib Ic
                count++;

//                System.out.println("LINE DATA "+Arrays.toString(lineData));
//                            System.out.println("K1 "+Arrays.toString(k1));
//            System.out.println("K2 "+Arrays.toString(k2));

            }
        }
        count = 0;
        br = new BufferedReader(new FileReader(comtrDat));
        while((line = br.readLine()) != null) {
            count++;
            if(!(count > 800 && count < 1900)) continue;//чтоб не смотреть весь график
            lineData = line.split(",");
//            System.out.println("LINE DATA1 "+Arrays.toString(lineData));
//            System.out.println("K1 "+Arrays.toString(k1));
//            System.out.println("K2 "+Arrays.toString(k2));
            sv.setTime(Double.parseDouble(lineData[1]));//750000
            sv.setPhA(Double.parseDouble(lineData[2])*k1[0]+k2[0]);//35638*1.19011E-4+-4.01058
            sv.setPhB(Double.parseDouble(lineData[2])*k1[1]+k2[1]);
            sv.setPhC(Double.parseDouble(lineData[2])*k1[2]+k2[2]);

            for (int i = 0; i <3 ; i++) {
                switch (i){
                    case 0:filter.calculate(sv.getPhA(),i);
                    break;
                    case 1:filter.calculate(sv.getPhB(),i);
                    break;
                    case 2:filter.calculate(sv.getPhC(),i);
                    break;
                }
            }

            logic.process();

            Charts.addAnalogData(0, 0, sv.getPhA()); //красный мгновенное
            Charts.addAnalogData(0, 1, rms.getPhA()); //синий действ

            Charts.addAnalogData(1, 0, sv.getPhB());
            Charts.addAnalogData(1, 1, rms.getPhB());

            Charts.addAnalogData(2, 0, sv.getPhC());
            Charts.addAnalogData(2, 1, rms.getPhC());

        }

    }

}
