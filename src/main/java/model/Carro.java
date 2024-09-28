package model;

import lombok.*;

@Data
@Getter
@Setter
public class Carro {
    private int id;
    private String modelo;
    private String placa;
    private String chassi;
}