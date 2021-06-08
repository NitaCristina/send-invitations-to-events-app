package com.example.finalproject.repository;

import com.example.finalproject.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {


    @Query("Select e From Event e where e.planner.id=:userId")
    List<Event> findByUserId(int userId);



}
