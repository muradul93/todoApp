package com.murad.todoApp.services.servicesImpl;

import com.murad.todoApp.dao.TodoItemsDAO;
import com.murad.todoApp.dto.ReturnObject;
import com.murad.todoApp.enums.TodoItemStatus;
import com.murad.todoApp.enums.TodoPriority;
import com.murad.todoApp.models.TodoItemsChangeLogModel;
import com.murad.todoApp.models.TodoItemsModel;
import com.murad.todoApp.repositories.TodoItemsChangeLogRepository;
import com.murad.todoApp.repositories.TodoItemsRepository;
import com.murad.todoApp.services.TodoItemsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class TodoItemsServiceImpl implements TodoItemsService {
	private static Logger logger = LoggerFactory.getLogger(TodoItemsServiceImpl.class.getName());

	@Autowired
	private TodoItemsRepository todoItemRepository;
	@Autowired
	private TodoItemsChangeLogRepository changeLogRepository;

	@Autowired
	@Qualifier("todoItemsDAO")
	private TodoItemsDAO todoItemsDAO;
	@Autowired
	private MessageSource messageSource;

	@Override
	public TodoItemsModel save(TodoItemsModel todoItem) {
		todoItem.setPriority(TodoPriority.valueOf(todoItem.getPriority()).getValue().toString());
		return todoItemRepository.save(todoItem);
	}

	/**
	 * Process all criteria to find filtered todoList to send over network
	 * 
	 * @return
	 */
	@Override
	public List<TodoItemsModel> findTodoList(TodoItemStatus status, Date startDate, Date endDate) {
		Map<String, Object> criteria = new HashMap<>(3);
		if (status != null)
			criteria.put("status", status);
		if (startDate != null)
			criteria.put("startDate", startDate);
		if (endDate != null)
			criteria.put("endDate", endDate);
		return todoItemsDAO.getTodoListFromDynamicCriteria(criteria);
	}


    @Override
	public TodoItemsModel getById(Integer itemId) {
		Optional<TodoItemsModel> todoItem = todoItemRepository.findByItemId(itemId);
		if (todoItem.isPresent()) {
            todoItem.get().setPriority(TodoPriority.getNameByValue(todoItem.get().getPriority()));
			return todoItem.get();
		}
		return null;
	}

	@Override
	public boolean deleteByItemId(Integer itemId) {
		try {
			todoItemRepository.deleteById(itemId);
			return true;
		} catch (Exception e) {
			logger.error("Exception", e);
		}
		return false;
	}

	/**
	 * Update existing one if id found or create new one
	 * 
	 * @param todoItem
	 * @return
	 */
	@Override
	public ReturnObject update(TodoItemsModel todoItem, Locale locale) {
		ReturnObject ro = new ReturnObject();
		try {
			Optional<TodoItemsModel> fetchExistingModel = todoItemRepository.findByItemId(todoItem.getItemId());
			if (fetchExistingModel.isPresent()) {
				if (checkIfFieldChanged(todoItem, fetchExistingModel.get())) {
					TodoItemsModel updateModel = fetchExistingModel.get();
					updateModel.setDescription(todoItem.getDescription());
					updateModel.setItemTitle(todoItem.getItemTitle());
					updateModel.setStatus(todoItem.getStatus());
					updateModel.setPriority(TodoPriority.valueOf(todoItem.getPriority()).getValue().toString());
					todoItemRepository.save(updateModel);
					ro.setSuccessfull(true);
				}
			} else {
				ro.setMessage(messageSource.getMessage("system.message.todoItemNotFound",
						new String[] { "" + todoItem.getItemId() }, locale));
			}
		} catch (Exception e) {
			logger.error("Exception", e);
			ro.setMessage(messageSource.getMessage("system.message.systemError", null, locale));
		}
		return ro;
	}

	/**
	 * Check if required field changed If changed then update data If status change
	 * then keep a log into log table
	 * 
	 * @param changedModel
	 * @param oldModel
	 * @return
	 */

	private boolean checkIfFieldChanged(TodoItemsModel changedModel, TodoItemsModel oldModel) {
		if (!changedModel.getStatus().equals(oldModel.getStatus())) {
			TodoItemsChangeLogModel statusLog = new TodoItemsChangeLogModel(oldModel.getStatus(),
					changedModel.getStatus(), oldModel);
			changeLogRepository.save(statusLog);
			return true;
		}

		if (!TodoPriority.valueOf(changedModel.getPriority()).getValue().toString().equalsIgnoreCase(oldModel.getPriority())) {
			return true;
		}
		if (!changedModel.getItemTitle().equalsIgnoreCase(oldModel.getItemTitle()))
			return true;
		if (!changedModel.getDescription().equalsIgnoreCase(oldModel.getDescription()))
			return true;
		return false;
	}
}
