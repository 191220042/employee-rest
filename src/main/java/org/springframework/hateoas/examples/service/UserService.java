package org.springframework.hateoas.examples.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.examples.dao.UserDAO;
import org.springframework.hateoas.examples.projo.User;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.util.Random;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;


    public int register(User user, HttpSession session) {
        String username = user.getUsername();
        String name = user.getName();
        String password = user.getPassword();
        String email = user.getEmail();

        username = HtmlUtils.htmlEscape(username);
        user.setUsername(username);
        name = HtmlUtils.htmlEscape(name);
        user.setName(name);

        user.setEmail(email);
        user.setPassword(password);


        if (username.equals("") || password.equals("")) {
            return 0;
        }
        boolean exist = isExist(username);
        if (exist) {
            return 2;
        }
        if(email.equals(""))
            return 3;

        String myemail = (String) session.getAttribute("email");
        String mycode = (String) session.getAttribute("code");
        String voCode = user.getCode();

        System.out.println(myemail);
        System.out.println(mycode);
        if (email == null || email.isEmpty() ||!mycode.equals(voCode)) {
            return 4;
        }

        userDAO.save(user);
        return 1;
    }

    public boolean isExist(String username) {
        User user = getByName(username);
        return null!=user;
    }

    public User getByName(String username) {
        return userDAO.findByUsername(username);
    }

    public User get(String username, String password){
        return userDAO.getByUsernameAndPassword(username, password);
    }

    public void add(User user) {
        userDAO.save(user);
    }
}

