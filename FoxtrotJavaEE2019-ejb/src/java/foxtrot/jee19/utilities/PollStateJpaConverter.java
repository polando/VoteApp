/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package foxtrot.jee19.utilities;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import foxtrot.jee19.logic.PollState;

/**
 *
 * @author ussocom
 */
@Converter
public class PollStateJpaConverter implements AttributeConverter<PollState, String> {
   @Override
   public String convertToDatabaseColumn(PollState priority) {
       if (priority == null) {
           return null;
       }
       return priority.toString();
   }
 
   @Override
   public PollState convertToEntityAttribute(String string) {
       if (string == null) {
           return null;
       }
       try {
           return PollState.valueOf(string);
       } catch (IllegalArgumentException e) {
           return null;
       }
   }
}
