package com.murad.todoApp.services;

import com.murad.todoApp.dto.StatusCountDTO;
import com.murad.todoApp.repositories.TodoItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;


public interface DashboardService {
	 List<StatusCountDTO> getTodoCountGroupByStatus() ;
}
