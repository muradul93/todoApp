package com.murad.todoApp.models;

import com.murad.todoApp.enums.TodoItemStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "todo_items")
public class TodoItemsModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Integer itemId;
    @NotEmpty
    @Column(name = "item_title", nullable = false)
    private String itemTitle;
    @Column(columnDefinition = "text")
    private String description;
    @CreationTimestamp
    @Column(name = "creation_date_time")
    private Date creationDateTime;
    @Transient
    private String creationDateTimeStr;
    @Enumerated(EnumType.STRING)
    private TodoItemStatus status;
    private String priority;

    @JsonBackReference
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UsersModel user;

    @JsonManagedReference
    @OneToMany(mappedBy = "statusChangeLog", cascade = CascadeType.REMOVE)
    private List<TodoItemsChangeLogModel> statusChangeLogList;


    public TodoItemsModel(String itemTitle, String description, TodoItemStatus status, String priority, UsersModel user) {
        this.itemTitle = itemTitle;
        this.description = description;
        this.status = status;
        this.priority=priority;
        this.user = user;

    }


}
