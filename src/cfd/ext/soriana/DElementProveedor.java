/*
 * Copyright 2010-2011 Sergio Abraham Flores Gutiérrez
 * All rights reserved.
 */

package cfd.ext.soriana;

/**
 *
 * @author Juan Barajas
 */
public class DElementProveedor extends cfd.DElement {

    public DElementProveedor(java.lang.String value) {
        super("Proveedor", value);
    }
    
    @Override
    public java.lang.String getElementForOriginalString() {
        return "";
    }
}
