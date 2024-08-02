package org.rahmi.gitproject.Service;

import org.rahmi.gitproject.Entity.TaskDate;

public interface ITaskService {

    void saveTaskDate(TaskDate taskDate);

    TaskDate getTaskDate();
}
