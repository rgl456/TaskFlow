package com.project.TaskFlow.repository;

import com.project.TaskFlow.model.TaskActivity;
import com.project.TaskFlow.model.TaskComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskActivityRepository extends JpaRepository<TaskActivity, Long> {
}
