/*
 * Copyright 2010-2011 Sergio Abraham Flores Gutiérrez
 * All rights reserved.
 */

package cfd.ver2;

import cfd.DAttribute;
import cfd.DAttributeDatetime;
import cfd.DAttributeInteger;
import cfd.DAttributeOptionCondicionesPago;
import cfd.DAttributeOptionFormaPago;
import cfd.DAttributeOptionTipoComprobante;
import cfd.DAttributeString;
import cfd.DAttributeTypeImporte;
import cfd.ext.addenda1.DElementAddenda1;

/**
 *
 * @author Sergio Abraham Flores Gutiérrez
 */
public class DElementComprobante extends cfd.DElement {

    protected float mfVersion;
    protected String[] masAddendaXmlLocationNs;

    protected cfd.DAttributeString moAttVersion;
    protected cfd.DAttributeString moAttSerie;
    protected cfd.DAttributeString moAttFolio;
    protected cfd.DAttributeDatetime moAttFecha;
    protected cfd.DAttributeString moAttSello;
    protected cfd.DAttributeInteger moAttNoAprobacion;
    protected cfd.DAttributeInteger moAttAnoAprobacion;
    protected cfd.DAttributeOptionFormaPago moAttFormaDePago;
    protected cfd.DAttributeString moAttNoCertificado;
    protected cfd.DAttributeString moAttCertificado;
    protected cfd.DAttributeOptionCondicionesPago moAttCondicionesDePago;
    protected cfd.DAttributeTypeImporte moAttSubTotal;
    protected cfd.DAttributeTypeImporte moAttDescuento;
    protected cfd.DAttributeString moAttMotivoDescuento;
    protected cfd.DAttributeTypeImporte moAttTipoCambio;
    protected cfd.DAttributeString moAttMoneda;
    protected cfd.DAttributeTypeImporte moAttTotal;
    protected cfd.DAttributeOptionTipoComprobante moAttTipoDeComprobante;
    protected cfd.DAttributeString moAttMetodoDePago;
    protected cfd.DAttributeString moAttLugarExpedicion;
    protected cfd.DAttributeString moAttNumCtaPago;

    protected cfd.ver2.DElementEmisor moEltEmisor;
    protected cfd.ver2.DElementReceptor moEltReceptor;
    protected cfd.ver2.DElementConceptos moEltConceptos;
    protected cfd.ver2.DElementImpuestos moEltImpuestos;
    protected cfd.ver2.DElementComplemento moEltOpcComplemento;
    protected cfd.ver2.DElementAddenda moEltOpcAddenda;

    public DElementComprobante(float version) {
        this(version, false);
    }

    public DElementComprobante(float version, boolean addenda1XmlLocationNs) {
        super("Comprobante");

        mfVersion = version;
        masAddendaXmlLocationNs = !addenda1XmlLocationNs ? null : DElementAddenda1.createXmlLocationNs();

        moAttVersion = new DAttributeString("version", true, 3, 3);
        moAttVersion.setString("" + mfVersion);
        moAttSerie = new DAttributeString("serie", false, 1);
        moAttFolio = new DAttributeString("folio", true, 1);
        moAttFecha = new DAttributeDatetime("fecha", true);
        moAttSello = new DAttributeString("sello", true, 1);
        moAttNoAprobacion = new DAttributeInteger("noAprobacion", true);
        moAttAnoAprobacion = new DAttributeInteger("anoAprobacion", true, 4, 4);
        moAttFormaDePago = new DAttributeOptionFormaPago("formaDePago", true);
        moAttNoCertificado = new DAttributeString("noCertificado", true, 20, 20);
        moAttCertificado = new DAttributeString("certificado", false, 1);
        moAttCondicionesDePago = new DAttributeOptionCondicionesPago("condicionesDePago", false);
        moAttSubTotal = new DAttributeTypeImporte("subTotal", true);
        moAttSubTotal.setCanBeZero(true);
        moAttDescuento = new DAttributeTypeImporte("descuento", false);
        moAttMotivoDescuento = new DAttributeString("motivoDescuento", false, 1);
        moAttTipoCambio = new DAttributeTypeImporte("TipoCambio", false);
        moAttMoneda = new DAttributeString("Moneda", false, 3, 3);
        moAttTotal = new DAttributeTypeImporte("total", true);
        moAttTotal.setCanBeZero(true);
        moAttTipoDeComprobante = new DAttributeOptionTipoComprobante("tipoDeComprobante", true);
        moAttMetodoDePago = new DAttributeString("metodoDePago", false);
        moAttLugarExpedicion = new DAttributeString("LugarExpedicion", false);
        moAttNumCtaPago = new DAttributeString("NumCtaPago", false, 4);

        mvAttributes.add(moAttVersion);
        mvAttributes.add(moAttSerie);
        mvAttributes.add(moAttFolio);
        mvAttributes.add(moAttFecha);
        mvAttributes.add(moAttSello);
        mvAttributes.add(moAttNoAprobacion);
        mvAttributes.add(moAttAnoAprobacion);
        mvAttributes.add(moAttFormaDePago);
        mvAttributes.add(moAttNoCertificado);
        mvAttributes.add(moAttCertificado);
        mvAttributes.add(moAttCondicionesDePago);
        mvAttributes.add(moAttSubTotal);
        mvAttributes.add(moAttDescuento);
        mvAttributes.add(moAttMotivoDescuento);
        mvAttributes.add(moAttTipoCambio);
        mvAttributes.add(moAttMoneda);
        mvAttributes.add(moAttTotal);
        mvAttributes.add(moAttTipoDeComprobante);
        mvAttributes.add(moAttMetodoDePago);
        mvAttributes.add(moAttLugarExpedicion);
        mvAttributes.add(moAttNumCtaPago);

        moEltEmisor = new DElementEmisor(mfVersion);
        moEltReceptor = new DElementReceptor();
        moEltConceptos = new DElementConceptos();
        moEltImpuestos = new DElementImpuestos();
        moEltOpcComplemento = null;
        moEltOpcAddenda = null;
    }

