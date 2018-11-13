package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskMapperTest {
    @Autowired
    private TaskMapper taskMapper;

    @Test
    public void shouldMapToTaskAndTaskDto() {
        // Given
        TaskDto taskDto = new TaskDto(32L, "Test", "Mapper test");
        Task task = new Task(32L, "Test", "Mapper test");

        // When
        Task mappedTask = taskMapper.mapToTask(taskDto);
        TaskDto mappedTaskDto = taskMapper.mapToTaskDto(task);

        // Then
        assertEquals(task, mappedTask);
        assertEquals(taskDto, mappedTaskDto);
    }

    @Test
    public void shouldMapToTaskDtoList() {
        // Given
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(32L, "Test", "Mapper test"));

        // When
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(tasks);

        // Then
        assertNotNull(taskDtoList);
        assertEquals(1, taskDtoList.size());

        taskDtoList.forEach(taskDto -> {
            assertEquals(32L, taskDto.getId().longValue());
            assertEquals("Test", taskDto.getTitle());
            assertEquals("Mapper test", taskDto.getContent());
        });
    }
}
