/*
 * Copyright 2010-2011 Sergio Abraham Flores Gutiérrez
 * All rights reserved.
 */

package cfd.ext.amece71;

import cfd.DElement;
import java.util.Vector;

/**
 *
 * @author Juan Barajas
 */
public class DElementItems extends cfd.DElementParent {

    protected java.util.Vector<cfd.ext.amece71.DElementLineItem> mvEltHijosLineItem;

    public DElementItems() {
        super("");
        mvEltHijosLineItem = new Vector<DElementLineItem>();
    }

    public java.util.Vector<cfd.ext.amece71.DElementLineItem> getEltHijosLineItem() { return mvEltHijosLineItem; }

    @Override
    public java.lang.String getElementForXml() {
        String xml = "";
        String string = "";

        if (mvEltHijosLineItem.size() == 0) {
            throw new IllegalStateException(DElement.MSG_ERR_NO_ELEMENTS + "'" + msName + "'.");
        }
        else {
            for (DElementLineItem item : mvEltHijosLineItem) {
                xml = item.getElementForXml();
                string += xml.length() == 0 ? "" : "\n" + xml;
            }
        }

        return string;
    }

    @Override
    public java.lang.String getElementForOriginalString() {
        return "";
    }
}
