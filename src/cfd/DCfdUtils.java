/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cfd;

import cfd.ext.addenda1.DElementAddenda1;
import cfd.util.DUtilUtils;
import cfd.ver3.DCfdVer3Consts;
import java.io.ByteArrayInputStream;
import java.util.TreeMap;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import sa.lib.SLibUtils;
import sa.lib.xml.SXmlUtils;

/**
 *
 * @author Sergio Abraham Flores Gutiérrez
 */
public abstract class DCfdUtils {

    public static cfd.ver2.DElementComprobante getCfd(String xml) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static cfd.ver3.DElementComprobante getCfdi(String xml) throws Exception {
        double dTotalImptoRetenido = 0;
        double dTotalImptoTrasladado = 0;
        DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = docBuilder.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
        Node node = null;
        Node nodeChild = null;
        Vector<Node> nodeChilds = null;
        Vector<Node> nodeChildsAux = null;
        Vector<Node> nodeChildsAduanera = null;
        Vector<Node> nodeChildsParte = null;
        NamedNodeMap namedNodeMap = null;
        NamedNodeMap namedNodeMapChild = null;
        TreeMap<Integer, String> moOptions = null;
        double versionPayroll = 0;

        cfd.ver3.DElementComprobante comprobante = new cfd.ver3.DElementComprobante();

        // Comprobante:

        node = SXmlUtils.extractElements(doc, "cfdi:Comprobante").item(0);
        namedNodeMap = node.getAttributes();

        comprobante.getAttVersion().setString(SXmlUtils.extractAttributeValue(namedNodeMap, "version", true));
        comprobante.getAttSerie().setString(SXmlUtils.extractAttributeValue(namedNodeMap, "serie", false));
        comprobante.getAttFolio().setString(SXmlUtils.extractAttributeValue(namedNodeMap, "folio", true));
        comprobante.getAttFecha().setDatetime(DUtilUtils.DbmsDateFormatDatetime.parse(SXmlUtils.extractAttributeValue(namedNodeMap, "fecha", true).replaceAll("T", " ")));
        comprobante.getAttSello().setString(SXmlUtils.extractAttributeValue(namedNodeMap, "sello", true));
        comprobante.getAttNoCertificado().setString(SXmlUtils.extractAttributeValue(namedNodeMap, "noCertificado", true));
        comprobante.getAttCertificado().setString(SXmlUtils.extractAttributeValue(namedNodeMap, "certificado", false));

        moOptions = comprobante.getAttFormaDePago().moOptions;

        for (int i = 0; i < moOptions.size(); i++) {
            if (((String) moOptions.values().toArray()[i]).compareTo(SXmlUtils.extractAttributeValue(namedNodeMap, "formaDePago", true)) == 0) {
                comprobante.getAttFormaDePago().setOption((Integer) moOptions.keySet().toArray()[i]);
                break;
            }
        }

        moOptions = comprobante.getAttCondicionesDePago().moOptions;

        for (int i = 0; i < moOptions.size(); i++) {
            if (((String) moOptions.values().toArray()[i]).compareTo(SXmlUtils.extractAttributeValue(namedNodeMap, "condicionesDePago", false)) == 0) {
                comprobante.getAttCondicionesDePago().setOption((Integer) moOptions.keySet().toArray()[i]);
                break;
            }
        }

        comprobante.getAttSubTotal().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMap, "subTotal", true)));
        comprobante.getAttDescuento().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMap, "descuento", false)));
        comprobante.getAttMotivoDescuento().setString(SXmlUtils.extractAttributeValue(namedNodeMap, "motivoDescuento", false));
        comprobante.getAttTipoCambio().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMap, "TipoCambio", false)));
        comprobante.getAttMoneda().setString(SXmlUtils.extractAttributeValue(namedNodeMap, "Moneda", false));
        comprobante.getAttTotal().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMap, "total", true)));

        comprobante.getAttMetodoDePago().setString(SXmlUtils.extractAttributeValue(namedNodeMap, "metodoDePago", false));

        comprobante.getAttLugarExpedicion().setString(SXmlUtils.extractAttributeValue(namedNodeMap, "LugarExpedicion", true));

        moOptions = comprobante.getAttTipoDeComprobante().moOptions;

        for (int i = 0; i < moOptions.size(); i++) {
            if (((String) moOptions.values().toArray()[i]).compareTo(SXmlUtils.extractAttributeValue(namedNodeMap, "tipoDeComprobante", true)) == 0) {
                comprobante.getAttTipoDeComprobante().setOption((Integer) moOptions.keySet().toArray()[i]);
                break;
            }
        }

        comprobante.getAttNumCtaPago().setString(SXmlUtils.extractAttributeValue(namedNodeMap, "NumCtaPago", false));
        comprobante.getAttFolioFiscalOrig().setString(SXmlUtils.extractAttributeValue(namedNodeMap, "FolioFiscalOrig", false));
        comprobante.getAttSerieFolioFiscalOrig().setString(SXmlUtils.extractAttributeValue(namedNodeMap, "SerieFolioFiscalOrig", false));
        comprobante.getAttFechaFolioFiscalOrig().setString(SXmlUtils.extractAttributeValue(namedNodeMap, "FechaFolioFiscalOrig", false));
        comprobante.getAttMontoFolioFiscalOrig().setString(SXmlUtils.extractAttributeValue(namedNodeMap, "MontoFolioFiscalOrig", false));

        // Emisor:

        node = SXmlUtils.extractElements(doc, "cfdi:Emisor").item(0);
        namedNodeMap = node.getAttributes();

        comprobante.getEltEmisor().getAttRfc().setString(SXmlUtils.extractAttributeValue(namedNodeMap, "rfc", true));
        comprobante.getEltEmisor().getAttNombre().setString(SXmlUtils.extractAttributeValue(namedNodeMap, "nombre", true));

        if (SXmlUtils.hasChildElement(node, "cfdi:DomicilioFiscal")) {
            nodeChild = SXmlUtils.extractChildElements(node, "cfdi:DomicilioFiscal").get(0);
            namedNodeMapChild = nodeChild.getAttributes();

            comprobante.getEltEmisor().getEltDomicilioFiscal().getAttCalle().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "calle", true));
            comprobante.getEltEmisor().getEltDomicilioFiscal().getAttNoExterior().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "noExterior", false));
            comprobante.getEltEmisor().getEltDomicilioFiscal().getAttNoInterior().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "noInterior", false));
            comprobante.getEltEmisor().getEltDomicilioFiscal().getAttColonia().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "colonia", false));
            comprobante.getEltEmisor().getEltDomicilioFiscal().getAttLocalidad().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "localidad", false));
            comprobante.getEltEmisor().getEltDomicilioFiscal().getAttReferencia().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "referencia", false));
            comprobante.getEltEmisor().getEltDomicilioFiscal().getAttMunicipio().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "municipio", true));
            comprobante.getEltEmisor().getEltDomicilioFiscal().getAttEstado().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "estado", true));
            comprobante.getEltEmisor().getEltDomicilioFiscal().getAttPais().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "pais", true));
            comprobante.getEltEmisor().getEltDomicilioFiscal().getAttCodigoPostal().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "codigoPostal", true));
        }
        
        if (SXmlUtils.hasChildElement(node, "cfdi:RegimenFiscal")) {
            nodeChild = SXmlUtils.extractChildElements(node, "cfdi:RegimenFiscal").get(0);
            namedNodeMapChild = nodeChild.getAttributes();
        }

        if (SXmlUtils.extractAttributeValue(namedNodeMapChild, "Regimen", false).length() > 0) {
            cfd.ver3.DElementRegimenFiscal regimen = new cfd.ver3.DElementRegimenFiscal();

            regimen.getAttRegimen().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "Regimen", true));

            comprobante.getEltEmisor().getEltHijosRegimenFiscal().add(regimen);
        }

        if (SXmlUtils.hasChildElement(node, "cfdi:ExpedidoEn")) {
            nodeChild = SXmlUtils.extractChildElements(node, "cfdi:ExpedidoEn").get(0);
            namedNodeMapChild = nodeChild.getAttributes();

            cfd.ver3.DElementTipoUbicacion expedidoEn = new cfd.ver3.DElementTipoUbicacion("cfdi:ExpedidoEn");

            expedidoEn.getAttCalle().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "calle", false));
            expedidoEn.getAttNoExterior().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "noExterior", false));
            expedidoEn.getAttNoInterior().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "noInterior", false));
            expedidoEn.getAttColonia().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "colonia", false));
            expedidoEn.getAttLocalidad().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "localidad", false));
            expedidoEn.getAttReferencia().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "referencia", false));
            expedidoEn.getAttMunicipio().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "municipio", false));
            expedidoEn.getAttEstado().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "estado", false));
            expedidoEn.getAttPais().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "pais", true));
            expedidoEn.getAttCodigoPostal().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "codigoPostal", false));

            comprobante.getEltEmisor().setEltOpcExpedidoEn(expedidoEn);
        }

        // Receptor:

        node = SXmlUtils.extractElements(doc, "cfdi:Receptor").item(0);
        namedNodeMap = node.getAttributes();

        comprobante.getEltReceptor().getAttRfc().setString(SXmlUtils.extractAttributeValue(namedNodeMap, "rfc", true));
        comprobante.getEltReceptor().getAttNombre().setString(SXmlUtils.extractAttributeValue(namedNodeMap, "nombre", true));

        if (SXmlUtils.hasChildElement(node, "cfdi:Domicilio")) {
            nodeChild = SXmlUtils.extractChildElements(node, "cfdi:Domicilio").get(0);
            namedNodeMapChild = nodeChild.getAttributes();

            comprobante.getEltReceptor().getEltDomicilio().getAttCalle().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "calle", false));
            comprobante.getEltReceptor().getEltDomicilio().getAttNoExterior().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "noExterior", false));
            comprobante.getEltReceptor().getEltDomicilio().getAttNoInterior().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "noInterior", false));
            comprobante.getEltReceptor().getEltDomicilio().getAttColonia().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "colonia", false));
            comprobante.getEltReceptor().getEltDomicilio().getAttLocalidad().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "localidad", false));
            comprobante.getEltReceptor().getEltDomicilio().getAttReferencia().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "referencia", false));
            comprobante.getEltReceptor().getEltDomicilio().getAttMunicipio().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "municipio", false));
            comprobante.getEltReceptor().getEltDomicilio().getAttEstado().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "estado", false));
            comprobante.getEltReceptor().getEltDomicilio().getAttPais().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "pais", true));
            comprobante.getEltReceptor().getEltDomicilio().getAttCodigoPostal().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "codigoPostal", false));
        }

        // Concepts:

        node = SXmlUtils.extractElements(doc, "cfdi:Conceptos").item(0);
        nodeChilds = SXmlUtils.extractChildElements(node, "cfdi:Concepto");

        for (int i = 0; i < nodeChilds.size(); i++) {
            cfd.ver3.DElementConcepto concepto = new cfd.ver3.DElementConcepto();

            nodeChild = nodeChilds.get(i);
            namedNodeMapChild = nodeChild.getAttributes();

            concepto.getAttCantidad().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "cantidad", true)));
            concepto.getAttUnidad().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "unidad", true));
            concepto.getAttNoIdentificacion().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "noIdentificacion", false));
            concepto.getAttDescripcion().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "descripcion", true));
            concepto.getAttValorUnitario().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "valorUnitario", true)));
            concepto.getAttImporte().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "importe", true)));

            if (SXmlUtils.hasChildElement(node, "cfdi:InformacionAduanera")) {
                nodeChildsAduanera = SXmlUtils.extractChildElements(nodeChild, "cfdi:InformacionAduanera");

                for (int j = 0; j < nodeChildsAduanera.size(); j++) {
                    cfd.ver3.DElementInformacionAduanera aduanera = new cfd.ver3.DElementInformacionAduanera();

                    nodeChild = nodeChildsAduanera.get(j);
                    namedNodeMapChild = nodeChild.getAttributes();

                    aduanera.getAttNumero().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "numero", true));
                    aduanera.getAttFecha().setDate(DUtilUtils.DbmsDateFormatDate.parse(SXmlUtils.extractAttributeValue(namedNodeMapChild, "fecha", true)));
                    aduanera.getAttAduana().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "aduana", false));

                    concepto.getEltHijosInformacionAduanera().add(aduanera);
                }
            }

            nodeChild = nodeChilds.get(i);
            if (SXmlUtils.hasChildElement(node, "cfdi:Parte")) {
                nodeChildsParte = SXmlUtils.extractChildElements(nodeChild, "cfdi:Parte");

                for (int j = 0; j < nodeChildsParte.size(); j++) {
                    cfd.ver3.DElementParte parte = new cfd.ver3.DElementParte();

                    nodeChild = nodeChildsParte.get(j);
                    namedNodeMapChild = nodeChild.getAttributes();

                    parte.getAttCantidad().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "cantidad", true)));
                    parte.getAttUnidad().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "unidad", false));
                    parte.getAttNoIdentificacion().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "noIdentificacion", false));
                    parte.getAttDescripcion().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "descripcion", true));
                    parte.getAttValorUnitario().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "valorUnitario", false)));
                    parte.getAttImporte().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "importe", false)));

                    nodeChildsAduanera = SXmlUtils.extractChildElements(nodeChild, "cfdi:InformacionAduanera");

                    for (int k = 0; k < nodeChildsAduanera.size(); k++) {
                        cfd.ver3.DElementInformacionAduanera aduanera = new cfd.ver3.DElementInformacionAduanera();

                        nodeChild = nodeChildsAduanera.get(k);
                        namedNodeMapChild = nodeChild.getAttributes();

                        aduanera.getAttNumero().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "numero", true));
                        aduanera.getAttFecha().setDate(DUtilUtils.DbmsDateFormatDate.parse(SXmlUtils.extractAttributeValue(namedNodeMapChild, "fecha", true)));
                        aduanera.getAttAduana().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "aduana", false));

                        parte.getEltHijosInformacionAduanera().add(aduanera);
                    }

                    concepto.getEltHijosParte().add(parte);
                }
            }

            nodeChild = nodeChilds.get(i);
            if (SXmlUtils.hasChildElement(nodeChild, "cfdi:CuentaPredial")) {
                cfd.ver3.DElementCuentaPredial predial = new cfd.ver3.DElementCuentaPredial();

                nodeChild = SXmlUtils.extractChildElements(nodeChild, "cfdi:CuentaPredial").get(0);
                namedNodeMapChild = nodeChild.getAttributes();

                predial.getAttNumero().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "numero", true));

                concepto.setEltOpcCuentaPredial(predial);
            }
            comprobante.getEltConceptos().getEltHijosConcepto().add(concepto);
        }

        // Tax:

        node = SXmlUtils.extractElements(doc, "cfdi:Impuestos").item(0);

        cfd.ver3.DElementImpuestosRetenidos retenidos = new cfd.ver3.DElementImpuestosRetenidos();
        cfd.ver3.DElementImpuestosTrasladados trasladados = new cfd.ver3.DElementImpuestosTrasladados();

        if (SXmlUtils.hasChildElement(node, "cfdi:Retenciones")) {
            nodeChild = SXmlUtils.extractChildElements(node, "cfdi:Retenciones").get(0);
            nodeChilds = SXmlUtils.extractChildElements(nodeChild, "cfdi:Retencion");

            for (int i = 0; i < nodeChilds.size(); i++) {
                cfd.ver3.DElementImpuestoRetencion retencion = new cfd.ver3.DElementImpuestoRetencion();

                nodeChild = nodeChilds.get(i);
                namedNodeMapChild = nodeChild.getAttributes();

                moOptions = retencion.getAttImpuesto().moOptions;

                for (int j = 0; j < moOptions.size(); j++) {
                    if (((String) moOptions.values().toArray()[j]).compareTo(SXmlUtils.extractAttributeValue(namedNodeMapChild, "impuesto", true)) == 0) {
                        retencion.getAttImpuesto().setOption((Integer) moOptions.keySet().toArray()[j]);
                    }
                }

                dTotalImptoRetenido += DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "importe", true));
                retencion.getAttImporte().setDouble(dTotalImptoRetenido);

                retenidos.getEltHijosImpuestoRetenido().add(retencion);
            }
        }

        if (SXmlUtils.hasChildElement(node, "cfdi:Traslados")) {
            nodeChild = SXmlUtils.extractChildElements(node, "cfdi:Traslados").get(0);
            nodeChilds = SXmlUtils.extractChildElements(nodeChild, "cfdi:Traslado");

            for (int i = 0; i < nodeChilds.size(); i++) {
                cfd.ver3.DElementImpuestoTraslado traslado = new cfd.ver3.DElementImpuestoTraslado();

                nodeChild = nodeChilds.get(i);
                namedNodeMapChild = nodeChild.getAttributes();

                moOptions = traslado.getAttImpuesto().moOptions;

                for (int j = 0; j < moOptions.size(); j++) {
                    if (((String) moOptions.values().toArray()[j]).compareTo(SXmlUtils.extractAttributeValue(namedNodeMapChild, "impuesto", true)) == 0) {
                        traslado.getAttImpuesto().setOption((Integer) moOptions.keySet().toArray()[j]);
                    }
                }

                traslado.getAttTasa().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "tasa", true)));
                dTotalImptoTrasladado += DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "importe", true));
                traslado.getAttImporte().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "importe", true)));

                trasladados.getEltHijosImpuestoTrasladado().add(traslado);
            }
        }

        if (retenidos.getEltHijosImpuestoRetenido().size() > 0) {
            comprobante.getEltImpuestos().getAttTotalImpuestosRetenidos().setDouble(dTotalImptoRetenido);
            comprobante.getEltImpuestos().setEltOpcImpuestosRetenidos(retenidos);
        }

        if (trasladados.getEltHijosImpuestoTrasladado().size() > 0) {
            comprobante.getEltImpuestos().getAttTotalImpuestosTrasladados().setDouble(dTotalImptoTrasladado);
            comprobante.getEltImpuestos().setEltOpcImpuestosTrasladados(trasladados);
        }

        // Child elements of element 'cfdi:Complemento':

        cfd.ver3.DElementComplemento complemento = new cfd.ver3.DElementComplemento();

        node = SXmlUtils.extractElements(doc, "cfdi:Complemento").item(0);

        if (node != null) {
            // Digital signature:
            
            if (SXmlUtils.hasChildElement(node, "tfd:TimbreFiscalDigital")) {
                cfd.ver3.DElementTimbreFiscalDigital tfd = new cfd.ver3.DElementTimbreFiscalDigital();

                nodeChild = SXmlUtils.extractChildElements(node, "tfd:TimbreFiscalDigital").get(0);
                namedNodeMapChild = nodeChild.getAttributes();

                tfd.getAttVersion().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "version", true));
                tfd.getAttUuid().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "UUID", true));
                tfd.getAttFechaTimbrado().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "FechaTimbrado", true));
                tfd.getAttSelloCfd().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "selloCFD", true));
                tfd.getAttNoCertificadoSAT().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "noCertificadoSAT", true));
                tfd.getAttSelloSAT().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "selloSAT", true));

                complemento.getElements().add(tfd);
            }
            
            // Payroll:
            
            if (SXmlUtils.hasChildElement(node, "nomina:Nomina") || SXmlUtils.hasChildElement(node, "nomina12:Nomina")) {
                if (SXmlUtils.hasChildElement(node, "nomina:Nomina")) {
                    node = SXmlUtils.extractChildElements(node, "nomina:Nomina").get(0);
                    namedNodeMapChild = node.getAttributes();
                }
                else {
                    node = SXmlUtils.extractChildElements(node, "nomina12:Nomina").get(0);
                    namedNodeMapChild = node.getAttributes();
                }
                
                versionPayroll = SLibUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "Version", true));
                
                if (versionPayroll == DCfdVer3Consts.VER_NOM_11) {
                    cfd.ver3.nom11.DElementNomina nomina = new cfd.ver3.nom11.DElementNomina();

                    nomina.getAttVersion().setString(versionPayroll + "");
                    nomina.getAttRegistroPatronal().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "RegistroPatronal", false));
                    nomina.getAttNumEmpleado().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "NumEmpleado", true));
                    nomina.getAttCurp().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "CURP", true));
                    nomina.getAttTipoRegimen().setInteger(DUtilUtils.parseInt(SXmlUtils.extractAttributeValue(namedNodeMapChild, "TipoRegimen", true)));
                    nomina.getAttNumSeguridadSocial().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "NumSeguridadSocial", false));
                    nomina.getAttFechaPago().setDate(DUtilUtils.DbmsDateFormatDate.parse(SXmlUtils.extractAttributeValue(namedNodeMapChild, "FechaPago", true)));
                    nomina.getAttFechaInicialPago().setDate(DUtilUtils.DbmsDateFormatDate.parse(SXmlUtils.extractAttributeValue(namedNodeMapChild, "FechaInicialPago", true)));
                    nomina.getAttFechaFinalPago().setDate(DUtilUtils.DbmsDateFormatDate.parse(SXmlUtils.extractAttributeValue(namedNodeMapChild, "FechaFinalPago", true)));
                    nomina.getAttNumDiasPagados().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "NumDiasPagados", true)));
                    nomina.getAttDepartamento().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "Departamento", false));
                    nomina.getAttClabe().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "CLABE", false));
                    nomina.getAttBanco().setInteger(DUtilUtils.parseInt(SXmlUtils.extractAttributeValue(namedNodeMapChild, "Banco", false)));
                    nomina.getAttFechaInicioRelLaboral().setDate(DUtilUtils.DbmsDateFormatDate.parse(SXmlUtils.extractAttributeValue(namedNodeMapChild, "FechaInicioRelLaboral", false)));
                    nomina.getAttAntiguedad().setInteger(DUtilUtils.parseInt(SXmlUtils.extractAttributeValue(namedNodeMapChild, "Antiguedad", false)));
                    nomina.getAttPuesto().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "Puesto", false));
                    nomina.getAttTipoContrato().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "TipoContrato", false));
                    nomina.getAttTipoJornada().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "TipoJornada", false));
                    nomina.getAttPeriodicidadPago().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "PeriodicidadPago", true));
                    nomina.getAttSalarioBaseCotApor().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "SalarioBaseCotApor", false)));
                    nomina.getAttRiesgoPuesto().setInteger(DUtilUtils.parseInt(SXmlUtils.extractAttributeValue(namedNodeMapChild, "RiesgoPuesto", false)));
                    nomina.getAttSalarioDiarioIntegrado().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "SalarioDiarioIntegrado", false)));

                    // Perceptions:

                    if (SXmlUtils.hasChildElement(node, "nomina:Percepciones")) {
                        cfd.ver3.nom11.DElementPercepciones percepciones = new cfd.ver3.nom11.DElementPercepciones();

                        nodeChild = SXmlUtils.extractChildElements(node, "nomina:Percepciones").get(0);
                        namedNodeMapChild = nodeChild.getAttributes();

                        percepciones.getAttTotalGravado().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "TotalGravado", true)));
                        percepciones.getAttTotalExento().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "TotalExento", true)));

                        nodeChilds = SXmlUtils.extractChildElements(nodeChild, "nomina:Percepcion");

                        for (int i = 0; i < nodeChilds.size(); i++) {
                            cfd.ver3.nom11.DElementPercepcion percepcion = new cfd.ver3.nom11.DElementPercepcion();

                            nodeChild = nodeChilds.get(i);
                            namedNodeMapChild = nodeChild.getAttributes();

                            percepcion.getAttTipoPercepcion().setInteger(DUtilUtils.parseInt(SXmlUtils.extractAttributeValue(namedNodeMapChild, "TipoPercepcion", false)));
                            percepcion.getAttClave().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "Clave", true));
                            percepcion.getAttConcepto().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "Concepto", true));
                            percepcion.getAttImporteGravado().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "ImporteGravado", true)));
                            percepcion.getAttImporteExento().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "ImporteExento", true)));

                            percepciones.getEltHijosPercepcion().add(percepcion);
                        }
                        nomina.setEltPercepciones(percepciones);
                    }

                    // Deductions:

                    if (SXmlUtils.hasChildElement(node, "nomina:Deducciones")) {
                        cfd.ver3.nom11.DElementDeducciones deducciones = new cfd.ver3.nom11.DElementDeducciones();

                        nodeChild = SXmlUtils.extractChildElements(node, "nomina:Deducciones").get(0);
                        namedNodeMapChild = nodeChild.getAttributes();

                        deducciones.getAttTotalGravado().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "TotalGravado", true)));
                        deducciones.getAttTotalExento().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "TotalExento", true)));

                        nodeChilds = SXmlUtils.extractChildElements(nodeChild, "nomina:Deduccion");

                        for (int i = 0; i < nodeChilds.size(); i++) {
                            cfd.ver3.nom11.DElementDeduccion deduccion = new cfd.ver3.nom11.DElementDeduccion();

                            nodeChild = nodeChilds.get(i);
                            namedNodeMapChild = nodeChild.getAttributes();

                            deduccion.getAttTipoDeduccion().setInteger(DUtilUtils.parseInt(SXmlUtils.extractAttributeValue(namedNodeMapChild, "TipoDeduccion", false)));
                            deduccion.getAttClave().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "Clave", true));
                            deduccion.getAttConcepto().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "Concepto", true));
                            deduccion.getAttImporteGravado().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "ImporteGravado", true)));
                            deduccion.getAttImporteExento().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "ImporteExento", true)));

                            deducciones.getEltHijosDeduccion().add(deduccion);
                        }
                        nomina.setEltDeducciones(deducciones);
                    }

                    // Incapacities:

                    if (SXmlUtils.hasChildElement(node, "nomina:Incapacidades")) {
                        cfd.ver3.nom11.DElementIncapacidades incapacidades = new cfd.ver3.nom11.DElementIncapacidades();

                        nodeChild = SXmlUtils.extractChildElements(node, "nomina:Incapacidades").get(0);
                        nodeChilds = SXmlUtils.extractChildElements(nodeChild, "nomina:Incapacidad");

                        for (int i = 0; i < nodeChilds.size(); i++) {
                            cfd.ver3.nom11.DElementIncapacidad incapacidad = new cfd.ver3.nom11.DElementIncapacidad();

                            nodeChild = nodeChilds.get(i);
                            namedNodeMapChild = nodeChild.getAttributes();

                            incapacidad.getAttDiasIncapacidad().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "DiasIncapacidad", true)));
                            incapacidad.getAttTipoIncapacidad().setInteger(DUtilUtils.parseInt(SXmlUtils.extractAttributeValue(namedNodeMapChild, "TipoIncapacidad", false)));
                            incapacidad.getAttDescuento().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "Descuento", true)));

                            incapacidades.getEltHijosIncapacidad().add(incapacidad);
                        }
                        nomina.setEltIncapacidades(incapacidades);
                    }

                    // ExtraTimes:

                    if (SXmlUtils.hasChildElement(node, "nomina:HorasExtras")) {
                        cfd.ver3.nom11.DElementHorasExtras horasExtras = new cfd.ver3.nom11.DElementHorasExtras();

                        nodeChild = SXmlUtils.extractChildElements(node, "nomina:HorasExtras").get(0);
                        nodeChilds = SXmlUtils.extractChildElements(nodeChild, "nomina:HorasExtra");

                        for (int i = 0; i < nodeChilds.size(); i++) {
                            cfd.ver3.nom11.DElementHorasExtra horasExtra = new cfd.ver3.nom11.DElementHorasExtra();

                            nodeChild = nodeChilds.get(i);
                            namedNodeMapChild = nodeChild.getAttributes();

                            horasExtra.getAttDias().setInteger(DUtilUtils.parseInt(SXmlUtils.extractAttributeValue(namedNodeMapChild, "Dias", true)));
                            horasExtra.getAttTipoHoras().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "TipoHoras", true));
                            horasExtra.getAttHorasExtra().setInteger(DUtilUtils.parseInt(SXmlUtils.extractAttributeValue(namedNodeMapChild, "HorasExtra", false)));
                            horasExtra.getAttImportePagado().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "ImportePagado", true)));

                            horasExtras.getEltHijosHorasExtra().add(horasExtra);
                        }
                        nomina.setEltHorasExtras(horasExtras);
                    }
                    complemento.getElements().add(nomina);
                }
                else if (versionPayroll == DCfdVer3Consts.VER_NOM_12) {
                    cfd.ver3.nom12.DElementNomina nomina = new cfd.ver3.nom12.DElementNomina();

                    nomina.getAttVersion().setString(versionPayroll + "");
                    nomina.getAttFechaPago().setDate(DUtilUtils.DbmsDateFormatDate.parse(SXmlUtils.extractAttributeValue(namedNodeMapChild, "FechaPago", true)));
                    nomina.getAttFechaInicialPago().setDate(DUtilUtils.DbmsDateFormatDate.parse(SXmlUtils.extractAttributeValue(namedNodeMapChild, "FechaInicialPago", true)));
                    nomina.getAttFechaFinalPago().setDate(DUtilUtils.DbmsDateFormatDate.parse(SXmlUtils.extractAttributeValue(namedNodeMapChild, "FechaFinalPago", true)));
                    nomina.getAttNumDiasPagados().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "NumDiasPagados", true)));

                    // Emisor:
                    
                    if (SXmlUtils.hasChildElement(node, "nomina12:Emisor")) {
                        cfd.ver3.nom12.DElementEmisor emisor = new cfd.ver3.nom12.DElementEmisor();
                        
                        nodeChild = SXmlUtils.extractChildElements(node, "nomina12:Emisor").get(0);
                        namedNodeMapChild = nodeChild.getAttributes();
                        
                        emisor.getAttRegistroPatronal().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "RegistroPatronal", false));
                        
                        nomina.setEltEmisor(emisor);
                    }
                    
                    // Receptor:
                    
                    if (SXmlUtils.hasChildElement(node, "nomina12:Receptor")) {
                        cfd.ver3.nom12.DElementReceptor receptor = new cfd.ver3.nom12.DElementReceptor();
                        
                        nodeChild = SXmlUtils.extractChildElements(node, "nomina12:Receptor").get(0);
                        namedNodeMapChild = nodeChild.getAttributes();
                        
                        receptor.getAttCurp().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "Curp", true));
                        receptor.getAttNumSeguridadSocial().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "NumSeguridadSocial", false));
                        receptor.getAttFechaInicioRelLaboral().setDate(DUtilUtils.DbmsDateFormatDate.parse(SXmlUtils.extractAttributeValue(namedNodeMapChild, "FechaInicioRelLaboral", false)));
                        receptor.getAttAntiguedad().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "Antigüedad", true));
                        receptor.getAttTipoContrato().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "TipoContrato", true));
                        receptor.getAttSindicalizado().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "Sindicalizado", false));
                        receptor.getAttTipoJornada().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "TipoJornada", false));
                        receptor.getAttTipoRegimen().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "TipoRegimen", true));
                        receptor.getAttNumEmpleado().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "NumEmpleado", true));
                        receptor.getAttDepartamento().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "Departamento", false));
                        receptor.getAttPuesto().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "Puesto", false));
                        receptor.getAttRiesgoPuesto().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "RiesgoPuesto", false));
                        receptor.getAttPeriodicidadPago().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "PeriodicidadPago", true));
                        receptor.getAttBanco().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "Banco", false));
                        receptor.getAttCuentaBancaria().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "CuentaBancaria", false));
                        receptor.getAttSalarioBaseCotApor().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "SalarioBaseCotApor", false)));
                        receptor.getAttSalarioDiarioIntegrado().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "SalarioDiarioIntegrado", false)));
                        receptor.getAttClaveEntFed().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "ClaveEntFed", true));
                        
                        nomina.setEltReceptor(receptor);
                    }
                    
                    // Perceptions:

                    if (SXmlUtils.hasChildElement(node, "nomina12:Percepciones")) {
                        cfd.ver3.nom12.DElementPercepciones percepciones = new cfd.ver3.nom12.DElementPercepciones();

                        nodeChild = SXmlUtils.extractChildElements(node, "nomina12:Percepciones").get(0);
                        namedNodeMapChild = nodeChild.getAttributes();

                        percepciones.getAttTotalGravado().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "TotalGravado", false)));
                        percepciones.getAttTotalExento().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "TotalExento", false)));

                        nodeChilds = SXmlUtils.extractChildElements(nodeChild, "nomina12:Percepcion");

                        for (int i = 0; i < nodeChilds.size(); i++) {
                            cfd.ver3.nom12.DElementPercepcion percepcion = new cfd.ver3.nom12.DElementPercepcion();

                            nodeChild = nodeChilds.get(i);
                            namedNodeMapChild = nodeChild.getAttributes();

                            percepcion.getAttTipoPercepcion().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "TipoPercepcion", true));
                            percepcion.getAttClave().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "Clave", true));
                            percepcion.getAttConcepto().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "Concepto", true));
                            percepcion.getAttImporteGravado().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "ImporteGravado", true)));
                            percepcion.getAttImporteExento().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "ImporteExento", true)));
                            
                            // ExtraTimes:
                            
                            if (SXmlUtils.hasChildElement(nodeChild, "nomina12:HorasExtra")) {
                                nodeChildsAux = SXmlUtils.extractChildElements(nodeChild, "nomina12:HorasExtra");

                                for (int overTime = 0; overTime < nodeChildsAux.size(); overTime++) {
                                    cfd.ver3.nom12.DElementHorasExtra horasExtra = new cfd.ver3.nom12.DElementHorasExtra();

                                    nodeChild = nodeChildsAux.get(overTime);
                                    namedNodeMapChild = nodeChild.getAttributes();

                                    horasExtra.getAttDias().setInteger(DUtilUtils.parseInt(SXmlUtils.extractAttributeValue(namedNodeMapChild, "Dias", true)));
                                    horasExtra.getAttTipoHoras().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "TipoHoras", true));
                                    horasExtra.getAttHorasExtra().setInteger(DUtilUtils.parseInt(SXmlUtils.extractAttributeValue(namedNodeMapChild, "HorasExtra", true)));
                                    horasExtra.getAttImportePagado().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "ImportePagado", true)));

                                    percepcion.getEltHijosHorasExtra().add(horasExtra);
                                }
                            }
                            percepciones.getEltHijosPercepcion().add(percepcion);
                            
                        }
                        nomina.setEltPercepciones(percepciones);
                    }
                    
                    // Other payments:

                    if (SXmlUtils.hasChildElement(node, "nomina12:OtrosPagos")) {
                        cfd.ver3.nom12.DElementOtrosPagos otrosPagos = new cfd.ver3.nom12.DElementOtrosPagos();

                        nodeChild = SXmlUtils.extractChildElements(node, "nomina12:OtrosPagos").get(0);
                        
                        nodeChilds = SXmlUtils.extractChildElements(nodeChild, "nomina12:OtroPago");

                        for (int i = 0; i < nodeChilds.size(); i++) {
                            cfd.ver3.nom12.DElementOtroPago otroPago = new cfd.ver3.nom12.DElementOtroPago();

                            nodeChild = nodeChilds.get(i);
                            namedNodeMapChild = nodeChild.getAttributes();

                            otroPago.getAttTipoOtroPago().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "TipoOtroPago", true));
                            otroPago.getAttClave().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "Clave", true));
                            otroPago.getAttConcepto().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "Concepto", true));
                            otroPago.getAttImporte().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "Importe", true)));
                            
                            nodeChildsAux = SXmlUtils.extractChildElements(nodeChild, "nomina12:SubsidioAlEmpleo");

                            for (int sub = 0; sub < nodeChildsAux.size(); sub++) {
                                cfd.ver3.nom12.DElementSubsidioEmpleo subsidio = new cfd.ver3.nom12.DElementSubsidioEmpleo();
                                
                                nodeChild = nodeChildsAux.get(sub);
                                namedNodeMapChild = nodeChild.getAttributes();

                                subsidio.getAttSubsidioCausado().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "SubsidioCausado", true)));
                                
                                otroPago.setEltSubsidioEmpleo(subsidio);
                            }
                            
                            otrosPagos.getEltHijosOtroPago().add(otroPago);
                        }
                        nomina.setEltOtrosPagos(otrosPagos);
                    }

                    // Deductions:

                    if (SXmlUtils.hasChildElement(node, "nomina12:Deducciones")) {
                        cfd.ver3.nom12.DElementDeducciones deducciones = new cfd.ver3.nom12.DElementDeducciones();

                        nodeChild = SXmlUtils.extractChildElements(node, "nomina12:Deducciones").get(0);
                        namedNodeMapChild = nodeChild.getAttributes();

                        deducciones.getAttTotalImpuestosRetenidos().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "TotalImpuestosRetenidos", false)));
                        deducciones.getAttTotalOtrasDeducciones().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "TotalOtrasDeducciones", false)));

                        nodeChilds = SXmlUtils.extractChildElements(nodeChild, "nomina12:Deduccion");

                        for (int i = 0; i < nodeChilds.size(); i++) {
                            cfd.ver3.nom12.DElementDeduccion deduccion = new cfd.ver3.nom12.DElementDeduccion();

                            nodeChild = nodeChilds.get(i);
                            namedNodeMapChild = nodeChild.getAttributes();

                            deduccion.getAttTipoDeduccion().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "TipoDeduccion", true));
                            deduccion.getAttClave().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "Clave", true));
                            deduccion.getAttConcepto().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "Concepto", true));
                            deduccion.getAttImporte().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "Importe", true)));

                            deducciones.getEltHijosDeduccion().add(deduccion);
                        }
                        nomina.setEltDeducciones(deducciones);
                    }

                    // Incapacities:

                    if (SXmlUtils.hasChildElement(node, "nomina12:Incapacidades")) {
                        cfd.ver3.nom12.DElementIncapacidades incapacidades = new cfd.ver3.nom12.DElementIncapacidades();

                        nodeChild = SXmlUtils.extractChildElements(node, "nomina12:Incapacidades").get(0);
                        nodeChilds = SXmlUtils.extractChildElements(nodeChild, "nomina12:Incapacidad");

                        for (int i = 0; i < nodeChilds.size(); i++) {
                            cfd.ver3.nom12.DElementIncapacidad incapacidad = new cfd.ver3.nom12.DElementIncapacidad();

                            nodeChild = nodeChilds.get(i);
                            namedNodeMapChild = nodeChild.getAttributes();

                            incapacidad.getAttDiasIncapacidad().setInteger(DUtilUtils.parseInt(SXmlUtils.extractAttributeValue(namedNodeMapChild, "DiasIncapacidad", true)));
                            incapacidad.getAttTipoIncapacidad().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "TipoIncapacidad", true));
                            incapacidad.getAttImporteMonetario().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "ImporteMonetario", true)));

                            incapacidades.getEltHijosIncapacidad().add(incapacidad);
                        }
                        nomina.setEltIncapacidades(incapacidades);
                    }
                    complemento.getElements().add(nomina);
                }
            }
        }

        if (complemento.getElements().size() > 0) {
            comprobante.setEltOpcComplemento(complemento);
        }

        // Addenda:
