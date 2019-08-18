package com.stackroute.keepnote.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.exception.ReminderNotCreatedException;
import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Reminder;
import com.stackroute.keepnote.repository.ReminderRepository;

@Service
public class ReminderServiceImpl implements ReminderService {

	/**
	 * 
	 */
	private ReminderRepository remRepo;

	/**
	 * 
	 * @param remRepo
	 */
	@Autowired
	public ReminderServiceImpl(ReminderRepository remRepo) {
		this.remRepo = remRepo;
	}

	/**
	 * 
	 */
	public Reminder createReminder(Reminder reminder) throws ReminderNotCreatedException {

		Reminder remExists = null;
		try {
			remExists = getReminderById(reminder.getReminderId());
		} catch (Exception e) {
		}

		if (remExists == null) {
			reminder.setReminderCreationDate(new Date());
			remExists = this.remRepo.insert(reminder);
		} else {
			throw new ReminderNotCreatedException("reminder already exists");
		}
		
		if (remExists == null) {
			throw new ReminderNotCreatedException("reminder creation failed");
		}

		return reminder;
	}

	/**
	 * 
	 */
	public boolean deleteReminder(String reminderId) throws ReminderNotFoundException {

		Optional<Reminder> remExists = this.remRepo.findById(reminderId);

		if (!remExists.isPresent() || (remExists.isPresent() && remExists.get() == null)) {
			throw new ReminderNotFoundException("reminder not found");
		}
		this.remRepo.deleteById(reminderId);
		return true;
	}

	/**
	 * This method should be used to update a existing reminder.Call the
	 * corresponding method of Respository interface.
	 */
	public Reminder updateReminder(Reminder reminder, String reminderId) throws ReminderNotFoundException {
		Optional<Reminder> remExists = this.remRepo.findById(reminderId);
		if(remExists.isPresent()) {
			reminder.setReminderId(reminderId);
			reminder.setReminderCreationDate(new Date());
			this.remRepo.save(reminder);
		}
		else {
			throw new ReminderNotFoundException("reminder not found");
		}
		return reminder;
	}

	/**
	 * This method should be used to get a reminder by reminderId.Call the
	 * corresponding method of Respository interface.
	 */
	public Reminder getReminderById(String reminderId) throws ReminderNotFoundException {

		Optional<Reminder> remind = this.remRepo.findById(reminderId);
		if (!remind.isPresent() || (remind.isPresent() && remind.get() == null)) {
			throw new ReminderNotFoundException("Reminder not found");
		}
		return remind.get();
	}

	/**
	 * This method should be used to get all reminders. Call the corresponding
	 * method of Respository interface.
	 */

	public List<Reminder> getAllReminders() {

		return this.remRepo.findAll();
	}

}
