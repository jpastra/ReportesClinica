/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package events;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javax.swing.JTable;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.DateFormat;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author Viviana
 */
public class Exporter {
    private File file;
    private List<JTable> tables;
    private List<String> nom_files;
    
    //constructor
    public Exporter(File file, List<JTable> table, List<String> nom_files) throws Exception
    {
        this.file = file;
        this.tables = table;
        this.nom_files = nom_files;
        if(nom_files.size() != table.size())
        {
            throw new Exception("Error");
        }
    }
    
    //Metodo
    public boolean export() throws FileNotFoundException, WriteException, IOException
    {
        try{
            DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
            WritableWorkbook wb = Workbook.createWorkbook(out);
            for(int index = 0; index < tables.size(); index++)
            {
                JTable table = tables.get(index);
                WritableSheet ws = wb.createSheet(nom_files.get(index), 0);
                
                //Formato de una hoja en Excel
                //Esta es para el nombre de las columnas
                WritableFont wfobj = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
                WritableCellFormat cfobj = new WritableCellFormat(wfobj);
                cfobj.setBorder(Border.ALL, BorderLineStyle.THIN);
                cfobj.setAlignment(Alignment.CENTRE);
                cfobj.setBackground(Colour.ICE_BLUE);
                
                //Agregar los titulos
                for(int c=0; c<table.getColumnCount(); c++)
                {
                   Object titleC = table.getColumnName(c);
                   ws.addCell(new Label(c,0,String.valueOf(titleC),cfobj));
                }
                
                //Formato para el resto de las columnas y renglones
                WritableFont wfobjT = new WritableFont(WritableFont.ARIAL, 10);
                WritableCellFormat cfobjT = new WritableCellFormat(wfobjT);
                cfobjT.setBorder(Border.ALL, BorderLineStyle.THIN);
                
                //Formato Fecha
                DateFormat customDateF = new DateFormat("MM DD YYYY");
                WritableCellFormat dateformat = new WritableCellFormat(customDateF);
                
                for(int i=0; i < table.getColumnCount(); i++)
                {
                    for(int j=0; j < table.getRowCount(); j++)
                    {
                        Object object = table.getValueAt(j, i);
                        ws.addCell(new Label(i,j+1,String.valueOf(object),cfobjT));
                    }
                }
                             
            }
            wb.write();
            wb.close();
            out.close();
            return true;
        }
        catch(IOException | WriteException e)
        {
           return false;    
        }
    }
    
}
