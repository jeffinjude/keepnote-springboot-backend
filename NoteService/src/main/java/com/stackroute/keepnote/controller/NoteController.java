package com.stackroute.keepnote.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.service.NoteService;

@RestController
@CrossOrigin // cross origin annotation is added so that the client angular app running on different domain could access this api.
			// Specify origins param to restrict to specific domains. If origins is not given all origins are allowed.
public class NoteController {

	private NoteService noteService;

	/**
	 * 
	 * @param noteService
	 */
	@Autowired
	public NoteController(NoteService noteService) {
		this.noteService = noteService;
	}

	/**
	 * 
	 * @param note
	 * @return
	 */
	@PostMapping("/api/v1/note")
	public ResponseEntity<Note> saveNote(@RequestBody Note note) {
		boolean status = false;
		ResponseEntity<Note> resEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		try {
			status = this.noteService.createNote(note);
		} catch (Exception ex) {
			status = false;

		}
		if (status) {
			resEntity = new ResponseEntity<>(note, HttpStatus.CREATED);
		} else {
			resEntity = new ResponseEntity<>(note, HttpStatus.CONFLICT);
		}

		return resEntity;
	}
	
	@PostMapping("/api/v1/note/{userId}")
	public ResponseEntity<Note> saveNoteOfUser(@PathVariable("userId") String userId, @RequestBody Note note) {
		boolean status = false;
		ResponseEntity<Note> resEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		try {
			note.setNoteCreatedBy(userId);
			status = this.noteService.createNoteOfUser(note);
		} catch (Exception ex) {
			status = false;

		}
		if (status) {
			resEntity = new ResponseEntity<>(note, HttpStatus.CREATED);
		} else {
			resEntity = new ResponseEntity<>(note, HttpStatus.CONFLICT);
		}

		return resEntity;
	}


	/**
	 * 
	 * @param userId
	 * @param noteId
	 * @return
	 */
	@DeleteMapping("/api/v1/note/{userId}/{id}")
	public ResponseEntity<Note> deleteNote(@PathVariable("userId") String userId, @PathVariable("id") int noteId) {
		boolean status = false;
		Note noteCheck = null;
		try {
			status = this.noteService.deleteNote(userId, noteId);
		} catch (Exception ex) {
		}
		return status ? new ResponseEntity<>(noteCheck, HttpStatus.OK)
				: new ResponseEntity<>(noteCheck, HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/api/v1/note/{userId}")
	public ResponseEntity<Note> deleteAllNote(@PathVariable("userId") String userId) {
		boolean status = false;
		Note noteCheck = null;
		try {
			status = this.noteService.deleteAllNotes(userId);

		} catch (Exception ex) {
		}
		return status ? new ResponseEntity<>(noteCheck, HttpStatus.OK)
				: new ResponseEntity<>(noteCheck, HttpStatus.NOT_FOUND);
	}

	/**
	 * 
	 * @param note
	 * @param userId
	 * @param noteId
	 * @return
	 */
	@PutMapping("/api/v1/note/{userId}/{id}")
	public ResponseEntity<Note> updateNote(@RequestBody Note note, @PathVariable("userId") String userId,
			@PathVariable("id") int noteId) {
		boolean status = false;
		try {
			note.setNoteCreatedBy(userId);
			this.noteService.updateNote(note, noteId, userId);
			status = true;
		} catch (Exception ex) {
		}
		return status ? new ResponseEntity<>(note, HttpStatus.OK) : new ResponseEntity<>(note, HttpStatus.NOT_FOUND);
	}

	/**
	 * 
	 * @param userId
	 * @return
	 */
	@GetMapping("/api/v1/note/{userId}")
	public ResponseEntity<List<Note>> getAllNotesByUserId(@PathVariable("userId") String userId) {
		boolean status = false;
		List<Note> note = null;
		try {
			note = this.noteService.getAllNoteByUserId(userId);
			status = true;
		} catch (Exception ex) {

		}
		return status ? new ResponseEntity<>(note, HttpStatus.OK) : new ResponseEntity<>(note, HttpStatus.NOT_FOUND);
	}

	/*
	 * Define a handler method which will show details of a specific note created by
	 * specific user. This handler method should return any one of the status
	 * messages basis on different situations: 1. 200(OK) - If the note found
	 * successfully. 2. 404(NOT FOUND) - If the note with specified noteId is not
	 * found. This handler method should map to the URL
	 * "/api/v1/note/{userId}/{noteId}" using HTTP GET method where "id" should be
	 * replaced by a valid reminderId without {}
	 * 
	 */
	@GetMapping("/api/v1/note/{userId}/{noteId}")
	public ResponseEntity<Note> getNoteById(@PathVariable("userId") String userId, @PathVariable("noteId") int noteId) {
		boolean status = false;
		Note note = null;
		try {
			note = this.noteService.getNoteByNoteId(userId, noteId);
			status = true;
		} catch (Exception ex) {
		}
		return status ? new ResponseEntity<>(note, HttpStatus.OK) : new ResponseEntity<>(note, HttpStatus.NOT_FOUND);
	}

}
