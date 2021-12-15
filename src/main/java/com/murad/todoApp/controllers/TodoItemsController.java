package com.murad.todoApp.controllers;

import com.murad.todoApp.constant.PropertyNamespace;
import com.murad.todoApp.dto.ReturnObject;
import com.murad.todoApp.enums.TodoItemStatus;
import com.murad.todoApp.enums.TodoPriority;
import com.murad.todoApp.exceptions.ApiException;
import com.murad.todoApp.models.TodoItemsModel;
import com.murad.todoApp.models.UsersModel;
import com.murad.todoApp.services.TodoItemsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@CrossOrigin
@Api(tags = "todo-items", value = "todo-items")
@Controller
@RequestMapping("/todo-items")
public class TodoItemsController {
    @Autowired
    private TodoItemsService todoItemService;
    @Autowired
    private MessageSource messageSource;



    @GetMapping("/view-all")
    public String findAll(Model model, @ModelAttribute("status") String status,
                          @ModelAttribute("message") String message) {
        log.info("initiating view-all-todo");
        if (!StringUtils.isEmpty(status)) {
            model.addAttribute("status", status);
            model.addAttribute("message", message);
        }
        model.addAttribute("statusList", Stream.of(TodoItemStatus.values()).map(TodoItemStatus::name).collect(Collectors.toList()));
        model.addAttribute("priorities", Stream.of(TodoPriority.values()).map(TodoPriority::name).collect(Collectors.toList()));

        return "/todoitems/view-todo-items";
    }

    @GetMapping("/add")
    public String addTodoItem(Model model) {
        log.info("loading add todo form");
        model.addAttribute("priorities", Stream.of(TodoPriority.values()).map(TodoPriority::name).collect(Collectors.toList()));
        return "/todoitems/add-todo-item";
    }


    @ApiOperation(value = "all", notes = "Get list of Todo(s)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<List<TodoItemsModel>> todoList(@RequestParam("status") Optional<TodoItemStatus> status,
                                                         @RequestParam("startDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Optional<Date> startDate,
                                                         @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Optional<Date> endDate) {

        List<TodoItemsModel> todoList = todoItemService.findTodoList(status.orElse(null), startDate.orElse(null), endDate.orElse(null));

        try {
            log.info("initiating get-all-todo-api");
            if (null!=todoList)
                return new ResponseEntity<>(todoList, HttpStatus.OK);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (ApiException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error(PropertyNamespace.ERROR, ex);
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }


    @ApiOperation(value = "Create Todo", notes = "Create Todo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Todo create success"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping(value = "/add", consumes = "application/json")
    @ResponseBody
    public ResponseEntity<?> addTodoItem(@RequestBody Optional<TodoItemsModel> todoItemModel, Locale locale) {
        log.info("initiating create-todo");
        if (todoItemModel.isPresent()) {
            UsersModel user = (UsersModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            todoItemModel.get().setUser(user);
            TodoItemsModel persistModel = todoItemService.save(todoItemModel.get());
            if (persistModel != null) {
                return new ResponseEntity<>(persistModel, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(messageSource.getMessage("system.message.invalidParam", null, locale),
                HttpStatus.BAD_REQUEST);
    }


    @PostMapping(value = "/update")
    public String update(@Valid @ModelAttribute Optional<TodoItemsModel> todoItem, RedirectAttributes redirectAttr,
                         Locale locale) {
        log.info("initiating update-todo");
        if (todoItem.isPresent()) {
            ReturnObject ro = todoItemService.update(todoItem.get(), locale);
            if (ro.isSuccessfull()) {
                redirectAttr.addFlashAttribute("status", "success");
                redirectAttr.addFlashAttribute("message", messageSource.getMessage("system.message.todoItemUpdated",
                        new String[]{todoItem.get().getItemTitle()}, locale));
            } else {
                redirectAttr.addFlashAttribute("status", "error");
                redirectAttr.addFlashAttribute("message", ro.getMessage());
            }
        } else {
            redirectAttr.addFlashAttribute("status", "error");
            redirectAttr.addFlashAttribute("message",
                    messageSource.getMessage("system.message.invalidParam", null, locale));
        }
        return "redirect:/todo-items/view-all";
    }

    @GetMapping("edit/{itemId}")
    public String getTodoById(@PathVariable("itemId") Optional<Integer> itemId, Model model) {
        log.info("update-todo-api");
        if (itemId.isPresent()) {
            TodoItemsModel todoItem = todoItemService.getById(itemId.get());
            if (todoItem != null)
                model.addAttribute("todoItem", todoItem);
            model.addAttribute("priorities", Stream.of(TodoPriority.values()).map(TodoPriority::name).collect(Collectors.toList()));
            model.addAttribute("statusList", Stream.of(TodoItemStatus.values()).map(TodoItemStatus::name).collect(Collectors.toList()));
        }
        return "/todoitems/edit-todo-item";
    }

    @ApiOperation(value = "Delete Todo", notes = "Delete a single Todo item")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })

    @GetMapping("/delete/{itemId}")
    @ResponseBody
    public ResponseEntity<?> deleteById(@PathVariable("itemId") Optional<Integer> itemId, Locale locale) {
        log.info("initiating delete todo api");
        if (itemId.isPresent()) {
            if (todoItemService.deleteByItemId(itemId.get())) {
                return new ResponseEntity<>(messageSource.getMessage("system.message.todoItemDeleted", null, locale),
                        HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(messageSource.getMessage("system.message.invalidParam", null, locale),
                HttpStatus.BAD_REQUEST);
    }
}
