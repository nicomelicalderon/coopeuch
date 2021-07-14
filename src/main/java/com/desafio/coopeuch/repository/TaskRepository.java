package com.desafio.coopeuch.repository;

import com.desafio.coopeuch.model.entity.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface TaskRepository extends CrudRepository<Task, Long> {

    @Query("select t from Task t")
    public List<Task> listAll();

    @Query("select t from Task t " +
            "where t.id=:id")
    public Task getOneTask(Long id);
}
