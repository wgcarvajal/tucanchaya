/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tucanchaya.controller;

import com.tucanchaya.controller.util.Util;
import com.tucanchaya.entities.Categoria;
import com.tucanchaya.entities.Precio;
import com.tucanchaya.entities.Producto;
import com.tucanchaya.facade.CategoriaFacade;
import com.tucanchaya.facade.PrecioFacade;
import com.tucanchaya.facade.ProductoFacade;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author daniel
 */
@ManagedBean(name="productsController")
@ViewScoped
public class ProductsController implements Serializable {
    @EJB
    private ProductoFacade productoEJB;
    @EJB
    private CategoriaFacade categoriaEJB;
    @EJB
    private PrecioFacade precioEJB;
    private String nombre;
    private List<Producto> products;
    private List<Categoria> categories;
    private String product;
    private Categoria category;
    private Producto productSelected;
    private String urlProducto;
    private Long catId;
    private UploadedFile file;
    private Long valor;
    
    
    
    @PostConstruct
    public void init() {
        if (categories == null) {
            categories = categoriaEJB.findAllOderByName("catNombre");
        }
    }
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getUrlProducto() {
        return urlProducto;
    }

    public void setUrlProducto(String urlProducto) {
        this.urlProducto = urlProducto;
    }

    public Long getCatId() {
        return catId;
    }

    public void setCatId(Long catId) {
        this.catId = catId;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void event(FileUploadEvent event)
    {
        file = event.getFile();
        System.out.println(file.getFileName());
    }

    public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }

    
    
    
    
    
    public void openRegistrerProductDialog()
    {     
        RequestContext requestContex = RequestContext.getCurrentInstance();
        this.nombre= "";
        requestContex.execute("PF('addProductDialog').show()");
    }
    
    public void openEditProductDialog(Producto producto)
    {
       RequestContext requestContext = RequestContext.getCurrentInstance();          
       this.nombre = producto.getProdNombre();
       this.productSelected = producto;
       requestContext.execute("PF('editProductDialog').show()");
    }
    
