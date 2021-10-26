//package org.springframework.hateoas.examples.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.hateoas.examples.dao.PlayerDao;
//import org.springframework.hateoas.examples.projo.Player;
//import org.springframework.stereotype.Service;
//
//@Service
//public class PlayerService {
//    @Autowired
//    PlayerDao playerDAO;
//
//    public boolean isExist(String name){
//        Player player=getByName(name);
//        return null!=player;
//    }
//
//    public Player getByName(String username) {
//        return playerDAO.findByUsername(username);
//    }
//
//    public void add(Player user) {
//        playerDAO.save(user);
//    }
//
//}
