package org.springframework.hateoas.examples.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.examples.projo.User;

public interface UserDAO extends JpaRepository<User,Integer> {
    User findByUsername(String username);

    User getByUsernameAndPassword(String username,String password);
}

