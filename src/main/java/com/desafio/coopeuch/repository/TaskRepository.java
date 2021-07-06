package com.desafio.coopeuch.repository;

import com.desafio.coopeuch.model.entity.Task;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

    @Query("select t from Task t")
    public List<Task> listAll();
}
