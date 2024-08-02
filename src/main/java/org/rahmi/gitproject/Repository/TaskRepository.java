package org.rahmi.gitproject.Repository;

import org.rahmi.gitproject.Entity.TaskDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskDate, Long> {

    TaskDate findFirstBy();
}
