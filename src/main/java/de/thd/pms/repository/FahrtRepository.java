package de.thd.pms.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import de.thd.pms.model.Boot;
import de.thd.pms.model.Fahrt;
import de.thd.pms.model.Person;

public interface FahrtRepository extends CrudRepository<Fahrt, Long> {

	Set<Fahrt> findByBoot(Boot boot);

	List<Fahrt> findByAnkunftIsNull();

	@Query("select f from Fahrt f where f.ruderer = ?1")
	List<Fahrt> findByRuderer(Person person);

}
