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
@Table(name = "NUTRIENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Nutrient.findAll", query = "SELECT n FROM Nutrient n")
    , @NamedQuery(name = "Nutrient.findByNutrientId", query = "SELECT n FROM Nutrient n WHERE n.nutrientId = :nutrientId")
    , @NamedQuery(name = "Nutrient.findByNutrientName", query = "SELECT n FROM Nutrient n WHERE LOWER(n.nutrientName) = LOWER(:nutrientName)")
    , @NamedQuery(name = "Nutrient.findByDailyIntake", query = "SELECT n FROM Nutrient n WHERE n.dailyIntake = :dailyIntake")})
public class Nutrient implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NUTRIENT_ID")
    private Integer nutrientId;
    @Basic(optional = false)
    @Column(name = "NUTRIENT_NAME")
    private String nutrientName;
    @Lob
    @Column(name = "DESCRIPTION")
    private String description;
    @Lob
    @Column(name = "BENEFIT")
    private String benefit;
    @Basic(optional = false)
    @Column(name = "DAILY_INTAKE")
    private double dailyIntake;

    public Nutrient() {
    }

    public Nutrient(Integer nutrientId) {
        this.nutrientId = nutrientId;
    }

    public Nutrient(Integer nutrientId, String nutrientName, double dailyIntake) {
        this.nutrientId = nutrientId;
        this.nutrientName = nutrientName;
        this.dailyIntake = dailyIntake;
    }

    public Integer getNutrientId() {
        return nutrientId;
    }

    public void setNutrientId(Integer nutrientId) {
        this.nutrientId = nutrientId;
    }

    public String getNutrientName() {
        return nutrientName;
    }

    public void setNutrientName(String nutrientName) {
        this.nutrientName = nutrientName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBenefit() {
        return benefit;
    }

    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }

    public double getDailyIntake() {
        return dailyIntake;
    }

    public void setDailyIntake(double dailyIntake) {
        this.dailyIntake = dailyIntake;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nutrientId != null ? nutrientId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Nutrient)) {
            return false;
        }
        Nutrient other = (Nutrient) object;
        if ((this.nutrientId == null && other.nutrientId != null) || (this.nutrientId != null && !this.nutrientId.equals(other.nutrientId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Nutrient Id: " + nutrientId + " Name: " + nutrientName;
    }
    
}
