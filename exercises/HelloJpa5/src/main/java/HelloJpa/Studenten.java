/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HelloJpa;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author tqkaufma
 */
@Entity
@Table(name = "Studenten")
@NamedQueries({
    @NamedQuery(name = "Studenten.findAll", query = "SELECT s FROM Studenten s")
})
public class Studenten implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "MatrNr")
    private Integer matrNr;

    @Basic(optional = false)
    @Column(name = "Name")

    private String name;
    @Column(name = "Semester")
    private Integer semester;

    @JoinTable(name = "h\u00f6ren", joinColumns = {
        @JoinColumn(name = "MatrNr", referencedColumnName = "MatrNr")}, inverseJoinColumns = {
        @JoinColumn(name = "VorlNr", referencedColumnName = "VorlNr")})

    @ManyToMany
    private Collection<Vorlesungen> vorlesungenCollection;

    public Studenten() {
    }

    public Studenten(Integer matrNr) {
        this.matrNr = matrNr;
    }

    public Studenten(Integer matrNr, String name) {
        this.matrNr = matrNr;
        this.name = name;
    }

    public Integer getMatrNr() {
        return matrNr;
    }

    public void setMatrNr(Integer matrNr) {
        this.matrNr = matrNr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public Collection<Vorlesungen> getVorlesungenCollection() {
        return vorlesungenCollection;
    }

    public void setVorlesungenCollection(Collection<Vorlesungen> vorlesungenCollection) {
        this.vorlesungenCollection = vorlesungenCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (matrNr != null ? matrNr.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Studenten)) {
            return false;
        }
        Studenten other = (Studenten) object;
        if ((this.matrNr == null && other.matrNr != null) || (this.matrNr != null && !this.matrNr.equals(other.matrNr))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication4.Studenten[ matrNr=" + matrNr + " ]";
    }

}
