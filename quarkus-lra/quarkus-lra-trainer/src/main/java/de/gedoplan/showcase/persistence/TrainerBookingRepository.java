package de.gedoplan.showcase.persistence;

import de.gedoplan.baselibs.persistence.repository.SingleIdEntityRepository;
import de.gedoplan.showcase.model.TrainerBooking;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TrainerBookingRepository extends SingleIdEntityRepository<Integer, TrainerBooking> {
}
