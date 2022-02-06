/**
 * Person.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.unibl.etf.mdp.model;

public class Person  implements java.io.Serializable {
    private long JMBG;

    private boolean blocked;

    private java.lang.String[][] map;

    private java.lang.String name;

    private java.lang.String password;

    private java.lang.String status;

    private java.lang.String surname;

    private int token;

    private java.lang.String vrijeme;

    public Person() {
    }

    public Person(
           long JMBG,
           boolean blocked,
           java.lang.String[][] map,
           java.lang.String name,
           java.lang.String password,
           java.lang.String status,
           java.lang.String surname,
           int token,
           java.lang.String vrijeme) {
           this.JMBG = JMBG;
           this.blocked = blocked;
           this.map = map;
           this.name = name;
           this.password = password;
           this.status = status;
           this.surname = surname;
           this.token = token;
           this.vrijeme = vrijeme;
    }


    /**
     * Gets the JMBG value for this Person.
     * 
     * @return JMBG
     */
    public long getJMBG() {
        return JMBG;
    }


    /**
     * Sets the JMBG value for this Person.
     * 
     * @param JMBG
     */
    public void setJMBG(long JMBG) {
        this.JMBG = JMBG;
    }


    /**
     * Gets the blocked value for this Person.
     * 
     * @return blocked
     */
    public boolean isBlocked() {
        return blocked;
    }


    /**
     * Sets the blocked value for this Person.
     * 
     * @param blocked
     */
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }


    /**
     * Gets the map value for this Person.
     * 
     * @return map
     */
    public java.lang.String[][] getMap() {
        return map;
    }


    /**
     * Sets the map value for this Person.
     * 
     * @param map
     */
    public void setMap(java.lang.String[][] map) {
        this.map = map;
    }


    /**
     * Gets the name value for this Person.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this Person.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the password value for this Person.
     * 
     * @return password
     */
    public java.lang.String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this Person.
     * 
     * @param password
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }


    /**
     * Gets the status value for this Person.
     * 
     * @return status
     */
    public java.lang.String getStatus() {
        return status;
    }


    /**
     * Sets the status value for this Person.
     * 
     * @param status
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
    }


    /**
     * Gets the surname value for this Person.
     * 
     * @return surname
     */
    public java.lang.String getSurname() {
        return surname;
    }


    /**
     * Sets the surname value for this Person.
     * 
     * @param surname
     */
    public void setSurname(java.lang.String surname) {
        this.surname = surname;
    }


    /**
     * Gets the token value for this Person.
     * 
     * @return token
     */
    public int getToken() {
        return token;
    }


    /**
     * Sets the token value for this Person.
     * 
     * @param token
     */
    public void setToken(int token) {
        this.token = token;
    }


    /**
     * Gets the vrijeme value for this Person.
     * 
     * @return vrijeme
     */
    public java.lang.String getVrijeme() {
        return vrijeme;
    }


    /**
     * Sets the vrijeme value for this Person.
     * 
     * @param vrijeme
     */
    public void setVrijeme(java.lang.String vrijeme) {
        this.vrijeme = vrijeme;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Person)) return false;
        Person other = (Person) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.JMBG == other.getJMBG() &&
            this.blocked == other.isBlocked() &&
            ((this.map==null && other.getMap()==null) || 
             (this.map!=null &&
              java.util.Arrays.equals(this.map, other.getMap()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.password==null && other.getPassword()==null) || 
             (this.password!=null &&
              this.password.equals(other.getPassword()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.surname==null && other.getSurname()==null) || 
             (this.surname!=null &&
              this.surname.equals(other.getSurname()))) &&
            this.token == other.getToken() &&
            ((this.vrijeme==null && other.getVrijeme()==null) || 
             (this.vrijeme!=null &&
              this.vrijeme.equals(other.getVrijeme())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += new Long(getJMBG()).hashCode();
        _hashCode += (isBlocked() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getMap() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getMap());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getMap(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getPassword() != null) {
            _hashCode += getPassword().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getSurname() != null) {
            _hashCode += getSurname().hashCode();
        }
        _hashCode += getToken();
        if (getVrijeme() != null) {
            _hashCode += getVrijeme().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Person.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://model.mdp.etf.unibl.org", "Person"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("JMBG");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.mdp.etf.unibl.org", "JMBG"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("blocked");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.mdp.etf.unibl.org", "blocked"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("map");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.mdp.etf.unibl.org", "map"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://services.soap.mdp.etf.unibl.org", "ArrayOf_xsd_string"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://services.soap.mdp.etf.unibl.org", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.mdp.etf.unibl.org", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("password");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.mdp.etf.unibl.org", "password"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.mdp.etf.unibl.org", "status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("surname");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.mdp.etf.unibl.org", "surname"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("token");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.mdp.etf.unibl.org", "token"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vrijeme");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.mdp.etf.unibl.org", "vrijeme"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
