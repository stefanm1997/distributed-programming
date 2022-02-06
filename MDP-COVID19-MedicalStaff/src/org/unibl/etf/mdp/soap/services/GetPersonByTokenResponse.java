/**
 * GetPersonByTokenResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.unibl.etf.mdp.soap.services;

public class GetPersonByTokenResponse  implements java.io.Serializable {
    private org.unibl.etf.mdp.model.Person getPersonByTokenReturn;

    public GetPersonByTokenResponse() {
    }

    public GetPersonByTokenResponse(
           org.unibl.etf.mdp.model.Person getPersonByTokenReturn) {
           this.getPersonByTokenReturn = getPersonByTokenReturn;
    }


    /**
     * Gets the getPersonByTokenReturn value for this GetPersonByTokenResponse.
     * 
     * @return getPersonByTokenReturn
     */
    public org.unibl.etf.mdp.model.Person getGetPersonByTokenReturn() {
        return getPersonByTokenReturn;
    }


    /**
     * Sets the getPersonByTokenReturn value for this GetPersonByTokenResponse.
     * 
     * @param getPersonByTokenReturn
     */
    public void setGetPersonByTokenReturn(org.unibl.etf.mdp.model.Person getPersonByTokenReturn) {
        this.getPersonByTokenReturn = getPersonByTokenReturn;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetPersonByTokenResponse)) return false;
        GetPersonByTokenResponse other = (GetPersonByTokenResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getPersonByTokenReturn==null && other.getGetPersonByTokenReturn()==null) || 
             (this.getPersonByTokenReturn!=null &&
              this.getPersonByTokenReturn.equals(other.getGetPersonByTokenReturn())));
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
        if (getGetPersonByTokenReturn() != null) {
            _hashCode += getGetPersonByTokenReturn().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetPersonByTokenResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://services.soap.mdp.etf.unibl.org", ">getPersonByTokenResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getPersonByTokenReturn");
        elemField.setXmlName(new javax.xml.namespace.QName("http://services.soap.mdp.etf.unibl.org", "getPersonByTokenReturn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://model.mdp.etf.unibl.org", "Person"));
        elemField.setNillable(false);
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