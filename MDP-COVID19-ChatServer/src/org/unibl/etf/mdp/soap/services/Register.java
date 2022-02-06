/**
 * Register.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.unibl.etf.mdp.soap.services;

public interface Register extends java.rmi.Remote {
    public void changePassword(int token, java.lang.String password) throws java.rmi.RemoteException;
    public int createToken(java.lang.String name, java.lang.String surname, long JMBG) throws java.rmi.RemoteException;
    public void setMedicalNotActive(int id) throws java.rmi.RemoteException;
    public org.unibl.etf.mdp.model.Person getPersonByToken(int token) throws java.rmi.RemoteException;
    public void deleteToken(int token) throws java.rmi.RemoteException;
    public java.lang.String getMedicalStaff() throws java.rmi.RemoteException;
    public java.lang.String listAllTokens() throws java.rmi.RemoteException;
    public boolean checkOneToken(int token) throws java.rmi.RemoteException;
    public void setMedicalActive(int id) throws java.rmi.RemoteException;
}
