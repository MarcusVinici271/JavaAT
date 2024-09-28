package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.CarroDTOInput;
import dto.CarroDTOOutput;
import service.CarroService;
import spark.Spark;

import java.util.List;

public class CarroController {

    private CarroService carroService;
    private ObjectMapper objectMapper;


    public CarroController() {
        this.carroService = new CarroService();
        this.objectMapper = new ObjectMapper();
    }


    public void respostasRequisicoes() {

        Spark.get("/carros", (req, res) -> {
            res.type("application/json");
            List<CarroDTOOutput> listaCarros = carroService.listar();
            return objectMapper.writeValueAsString(listaCarros);
        });


        Spark.post("/carros", (req, res) -> {
            res.type("application/json");

            CarroDTOInput carroDTOInput = objectMapper.readValue(req.body(), CarroDTOInput.class);
            carroService.inserir(carroDTOInput);
            res.status(201);
            return objectMapper.writeValueAsString(carroDTOInput);
        });


        Spark.put("/carros", (req, res) -> {
            res.type("application/json");

            CarroDTOInput carroDTOInput = objectMapper.readValue(req.body(), CarroDTOInput.class);
            carroService.alterar(carroDTOInput);
            res.status(204);
            return "";
        });
    }
}
