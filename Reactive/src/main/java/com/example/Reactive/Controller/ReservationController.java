package com.example.Reactive.Controller;

import com.example.Reactive.Respository.ReservationRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Collections;

@RestController
public class ReservationController {

    private final ReservationRepo reservationRepo;

    public ReservationController(ReservationRepo reservationRepo) {
        this.reservationRepo = reservationRepo;
    }

    @GetMapping("/findAll")
    public Flux<String> getAllReservation() {
        return reservationRepo.findAll().flatMapIterable(reservation -> Collections.singleton(reservation.getName()));
    }
}
