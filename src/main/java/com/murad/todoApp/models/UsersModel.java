package com.murad.todoApp.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }) })
public class UsersModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer userId;
	@NotEmpty
	@NotNull(message = "username must have value")
	@Column(columnDefinition = "varchar(32)")
	@Length(max = 32, min = 3)
	private String username;
	@NotEmpty
	@Length(min = 4, message = "Password should have minimum 4 characters")
	@Column(nullable = false)
	private String password;
	@Column(name = "first_name", columnDefinition = "varchar(64)")
	@Length(max = 64, message = "First name lenght maximum 32 allowed")
	private String firstName;
	@Column(name = "last_name", columnDefinition = "varchar(64)")
	@Length(max = 64, message = "First name lenght maximum 64 allowed")
	private String lastName;
	private boolean enabled;
	@CreationTimestamp
	@Column(name = "creation_date_time")
	private Date creationDateTime;

	@JsonManagedReference
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<TodoItemsModel> todoList;

	public UsersModel(String username, String password, String firstName, String lastName, boolean enabled) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.enabled = enabled;
	}

	public UsersModel(UsersModel user) {
		this.userId = user.userId;
		this.username = user.username;
		this.password = user.password;
		this.firstName = user.firstName;
		this.lastName = user.lastName;
		this.enabled = user.enabled;
		this.creationDateTime = user.creationDateTime;
		this.todoList = user.todoList;
	}


}
