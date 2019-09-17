/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import jee19.logic.ItemType;
import jee19.utilities.ItemTypeJpaConverter;

/**
 *
 * @author ussocom
 */
@NamedQueries({
    @NamedQuery(name = "getPollItemCount", query = "SELECT COUNT(p) FROM ItemEntity p"),
    @NamedQuery(name = "getPollItemList", query = "SELECT p FROM ItemEntity p ORDER BY p.name, p.uuid"),
})
@Entity
@Table(name="ITEM")
public class ItemEntity extends NamedEntity {
    
    private static final long serialVersionUID = -63382180974330477L;
    
    
    @OneToMany(mappedBy = "item")
    private Set<ResultEntity> resultEntities;
    
    @ManyToMany(mappedBy = "itemEntities")
    private List<PollEntity> pollEntities;
    
    @ManyToMany
    private List<OptEntity> optionEntities;
    
    private String Title;
    
    @Convert(converter = ItemTypeJpaConverter.class)
    private ItemType itemType;
    

    
    public ItemEntity() {
    }

    public ItemEntity(boolean isNew) {
        super(isNew);
        if (isNew) {
            resultEntities = new HashSet<>();
        }
    }

    public Set<ResultEntity> getResultEntities() {
        return resultEntities;
    }

    public void setResultEntities(Set<ResultEntity> resultEntities) {
        this.resultEntities = resultEntities;
    }

    public List<PollEntity> getPollEntities() {
        return pollEntities;
    }

    public void setPollEntities(List<PollEntity> pollEntities) {
        this.pollEntities = pollEntities;
    }

    public List<OptEntity> getOptionEntities() {
        return optionEntities;
    }

    public void setOptionEntities(List<OptEntity> optionEntities) {
        this.optionEntities = optionEntities;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }
    
   

    
    
    
    
    
    
}
