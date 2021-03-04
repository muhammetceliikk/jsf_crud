package org.ismek.beans;

import org.ismek.db.DbOperations;
import org.ismek.domain.Rehber;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.Facelet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "rehberMB")
public class RehberManagedBean {
    private int id;
    private String isim;
    private String telefon;
    private List<Rehber> rehberList = new ArrayList<>();

    public RehberManagedBean() {
        HttpServletRequest req= (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String rehberId=req.getParameter("id");
        rehberList = DbOperations.tumRehberiGetir();
        if(rehberId!=null){
            Rehber rehber=DbOperations.kisiGetir(Integer.parseInt(rehberId));
            id=rehber.getId();
            isim=rehber.getIsim();
            telefon=rehber.getTelefon();
        }
    }
    public void ara() {
        List<Rehber> rehberListesi = DbOperations.kisiGetir(isim);
        setRehberList(rehberListesi);
    }

    public void save() {
        DbOperations.rehbereEkle(isim, telefon);
        sendRedirect("/faces/index.xhtml");
    }

    public void update(){
        DbOperations.kisiGuncelle(id,isim,telefon);
        sendRedirect("/faces/index.xhtml");

    }
    public void delete(){
        DbOperations.kisiSil(id);
        sendRedirect("/faces/index.xhtml");
    }

    public void edit(int id){
        sendRedirect("/faces/edit.xhtml?id="+id);
    }

    public void deletePage(int id){
        sendRedirect("/faces/delete.xhtml?id="+id);
    }

    public void sendRedirect(String url){
        FacesContext currentInstance = FacesContext.getCurrentInstance();
        ExternalContext externalContext = currentInstance.getExternalContext();
        try {
            externalContext.redirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Rehber> getRehberList() {
        return rehberList;
    }

    public void setRehberList(List<Rehber> rehberList) {
        this.rehberList = rehberList;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}