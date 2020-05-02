package com.example.switter.repos;

import com.example.switter.domain.Message;
import com.example.switter.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepo extends CrudRepository<Message, Integer> {
    List<Message> findByTag(String filter);

    List<Message> findByAuthor(User user);
}
