package org.unibl.etf.mdp.soap.services;

public class RegisterProxy implements org.unibl.etf.mdp.soap.services.Register {
  private String _endpoint = null;
  private org.unibl.etf.mdp.soap.services.Register register = null;
  
  public RegisterProxy() {
    _initRegisterProxy();
  }
  
  public RegisterProxy(String endpoint) {
    _endpoint = endpoint;
    _initRegisterProxy();
  }
  
  private void _initRegisterProxy() {
    try {
      register = (new org.unibl.etf.mdp.soap.services.RegisterServiceLocator()).getRegister();
      if (register != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)register)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)register)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (register != null)
      ((javax.xml.rpc.Stub)register)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.unibl.etf.mdp.soap.services.Register getRegister() {
    if (register == null)
      _initRegisterProxy();
    return register;
  }
  
  public void changePassword(int token, java.lang.String password) throws java.rmi.RemoteException{
    if (register == null)
      _initRegisterProxy();
    register.changePassword(token, password);
  }
  
  public int createToken(java.lang.String name, java.lang.String surname, long JMBG) throws java.rmi.RemoteException{
    if (register == null)
      _initRegisterProxy();
    return register.createToken(name, surname, JMBG);
  }
  
  public void setMedicalNotActive(int id) throws java.rmi.RemoteException{
    if (register == null)
      _initRegisterProxy();
    register.setMedicalNotActive(id);
  }
  
  public org.unibl.etf.mdp.model.Person getPersonByToken(int token) throws java.rmi.RemoteException{
    if (register == null)
      _initRegisterProxy();
    return register.getPersonByToken(token);
  }
  
  public void deleteToken(int token) throws java.rmi.RemoteException{
    if (register == null)
      _initRegisterProxy();
    register.deleteToken(token);
  }
  
  public java.lang.String getMedicalStaff() throws java.rmi.RemoteException{
    if (register == null)
      _initRegisterProxy();
    return register.getMedicalStaff();
  }
  
  public java.lang.String listAllTokens() throws java.rmi.RemoteException{
    if (register == null)
      _initRegisterProxy();
    return register.listAllTokens();
  }
  
  public boolean checkOneToken(int token) throws java.rmi.RemoteException{
    if (register == null)
      _initRegisterProxy();
    return register.checkOneToken(token);
  }
  
  public void setMedicalActive(int id) throws java.rmi.RemoteException{
    if (register == null)
      _initRegisterProxy();
    register.setMedicalActive(id);
  }
  
  
}