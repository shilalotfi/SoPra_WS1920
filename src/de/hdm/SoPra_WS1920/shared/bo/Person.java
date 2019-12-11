package de.hdm.SoPra_WS1920.shared.bo;

import java.util.*;

/**
 * @author GianlucaBernert
 * Klasse Person die die Variable Vorname, Nachname, E-Mail und isAdmin besitzt
 * sie erbt von der Klasse BusinessObject die SerialVersionUID, die id und den Erstellzeitpunkt
 */
public class Person extends BusinessObject {

	/**
     * Variablen der Klasse Person
     */
    private String firstname;
    private String lastname;
    private String eMail;
    
    /**
     * Konstruktor der Klasse Person, welcher beim Aufruf dieser eine Instanz seiner selbst erzeugt
     */
    public Person() {
    }

    /**
     * Methode um den Vornamen einer Person auszugeben
     * @return String firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Methode um den Vornamen einer Perosn zu setzen
     * @param String firstname 
     */
    public void setFirstname(String firstname) {
       this.firstname = firstname;
    }

    /**
     * Methode um den Nachnamen einer Person auszugeben
     * @return String lastname
     */
    public String getLastname() {
       return lastname;
    }

    /**
     * Methode um den Nachnamen einer Person zu setzen
     * @param String lastname 
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Methode um die eMail adresse einer Person auszugeben
     * @return String email
     */
    public String getEMail() {
        return eMail;
    }

    /**
     * Methode um die eMail adresse einer Person zu setzen
     * @param String eMail 
     */
    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    /**
     * Methode um abzufragen ob eine Person Adminrechte besitzt 
     * @return boolean isAdmin
     */
    public boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     * Methode um einer Person Adminrechte zu erteilen
     * @param boolean isAdmin 
     */
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    
    /**
     * Methode um eine textuelle Dastellung der jeweiligen Instanz zu erzeugen
	 * @return String id + firstname + lastname
	 */
	public String toString() {
		return "PersonID #P" + super.getId() + " " + this.firstname + " " + this.lastname;
	}

}