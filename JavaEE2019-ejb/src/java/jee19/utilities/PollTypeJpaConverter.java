/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee19.utilities;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import jee19.logic.PollType;

/**
 *
 * @author ussocom
 */
@Converter
public class PollTypeJpaConverter implements AttributeConverter<PollType, String>  {

    @Override
    public String convertToDatabaseColumn(PollType attribute) {
        if (attribute == null) {
           return null;
       }
       return attribute.toString();
    }

    @Override
    public PollType convertToEntityAttribute(String dbData) {
       if (dbData == null) {
           return null;
       }
       try {
           return PollType.valueOf(dbData);
       } catch (IllegalArgumentException e) {
           return null;
       }    }
    
}
