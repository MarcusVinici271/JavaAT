package dto;

import lombok.*;

@Data
@Getter
@Setter
public class CarroDTOInput {
    private int id;
    private String modelo;
    private String placa;
    private String chassi;
}
