/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validadores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Wilson Carvajal
 */
@FacesValidator(value="validarCampoMaximoLength200Characteres")
public class ValidarCampoMaximoLength200Characteres implements Validator 
{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException 
    {
        String texto = String.valueOf(value);      
        if(texto.length()>100)
        {
            FacesMessage msg= new FacesMessage(FacesMessage.SEVERITY_ERROR,"Maximo 100 caracteres.","Maximo 100 caracteres.");
            throw new ValidatorException(msg);
        }
    }
}
