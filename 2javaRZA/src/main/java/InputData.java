import java.io.*;

public class InputData {
    private File comtrCfg, comtrDat;
    private BufferedReader br;
    private String line;
    private String[] lineData;
    private double k1[], k2[], time;
    private int counter=0;
    private String comtrName = "Vkl";
    private String path = "C:\\Users\\1\\Downloads\\ОпытыComtrade\\DPR\\";
    private String cfgName = path+comtrName+".cfg";
    private String datName = path+comtrName+".dat";

    public InputData() {
        comtrCfg = new File(cfgName);
        comtrDat = new File(datName);
    }

    private SampleValues sv = new SampleValues();
    private RMSValues Hrms = new RMSValues();
    private RMSValues Lrms = new RMSValues();
    private RMSFilter HRMSFilter = new RMSFilter(sv, Hrms);
    private RMSFilter LRMSFilter = new RMSFilter(sv, Lrms);
    private DiffValues dv1 =new DiffValues();
    private DiffValues dv2 =new DiffValues();
    private DiffFilter diff1 = new DiffFilter(Hrms,Lrms, dv1);
    private DiffFilter diff2 = new DiffFilter(Hrms,Lrms, dv2);

    private Logic logic = new Logic(dv1,dv2,Hrms);

    public void start() throws IOException {


        br = new BufferedReader(new FileReader(comtrCfg)); // поток, который подключается к текстовому файлу+его чтение

        int lineNumber = 0;
        int count = 0;

        int numberData = 100;

        while((line = br.readLine()) != null) {//читаем строчки
            lineNumber++;
            //System.out.println("LINE ~~~ "+line);
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
            if(!(count > 330 && count < 2000)) continue;//чтоб не смотреть весь график
            lineData = line.split(",");
//            System.out.println("LINE DATA1 "+Arrays.toString(lineData));
//            System.out.println("K1 "+Arrays.toString(k1));
//            System.out.println("K2 "+Arrays.toString(k2));
            sv.setTime(Double.parseDouble(lineData[1]));//750000
            sv.setHphA(Double.parseDouble(lineData[2])*k1[0]+k2[0]);//35638*1.19011E-4+-4.01058
            sv.setHphB(Double.parseDouble(lineData[2])*k1[1]+k2[1]);
            sv.setHphC(Double.parseDouble(lineData[2])*k1[2]+k2[2]);
            sv.setLphA(Double.parseDouble(lineData[2])*k1[3]+k2[3]);
            sv.setLphB(Double.parseDouble(lineData[2])*k1[4]+k2[4]);
            sv.setLphC(Double.parseDouble(lineData[2])*k1[5]+k2[5]);
            for (int i = 0; i <3 ; i++) {
                for (int k = 1; k <=2 ; k++) {
                    switch (i){
                        case 0:
                            System.out.println(k);
                            HRMSFilter.calculate(sv.getHphA(),i,k);
                            LRMSFilter.calculate(sv.getLphA(),i,k);
                            if (k==1) diff1.calcDiffSum(Hrms.getPhA1(),Lrms.getPhA1(),i, Hrms.getAngleA1(),Lrms.getAngleA1());
                            else      diff2.calcDiffSum(Hrms.getPhA2(),Lrms.getPhA2(),i, Hrms.getAngleA2(),Lrms.getAngleA2());
                            break;
                        case 1:
                            HRMSFilter.calculate(sv.getHphB(),i,k);
                            LRMSFilter.calculate(sv.getLphB(),i,k);
                            if (k==1) diff1.calcDiffSum(Hrms.getPhB1(),Lrms.getPhB1(),i, Hrms.getAngleB1(),Lrms.getAngleB1());
                            else      diff2.calcDiffSum(Hrms.getPhB2(),Lrms.getPhB2(),i, Hrms.getAngleB2(),Lrms.getAngleB2());
                            break;
                        case 2:
                            HRMSFilter.calculate(sv.getHphC(),i,k);
                            LRMSFilter.calculate(sv.getLphC(),i,k);
                            if(k==1) diff1.calcDiffSum(Hrms.getPhC1(),Lrms.getPhC1(),i, Hrms.getAngleC1(),Lrms.getAngleC1());
                            else     diff2.calcDiffSum(Hrms.getPhC2(),Lrms.getPhC2(),i, Hrms.getAngleC2(),Lrms.getAngleC2());
                            break;
                    }

                }
            }

            logic.process();

            Charts.addAnalogData(0, 0, dv1.getDiffPhA()); //красный мгновенное
            Charts.addAnalogData(0, 1, Hrms.getPhA1()+Lrms.getPhA1()); //синий действ
            Charts.addAnalogData(0,2,  dv2.getDiffPhA()/dv1.getDiffPhA());

            Charts.addAnalogData(1, 0, dv1.getDiffPhB());
            Charts.addAnalogData(1, 1, Hrms.getPhB1()+Lrms.getPhB1());
           // Charts.addAnalogData(1,2,  dv2.getDiffPhB()/dv1.getDiffPhB());

            Charts.addAnalogData(2, 0, dv1.getDiffPhC());
            Charts.addAnalogData(2, 1, Hrms.getPhC1()+Lrms.getPhC1());

           // Charts.addAnalogData(2,2,  dv2.getDiffPhC()/dv1.getDiffPhC());

        }

    }

}
