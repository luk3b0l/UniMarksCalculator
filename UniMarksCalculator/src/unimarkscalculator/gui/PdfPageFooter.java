package unimarkscalculator.gui;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Lukasz Bol
 */
class PdfPageFooter extends PdfPageEventHelper
{
    Font footerFont = new Font(Font.FontFamily.UNDEFINED, 5, Font.ITALIC);
    
    public void onEndPage(PdfWriter writer, Document document)
    {
        String currentDate = getCurrentDate();
        PdfContentByte contentByte = writer.getDirectContent();
        Phrase pageFooter = new Phrase("Date created: " + currentDate, footerFont);
        ColumnText.showTextAligned(contentByte, Element.ALIGN_CENTER, pageFooter, (document.right() - document.left()) / 2 + document.leftMargin(), document.bottom() - 10, 0);
    }
    
    private String getCurrentDate()
    {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date currentDate = new Date();
        String currentDateToString = dateFormat.format(currentDate);
        return currentDateToString;
    }
}