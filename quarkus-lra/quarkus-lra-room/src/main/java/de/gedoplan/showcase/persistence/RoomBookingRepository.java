package de.gedoplan.showcase.persistence;

import de.gedoplan.baselibs.persistence.repository.SingleIdEntityRepository;
import de.gedoplan.showcase.model.Room;
import de.gedoplan.showcase.model.RoomBooking;
import de.gedoplan.showcase.model.Room_;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RoomBookingRepository extends SingleIdEntityRepository<Integer, RoomBooking> {
}
