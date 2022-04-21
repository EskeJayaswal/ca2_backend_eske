/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import javax.persistence.EntityManagerFactory;

import dtos.CocktailDTO;
import entities.Cocktail;
import utils.EMF_Creator;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        FacadeExample fe = FacadeExample.getFacadeExample(emf);
        fe.create(new CocktailDTO(new Cocktail("Gin", "Tonic", "Agurk")));
        fe.create(new CocktailDTO(new Cocktail("Vodka", "Schweppers", "Limejuice")));
        fe.create(new CocktailDTO(new Cocktail("Whiskey", "Vermouth", "Bitter")));
    }
    
    public static void main(String[] args) {
        populate();
    }
}
