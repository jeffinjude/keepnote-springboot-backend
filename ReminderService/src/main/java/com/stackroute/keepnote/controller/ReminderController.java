package com.stackroute.keepnote.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.keepnote.exception.ReminderNotCreatedException;
import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Reminder;
import com.stackroute.keepnote.service.ReminderService;

@RestController
public class ReminderController {

	/**
	 * 
	 */

	private ReminderService reminderService;

	/**
	 * 
	 * @param reminderService
	 */
	@Autowired
	public ReminderController(ReminderService reminderService) {
		this.reminderService = reminderService;
	}

	/**
	 * 
	 * @param reminder
	 * @return
	 */
	@PostMapping("/api/v1/reminder")
	public ResponseEntity<Reminder> addReminder(@RequestBody Reminder reminder) {
		boolean status = false;
		try {
			this.reminderService.createReminder(reminder);
			status = true;
		} catch (ReminderNotCreatedException e) {
		}
		return status ? new ResponseEntity<>(reminder, HttpStatus.CREATED)
				: new ResponseEntity<>(reminder, HttpStatus.CONFLICT);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/api/v1/reminder/{id}")
	public ResponseEntity<String> removeReminder(@PathVariable("id") String id) {
		boolean status = false;
		try {
			if (this.reminderService.deleteReminder(id)) {
				status = true;
			}
		} catch (ReminderNotFoundException e) {
		}
		return status ? ResponseEntity.status(HttpStatus.OK).body("deleted reminder successfully")
				: ResponseEntity.status(HttpStatus.NOT_FOUND).body("reminder not found ");
	}

	/**
	 * 
	 * @param remind
	 * @param id
	 * @return
	 */
	@PutMapping("/api/v1/reminder/{id}")
	public ResponseEntity<String> modifyRemind(@RequestBody Reminder remind, @PathVariable("id") String id) {
		boolean status = false;
		try {
			if (this.reminderService.updateReminder(remind, id) != null) {
				status = true;
			}
		} catch (ReminderNotFoundException e) {
		}

		return status ? ResponseEntity.status(HttpStatus.OK).body(" reminder detail modified")
				: ResponseEntity.status(HttpStatus.NOT_FOUND).body(" reminder not found ");
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/api/v1/reminder/{id}")
	public ResponseEntity<Reminder> getReminder(@PathVariable("id") String id) {
		boolean status = false;
		Reminder remind = null;
		try {
			remind = this.reminderService.getReminderById(id);
			status = true;
		} catch (ReminderNotFoundException e) {
		}
		return status ? new ResponseEntity<>(remind, HttpStatus.OK)
				: new ResponseEntity<>(remind, HttpStatus.NOT_FOUND);
	}

	/**
	 * 
	 * @return
	 */
	@GetMapping("/api/v1/reminder")
	public List<Reminder> getAllReminders() {
		return this.reminderService.getAllReminders();
	}
}
