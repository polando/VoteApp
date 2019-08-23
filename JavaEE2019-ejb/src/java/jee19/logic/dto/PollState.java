/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.logic.dto;

/**
 *
 * @author ussocom
 */
public class PollState extends Named{

    private static final long serialVersionUID = 8542930970683623562L;
    
    public PollState(String uuid, long jpaVersion, String name) {
        super(uuid, jpaVersion, name);
    }
    
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    
    
}
