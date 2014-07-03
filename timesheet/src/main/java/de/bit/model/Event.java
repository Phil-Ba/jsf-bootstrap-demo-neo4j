package de.bit.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.Duration;
import org.joda.time.LocalTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

import de.bit.common.Constants;

@NodeEntity
public class Event {

	@Id
	private Long id;

	@Indexed
	@NotBlank
	private String name;

	@Indexed
	@NotNull
	private LocalTime startTime = new LocalTime();

	@Indexed
	@NotNull
	private LocalTime endTime = new LocalTime();

	public long durationMinutes() {
		return Duration.millis(endTime.getMillisOfDay() - startTime.getMillisOfDay()).getStandardMinutes();
	}

	public long durationHours() {
		long minutes = durationMinutes();
		Double durationHours = Math.floor(minutes / 60d);
		if (startTime.getMinuteOfHour() > endTime.getMinuteOfHour()) {
			durationHours++;
		}
		if (endTime.getMinuteOfHour() != 0) {
			durationHours++;
		}
		return durationHours.longValue();
	}

	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public String getStartTimeStr() {
		return getStartTime().toString(Constants.TIME_FORMATTER);
	}

	public void setStartTimeStr(final String startTime) {
		this.setStartTime(LocalTime.parse(startTime, Constants.TIME_FORMATTER));
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public String getEndTimeStr() {
		return getEndTime().toString(Constants.TIME_FORMATTER);
	}

	public void setEndTimeStr(final String endTime) {
		this.setEndTime(LocalTime.parse(endTime, Constants.TIME_FORMATTER));
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setStartTime(final LocalTime startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(final LocalTime endTime) {
		this.endTime = endTime;
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
		Event other = (Event) obj;
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
