package com.murad.todoApp.enums;

public enum TodoPriority {

    HIGH(1), MEDIUM(2), LOW(3);
    private Integer value;

    public Integer getValue() {
        return this.value;
    }

    TodoPriority(Integer value) {
        this.value = value;
    }

    public static String getNameByValue(String value) {
        if(null!=value) {
            int code = Integer.parseInt(value);
            for (TodoPriority todoPriority : TodoPriority.values()) {
                if (code == todoPriority.value) return todoPriority.name();
            }
        }
        return null;
    }
}