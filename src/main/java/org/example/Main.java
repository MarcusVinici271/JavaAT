package org.example;

import controller.CarroController;
import spark.Spark;

public class Main {
    public static void main(String[] args) {
        CarroController carroController = new CarroController();
        carroController.respostasRequisicoes();

        Spark.port(4567);
        Spark.init();
    }
}