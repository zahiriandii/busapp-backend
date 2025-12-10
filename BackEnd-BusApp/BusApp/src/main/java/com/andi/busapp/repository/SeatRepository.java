package com.andi.busapp.repository;

import com.andi.busapp.entity.Bus;
import com.andi.busapp.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long>
{
    List<Seat> findByBusIdOrderBySeatNumberAsc(Long busId);
    void deleteByBusId(Long busId);
    List<Seat> findByBus(Bus bus);
}
