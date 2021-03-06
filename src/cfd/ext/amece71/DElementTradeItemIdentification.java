/*
 * Copyright 2010-2011 Sergio Abraham Flores Gutiérrez
 * All rights reserved.
 */

package cfd.ext.amece71;

/**
 *
 * @author Juan Barajas
 */
public class DElementTradeItemIdentification extends cfd.DElementParent {

    protected cfd.ext.amece71.DElementGtin moEltGtin;

    public DElementTradeItemIdentification() {
        super("tradeItemIdentification");

        moEltGtin = new DElementGtin("");

        mvElements.add(moEltGtin);
    }

    public cfd.ext.amece71.DElementGtin getEltGtin() { return moEltGtin; }

    @Override
    public java.lang.String getElementForOriginalString() {
        return "";
    }
}
