package org.jboss.jdf.example.ticketmonster.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * A show is an instance of an event taking place at a particular venue. A show
 * can have multiple performances.
 * </p>
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "event_id",
		"venue_id" }))
public class Show {
	/* Declaration of fields */
	/**
	 * The synthetic id of the object.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * <p>
	 * The event of which this show is an instance. The
	 * <code>@ManyToOne<code> JPA mapping
establishes this relationship.
	 * </p>
	 * 
	 * <p>
	 * The <code>@NotNull</code> Bean Validation constraint means that the event
	 * must be specified.
	 * </p>
	 */
	@ManyToOne
	@NotNull
	private Event event;
	/**
	 * <p>
	 * The venue where this show takes place. The
	 * <code>@ManyToOne<code> JPA mapping
establishes this relationship.
	 * </p>
	 * 
	 * <p>
	 * The <code>@NotNull</code> Bean Validation constraint means that the venue
	 * must be specified.
	 * </p>
	 */
	@ManyToOne
	@NotNull
	private Venue venue;
	/**
	 * <p>
	 * The set of performances of this show.
	 * </p>
	 * 
	 * <p>
	 * The
	 * <code>@OneToMany<code> JPA mapping establishes this relationship. TODO Explain
	EAGER fetch.
	* This relationship is bi-directional (a performance knows which show it is part of),
	and the <code>mappedBy</code> attribute establishes this. We cascade all
	 * persistence operations to the set of performances, so, for example if a
	 * show is removed, then all of it's performances will also be removed.
	 * </p>
	 * 
	 * <p>
	 * Normally a collection is loaded from the database in the order of the
	 * rows, but here we want to make sure that performances are ordered by date
	 * - we let the RDBMS do the heavy lifting. The
	 * <code>@OrderBy<code> annotation instructs JPA to do this.
	 * </p>
	 */
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "show", cascade = CascadeType.ALL)
	// @OrderBy("date")
	private Set<Performance> performances = new HashSet<Performance>();

	/**
	 * <p>
	 * The set of ticket prices available for this show.
	 * </p>
	 * 
	 * <p>
	 * The <code>@OneToMany<code> JPA mapping establishes this relationship.
	 * This relationship is bi-directional (a ticket price category knows which show it is
	part of), and the <code>mappedBy</code> attribute establishes this. We
	 * cascade all persistence operations to the set of performances, so, for
	 * example if a show is removed, then all of it's ticket price categories
	 * are also removed.
	 * </p>
	 */
	@OneToMany(mappedBy = "show", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<TicketPrice> ticketPrices = new HashSet<TicketPrice>();

	/* Boilerplate getters and setters */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Venue getVenue() {
		return venue;
	}

	public void setVenue(Venue venue) {
		this.venue = venue;
	}

	/*
	 * toString(), equals() and hashCode() for Show, using the natural identity
	 * of the object
	 */

	public Set<TicketPrice> getTicketPrices() {
		return ticketPrices;
	}

	public void setTicketPrices(Set<TicketPrice> ticketPrices) {
		this.ticketPrices = ticketPrices;
	}

	public Set<Performance> getPerformances() {
		return performances;
	}

	public void setPerformances(Set<Performance> performances) {
		this.performances = performances;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Show show = (Show) o;
		if (event != null ? !event.equals(show.event) : show.event != null)
			return false;
		if (venue != null ? !venue.equals(show.venue) : show.venue != null)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = event != null ? event.hashCode() : 0;
		result = 31 * result + (venue != null ? venue.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return event + " at " + venue;
	}
}
