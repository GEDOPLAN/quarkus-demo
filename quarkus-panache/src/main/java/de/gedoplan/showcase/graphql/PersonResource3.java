package de.gedoplan.showcase.graphql;

import java.util.List;

import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import de.gedoplan.showcase.entity.Person;

@GraphQLApi
public class PersonResource3 {

    @Query("allPersons")
    @Description("Get all persons")
    public List<Person> getAllPersons() {
        return Person.listAll();
    }
}
