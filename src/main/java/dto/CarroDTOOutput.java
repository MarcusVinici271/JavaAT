package dto;

import lombok.*;

@Data
@Getter
@Setter
public class CarroDTOOutput {
    private int id;
    private String modelo;
    private String placa;
}
