import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        Charts.createAnalogChart("Фаза А", 0);//создаем график
        Charts.addSeries("Фаза A I0", 0 , 0);//создаем сигнал
        Charts.addSeries("Фаза A rms", 0 , 1);
        Charts.addSeries("Фаза А S0", 0 , 2);
        Charts.addSeries("Фаза А U0", 0 , 3);

        Charts.createAnalogChart("Фаза Б", 1);
        Charts.addSeries("Фаза Б I0", 1 , 0);
        Charts.addSeries("Фаза Б rms", 1 , 1);
        Charts.addSeries("Фаза Б S0", 1 , 2);
        Charts.addSeries("Фаза Б U0", 1 , 3);


        Charts.createAnalogChart("Фаза C", 2);
        Charts.addSeries("Фаза С I0", 2 , 0);
        Charts.addSeries("Фаза С rms", 2 , 1);
        Charts.addSeries("Фаза С S0", 2 , 2);
        Charts.addSeries("Фаза С U0", 2 , 3);


        Charts.createDiscreteChart("Trip 1", 0);
        Charts.createDiscreteChart("Trip 2", 1);
        Charts.createDiscreteChart("Trip 3", 2);
        Charts.createDiscreteChart("Trip 4", 3);
        Charts.createDiscreteChart("Trip 5", 4);

        InputData inD = new InputData();
        inD.start();
    }
}
