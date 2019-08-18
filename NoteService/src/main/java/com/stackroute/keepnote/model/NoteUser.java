package com.stackroute.keepnote.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class NoteUser {

	@Id
    private String userId;
    private List<Note> notes;

    /**
     * 
     */
    public NoteUser() {}

    /**
	 * @param userId
	 * @param notes
	 */
	public NoteUser(final String userId, final List<Note> notes) {
		super();
		this.userId = userId;
		this.notes = notes;
	}

	public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NoteUser [userId=" + userId + ", notes=" + notes + "]";
	}
    
    
}
