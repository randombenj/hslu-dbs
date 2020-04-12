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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author tqkaufma
 */
@Entity
@Table(name = "Professoren")
@NamedQueries({
    @NamedQuery(name = "Professoren.findAll", query = "SELECT p FROM Professoren p")
})
public class Professoren implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "PersNr")
    private Integer persNr;

    @Basic(optional = false)
    @Column(name = "Name")
    private String name;

    @Column(name = "Rang")
    private String rang;

    @Column(name = "Raum")
    private Integer raum;

    @OneToMany(mappedBy = "gelesenVon")
    private Collection<Vorlesungen> vorlesungenCollection;

    public Professoren() {
    }

    public Professoren(Integer persNr) {
        this.persNr = persNr;
    }

    public Professoren(Integer persNr, String name) {
        this.persNr = persNr;
        this.name = name;
    }

    public Integer getPersNr() {
        return persNr;
    }

    public void setPersNr(Integer persNr) {
        this.persNr = persNr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRang() {
        return rang;
    }

    public void setRang(String rang) {
        this.rang = rang;
    }

    public Integer getRaum() {
        return raum;
    }

    public void setRaum(Integer raum) {
        this.raum = raum;
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
        hash += (persNr != null ? persNr.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Professoren)) {
            return false;
        }
        Professoren other = (Professoren) object;
        if ((this.persNr == null && other.persNr != null) || (this.persNr != null && !this.persNr.equals(other.persNr))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication4.Professoren[ persNr=" + persNr + " ]";
    }

}
