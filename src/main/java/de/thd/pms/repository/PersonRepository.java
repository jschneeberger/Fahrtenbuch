package de.thd.pms.repository;

import org.springframework.data.repository.CrudRepository;

import de.thd.pms.model.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {

}
