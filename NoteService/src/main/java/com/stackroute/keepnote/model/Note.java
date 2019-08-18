package com.stackroute.keepnote.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document
public class Note {
	
	@Id
	@JsonProperty("id") // In angular app the field names are id, title, text and state.
	private int noteId;
	@JsonProperty("title")
	private String noteTitle;
	@JsonProperty("text")
	private String noteContent;
	@JsonProperty("state")
	private String noteStatus;
	private Date noteCreationDate;
	private Category cat;
	private List<Reminder> remind;
	private String createdBy;

	public Note() {}
	
	    /**
	 * @param noteId
	 * @param noteTitle
	 * @param noteContent
	 * @param noteStatus
	 * @param noteCreationDate
	 * @param cat
	 * @param remind
	 * @param createdBy
	 */
	public Note(final int noteId, final String noteTitle, final String noteContent, final String noteStatus, final Date noteCreationDate,
			final Category cat, final List<Reminder> remind, final String createdBy) {
		super();
		this.noteId = noteId;
		this.noteTitle = noteTitle;
		this.noteContent = noteContent;
		this.noteStatus = noteStatus;
		this.noteCreationDate = noteCreationDate;
		this.cat = cat;
		this.remind = remind;
		this.createdBy = createdBy;
	}

		public int getNoteId() {
	        return this.noteId;
	    }

	    public void setNoteId(final int noteId) {
	     this.noteId = noteId;
	    }

	    public String getNoteTitle() {
	        return this.noteTitle;
	    }

	    public void setNoteTitle(final String noteTitle) {
	       this.noteTitle = noteTitle;
	    }

	    public String getNoteContent() {
	        return this.noteContent;
	    }

	    public void setNoteContent(final String noteContent) {
	       this.noteContent = noteContent;
	    }

	    public String getNoteStatus() {
	        return this.noteStatus;
	    }

	    public void setNoteStatus(final String noteStatus) {
	    	this.noteStatus = noteStatus;
	    }

	    public Date getNoteCreationDate() {
	        return this.noteCreationDate;
	    }

	    public void setNoteCreationDate(final Date noteCreationDate) {
	        this.noteCreationDate = noteCreationDate;
	    }

	    public String getNoteCreatedBy() {
	        return this.createdBy;
	    }

	    public void setNoteCreatedBy(final String noteCreatedBy) {
	        this.createdBy = noteCreatedBy;
	    }

	    public Category getCategory() {
	        return this.cat;
	    }

	    public void setCategory(final Category category) {
	       this.cat = category;
	    }

	    public List<Reminder> getReminders() {
	        return remind;
	    }

	    public void setReminders(final List<Reminder> reminders) {
	    	this.remind = reminders;
	    }
	
}
