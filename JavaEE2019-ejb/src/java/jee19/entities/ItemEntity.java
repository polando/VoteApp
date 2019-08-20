/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author ussocom
 */

@Entity
@Table(name="ITEM")
public class ItemEntity extends NamedEntity {
    
    private static final long serialVersionUID = -63382180974330477L;
    
    
    @OneToMany(mappedBy = "item")
    private Set<ResultEntity> resultEntities;

    public ItemEntity() {
    }

    public ItemEntity(boolean isNew) {
        super(isNew);
        if (isNew) {
            resultEntities = new HashSet<>();
        }
    }
    
    private String Item;

    public Set<ResultEntity> getResultEntities() {
        return resultEntities;
    }

    public void setResultEntities(Set<ResultEntity> resultEntities) {
        this.resultEntities = resultEntities;
    }
    

    public String getItem() {
        return Item;
    }

    public void setItem(String Item) {
        this.Item = Item;
    }
    
    
}
