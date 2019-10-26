/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brucelibrary.document;

import java.io.FileOutputStream;
import java.io.OutputStream;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author bruce
 */
public class Excel {

    private String resultMessage;//存放這個類別內各功能執行的結果

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public void ExportExcel(TableView tableview, String outputFilename, String sheetName) throws Exception {
        if (tableview == null || tableview.getItems().size() == 0) {
            return;
        }
        HSSFWorkbook resultExcel = new HSSFWorkbook();
        HSSFSheet resultSheet = resultExcel.createSheet(sheetName);
        resultSheet.createFreezePane(0, 1);
        HSSFRow row = resultSheet.createRow(0);
        int rowcount = 0;

        for (int i = 0; i < tableview.getColumns().size(); i++) {
            row.createCell(i).setCellValue(((TableColumn) tableview.getColumns().get(i)).getText().trim());
        }
        for (int i = 0; i < tableview.getItems().size(); i++) {
            if (rowcount > 60000) {
                resultSheet = resultExcel.createSheet(sheetName + " " + i);
                resultSheet.createFreezePane(0, 1);
                rowcount = 0;
            }
            row = resultSheet.createRow(rowcount + 1);
            rowcount++;
            for (int j = 0; j < tableview.getColumns().size(); j++) {
                if (((TableColumn) tableview.getColumns().get(j)).getCellData(i) != null) {
                    row.createCell(j).setCellValue(((TableColumn) tableview.getColumns().get(j)).getCellData(i).toString());
                } else {
                    row.createCell(j).setCellValue("");
                }
            }
        }

        FileOutputStream output = new FileOutputStream(outputFilename);
        resultExcel.write(output);
        output.close();
        resultExcel.close();
    }
}