    public void setEltEmisor(cfd.ver2.DElementEmisor o) { moEltEmisor = o; }
    public void setEltReceptor(cfd.ver2.DElementReceptor o) { moEltReceptor = o; }
    public void setEltOpcComplemento(cfd.ver2.DElementComplemento o) { moEltOpcComplemento = o; }
    public void setEltOpcAddenda(cfd.ver2.DElementAddenda addenda) { moEltOpcAddenda = addenda; }
    public void setEltOpcAddenda(cfd.ver2.DElementAddenda addenda, String[] addendaXmlLocationNs) { moEltOpcAddenda = addenda; masAddendaXmlLocationNs = addendaXmlLocationNs; }

    public float getVersion() { return mfVersion; }
    public String[] getAddendaXmlLocationNs() { return masAddendaXmlLocationNs; }
    public cfd.DAttributeString getAttVersion() { return moAttVersion; }
    public cfd.DAttributeString getAttSerie() { return moAttSerie; }
    public cfd.DAttributeString getAttFolio() { return moAttFolio; }
    public cfd.DAttributeDatetime getAttFecha() { return moAttFecha; }
    public cfd.DAttributeString getAttSello() { return moAttSello; }
    public cfd.DAttributeInteger getAttNoAprobacion() { return moAttNoAprobacion; }
    public cfd.DAttributeInteger getAttAnoAprobacion() { return moAttAnoAprobacion; }
    public cfd.DAttributeOptionFormaPago getAttFormaDePago() { return moAttFormaDePago; }
    public cfd.DAttributeString getAttNoCertificado() { return moAttNoCertificado; }
    public cfd.DAttributeString getAttCertificado() { return moAttCertificado; }
    public cfd.DAttributeOptionCondicionesPago getAttCondicionesDePago() { return moAttCondicionesDePago; }
    public cfd.DAttributeTypeImporte getAttSubTotal() { return moAttSubTotal; }
    public cfd.DAttributeTypeImporte getAttDescuento() { return moAttDescuento; }
    public cfd.DAttributeString getAttMotivoDescuento() { return moAttMotivoDescuento; }
    public cfd.DAttributeTypeImporte getAttTipoCambio() { return moAttTipoCambio; }
    public cfd.DAttributeString getAttMoneda() { return moAttMoneda; }
    public cfd.DAttributeTypeImporte getAttTotal() { return moAttTotal; }
    public cfd.DAttributeOptionTipoComprobante getAttTipoDeComprobante() { return moAttTipoDeComprobante; }
    public cfd.DAttributeString getAttMetodoDePago() { return moAttMetodoDePago; }
    public cfd.DAttributeString getAttLugarExpedicion() { return moAttLugarExpedicion; }
    public cfd.DAttributeString getAttNumCtaPago() { return moAttNumCtaPago; }

    public cfd.ver2.DElementEmisor getEltEmisor() { return moEltEmisor; }
    public cfd.ver2.DElementReceptor getEltReceptor() { return moEltReceptor; }
    public cfd.ver2.DElementConceptos getEltConceptos() { return moEltConceptos; }
    public cfd.ver2.DElementImpuestos getEltImpuestos() { return moEltImpuestos; }
    public cfd.ver2.DElementComplemento getEltOpcComplemento() { return moEltOpcComplemento; }
    public cfd.ver2.DElementAddenda getEltOpcAddenda() { return moEltOpcAddenda; }

