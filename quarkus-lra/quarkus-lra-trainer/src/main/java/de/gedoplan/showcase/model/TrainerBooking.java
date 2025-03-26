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
public class TrainerBooking extends GeneratedIntegerIdEntity {
  @ManyToOne
  private Trainer trainer;

  private String course;

  @Column(name = "BEGIN_DATE")
  private LocalDate begin;
  @Column(name = "END_DATE")
  private LocalDate end;

  @Enumerated(EnumType.STRING)
  private BookingType bookingType;

  private String reference;

  private String lraId;

}
