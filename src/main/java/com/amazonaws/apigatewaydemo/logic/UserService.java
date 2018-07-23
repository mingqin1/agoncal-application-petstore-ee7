/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amazonaws.apigatewaydemo.logic;

import com.amazonaws.apigatewaydemo.model.user.User;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Ming
 */
@Stateless
@LocalBean
public class UserService implements Service {
   
    public User getUserByFirstName( String firstName){
        
         User user = new User();
         user.setFirstName( firstName);
         return user;
    }
    
    
}