/*
        node = SXmlUtils.extractElements(doc, "cfdi:Comprobante").item(0);

        if (SXmlUtils.hasChildElement(node, "cfdi:Addenda")) {
            node = SXmlUtils.extractElements(doc, "cfdi:Addenda").item(0);
*/
        node = SXmlUtils.extractElements(doc, "cfdi:Addenda").item(0);

        if (node != null) {
            if (SXmlUtils.hasChildElement(node, "myadd:Addenda1")) {
                node = SXmlUtils.extractChildElements(node, "myadd:Addenda1").get(0);

                cfd.ver3.DElementAddenda addenda = new cfd.ver3.DElementAddenda();
                cfd.ext.addenda1.DElementAddenda1 addenda1 = new cfd.ext.addenda1.DElementAddenda1();

                nodeChild = SXmlUtils.extractChildElements(node, "myadd:Moneda").get(0);
                namedNodeMapChild = nodeChild.getAttributes();

                moOptions = addenda1.getEltMoneda().getAttClaveMoneda().moOptions;

                for (int j = 0; j < moOptions.size(); j++) {
                    if (((String) moOptions.values().toArray()[j]).compareTo(SXmlUtils.extractAttributeValue(namedNodeMapChild, "claveMoneda", true)) == 0) {
                        addenda1.getEltMoneda().getAttClaveMoneda().setOption((Integer) moOptions.keySet().toArray()[j]);
                    }
                }

                addenda1.getEltMoneda().getAttTipoDeCambio().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "tipoDeCambio", true)));

                nodeChild = SXmlUtils.extractChildElements(node, "myadd:Adicional").get(0);
                namedNodeMapChild = nodeChild.getAttributes();

                addenda1.getEltAdicional().getAttDiasDeCredito().setInteger(DUtilUtils.parseInt(SXmlUtils.extractAttributeValue(namedNodeMapChild, "diasDeCredito", false)));
                addenda1.getEltAdicional().getAttEmbarque().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "embarque", false));
                addenda1.getEltAdicional().getAttOrdenDeEmbarque().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "ordenDeEmbarque", false));
                addenda1.getEltAdicional().getAttOrdenDeCompra().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "ordenDeCompra", false));
                addenda1.getEltAdicional().getAttContrato().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "contrato", false));
                addenda1.getEltAdicional().getAttPedido().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "pedido", false));
                addenda1.getEltAdicional().getAttFactura().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "factura", false));
                addenda1.getEltAdicional().getAttCliente().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "cliente", false));
                addenda1.getEltAdicional().getAttSucursal().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "sucursal", false));
                addenda1.getEltAdicional().getAttAgente().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "agente", false));
                addenda1.getEltAdicional().getAttRuta().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "ruta", false));
                addenda1.getEltAdicional().getAttChofer().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "chofer", false));
                addenda1.getEltAdicional().getAttPlacas().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "placas", false));
                addenda1.getEltAdicional().getAttBoleto().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "boleto", false));
                addenda1.getEltAdicional().getAttPesoBruto().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "pesoBruto", false)));
                addenda1.getEltAdicional().getAttPesoNeto().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "pesoNeto", false)));
                addenda1.getEltAdicional().getAttUnidadPesoBruto().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "unidadPesoBruto", false));
                addenda1.getEltAdicional().getAttUnidadPesoNeto().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "unidadPesoNeto", false));

                if (SXmlUtils.hasChildElement(node, "myadd:Pagare")) {
                    cfd.ext.addenda1.DElementPagare pagare = new cfd.ext.addenda1.DElementPagare();

                    nodeChild = SXmlUtils.extractChildElements(node, "myadd:Pagare").get(0);
                    namedNodeMapChild = nodeChild.getAttributes();

                    pagare.getAttFecha().setDate(DUtilUtils.DbmsDateFormatDate.parse(SXmlUtils.extractAttributeValue(namedNodeMapChild, "fecha", true)));
                    pagare.getAttFechaDeVencimiento().setDate(DUtilUtils.DbmsDateFormatDate.parse(SXmlUtils.extractAttributeValue(namedNodeMapChild, "fechaDeVencimiento", true)));
                    pagare.getAttImporte().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "importe", true)));
                    pagare.getAttClaveMoneda().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "claveMoneda", false));
                    pagare.getAttInteresMoratorio().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "interesMoratorio", true)));

                    addenda1.setEltOpcPagare(pagare);
                }

                if (SXmlUtils.hasChildElement(node, "myadd:AdicionalConceptos")) {
                    nodeChild = SXmlUtils.extractChildElements(node, "myadd:AdicionalConceptos").get(0);
                    nodeChilds = SXmlUtils.extractChildElements(nodeChild, "myadd:AdicionalConcepto");

                    for (int i = 0; i < nodeChilds.size(); i++) {
                        cfd.ext.addenda1.DElementAdicionalConcepto concepto = new cfd.ext.addenda1.DElementAdicionalConcepto();

                        nodeChild = nodeChilds.get(i);
                        namedNodeMapChild = nodeChild.getAttributes();

                        concepto.getAttClaveConcepto().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "claveConcepto", true));
                        concepto.getAttPresentacion().setString(SXmlUtils.extractAttributeValue(namedNodeMapChild, "presentacion", false));
                        concepto.getAttDescuentoUnitario().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "descuentoUnitario", false)));
                        concepto.getAttDescuento().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "descuento", false)));
                        concepto.getAttPesoBruto().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "pesoBruto", false)));
                        concepto.getAttPesoNeto().setDouble(DUtilUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMapChild, "pesoNeto", false)));

                        addenda1.getEltAdicional().getEltAdicionalConceptos().getEltHijosAdicionalConcepto().add(concepto);
                    }
                }

                addenda.getElements().add(addenda1);

                comprobante.setEltOpcAddenda(addenda, DElementAddenda1.createXmlLocationNs());
            }
        }

        return comprobante;
    }
    
    public static double getVersionPayrollComplement(String xml) {
        DocumentBuilder docBuilder = null;
        Document doc = null;
        Node node = null;
        double version = 0;
        NamedNodeMap namedNodeMap = null;

        try {
            docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            doc = docBuilder.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
            
            node = SXmlUtils.extractElements(doc, "cfdi:Complemento").item(0);

            if (node != null) {
                if (SXmlUtils.hasChildElement(node, "nomina:Nomina")) {
                    node = SXmlUtils.extractChildElements(node, "nomina:Nomina").get(0);
                    namedNodeMap = node.getAttributes();
                }
                else {
                    node = SXmlUtils.extractChildElements(node, "nomina12:Nomina").get(0);
                    namedNodeMap = node.getAttributes();
                }

                version = SLibUtils.parseDouble(SXmlUtils.extractAttributeValue(namedNodeMap, "Version", true));
            }
        }
        catch (Exception e) {
            SLibUtils.printException(DCfdUtils.class, e);
        }
        
        return version;
    }
    
    /**
     * Gets attribute "metodo de pago" in format: clave
     * @param metodoPago Desired "metodo de pago".
     */
    public static String getMetodoPagoClave(final String metodoPago) {
        String clave = "";
        DAttributeOptionMetodoPago attMetodoPaqo = new DAttributeOptionMetodoPago("", false);
        DAttributeOptionMetodoPagoClave attMetodoPaqoClave = new DAttributeOptionMetodoPagoClave("", false);
        
        if (!metodoPago.isEmpty()) {
            for (Integer key : attMetodoPaqo.getOptions().keySet()) {
                if (((String) attMetodoPaqo.getOptions().get(key)).compareTo(metodoPago) == 0) {
                    clave = (String) attMetodoPaqoClave.getOptions().get(key);
                    break;
                }
            }
        }
        
        return clave;
    }
    
    /**
     * Composes attribute "metodo de pago" in format: clave - nombre
     * @param fiscalCode Fiscal code of desired "metodo de pago".
     */
    public static String composeMetodoPago(final String fiscalCode) {
        String metodoPago = "";
        DAttributeOptionMetodoPago attMetodoPaqo = new DAttributeOptionMetodoPago("", false);
        DAttributeOptionMetodoPagoClave attMetodoPaqoClave = new DAttributeOptionMetodoPagoClave("", false);
        
        if (!fiscalCode.isEmpty()) {
            for (Integer key : attMetodoPaqoClave.getOptions().keySet()) {
                if (((String) attMetodoPaqoClave.getOptions().get(key)).compareTo(fiscalCode) == 0) {
                    metodoPago = fiscalCode + " - " + (String) attMetodoPaqo.getOptions().get(key);
                    break;
                }
            }
        }
        
        return metodoPago;
    }
}
