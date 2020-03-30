import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        Charts.createAnalogChart("Фаза А", 0);//создаем график
        Charts.addSeries("Фаза A (sv)", 0 , 0);//создаем сигнал
        Charts.addSeries("Фаза A (rms)", 0 , 1);
        Charts.addSeries("Уставка", 0 , 2);

        Charts.createAnalogChart("Фаза Б", 1);
        Charts.addSeries("Фаза A (sv)", 1 , 0);
        Charts.addSeries("Фаза A (rms)", 1 , 1);
        Charts.addSeries("Уставка", 1 , 2);

        Charts.createAnalogChart("Фаза C", 2);
        Charts.addSeries("Фаза A (sv)", 2 , 0);
        Charts.addSeries("Фаза A (rms)", 2 , 1);
        Charts.addSeries("Уставка", 2 , 2);

        Charts.createDiscreteChart("Trip", 0);
        InputData inD = new InputData();
        inD.start();
    }
}
