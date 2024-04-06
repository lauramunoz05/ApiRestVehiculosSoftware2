package com.software2.apiRestVehiculos.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.software2.apiRestVehiculos.model.Vehiculo;

@RestController
@RequestMapping("/api/vehiculos")

public class VehiculoController {
    private List<Vehiculo> vehiculos;

    public VehiculoController(){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Vehiculo[] vehiculosArray = objectMapper.readValue(new ClassPathResource("vehiculos.json").getFile(), Vehiculo[].class);
            vehiculos = new ArrayList<>(Arrays.asList(vehiculosArray));

        } catch (IOException e) {
            e.printStackTrace();
            vehiculos = new ArrayList<>();
        }
    }

    @GetMapping
    public List<Vehiculo> getAllVehiculos(){
        return vehiculos;
    }

    @GetMapping("/{placa}")
    public Vehiculo getVehiculoByplaca(@PathVariable String placa) {
        return vehiculos.stream()
            .filter(vehiculo -> vehiculo.getPlaca().equals(placa))
            .findFirst()
            .orElse(null);
    }

    @PostMapping
    public Vehiculo createVehiculo(@RequestBody Vehiculo vehiculo) {
        vehiculos.add(vehiculo);
        return vehiculo;
    }

    @PutMapping("/{placa}")
    public Vehiculo updateVehiculo(@PathVariable String placa, @RequestBody Vehiculo updatedVehiculo) {
        Vehiculo vehiculo = getVehiculoByplaca(placa);
        if (vehiculo != null) {
            vehiculo.setMarca(updatedVehiculo.getMarca());
            vehiculo.setModelo(updatedVehiculo.getModelo());
            vehiculo.setAño(updatedVehiculo.getAño());
            vehiculo.setColor(updatedVehiculo.getColor());
            vehiculo.setPrecio(updatedVehiculo.getPrecio());
            return vehiculo;
        }

        return null;
    }

    @DeleteMapping("/{placa}")
    public void deleteVehiculo(@PathVariable String placa) {
        vehiculos.removeIf(vehiculo -> vehiculo.getPlaca().equals(placa));
    }
}

