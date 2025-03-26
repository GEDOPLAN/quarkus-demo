package de.gedoplan.showcase.model;

import de.gedoplan.baselibs.persistence.entity.GeneratedIntegerIdEntity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ROOM_BOOKING")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoomBooking extends GeneratedIntegerIdEntity {
  @ManyToOne
  private Room room;

  @Column(name = "BEGIN_DATE")
  private LocalDate begin;
  @Column(name = "END_DATE")
  private LocalDate end;

  @Enumerated(EnumType.STRING)
  private BookingType bookingType;

  private String reference;

  private String lraId;

}
