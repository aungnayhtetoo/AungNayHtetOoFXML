/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aung Nay
 */
@Entity
@Table(name = "RECIPEINFORMATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recipeinformation.findAll", query = "SELECT r FROM Recipeinformation r")
    , @NamedQuery(name = "Recipeinformation.findByRecipeInfoId", query = "SELECT r FROM Recipeinformation r WHERE r.recipeInfoId = :recipeInfoId")
    , @NamedQuery(name = "Recipeinformation.findByRecipeId", query = "SELECT r FROM Recipeinformation r WHERE r.recipeId = :recipeId")})
public class Recipeinformation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "RECIPE_INFO_ID")
    private Integer recipeInfoId;
    @Basic(optional = false)
    @Column(name = "RECIPE_ID")
    private int recipeId;
    @Lob
    @Column(name = "RECIPE_INFORMATION")
    private String recipeInformation;

    public Recipeinformation() {
    }

    public Recipeinformation(Integer recipeInfoId) {
        this.recipeInfoId = recipeInfoId;
    }

    public Recipeinformation(Integer recipeInfoId, int recipeId) {
        this.recipeInfoId = recipeInfoId;
        this.recipeId = recipeId;
    }

    public Integer getRecipeInfoId() {
        return recipeInfoId;
    }

    public void setRecipeInfoId(Integer recipeInfoId) {
        this.recipeInfoId = recipeInfoId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeInformation() {
        return recipeInformation;
    }

    public void setRecipeInformation(String recipeInformation) {
        this.recipeInformation = recipeInformation;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recipeInfoId != null ? recipeInfoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recipeinformation)) {
            return false;
        }
        Recipeinformation other = (Recipeinformation) object;
        if ((this.recipeInfoId == null && other.recipeInfoId != null) || (this.recipeInfoId != null && !this.recipeInfoId.equals(other.recipeInfoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Recipeinformation[ recipeInfoId=" + recipeInfoId + " ]";
    }
    
}
