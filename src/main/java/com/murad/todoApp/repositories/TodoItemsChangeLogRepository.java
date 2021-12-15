package com.murad.todoApp.repositories;

import com.murad.todoApp.models.TodoItemsChangeLogModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoItemsChangeLogRepository extends JpaRepository<TodoItemsChangeLogModel, Integer> {

}
