package com.example.invitaciones.services;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;


public class FacturaPDFExport {

    private String nombre;
    private String descripcion;

    public FacturaPDFExport(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

   /* private void writeHeaderFactura(PdfPTable table) {

        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.black);
        cell.setPadding(4);
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setColor(Color.white);
        cell.setPhrase(new Phrase("Nombre de campaña",font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Descripcion de campaña",font));
        table.addCell(cell);


    }
    private void writeTableFactura(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        table.addCell(nombre);
        table.addCell(String.valueOf(descripcion));

    }*/

    public void usePDFExport(HttpServletResponse response) throws DocumentException, IOException {
        Document doc= new Document(PageSize.A4);
        PdfWriter.getInstance(doc, response.getOutputStream());
        doc.open();
        //doc.addCreationDate();
        //doc.addTitle(String.valueOf(new Paragraph("LollaPalooza")));
        //doc.addHeader(factura.getClient().getFirstName() + factura.getClient().getLastName(), String.valueOf(factura.getId()));

        //FUENTES
        Font font = FontFactory.getFont(FontFactory.COURIER);
        //font.setColor(Color.BLACK);
        Font fontTit = FontFactory.getFont(FontFactory.COURIER);
        fontTit.setSize(25);
        fontTit.setStyle(Font.BOLD);
        font.setStyle(Font.BOLD);
        font.setColor(Color.BLACK);

        //PARRAFOS
        //TAMAÑOS CELDAS
        /*PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.0f, 2.0f});
        table.setSpacingBefore(10);*/

        //CREACION TABLA PRODUCTOS Y CABECERA
        /*writeHeaderFactura(table);
        writeTableFactura(table);
        doc.add(table);*/

        Paragraph tittle = new Paragraph("Cotización de Campaña",font);
        tittle.setSpacingBefore(15);
        Paragraph p = new Paragraph("Bienvenido! a sido seleccionado para participar de nuestro sistema de seleccion, a continuacion le " +
                "brindaremos detalles de la campaña. ");
        Paragraph pvacio = new Paragraph("  ",font);
        Paragraph p1 = new Paragraph("Campaña:"+" "+nombre,font);
        Paragraph p2 = new Paragraph("Descripcion:"+" "+descripcion,font);
        Paragraph p3 = new Paragraph("Fecha:"+" "+ LocalDate.now());

        tittle.setAlignment(Paragraph.ALIGN_CENTER);
        pvacio.setAlignment(Paragraph.ALIGN_LEFT);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        p1.setAlignment(Paragraph.ALIGN_LEFT);
        p2.setAlignment(Paragraph.ALIGN_LEFT);
        p3.setAlignment(Paragraph.ALIGN_RIGHT);
        doc.add(tittle);


        doc.add(pvacio);
        doc.add(pvacio);
        doc.add(p);
        doc.add(pvacio);
        doc.add(pvacio);
        doc.add(p1);
        doc.add(p2);
        doc.add(pvacio);
        doc.add(pvacio);
        doc.add(p3);

        //CIERRE PDF
        doc.close();
    }
}
