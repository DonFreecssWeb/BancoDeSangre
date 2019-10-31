package com.example.proyecto;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

public class Usuario implements Serializable {
    private String nombre,apellido,correo;
    private String clave;
    private String urlimagen;





    public Usuario(){

    }


    public Usuario(String nombre, String apellido, String correo, String urlimagen) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.urlimagen = urlimagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    public String getUrlimagen() {
        return urlimagen;
    }

    public void setUrlimagen(String urlimagen) {
        this.urlimagen = urlimagen;
    }

    public String getImagen(String urlimagen){

        String urlimagen1 = urlimagen;
         /*   try {
                InputStream inputStream = (InputStream) new URL(urlimagen).getContent();
                Drawable drawable = Drawable.createFromStream(inputStream,"src name");
                return drawable;
            } catch (IOException e) {
                e.printStackTrace();
                return  null;
            }*/
return  urlimagen1;
    }

}
