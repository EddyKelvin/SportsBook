package com.example.demo.Repositories;

import com.example.demo.Models.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends CrudRepository<Event, Integer> {

    @Query(value = "Select * from event e where e.name = :name", nativeQuery = true)
    Event findEventByName(@Param("name") String name);
}
