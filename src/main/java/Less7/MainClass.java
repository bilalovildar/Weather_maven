package Less7;

import java.io.IOException;

public class MainClass {
    public static void main(String[] args) throws IOException {
        System.out.println(WeatherResponse.sendCityIdRequest("Moscow"));
        System.out.println(WeatherResponse.sendTempRequest("294021"));
    }

}


