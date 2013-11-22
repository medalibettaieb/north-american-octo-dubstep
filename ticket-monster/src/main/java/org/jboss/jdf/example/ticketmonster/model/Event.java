package org.jboss.jdf.example.ticketmonster.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@SuppressWarnings("serial")
public class Event implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id = null;
	@Version
	@Column(name = "version")
	private int version = 0;

	@Column
	@NotNull
	@Size(message = "between 5 et 50", min = 5, max = 50)
	private String name;

	@Column
	@Size(message = "between 20 and 1000", min = 20, max = 1000)
	private String description;

	@Column
	private boolean major;

	@Column
	private String picture;

	/**
	 * <p>
	 * A media item, such as an image, which can be used to entice a browser to
	 * book a ticket.
	 * </p>
	 * 
	 * <p>
	 * Media items can be shared between events, so this is modeled as a
	 * <code>@ManyToOne</code> relationship.
	 * </p>
	 * 
	 * <p>
	 * Adding a media item is optional, and the view layer will adapt if none is
	 * provided.
	 * </p>
	 * 
	 */
	@ManyToOne
	private MediaItem mediaItem;

	/**
	 * <p>
	 * The category of the event
	 * </p>
	 * 
	 * <p>
	 * Event categories are used to ease searching of available of events, and
	 * hence this is modeled as a relationship
	 * </p>
	 * 
	 * <p>
	 * The Bean Validation constraint <code>@NotNull</code> indicates that the
	 * event category must be specified.
	 */
	@ManyToOne
	@NotNull
	private EventCategory category;

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	public EventCategory getCategory() {
		return category;
	}

	public void setCategory(EventCategory category) {
		this.category = category;
	}

	@Override
	public boolean equals(Object that) {
		if (this == that) {
			return true;
		}
		if (that == null) {
			return false;
		}
		if (getClass() != that.getClass()) {
			return false;
		}
		if (id != null) {
			return id.equals(((Event) that).id);
		}
		return super.equals(that);
	}

	@Override
	public int hashCode() {
		if (id != null) {
			return id.hashCode();
		}
		return super.hashCode();
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public boolean getMajor() {
		return this.major;
	}

	public void setMajor(final boolean major) {
		this.major = major;
	}

	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

	public MediaItem getMediaItem() {
		return mediaItem;
	}

	public void setMediaItem(MediaItem mediaItem) {
		this.mediaItem = mediaItem;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (name != null && !name.trim().isEmpty())
			result += "name: " + name;
		if (description != null && !description.trim().isEmpty())
			result += ", description: " + description;
		result += ", major: " + major;
		if (picture != null && !picture.trim().isEmpty())
			result += ", picture: " + picture;
		return result;
	}
}