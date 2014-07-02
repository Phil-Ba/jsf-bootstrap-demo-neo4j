package de.bit.controller;

import javax.annotation.PostConstruct;
import javax.faces.component.html.HtmlInputText;
import javax.faces.event.AjaxBehaviorEvent;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import de.bit.common.Constants;
import de.bit.model.Date;
import de.bit.model.Event;
import de.bit.repository.DateRepository;
import de.bit.repository.EventRepository;

@Controller
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class DayController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DayController.class);

	@Autowired
	private Neo4jTemplate templ;

	@Autowired
	private DateRepository dateRepo;

	@Autowired
	private EventRepository eventRepo;

	private Date currentDate;
	private Event event = new Event();

	@PostConstruct
	@Transactional
	private void init() {
		LOGGER.debug("constructed");
		findorCreateDate(new LocalDate());
	}

	@Transactional
	public String save() {
		currentDate.getEvents().add(event);
		event = new Event();
		return "dayView";
	}

	public String cancel() {
		event = new Event();
		return "dayView";
	}

	@Transactional
	public void next() {
		findorCreateDate(currentDate.getDate().plusDays(1));
	}

	@Transactional
	public void gotoDate(final AjaxBehaviorEvent event) {
		LOGGER.debug("gotoDate called");
		Object source = event.getSource();
		if (source instanceof HtmlInputText) {
			LocalDate date = LocalDate.parse(((HtmlInputText) source).getValue().toString(), Constants.DATE_FORMATTER);
			LOGGER.debug("gotoDate " + date.toString(Constants.DATE_FORMATTER));
			findorCreateDate(date);
		}
	}

	@Transactional()
	public void previous() {
		findorCreateDate(currentDate.getDate().minusDays(1));
	}

	private void findorCreateDate(final LocalDate now) {
		// for some reason the index uses the .toString() value of an object,
		// and not the actual value stored in the db
		currentDate = dateRepo.findByPropertyValue("date", now.toString());
		if (currentDate == null) {
			currentDate = new Date();
			currentDate.setDate(now);
			currentDate = dateRepo.save(currentDate);
		} else {
			templ.fetch(currentDate.getEvents());
			for (Event e : currentDate.getEvents()) {
				LOGGER.info("Found event {} ({}-{})", e.getName(), e.getStartTime(), e.getEndTime());
			}
		}
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(final Date currentDate) {
		this.currentDate = currentDate;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(final Event event) {
		this.event = event;
	}

}