    @Override
    public java.lang.String getElementForXml() {
        String aux = "";
        String xml = "";

        if (mfVersion == 2.0f) {
            xml = "<" + msName + " " +
                    "xsi:schemaLocation=\"http://www.sat.gob.mx/cfd/2 http://www.sat.gob.mx/sitio_internet/cfd/2/cfdv2.xsd" +
                    (masAddendaXmlLocationNs == null ? "" : " " + masAddendaXmlLocationNs[0]) + "\" " +
                    "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                    "xmlns=\"http://www.sat.gob.mx/cfd/2\"" +
                    (masAddendaXmlLocationNs == null ? "" : " " + masAddendaXmlLocationNs[1]);
        }
        else if (mfVersion == 2.2f) {
            xml = "<" + msName + " " +
                    "xsi:schemaLocation=\"http://www.sat.gob.mx/cfd/2 http://www.sat.gob.mx/sitio_internet/cfd/2/cfdv22.xsd" +
                    (masAddendaXmlLocationNs == null ? "" : " " + masAddendaXmlLocationNs[0]) + "\" " +
                    "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                    "xmlns=\"http://www.sat.gob.mx/cfd/2\"" +
                    (masAddendaXmlLocationNs == null ? "" : " " + masAddendaXmlLocationNs[1]);
        }

        for (DAttribute attribute : mvAttributes) {
            if (mfVersion == 2.0f && (
                attribute == moAttLugarExpedicion ||
                attribute == moAttNumCtaPago ||
                attribute == moAttTipoCambio ||
                attribute == moAttMoneda)) {
                continue;
            }

            aux = attribute.getAttributeForXml();
            if (!aux.isEmpty()) {
                xml += " " + aux;
            }
        }

        xml += ">";

        aux = moEltEmisor.getElementForXml();
        xml += aux.length() == 0 ? "" : "\n" + aux;

        aux = moEltReceptor.getElementForXml();
        xml += aux.length() == 0 ? "" : "\n" + aux;

        aux = moEltConceptos.getElementForXml();
        xml += aux.length() == 0 ? "" : "\n" + aux;

        aux = moEltImpuestos.getElementForXml();
        xml += aux.length() == 0 ? "" : "\n" + aux;

        if (moEltOpcComplemento != null) {
            aux = moEltOpcComplemento.getElementForXml();
            xml += aux.length() == 0 ? "" : "\n" + aux;
        }

        if (moEltOpcAddenda != null) {
            aux = moEltOpcAddenda.getElementForXml();
            xml += aux.length() == 0 ? "" : "\n" + aux;
        }

        xml += "\n</" + msName + ">";

        return xml;
    }

    @Override
    public java.lang.String getElementForOriginalString() {
        String string = "";

        string += moAttVersion.getAttributeForOriginalString();
        string += moAttSerie.getAttributeForOriginalString();
        string += moAttFolio.getAttributeForOriginalString();
        string += moAttFecha.getAttributeForOriginalString();
        string += moAttNoAprobacion.getAttributeForOriginalString();
        string += moAttAnoAprobacion.getAttributeForOriginalString();
        string += moAttTipoDeComprobante.getAttributeForOriginalString();
        string += moAttFormaDePago.getAttributeForOriginalString();
        string += moAttCondicionesDePago.getAttributeForOriginalString();
        string += moAttSubTotal.getAttributeForOriginalString();
        string += moAttDescuento.getAttributeForOriginalString();
        string += moAttTotal.getAttributeForOriginalString();

        if (mfVersion == 2.2f) {
            string += moAttMetodoDePago.getAttributeForOriginalString();
            string += moAttLugarExpedicion.getAttributeForOriginalString();
            string += moAttNumCtaPago.getAttributeForOriginalString();
            string += moAttTipoCambio.getAttributeForOriginalString();
            string += moAttMoneda.getAttributeForOriginalString();
        }

        string += moEltEmisor.getElementForOriginalString();
        string += moEltReceptor.getElementForOriginalString();
        string += moEltConceptos.getElementForOriginalString();
        string += moEltImpuestos.getElementForOriginalString();

        if (moEltOpcComplemento != null) {
            string += moEltOpcComplemento.getElementForOriginalString();
        }

        /* NOTE:
         * Addenda is not required for CFD version 2.0 nor 2.2 in original string.
         */

        return string;
    }
}
