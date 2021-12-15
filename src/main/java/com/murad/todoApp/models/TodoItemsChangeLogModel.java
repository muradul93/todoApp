package com.murad.todoApp.models;

import com.murad.todoApp.enums.TodoItemStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "todo_items_status_change_log")
public class TodoItemsChangeLogModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "change_log_id")
	private Integer changeLogId;
	@Enumerated(EnumType.STRING)
	private TodoItemStatus prevStatus;
	@Column(name = "changed_status")
	@Enumerated(EnumType.STRING)
	private TodoItemStatus changedStatus;
	@CreationTimestamp
	@Column(name = "creation_date_time")
	private Date creationDateTime;
	@Transient
	private String creationDateTimeStr;
	@JsonBackReference
	@JoinColumn(name = "item_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private TodoItemsModel statusChangeLog;


	public TodoItemsChangeLogModel(TodoItemStatus prevStatus, TodoItemStatus changedStatus,
			TodoItemsModel statusChangeLog) {
		this.prevStatus = prevStatus;
		this.changedStatus = changedStatus;
		this.statusChangeLog = statusChangeLog;
	}



}
