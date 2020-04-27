import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        Charts.createAnalogChart("Фаза А", 0);//создаем график
        Charts.addSeries("Фаза A (Hrms)", 0 , 0);//создаем сигнал
        Charts.addSeries("Фаза A diff1", 0 , 1);
        Charts.addSeries("Фаза А diff2", 0 , 2);
        Charts.addSeries("Фаза А brake", 0 , 3);
        Charts.addSeries("Фаза А Hsv", 0 , 4);
        Charts.addSeries("Фаза А Lsv", 0 , 5);


        Charts.createAnalogChart("Фаза Б", 1);
        Charts.addSeries("Фаза Б (Hrms)", 1 , 0);
        Charts.addSeries("Фаза Б diff1", 1 , 1);
        Charts.addSeries("Фаза Б diff2", 1 , 2);
        Charts.addSeries("Фаза Б brake", 1 , 3);
        Charts.addSeries("Фаза Б Hsv", 1 , 4);
        Charts.addSeries("Фаза Б Lsv", 1 , 5);

        Charts.createAnalogChart("Фаза C", 2);
        Charts.addSeries("Фаза С (Hrms)", 2 , 0);
        Charts.addSeries("Фаза С diff1", 2 , 1);
        Charts.addSeries("Фаза С diff2", 2 , 2);
        Charts.addSeries("Фаза С brake", 2 , 3);
        Charts.addSeries("Фаза С Hsv", 2 , 4);
        Charts.addSeries("Фаза С Lsv", 2 , 5);
        Charts.createDiscreteChart("Trip", 0);
        InputData inD = new InputData();
        inD.start();
    }
}
