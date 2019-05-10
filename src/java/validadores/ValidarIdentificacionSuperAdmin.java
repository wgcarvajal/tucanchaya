/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validadores;

import com.tucanchaya.facade.UsuarioFacade;
import javax.ejb.EJB;
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
@FacesValidator(value="validarIdentificacionSuperAdmin")
public class ValidarIdentificacionSuperAdmin implements Validator
{
    @EJB
    private UsuarioFacade usuarioEJB;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException 
    {
        String texto = String.valueOf(value);        
        Integer numeroIdentificacion=Integer.MIN_VALUE;
        try
        {
            numeroIdentificacion= Integer.parseInt(texto);
        }catch(Exception e)
        {
            
            FacesMessage msg= new FacesMessage(FacesMessage.SEVERITY_ERROR,"Campo solo puede contener números.","Campo solo puede contener números.");
            throw new ValidatorException(msg);
            
        }        
        if(texto.length()>15)
        {
            FacesMessage msg= new FacesMessage(FacesMessage.SEVERITY_ERROR,"Campo no mas de 15 números.","Campo no mas de 15 números.");
            throw new ValidatorException(msg); 
        }
        else
        {
            if(usuarioEJB.isRegisterUsuRolAndIdentification("superAdmin",texto))
            {
                FacesMessage msg= new FacesMessage(FacesMessage.SEVERITY_ERROR,"Número de identificación ya se encuentra registrado.","Número de identificación ya se encuentra registrado.");
                throw new ValidatorException(msg);  
            }  
        }        
        
    }
}
