package service;

import dto.CarroDTOInput;
import dto.CarroDTOOutput;
import model.Carro;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CarroService {

    private List<Carro> listaCarros;
    private ModelMapper modelMapper;


    public CarroService() {
        this.listaCarros = new ArrayList<>();
        this.modelMapper = new ModelMapper();
    }


    public List<CarroDTOOutput> listar() {
        return listaCarros.stream()
                .map(carro -> modelMapper.map(carro, CarroDTOOutput.class))
                .collect(Collectors.toList());
    }


    public void inserir(CarroDTOInput carroDTOInput) {

        Carro carro = modelMapper.map(carroDTOInput, Carro.class);
        listaCarros.add(carro);
    }


    public CarroDTOOutput buscar(int id) {
        return listaCarros.stream()
                .filter(carro -> carro.getId() == id)
                .findFirst()
                .map(carro -> modelMapper.map(carro, CarroDTOOutput.class))
                .orElse(null);
    }


    public boolean alterar(CarroDTOInput carroDTOInput) {

        CarroDTOOutput carroExistente = buscar(id);
        if (carroExistente != null) {

            Carro carroAtualizado = modelMapper.map(carroDTOInput, Carro.class);


            carroAtualizado.setId(carroExistente.getId());


            int index = listaCarros.indexOf(carroExistente);
            listaCarros.set(index, carroAtualizado);

            return true;
        }
        return false;
    }


    public void excluir(int id) {
        listaCarros.removeIf(carro -> carro.getId() == id);
    }
}
