package com.murad.todoApp.services;

import com.murad.todoApp.dto.ReturnObject;
import com.murad.todoApp.enums.TodoItemStatus;
import com.murad.todoApp.models.TodoItemsModel;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Locale;


public interface TodoItemsService {

    TodoItemsModel save(TodoItemsModel todoItem);

    List<TodoItemsModel> findTodoList(TodoItemStatus status, Date startDate, Date endDate);

    TodoItemsModel getById(Integer itemId);

    boolean deleteByItemId(Integer itemId);

    ReturnObject update(TodoItemsModel todoItem, Locale locale);
}
