package com.murad.todoApp.services.servicesImpl;

import com.murad.todoApp.dto.StatusCountDTO;
import com.murad.todoApp.repositories.TodoItemsRepository;
import com.murad.todoApp.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService{
	@Autowired
	private TodoItemsRepository todoRepository;

	@Override
	public List<StatusCountDTO> getTodoCountGroupByStatus() {
		List<StatusCountDTO> list = todoRepository.findCountGroupByStatus();
		if (!StringUtils.isEmpty(list))
			Collections.sort(list, StatusCountDTO.compareByStatus);
		return list;
	}
}
