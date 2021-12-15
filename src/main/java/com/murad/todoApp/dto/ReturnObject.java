package com.murad.todoApp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class ReturnObject {
	private boolean isSuccessfull = false;
	private String message = "";
	private Object object = null;

}
