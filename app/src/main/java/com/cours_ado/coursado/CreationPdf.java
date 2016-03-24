package com.cours_ado.coursado;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by laura on 23/03/2016.
 */
public class CreationPdf {
    public Boolean write(String fname, String fcontent) {
        try {

            File pdfFolder = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), fname);
            if (!pdfFolder.exists()) {
                pdfFolder.mkdir();
            }

            //Create time stamp
            Date date = new Date() ;
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);

            File myFile = new File(pdfFolder + timeStamp + ".pdf");
            /*String fpath = "/sdcard/" + fname + ".pdf";
            File file = new File(fpath);

            if (!file.exists()) {
                file.createNewFile();
            }*/

            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);


            Document document = new Document();

            PdfWriter.getInstance(document,
                    new FileOutputStream(myFile.getAbsoluteFile()));
            document.open();

            document.add(new Paragraph("Sigueme en Twitter!"));

            document.add(new Paragraph("@DavidHackro"));
            document.close();


            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }



}
