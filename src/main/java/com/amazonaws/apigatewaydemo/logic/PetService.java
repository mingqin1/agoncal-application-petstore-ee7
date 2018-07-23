/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amazonaws.apigatewaydemo.logic;

import com.amazonaws.apigatewaydemo.model.pet.Pet;
import com.amazonaws.apigatewaydemo.model.user.User;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Ming
 */
@Stateless
@LocalBean
public class PetService implements Service {
    
    @Inject UserService userService;
   
    public Pet getPetById( String petId){
        
         User aUser = userService.getUserByFirstName("John");
         Pet pet = new Pet();
         pet.setPetId( petId);
         return pet;
    }
    
    
}
