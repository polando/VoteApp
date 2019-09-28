/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.entities;

import java.util.List;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import foxtrot.jee19.logic.OptionType;
import foxtrot.jee19.utilities.OptionTypeJpaConverter;

/**
 *
 * @author ussocom
 */
@NamedQueries({
    @NamedQuery(name = "getPermanentOptionByState", query = "SELECT p FROM OptEntity p WHERE p.optiontype = :optionType"),
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
    
    @Convert(converter = OptionTypeJpaConverter.class)
    private OptionType optiontype;
    
    
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

    public OptionType getOptiontype() {
        return optiontype;
    }

    public void setOptiontype(OptionType optiontype) {
        this.optiontype = optiontype;
    }

    
    
    
    
    
}
