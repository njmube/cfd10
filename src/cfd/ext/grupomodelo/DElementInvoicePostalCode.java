/*
 * Copyright 2010-2011 Sergio Abraham Flores Gutiérrez
 * All rights reserved.
 */

package cfd.ext.grupomodelo;

/**
 *
 * @author Juan Barajas
 */
public class DElementInvoicePostalCode extends cfd.DElement {

    public DElementInvoicePostalCode(java.lang.String value) {
        super("modelo:postalCode", value);
    }

    @Override
    public java.lang.String getElementForOriginalString() {
        return "";
    }
}
