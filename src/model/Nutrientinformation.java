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
@Table(name = "NUTRIENTINFORMATION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Nutrientinformation.findAll", query = "SELECT n FROM Nutrientinformation n")
    , @NamedQuery(name = "Nutrientinformation.findByNutrientInfoId", query = "SELECT n FROM Nutrientinformation n WHERE n.nutrientInfoId = :nutrientInfoId")
    , @NamedQuery(name = "Nutrientinformation.findByNutrientId", query = "SELECT n FROM Nutrientinformation n WHERE n.nutrientId = :nutrientId")})
public class Nutrientinformation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NUTRIENT_INFO_ID")
    private Integer nutrientInfoId;
    @Basic(optional = false)
    @Column(name = "NUTRIENT_ID")
    private int nutrientId;
    @Lob
    @Column(name = "NUTRIENT_INFORMATION")
    private String nutrientInformation;

    public Nutrientinformation() {
    }

    public Nutrientinformation(Integer nutrientInfoId) {
        this.nutrientInfoId = nutrientInfoId;
    }

    public Nutrientinformation(Integer nutrientInfoId, int nutrientId) {
        this.nutrientInfoId = nutrientInfoId;
        this.nutrientId = nutrientId;
    }

    public Integer getNutrientInfoId() {
        return nutrientInfoId;
    }

    public void setNutrientInfoId(Integer nutrientInfoId) {
        this.nutrientInfoId = nutrientInfoId;
    }

    public int getNutrientId() {
        return nutrientId;
    }

    public void setNutrientId(int nutrientId) {
        this.nutrientId = nutrientId;
    }

    public String getNutrientInformation() {
        return nutrientInformation;
    }

    public void setNutrientInformation(String nutrientInformation) {
        this.nutrientInformation = nutrientInformation;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nutrientInfoId != null ? nutrientInfoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Nutrientinformation)) {
            return false;
        }
        Nutrientinformation other = (Nutrientinformation) object;
        if ((this.nutrientInfoId == null && other.nutrientInfoId != null) || (this.nutrientInfoId != null && !this.nutrientInfoId.equals(other.nutrientInfoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Nutrientinformation[ nutrientInfoId=" + nutrientInfoId + " ]";
    }
    
}
