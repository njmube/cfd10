/*
 * Copyright 2010-2011 Sergio Abraham Flores Gutiérrez
 * All rights reserved.
 */

package cfd;

import java.text.SimpleDateFormat;

/**
 *
 * @author Sergio Abraham Flores Gutiérrez
 */
public class DAttributeDate extends DAttribute {

    private static final java.text.SimpleDateFormat moDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    protected java.util.Date mtDate;

    public DAttributeDate(java.lang.String name, boolean isRequired) {
        super(name, isRequired);

        mtDate = null;
    }

    public void setDate(java.util.Date date) { mtDate = date; }

    public java.util.Date getDate() { return mtDate; }

    @Override
    public void validateValue() {
        if (mbIsRequired && mtDate == null) {
            throw new IllegalStateException(DAttribute.MSG_ERR_VAL_UNDEF + "'" + msName + "'.");
        }
    }

    @Override
    public java.lang.String getAttributeForXml() {
        validateValue();
        return mtDate == null ? "" : msName + "=\"" + moDateFormat.format(mtDate) + "\"";
    }

    @Override
    public java.lang.String getAttributeForOriginalString() {
        validateValue();
        return moDateFormat.format(mtDate) + "|";
    }
}
