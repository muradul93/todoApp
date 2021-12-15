package com.murad.todoApp.dao;


import com.murad.todoApp.enums.TodoItemStatus;
import com.murad.todoApp.enums.TodoPriority;
import com.murad.todoApp.models.TodoItemsModel;
import com.murad.todoApp.models.UsersModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TestTodoItemsDAO {

	@TestConfiguration
	static class TodoDAO {
		@Bean
		public TodoItemsDAO getDAOInstance() {
			return new TodoItemsDAO();
		}
	}

	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private TodoItemsDAO todoItemsDAO;

	@Before
	public void init() {
		try {
			UsersModel user = new UsersModel("admin", "admin", "Muradul", "Mostafa", true);
			entityManager.persist(user);
			TodoItemsModel todo = new TodoItemsModel("Todo Task 1", "use junit4", TodoItemStatus.TODO, TodoPriority.HIGH.name(), user);
			todo.setCreationDateTime(new SimpleDateFormat("dd-MM-yyyy hh:mm").parse("03-12-2020 10:10"));
			entityManager.persist(todo);

			TodoItemsModel inProgress = new TodoItemsModel("Write Integration test", "started to write",
					TodoItemStatus.IN_PROGRESS, TodoPriority.MEDIUM.name(), user);
			inProgress.setCreationDateTime(new SimpleDateFormat("dd-MM-yyyy").parse("04-12-2020 10:10"));
			entityManager.persist(inProgress);

			TodoItemsModel inProgress2 = new TodoItemsModel("Write Integration test2", "started to write",
					TodoItemStatus.IN_PROGRESS, TodoPriority.LOW.name(), user);
			inProgress2.setCreationDateTime(new SimpleDateFormat("dd-MM-yyyy").parse("02-12-2020 12:40"));
			entityManager.persist(inProgress2);
			entityManager.flush();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void when_status_todo_return_one_task() {
		Map<String, Object> criteria = new HashMap<>();
		criteria.put("status", TodoItemStatus.TODO);
		List<TodoItemsModel> list = todoItemsDAO.getTodoListFromDynamicCriteria(criteria);
		assertEquals(1, list.size());
		assertEquals("Todo Task 1", list.get(0).getItemTitle());
	}

	@Test
	public void when_status_inprogress_return_two_task() {
		Map<String, Object> criteria = new HashMap<>();
		criteria.put("status", TodoItemStatus.IN_PROGRESS);
		List<TodoItemsModel> list = todoItemsDAO.getTodoListFromDynamicCriteria(criteria);
		assertEquals(2, list.size());
	}
}
