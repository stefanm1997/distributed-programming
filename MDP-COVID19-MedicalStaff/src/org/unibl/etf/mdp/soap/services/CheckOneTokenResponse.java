/**
 * CheckOneTokenResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.unibl.etf.mdp.soap.services;

public class CheckOneTokenResponse  implements java.io.Serializable {
    private boolean checkOneTokenReturn;

    public CheckOneTokenResponse() {
    }

    public CheckOneTokenResponse(
           boolean checkOneTokenReturn) {
           this.checkOneTokenReturn = checkOneTokenReturn;
    }


    /**
     * Gets the checkOneTokenReturn value for this CheckOneTokenResponse.
     * 
     * @return checkOneTokenReturn
     */
    public boolean isCheckOneTokenReturn() {
        return checkOneTokenReturn;
    }


    /**
     * Sets the checkOneTokenReturn value for this CheckOneTokenResponse.
     * 
     * @param checkOneTokenReturn
     */
    public void setCheckOneTokenReturn(boolean checkOneTokenReturn) {
        this.checkOneTokenReturn = checkOneTokenReturn;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CheckOneTokenResponse)) return false;
        CheckOneTokenResponse other = (CheckOneTokenResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.checkOneTokenReturn == other.isCheckOneTokenReturn();
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
        _hashCode += (isCheckOneTokenReturn() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CheckOneTokenResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://services.soap.mdp.etf.unibl.org", ">checkOneTokenResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("checkOneTokenReturn");
        elemField.setXmlName(new javax.xml.namespace.QName("http://services.soap.mdp.etf.unibl.org", "checkOneTokenReturn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
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
