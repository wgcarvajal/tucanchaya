/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.controller;

import com.tucanchaya.entities.Categoria;
import com.tucanchaya.facade.CategoriaFacade;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author daniel
 */
@ManagedBean(name = "categoriesController")
@ViewScoped
public class CategoriesController implements Serializable{
    
    @EJB 
    private CategoriaFacade categoriasEJB;
    private String nombre;
    private List<Categoria> categories;
    private String category;
    private Categoria categorySelected;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    public List<Categoria> getCategories()
    {
        if(categories==null)
        {
            categories = categoriasEJB.findAllOderByName("catNombre");
        }
        return categories;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    
    public void openRegisterCategoryDialog() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.nombre = "";
        requestContext.execute("PF('addCategoryDialog').show()");
    }
    
    public void saveCategories()
    {
        if(!nombre.equals(""))
        {
         nombre = nombre.substring(0,1).toUpperCase() + nombre.substring(1).toLowerCase();
         if(!categoriasEJB.existCategory(nombre))
         {
             Categoria categoria = new Categoria();
             categoria.setCatNombre(nombre);
             categoriasEJB.create(categoria);
             categories = null;
             nombre = "";
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Categoria registrada correctamente."));
             RequestContext requestContext = RequestContext.getCurrentInstance();
             requestContext.update("categoryForm:categoryPanel");
         }
         else
           {
              FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "La Categoria ya se encuentra registrada."));
              RequestContext requestContext = RequestContext.getCurrentInstance();
              requestContext.update("categoryForm:categoryPanel"); 
           }
        }
    }
    
    public void openEditarCategoryDialog(Categoria categoria)
    {
       RequestContext requestContext = RequestContext.getCurrentInstance();          
       this.nombre = categoria.getCatNombre();
       this.categorySelected = categoria;
       requestContext.execute("PF('editCategoryDialog').show()");
    }
    
    
    public void openDeleteCategoryDialog(Categoria categoria)
    {
        RequestContext requestContext = RequestContext.getCurrentInstance();  
       this.categorySelected = categoria;
       FacesContext.getCurrentInstance().addMessage("deleteMessage", new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Esta seguro que desea eliminar la categoria de "+categoria.getCatNombre()+"?"));
       requestContext.execute("PF('deteletCategoryDialog').show()");
    }
    
    
    public void searchCategory()
    {
        categories = categoriasEJB.findByColumn(category);
    }
    
    public void editCategory()
    {
        if(!nombre.equals(""))
        {
           nombre = nombre.substring(0,1).toUpperCase() + nombre.substring(1).toLowerCase();
           if(!nombre.equals(categorySelected.getCatNombre()))
           {
               if(!categoriasEJB.existCategory(nombre))
                {
                    categorySelected.setCatNombre(nombre);
                    categoriasEJB.edit(categorySelected);
                    categories = null;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Categoria actualizada correctamente."));
                    RequestContext requestContext = RequestContext.getCurrentInstance();
                    requestContext.update("editCategoryForm:editCategoryPanel");
                }
                else
                {
                   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "La Categoria ya se encuentra registrada."));
                   RequestContext requestContext = RequestContext.getCurrentInstance();
                   requestContext.update("editCategoryForm:editCategoryPanel"); 
                }
           }
        }
    }
    
    public void deleteCategory()
    {
        RequestContext requestContext = RequestContext.getCurrentInstance();  
        categories=null;
        if(!categoriasEJB.isCategoryUsed(categorySelected.getCatId()))
        {
            categoriasEJB.remove(categorySelected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Categoria eliminada correctamente."));
        }
        else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se puede eliminar la categoria."));
        }
        requestContext.execute("PF('deteletCategoryDialog').hide()");
        requestContext.execute("PF('deteletCategoryResultDialog').show()");
    
    }
}
