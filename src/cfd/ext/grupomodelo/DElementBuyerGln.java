/*
 * Copyright 2010-2011 Sergio Abraham Flores Gutiérrez
 * All rights reserved.
 */

package cfd.ext.grupomodelo;

/**
 *
 * @author Juan Barajas
 */
public class DElementBuyerGln extends cfd.DElement {

    public DElementBuyerGln(java.lang.String value) {
        super("modelo:gln", value);
    }
    
    @Override
    public java.lang.String getElementForOriginalString() {
        return "";
    }
}
