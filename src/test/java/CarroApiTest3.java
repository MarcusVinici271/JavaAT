import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarroApiTest3 {

    private static final String BASE_URL = "http://localhost:4567/caros";
    private static final String PUBLIC_API_URL = "https://freetestapi.com/api/v1/cars";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public static void setUp() {

    }

    @Test
    public void testInserirCarro() throws Exception {

        Map<String, Object> carroData = getCarroData();


        String chassi = generateRandomChassi();


        Map<String, Object> carroJson = Map.of(
                "modelo", carroData.get("model"),
                "placa", carroData.get("plate"),
                "chassi", chassi
        );


        URL url = new URL(BASE_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");


        try (OutputStream os = connection.getOutputStream()) {
            os.write(objectMapper.writeValueAsBytes(carroJson));
            os.flush();
        }


        int responseCode = connection.getResponseCode();


        assertEquals(201, responseCode, "O código de resposta deve ser 201");


        connection.disconnect();
    }

    private Map<String, Object> getCarroData() throws IOException {
        URL url = new URL(PUBLIC_API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");


        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("Falha ao obter dados da API pública: " + responseCode);
        }


        try (InputStream inputStream = connection.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }


            Map<String, Object> jsonResponse = objectMapper.readValue(response.toString(), Map.class);

            return ((List<Map<String, Object>>) jsonResponse.get("data")).get(0);
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
