package com.andi.busapp.service;

import com.andi.busapp.dto.Bus.BusCreateUpdateDTO;
import com.andi.busapp.dto.Bus.BusResponseDTO;

import java.util.List;

public interface BusService
{
    List<BusResponseDTO> getAll();

    BusResponseDTO getById(Long id);

    BusResponseDTO create(BusCreateUpdateDTO request);

    BusResponseDTO update(Long id, BusCreateUpdateDTO request);

    void delete(Long id);
}
