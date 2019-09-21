/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.utilities;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import jee19.logic.ItemType;
import jee19.logic.OptionType;

/**
 *
 * @author ussocom
 */
@Converter
public class OptionTypeJpaConverter implements AttributeConverter<OptionType, String> {

    @Override
    public String convertToDatabaseColumn(OptionType attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.toString();
    }

    @Override
    public OptionType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            return OptionType.valueOf(dbData);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
