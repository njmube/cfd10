/*
 * Copyright 2010-2011 Sergio Abraham Flores Gutiérrez
 * All rights reserved.
 */

package cfd.ext.grupomodelo;

/**
 *
 * @author Juan Barajas
 */
public class DElementTaxCategory extends cfd.DElement {

    public DElementTaxCategory(java.lang.String value) {
        super("modelo:taxCategory", value);
    }

    @Override
    public java.lang.String getElementForOriginalString() {
        return "";
    }
}
