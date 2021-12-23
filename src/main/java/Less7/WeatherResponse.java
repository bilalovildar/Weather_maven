package Less7;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

/*
Geek Brains 7 lesson Билалов Ильдар 2021г
1. Реализовать корректный вывод информации о текущей погоде. Создать класс WeatherResponse и десериализовать ответ сервера. Выводить пользователю только текстовое описание погоды и температуру в градусах Цельсия.
2. Реализовать корректный выход из программы
3. Реализовать вывод погоды на следующие 5 дней в формате
| В городе CITY на дату DATE ожидается WEATHER_TEXT, температура - TEMPERATURE |

где CITY, DATE, WEATHER_TEXT и TEMPERATURE - уникальные значения для каждого дня.
*/


public class WeatherResponse {
    final static String API_KEY = "VmXNBDo9Yy3Els9MTNANHYA9GObEjNjN";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    final static OkHttpClient okHttpClient = new OkHttpClient();
    static String cityId;
    static String cityTemp;
    
    public static String sendCityIdRequest(String cityName) throws IOException {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("dataservice.accuweather.com")
                .addPathSegment("locations")
                .addPathSegment("v1")
                .addPathSegment("cities")
                .addPathSegment("search")
                .addQueryParameter("apikey", API_KEY)
                .addQueryParameter("q", cityName)
                .build();
        Request request = new Request.Builder()
                .addHeader("accept", "application/json")
                .url(httpUrl)
                .build();

        Response response = okHttpClient.newCall(request).execute();

        //String responseHeadersJson = response.header("cookie").toString();  // чтобы распарсить ответ в Стринг

        String responseJson = response.body().string();

        JsonNode cityIdNode = objectMapper
                .readTree(responseJson)
                .at("/0/Key");
        cityId = cityIdNode.asText();



        //System.out.println(responseJson);

        return cityName + " имеет " + cityId;
    }




    public static String sendTempRequest(String cityId) throws IOException {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("http")
                .host("dataservice.accuweather.com")
                .addPathSegment("forecasts")
                .addPathSegment("v1")
                .addPathSegment("daily")
                .addPathSegment("5day")
                .addPathSegment(cityId)
                .addQueryParameter("apikey", API_KEY)
                .build();
        Request request = new Request.Builder()
                .addHeader("accept", "application/json")
                .url(httpUrl)
                .build();

        Response response = okHttpClient.newCall(request).execute();

        //String responseHeadersJson = response.header("cookie").toString();  // чтобы распарсить ответ в Стринг

        String responseJson = response.body().string();

        JsonNode cityIdTemp = objectMapper
                .readTree(responseJson)
                .at("/DailyForecasts/0/Temperature/Minimum/Value");
        cityTemp = cityIdTemp.asText();

        //System.out.println(responseJson);

        return "Temperature " + cityTemp;
    }
}
