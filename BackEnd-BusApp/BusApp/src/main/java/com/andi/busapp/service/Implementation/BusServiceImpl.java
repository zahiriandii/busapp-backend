package com.andi.busapp.service.Implementation;




import com.andi.busapp.dto.Bus.BusCreateUpdateDTO;
import com.andi.busapp.dto.Bus.BusResponseDTO;
import com.andi.busapp.entity.Bus;
import com.andi.busapp.repository.BusRepository;
import com.andi.busapp.service.BusService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BusServiceImpl implements BusService {

    private final BusRepository busRepository;

    public BusServiceImpl(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    @Override
    public List<BusResponseDTO> getAll() {
        return busRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public BusResponseDTO getById(Long id) {
        Bus bus = busRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bus not found: " + id));
        return toDto(bus);
    }

    @Override
    public BusResponseDTO create(BusCreateUpdateDTO request) {
        Bus bus = new Bus();
        copy(bus, request);
        Bus saved = busRepository.save(bus);
        return toDto(saved);
    }

    @Override
    public BusResponseDTO update(Long id, BusCreateUpdateDTO request) {
        Bus bus = busRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bus not found: " + id));
        copy(bus, request);
        Bus saved = busRepository.save(bus);
        return toDto(saved);
    }

    @Override
    public void delete(Long id) {
        if (!busRepository.existsById(id)) {
            throw new IllegalArgumentException("Bus not found: " + id);
        }
        busRepository.deleteById(id);
    }

    private void copy(Bus bus, BusCreateUpdateDTO dto) {
        bus.setBusName(dto.busName());
        bus.setPlateNumber(dto.plateNumber());
        // seats are handled separately through AdminSeatService
    }

    private BusResponseDTO toDto(Bus bus) {
        int seatCount = bus.getSeats() == null ? 0 : bus.getSeats().size();

        return new BusResponseDTO(
                bus.getId(),
                bus.getBusName(),
                bus.getPlateNumber(),
                seatCount
        );
    }
}

