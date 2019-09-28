/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.utilities;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import foxtrot.jee19.logic.ItemType;

/**
 *
 * @author ussocom
 */
@Converter
public class ItemTypeJpaConverter implements AttributeConverter<ItemType, String> {

    @Override
    public String convertToDatabaseColumn(ItemType attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.toString();
    }

    @Override
    public ItemType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            return ItemType.valueOf(dbData);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
