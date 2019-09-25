/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.logic;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import jee19.logic.impl.PersonLogicImpl;

/**
 *
 * @author ussocom
 */
@Singleton
@Startup
public class initDataCreation {
    
    @EJB
    private PersonLogic personLogic;
    
    @PostConstruct
    void init(){
        personLogic.createTestData();
    }        
            
    
    
}
