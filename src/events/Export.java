/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package events;

import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import javax.swing.JTable;

/**
 *
 * @author Viviana
 */
public class Export {
    private File file;
    private List<JTable> tables;
    private List<String> nom_files;

    public Export(File file, List<JTable> table, List<String> nom_files) throws Exception {
        this.file = file;
        this.tables = table;
        this.nom_files = nom_files;
        if(nom_files.size() != table.size())
        {
            throw new Exception("Error");
        }

    }
    
    public boolean export(){
        try{
            Document outD = new Document(PageSize.A4.rotate());
            FileOutputStream pdfFile = new FileOutputStream(file);
            PdfWriter.getInstance(outD, pdfFile);
            outD.open();
            
            
            //Font font10pt = new Font(FontFamily.TIMES_ROMAN,10);
                      
            for(int index=0; index < tables.size(); index++){
                JTable table = tables.get(index);
                outD.setMargins(0, 0, 20, 10);
                outD.addTitle("Reporte Inhalos");
                
                PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
                

                //outD.add(new Paragraph("Reporte Job Pick List"));
                
               //Obtenemos el nombre de las columnas
                for(int c=0; c <= table.getColumnCount()-1; c++){
                   pdfTable.addCell(new Phrase(table.getColumnName(c),FontFactory.getFont(FontFactory.HELVETICA, 6)));                  
                }
                
                //extracting data form the JTable and inserting it to PdfPTable
                for(int rows=0; rows < table.getRowCount()-1; rows++){
                    for(int cols=0; cols<table.getColumnCount(); cols++){
                        pdfTable.addCell(new Phrase(table.getModel().getValueAt(rows, cols).toString(),FontFactory.getFont(FontFactory.HELVETICA, 6)));
                    }
                }
                
                outD.add(pdfTable);
                outD.newPage();
                outD.close();
                //JOptionPane.showMessageDialog(null, "Done");
                return true;
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }     
}
