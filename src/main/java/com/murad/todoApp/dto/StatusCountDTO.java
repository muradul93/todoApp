package com.murad.todoApp.dto;

import com.murad.todoApp.enums.TodoItemStatus;

import java.util.Comparator;

public class StatusCountDTO {
	private TodoItemStatus status;
	private Long count;

	public StatusCountDTO(TodoItemStatus status, Long count) {
		this.status = status;
		this.count = count;
	}

	public TodoItemStatus getStatus() {
		return status;
	}

	public void setStatus(TodoItemStatus status) {
		this.status = status;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public static Comparator<StatusCountDTO> compareByStatus = new Comparator<StatusCountDTO>() {

		@Override
		public int compare(StatusCountDTO o1, StatusCountDTO o2) {
			return o1.getStatus().compareTo(o2.getStatus());
		}
	};
}
