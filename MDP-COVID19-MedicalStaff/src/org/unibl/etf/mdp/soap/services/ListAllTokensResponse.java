/**
 * ListAllTokensResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.unibl.etf.mdp.soap.services;

public class ListAllTokensResponse  implements java.io.Serializable {
    private java.lang.String listAllTokensReturn;

    public ListAllTokensResponse() {
    }

    public ListAllTokensResponse(
           java.lang.String listAllTokensReturn) {
           this.listAllTokensReturn = listAllTokensReturn;
    }


    /**
     * Gets the listAllTokensReturn value for this ListAllTokensResponse.
     * 
     * @return listAllTokensReturn
     */
    public java.lang.String getListAllTokensReturn() {
        return listAllTokensReturn;
    }


    /**
     * Sets the listAllTokensReturn value for this ListAllTokensResponse.
     * 
     * @param listAllTokensReturn
     */
    public void setListAllTokensReturn(java.lang.String listAllTokensReturn) {
        this.listAllTokensReturn = listAllTokensReturn;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ListAllTokensResponse)) return false;
        ListAllTokensResponse other = (ListAllTokensResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.listAllTokensReturn==null && other.getListAllTokensReturn()==null) || 
             (this.listAllTokensReturn!=null &&
              this.listAllTokensReturn.equals(other.getListAllTokensReturn())));
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
        if (getListAllTokensReturn() != null) {
            _hashCode += getListAllTokensReturn().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ListAllTokensResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://services.soap.mdp.etf.unibl.org", ">listAllTokensResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("listAllTokensReturn");
        elemField.setXmlName(new javax.xml.namespace.QName("http://services.soap.mdp.etf.unibl.org", "listAllTokensReturn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
