/*
 * Copyright 2010-2011 Sergio Abraham Flores Gutiérrez
 * All rights reserved.
 */

package cfd.ext.amece71;

/**
 *
 * @author Juan Barajas
 */
public class DElementShipToGln extends cfd.DElement {

    public DElementShipToGln(java.lang.String value) {
        super("gln", value);
    }
    
    @Override
    public java.lang.String getElementForOriginalString() {
        return "";
    }
}
