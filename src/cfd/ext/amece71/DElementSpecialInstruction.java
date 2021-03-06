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
public class DElementSpecialInstruction extends cfd.DElementParent {

    protected cfd.DAttributeString moAttCode;
    protected cfd.ext.amece71.DElementSpecialInstructionText moEltText;

    public DElementSpecialInstruction() {
        super("specialInstruction");

        moAttCode = new DAttributeString("code", true, 1, 3);
        moEltText = new DElementSpecialInstructionText("");

        mvAttributes.add(moAttCode);
        mvElements.add(moEltText);
    }

    public cfd.DAttributeString getAttCode() { return moAttCode; }
    public cfd.ext.amece71.DElementSpecialInstructionText getEltText() { return moEltText; }

    @Override
    public java.lang.String getElementForOriginalString() {
        return "";
    }
}
