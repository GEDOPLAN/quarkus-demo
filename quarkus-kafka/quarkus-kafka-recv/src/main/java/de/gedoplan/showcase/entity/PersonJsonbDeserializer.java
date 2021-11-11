package de.gedoplan.showcase.entity;

import io.quarkus.kafka.client.serialization.JsonbDeserializer;

public class PersonJsonbDeserializer extends JsonbDeserializer<Person> {
    public PersonJsonbDeserializer() {
        super(Person.class);
    }
}
