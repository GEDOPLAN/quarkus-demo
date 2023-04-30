package de.gedoplan.showcase.entity;

import java.util.Set;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import de.gedoplan.baselibs.persistence.entity.GeneratedIntegerIdEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

@Entity
@Access(AccessType.FIELD)
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hero extends PanacheEntity {
    public String name;
    public String surname;
    public double height;
    public int mass;
    public boolean darkSide;
    public String lightSwordColor;

    @Singular
    @ManyToMany
    public Set<Film> films;
}
