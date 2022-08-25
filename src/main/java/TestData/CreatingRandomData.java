package TestData;

import java.util.Random;

public class CreatingRandomData {
    public static String getRandomKolyaevString() {
        return "KolyaevRandomTestData" + new Random().nextInt(3);
    }
}
