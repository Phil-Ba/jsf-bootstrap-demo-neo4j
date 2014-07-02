package de.bit.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import de.bit.model.Event;

public interface EventRepository extends GraphRepository<Event> {

	/**
	 * Custom pagination query, since we want to order by date which isnt a
	 * direct property of Events
	 * 
	 * @param page
	 *            the page to fetch
	 * @return the current page
	 */
	@Query(value = "START event=node:__types__(className='at.bit.model.Event') MATCH  event<-[:eventsToDay]-day RETURN event ORDER BY day.date DESC", countQuery = "START event=node:__types__(className='at.bit.model.Event') RETURN count(event)")
	public Page<Event> fetchOrderedPage(Pageable page);
}
