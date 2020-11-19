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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Aung Nay
 */
@Entity
@Table(name = "STUDENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Student.findAll", query = "SELECT s FROM Student s")
    , @NamedQuery(name = "Student.findByStudentId", query = "SELECT s FROM Student s WHERE s.studentId = :studentId")
    , @NamedQuery(name = "Student.findByFName", query = "SELECT s FROM Student s WHERE LOWER(s.fName) = LOWER(:fName)")
    , @NamedQuery(name = "Student.findByLName", query = "SELECT s FROM Student s WHERE s.lName = :lName")
    , @NamedQuery(name = "Student.findByGpa", query = "SELECT s FROM Student s WHERE s.gpa = :gpa")})
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "STUDENT_ID")
    private Integer studentId;
    @Basic(optional = false)
    @Column(name = "F_NAME")
    private String fName;
    @Basic(optional = false)
    @Column(name = "L_NAME")
    private String lName;
    @Basic(optional = false)
    @Column(name = "GPA")
    private double gpa;

    
    
    public Student() {
    }

    public Student(Integer studentId) {
        this.studentId = studentId;
    }

    public Student(Integer studentId, String fName, String lName, double gpa) {
        this.studentId = studentId;
        this.fName = fName;
        this.lName = lName;
        this.gpa = gpa;
    }
    
    public Student(String fName, String lName, double gpa) {
        //this.studentId = studentId;
        this.fName = fName;
        this.lName = lName;
        this.gpa = gpa;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getLName() {
        return lName;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentId != null ? studentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        if ((this.studentId == null && other.studentId != null) || (this.studentId != null && !this.studentId.equals(other.studentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Student[ studentId=" + studentId + " ]";
    }
    
    // testing
    public String printString() {
        return studentId + " " + fName + " " + lName + " " + gpa;
    }
    
}
