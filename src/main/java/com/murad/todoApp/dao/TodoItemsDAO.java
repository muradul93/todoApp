package com.murad.todoApp.dao;

import com.murad.todoApp.enums.TodoItemStatus;
import com.murad.todoApp.enums.TodoPriority;
import com.murad.todoApp.models.TodoItemsModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@Repository("todoItemsDAO")
public class TodoItemsDAO {
    @PersistenceContext
    private EntityManager entityManager;
    private Logger logger = LoggerFactory.getLogger(TodoItemsDAO.class.getName());

    public List<TodoItemsModel> getTodoListFromDynamicCriteria(Map<String, Object> criteria) {
        List<TodoItemsModel> sortedTodoItemsModels = null;
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<TodoItemsModel> query = cb.createQuery(TodoItemsModel.class);
            Root<TodoItemsModel> rows = query.from(TodoItemsModel.class);
            Map<String, Predicate> predicatesMap = new HashMap<>(5);

            if (criteria.containsKey("status")) {
                predicatesMap.put("status", cb.equal(rows.get("status"), (TodoItemStatus) criteria.get("status")));
            }
            if (criteria.containsKey("startDate")) {
                predicatesMap.put("creationDateTime",
                        cb.greaterThanOrEqualTo(rows.get("creationDateTime"), (Date) criteria.get("startDate")));
            }
            if (criteria.containsKey("endDate")) {
                predicatesMap.put("creationDateTime",
                        cb.lessThanOrEqualTo(rows.get("creationDateTime"), (Date) criteria.get("endDate")));
            }
            query.orderBy(cb.desc(rows.get("creationDateTime")));
            query.select(rows).where(predicatesMap.values().toArray(new Predicate[]{}));
            List<TodoItemsModel> todoItemsModels = entityManager.createQuery(query).getResultList();
            sortedTodoItemsModels = todoItemsModels.stream()
                    .sorted(Comparator.comparing(TodoItemsModel::getPriority))
                    .collect(Collectors.toList());
            sortedTodoItemsModels.forEach(this::mapTodoModels);
        } catch (Exception e) {
            logger.error("Exception", e);
        }
        return sortedTodoItemsModels;
    }

    String getDateString(Date date){
        Format formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return  formatter.format(date);
    }

    private void mapTodoModels(TodoItemsModel t) {
        t.setPriority(TodoPriority.getNameByValue(t.getPriority()));
                t.setCreationDateTimeStr(getDateString(t.getCreationDateTime()));
                t.getStatusChangeLogList().forEach(s->s.setCreationDateTimeStr(getDateString(s.getCreationDateTime())));
    }
}
