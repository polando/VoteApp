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
public class PollType extends Named {

    private static final long serialVersionUID = 52252945374794153L;
    
    public PollType(String uuid, long jpaVersion, String name) {
        super(uuid, jpaVersion, name);
    }
    
    private String ptype;

    public String getPtype() {
        System.out.println(ptype);
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    

    
    
    
    
}
