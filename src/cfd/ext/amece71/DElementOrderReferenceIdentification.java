/*
 * Copyright 2010-2011 Sergio Abraham Flores Gutiérrez
 * All rights reserved.
 */

package cfd.ext.amece71;

import cfd.DAttributeString;

/**
 *
 * @author Juan Barajas
 */
public class DElementOrderReferenceIdentification extends cfd.DElement {

    protected cfd.DAttributeString moAttType;

    public DElementOrderReferenceIdentification(java.lang.String value) {
        super("referenceIdentification", value);

        moAttType = new DAttributeString("type", true);
        moAttType.setString("ON");

        mvAttributes.add(moAttType);
    }

    public cfd.DAttributeString getAttType() { return moAttType; }

    @Override
    public java.lang.String getElementForOriginalString() {
        return "";
    }
}
