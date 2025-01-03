package com.example.Reactive.Respository;

import com.example.Reactive.Entity.Reservation;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepo extends ReactiveCrudRepository<Reservation, String> {

}
