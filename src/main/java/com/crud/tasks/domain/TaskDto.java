package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TaskDto {
    private Long id;
    private String title;
    private String content;

    @Override
    public boolean equals(Object o) {
        TaskDto taskDto = (TaskDto) o;
        return taskDto.id == this.id && taskDto.title == this.title && taskDto.content == this.content;
    }
}
