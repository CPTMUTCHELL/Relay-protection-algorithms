import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        Charts.createAnalogChart("Фаза А", 0);//создаем график
        Charts.addSeries("Фаза A (sv)", 0 , 0);//создаем сигнал
        Charts.addSeries("Фаза A Imp", 0 , 1);
        Charts.addSeries("Фаза А rms", 0 , 2);

        Charts.createAnalogChart("Фаза Б", 1);
        Charts.addSeries("Фаза Б (sv)", 1 , 0);
        Charts.addSeries("Фаза Б Imp", 1 , 1);
        Charts.addSeries("Фаза Б rms", 1 , 2);

        Charts.createAnalogChart("Фаза C", 2);
        Charts.addSeries("Фаза С (sv)", 2 , 0);
        Charts.addSeries("Фаза С Imp", 2 , 1);
        Charts.addSeries("Фаза С rms", 2 , 2);

        Charts.createDiscreteChart("Trip 1", 0);
        Charts.createDiscreteChart("Trip 2", 1);
        Charts.createDiscreteChart("Trip 3", 2);

        InputData inD = new InputData();
        inD.start();
    }
}
