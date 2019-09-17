/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.entities;

import java.util.HashSet;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author ussocom
 */
@NamedQueries({
    @NamedQuery(name = "getPermanentOptions", query = "SELECT p FROM OptEntity p WHERE p.permanentOption = TRUE"),
    @NamedQuery(name = "getNonPermanentOptions", query = "SELECT p FROM OptEntity p WHERE p.permanentOption = FALSE"),
})
@Entity
@Table(name="OPT")
public class OptEntity extends NamedEntity  {
    
    private static final long serialVersionUID = -406693472919582485L;
    
    public OptEntity() {
    }

    public OptEntity(boolean isNew) {
        super(isNew);
        if (isNew) {
            
        }
    }
    
    private String shortName;
    
    private String discription;
    
    private boolean permanentOption;
    
    @ManyToMany(mappedBy = "optionEntities")
    private List<ItemEntity> ItemEntities;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public List<ItemEntity> getItemEntities() {
        return ItemEntities;
    }

    public void setItemEntities(List<ItemEntity> ItemEntities) {
        this.ItemEntities = ItemEntities;
    }

    public boolean isPermanentOption() {
        return permanentOption;
    }

    public void setPermanentOption(boolean permanentOption) {
        this.permanentOption = permanentOption;
    }
    
    
    
    
    
}
