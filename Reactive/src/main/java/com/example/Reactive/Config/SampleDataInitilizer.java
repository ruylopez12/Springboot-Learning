package com.example.Reactive.Config;

import com.example.Reactive.Entity.Reservation;
import com.example.Reactive.Respository.ReservationRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

@Component
@Log4j2
@RequiredArgsConstructor
public class SampleDataInitilizer {

    private static final Logger log = LoggerFactory.getLogger(SampleDataInitilizer.class);
    private final ReservationRepo reservationRepo;

    public SampleDataInitilizer(ReservationRepo reservationRepo) {
        this.reservationRepo = reservationRepo;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void go() {
          var names= Flux.just("Praveen","Sabari","Hari","Murali","Sathish")
                    .map(name -> new Reservation(null,name))
                    .flatMap(reservationRepo::save);
          this.reservationRepo
                  .deleteAll()
                  .thenMany(names)
                  .thenMany(this.reservationRepo.findAll())
                  .subscribe(reservation -> log.info(reservation.toString()));
    }
}
