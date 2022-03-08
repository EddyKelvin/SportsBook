package com.example.demo.Controllers;

import com.example.demo.Entities.Event;
import com.example.demo.Repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/event")
public class EventController {
    @Autowired
    private EventRepository eventRepository;

    @PutMapping
    public @ResponseBody ResponseEntity<Event> updateEvent(@RequestParam String name, @RequestParam String score) {
        String[] scoreNumbers = score.split(":");
        if (scoreNumbers.length != 2) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Event event = eventRepository.findEventByName(name);
        if (event == null) {
            event = new Event();

            event.setName(name);
            event.setScore(score);
        }
        else {
            String lastScore = event.getScore();
            String[] lastScoreNumbers = lastScore.split(":");

            if (Integer.parseInt(lastScoreNumbers[0]) <= Integer.parseInt(scoreNumbers[0]) &&
                Integer.parseInt(lastScoreNumbers[1]) <= Integer.parseInt(scoreNumbers[1]) ) {
                event.setScore(score);
            }
        }

        event = eventRepository.save(event);
        return new ResponseEntity<>(event,HttpStatus.OK);
    }

    @GetMapping
    public @ResponseBody ResponseEntity<Event> getEvent(@RequestParam String name) {
        Event event = eventRepository.findEventByName(name);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @DeleteMapping
    public @ResponseBody ResponseEntity<String> deleteEvent(@RequestParam Integer id) {
        eventRepository.deleteById(id);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Event> getAllEvent() {
        return eventRepository.findAll();
    }
}
