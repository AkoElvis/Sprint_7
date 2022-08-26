package TestData;

import java.util.Random;

public class CreatingRandomData {
    public static String getRandomKolyaevString() {
        return "Kolyaev" + new Random().nextInt(10);
    }
    public static int getRandomInt() { return  new Random().nextInt(100);}
    public static String getRandomDataAsString() { return "2022-" + new Random().nextInt(12) + "-" + new Random().nextInt(31);}
}
