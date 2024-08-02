package org.rahmi.gitproject.Service.Imp;

import org.rahmi.gitproject.Entity.TaskDate;
import org.rahmi.gitproject.Repository.TaskRepository;
import org.rahmi.gitproject.Service.ITaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class TaskServiceImp implements ITaskService {

    private final TaskRepository taskRepository;
    private static final Logger logger = LoggerFactory.getLogger(TaskServiceImp.class);


    public TaskServiceImp(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void saveTaskDate(TaskDate taskDate) {

        try {
            logger.info("Saving task date: " + taskDate.getDate());
            taskRepository.save(taskDate);
        } catch (Exception e) {
            logger.error("Error saving taskDate: {} Error: {}", taskDate.getDate(), e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public TaskDate getTaskDate() {

        return taskRepository.findFirstBy();

    }
}
