import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarroApiTest {

    private static final String BASE_URL = "http://localhost:4567/caros";

    @Test
    public void testListarCarros() throws IOException {

        URL url = new URL(BASE_URL);


        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");


        int responseCode = connection.getResponseCode();


        assertEquals(200, responseCode, "O código de resposta deve ser 200");


        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            System.out.println("Resposta: " + response.toString());
        }


        connection.disconnect();
    }
}