    public void openDeleteProductDialog(Producto producto)
    {
       RequestContext requestContext = RequestContext.getCurrentInstance();          
       this.productSelected = producto;
       FacesContext.getCurrentInstance().addMessage("deleteMessage", new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Esta seguro que desea eliminar el producto "+producto.getProdNombre()+"?"));
       requestContext.execute("PF('deteletProductDialog').show()");
    }

    public Categoria getCategory() {
        return category;
    }

    public void setCategory(Categoria category) {
        this.category = category;
    }
    
    

    public List<Producto> getProducts() {
        if(products==null)
        {
            products = productoEJB.findAllOderByName("prodNombre");
        }
        return products;
    }

    public List<Categoria> getCategories() {
        return categories;
    }

    
    public boolean copyFile(String fileName, InputStream in , Producto producto) {
        try {

            String filePath = Util.RUTAFOTOSPRODUCTOS;
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            
            
            String[] split = fileName.split(Pattern.quote("."));
            int indice = split.length - 1;
            System.out.println("indice:" + indice);
            String extension = split[indice];
            System.out.println("EXTENCION   :" + extension);
            
            filePath = filePath + File.separator + producto.getProdId()  + "." + extension;
            producto.setProdImagen(producto.getProdId() + "." + extension);
            productoEJB.edit(producto);
            
            OutputStream out = new FileOutputStream(new File(filePath));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    
    
    public void searchProduct()
    {
        products = productoEJB.findProductByColumn(product);
    }
    
    
    
    
   
    
    public void saveProducts() throws IOException {
        if (!nombre.equals("")) {
            nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1).toLowerCase();
            if (!productoEJB.existProduct(nombre)) {
                
                if (file != null) {
                    Categoria categoria = new Categoria();
                    categoria.setCatId(catId);
                    Producto producto = new Producto();
                    producto.setProdNombre(nombre);
                    producto.setProdImagen("");
                    producto.setCatId(categoria);

                    if (valor != null) {
                        
                        productoEJB.create(producto);
                        

                        if (copyFile(file.getFileName(), file.getInputstream(), producto)) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Imagen subida con exito."));
                        } else {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Ocurrio un problema al subir la imagen."));
                        }
                        
                        Calendar fecha = new GregorianCalendar();
                        
                        int año = fecha.get(Calendar.YEAR);
                        int mes = fecha.get(Calendar.MONTH);
                        int dia = fecha.get(Calendar.DAY_OF_MONTH);
                        int hora = fecha.get(Calendar.HOUR_OF_DAY);
                        int minuto = fecha.get(Calendar.MINUTE);
                        int segundo = fecha.get(Calendar.SECOND);
                        
                        String date = "" + dia + "/" + mes  + "/" + año + "/" + hora + ":"+ minuto + ":" + segundo;
                        //System.out.println("Fecha Actual:"+ date);
                        
                        Precio precio = new Precio();
                        precio.setPrecValor(valor);
                        precio.setPrecioFechaInicial(date);
                        precio.setPrecioFechaFinal("");
                        precio.setProdId(producto);
                        
                        //System.out.println("VALORRRRRRRRRR "+precio.getPrecValor());
                        //System.out.println("VALORRRRRRRRRR "+precio.getPrecioFechaInicial());
                        //System.out.println("VALORRRRRRRRRR "+precio.getPrecioFechaFinal());
                        precioEJB.create(precio);

                        products = null;
                        nombre = "";
                        //catId=null;
                        //urlProducto="";
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Producto registrado correctamente."));
                        RequestContext requestContext = RequestContext.getCurrentInstance();
                        requestContext.update("productForm:productPanel");
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Debe digitar una precio."));
                        RequestContext requestContext = RequestContext.getCurrentInstance();
                        requestContext.update("productForm:productPanel");
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Debe seleccionar una imagen."));
                    RequestContext requestContext = RequestContext.getCurrentInstance();
                    requestContext.update("productForm:productPanel");
                }
                                
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El producto ya se encuentra registrado."));
                RequestContext requestContext = RequestContext.getCurrentInstance();
                requestContext.update("productForm:productPanel");
            }
        }
    }
    
    public void editProduct() throws IOException
    {
        if(!nombre.equals(""))
        {
           nombre = nombre.substring(0,1).toUpperCase() + nombre.substring(1).toLowerCase();
           if(!nombre.equals(productSelected.getProdNombre()))
           {
               if(!productoEJB.existProduct(nombre))
                {
                    productSelected.setProdNombre(nombre);
                    productoEJB.edit(productSelected);
                    products = null;
                    /*
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Producto actualizado correctamente."));
                    RequestContext requestContext = RequestContext.getCurrentInstance();
                    requestContext.update("editProductForm:editProductPanel");
                    */
                }
                else
                {
                   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El producto ya se encuentra registrado."));
                   RequestContext requestContext = RequestContext.getCurrentInstance();
                   requestContext.update("editProductForm:editProductPanel"); 
                }
           }
        }
        
        if (file != null) {

            String filePath = Util.RUTAFOTOSPRODUCTOS;
            filePath = filePath + File.separator + productSelected.getProdImagen();

            File dir = new File(filePath);

            dir.delete();

            System.out.println("NOMBREEEEEEE      " + filePath);
            
            

            if (copyFile(file.getFileName(), file.getInputstream(), productSelected)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Imagen subida con exito."));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Ocurrio un problema al subir la imagen."));
            }

        }
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Producto actualizado correctamente."));
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("editProductForm:editProductPanel");

        file=null;
    }
    
    public void deleteProduct()
    {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        products=null;
        
        if( productoEJB.isProductUsed(productSelected.getProdId()) )    
        {
            if (file != null) {
                String filePath = Util.RUTAFOTOSPRODUCTOS;
                filePath = filePath + File.separator + productSelected.getProdImagen();

                File dir = new File(filePath);

                dir.delete();
            }
            precioEJB.remove( precioEJB.getPriceProduct(productSelected.getProdId()) );
            productoEJB.remove(productSelected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Categoria eliminada correctamente."));
        }
        else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No se puede eliminar la categoria."));
        }

        requestContext.execute("PF('deteletProductDialog').hide()");
        requestContext.execute("PF('deteletProductResultDialog').show()");
    }
    
    
    public String getPhotoProduct(Long cenId) {
        List<Producto> listProd = productoEJB.findByProdId(cenId);
        if (listProd != null) {
            return listProd.get(0).getProdImagen();
        }
        return "";
    }
    
}
