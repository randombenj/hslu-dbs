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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author tqkaufma
 */
@Entity
@Table(name = "Vorlesungen")
@NamedQueries({
    @NamedQuery(name = "Vorlesungen.findAll", query = "SELECT v FROM Vorlesungen v")})
public class Vorlesungen implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "VorlNr")
    private Integer vorlNr;

    @Column(name = "Titel")
    private String titel;

    @Column(name = "SWS")
    private Integer sws;

    @ManyToMany(mappedBy = "vorlesungenCollection")
    private Collection<Studenten> studentenCollection;

    @JoinColumn(name = "gelesenVon", referencedColumnName = "PersNr")
    @ManyToOne
    private Professoren gelesenVon;

    public Vorlesungen() {
    }

    public Vorlesungen(Integer vorlNr) {
        this.vorlNr = vorlNr;
    }

    public Integer getVorlNr() {
        return vorlNr;
    }

    public void setVorlNr(Integer vorlNr) {
        this.vorlNr = vorlNr;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public Integer getSws() {
        return sws;
    }

    public void setSws(Integer sws) {
        this.sws = sws;
    }

    public Collection<Studenten> getStudentenCollection() {
        return studentenCollection;
    }

    public void setStudentenCollection(Collection<Studenten> studentenCollection) {
        this.studentenCollection = studentenCollection;
    }

    public Professoren getGelesenVon() {
        return gelesenVon;
    }

    public void setGelesenVon(Professoren gelesenVon) {
        this.gelesenVon = gelesenVon;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (vorlNr != null ? vorlNr.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vorlesungen)) {
            return false;
        }
        Vorlesungen other = (Vorlesungen) object;
        if ((this.vorlNr == null && other.vorlNr != null) || (this.vorlNr != null && !this.vorlNr.equals(other.vorlNr))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication4.Vorlesungen[ vorlNr=" + vorlNr + " ]";
    }

}
