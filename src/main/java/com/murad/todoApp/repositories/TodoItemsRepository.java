package com.murad.todoApp.repositories;

import com.murad.todoApp.dto.StatusCountDTO;
import com.murad.todoApp.enums.TodoItemStatus;
import com.murad.todoApp.models.TodoItemsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface TodoItemsRepository extends JpaRepository<TodoItemsModel, Integer> {
	Optional<TodoItemsModel> findByItemId(Integer itemId);

	Optional<TodoItemsModel> findByStatus(TodoItemStatus status);

	@Query("SELECT new com.murad.todoApp.dto.StatusCountDTO(t.status,COUNT(t.id) as c) FROM TodoItemsModel t GROUP BY t.status")
	List<StatusCountDTO> findCountGroupByStatus();

	List<TodoItemsModel> findAllByOrderByPriorityAsc();
}
