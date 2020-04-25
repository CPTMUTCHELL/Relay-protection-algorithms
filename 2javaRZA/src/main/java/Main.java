import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        Charts.createAnalogChart("Фаза А", 0);//создаем график
        Charts.addSeries("Фаза A (dv)", 0 , 0);//создаем сигнал
        Charts.addSeries("Фаза A (rms)", 0 , 1);
        Charts.addSeries("Фаза A (dv2/dv1)", 0 , 2);
        Charts.addSeries("Уставка", 0 , 3);

        Charts.createAnalogChart("Фаза Б", 1);
        Charts.addSeries("Фаза Б (dv)", 1 , 0);
        Charts.addSeries("Фаза Б (rms)", 1 , 1);
        Charts.addSeries("Фаза Б (dv2/dv1)", 0 , 2);
        Charts.addSeries("Уставка", 1 , 3);

        Charts.createAnalogChart("Фаза C", 2);
        Charts.addSeries("Фаза С (dv)", 2 , 0);
        Charts.addSeries("Фаза С (rms)", 2 , 1);
        Charts.addSeries("Фаза С (dv2/dv1)", 0 , 2);
        Charts.addSeries("Уставка", 2 , 3);

        Charts.createDiscreteChart("Trip", 0);
        InputData inD = new InputData();
        inD.start();
    }
}
