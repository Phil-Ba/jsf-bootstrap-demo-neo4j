package de.bit.model;

import java.util.Collection;

import org.joda.time.LocalDate;
import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import de.bit.common.Constants;

@NodeEntity
public class Date {

	@Id
	private Long id;

	@Indexed(indexName = "date", unique = true)
	private LocalDate date;


	@RelatedTo(direction = Direction.OUTGOING, type = "eventsToDay")
	private Collection<Event> events;

	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public String getDateStr() {
		return date.toString(Constants.DATE_FORMATTER);
	}

	public void setDate(final LocalDate date) {
		this.date = date;
	}

	public void setDateStr(final String date) {
		this.date = LocalDate.parse(date, Constants.DATE_FORMATTER);
	}

	public LocalDate getDate() {
		return date;
	}

	public Collection<Event> getEvents() {
		return events;
	}

	public void setEvents(final Collection<Event> events) {
		this.events = events;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Date other = (Date) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
