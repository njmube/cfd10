/*
 * Copyright 2010-2011 Sergio Abraham Flores Gutiérrez
 * All rights reserved.
 */

package cfd.ext.amece71;

/**
 *
 * @author Juan Barajas
 */
public class DElementNetAmount extends cfd.DElementParent {

    protected cfd.ext.amece71.DElementAmount moEltAmount;

    public DElementNetAmount() {
        super("netAmount");

        moEltAmount = new DElementAmount("");

        mvElements.add(moEltAmount);
    }

    public cfd.ext.amece71.DElementAmount getEltAmount() { return moEltAmount; }

    @Override
    public java.lang.String getElementForOriginalString() {
        return "";
    }
}
