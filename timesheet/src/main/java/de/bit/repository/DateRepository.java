package de.bit.repository;

import org.springframework.data.neo4j.repository.GraphRepository;

import de.bit.model.Date;

public interface DateRepository extends GraphRepository<Date> {

}
