import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarroApiTest2 {

    private static final String BASE_URL = "http://localhost:4567/carros";
    private static final String PUBLIC_API_URL = "https://freetestapi.com/api/v1/cars";

    @BeforeAll
    public static void setUp() {

    }

    @Test
    public void testInserirCarro() throws Exception {
        JsonObject carroData = getCarroData();
        String chassi = generateRandomChassi();

        JsonObject carroJson = Json.createObjectBuilder()
                .add("modelo", carroData.getString("model"))
                .add("placa", carroData.getString("plate"))
                .add("chassi", chassi)
                .build();

        URL url = new URL(BASE_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");

        try (OutputStream os = connection.getOutputStream()) {
            os.write(carroJson.toString().getBytes());
            os.flush();
        }

        int responseCode = connection.getResponseCode();
        assertEquals(201, responseCode, "O código de resposta deve ser 201");

        connection.disconnect();
    }

    private JsonObject getCarroData() throws Exception {
        URL url = new URL(PUBLIC_API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("Falha ao obter dados da API pública: " + responseCode);
        }

        try (InputStream inputStream = connection.getInputStream();
             JsonReader jsonReader = Json.createReader(inputStream)) {
            JsonObject jsonResponse = jsonReader.readObject();
            return jsonResponse.getJsonArray("data").getJsonObject(0);
        } finally {
            connection.disconnect();
        }
    }

    private String generateRandomChassi() {
        Random random = new Random();
        StringBuilder chassi = new StringBuilder();
        for (int i = 0; i < 17; i++) {
            int asciiCode = random.nextInt(36);
            char character = (char) (asciiCode < 10 ? '0' + asciiCode : 'A' + asciiCode - 10);
            chassi.append(character);
        }
        return chassi.toString();
    }
}
