package abl.frd.qremit.converter.helper;

import abl.frd.qremit.converter.model.BeftnModel;
import abl.frd.qremit.converter.service.CommonService;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
@Component
public class BeftnModelServiceHelper {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    @Value("${govt.incentive.percentage}")
    private float govtIncentivePercentage;
    private static float govtIncentivePercentageStatic;
    @Value("${agrani.incentive.percentage}")
    private float agraniIncentivePercentage;
    private static float agraniIncentivePercentageStatic;

    @PostConstruct
    public void init() {
        govtIncentivePercentageStatic = govtIncentivePercentage;
        agraniIncentivePercentageStatic = agraniIncentivePercentage;
    }


    public static ByteArrayInputStream BeftnMainModelsToExcel(List<BeftnModel> beftnModelList) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Beftn");
        Iterator<BeftnModel> iterator = beftnModelList.iterator();
        int rowIndex = 1;
        int count=1;
        Row row = null;

        row = sheet.createRow(0);
        Cell cell_0 = row.createCell(0);
        cell_0.setCellValue("REFERENCE_NO");
        Cell cell_1 = row.createCell(1);
        cell_1.setCellValue("ORG_COMPANY_ID");
        Cell cell_2 = row.createCell(2);
        cell_2.setCellValue("ORG_COMPANY_NAME");
        Cell cell_3 = row.createCell(3);
        cell_3.setCellValue("ORG_CUSTOMER_NO");
        Cell cell_4 = row.createCell(4);
        cell_4.setCellValue("ORG_NAME");
        Cell cell_5 = row.createCell(5);
        cell_5.setCellValue("ORG_ACCOUNT_NO");
        Cell cell_6 = row.createCell(6);
        cell_6.setCellValue("ORG_ACCOUNT_TYPE");
        Cell cell_7 = row.createCell(7);
        cell_7.setCellValue("BEN_NAME");
        Cell cell_8 = row.createCell(8);
        cell_8.setCellValue("BEN_ACCOUNT_NO");
        Cell cell_9 = row.createCell(9);
        cell_9.setCellValue("BEN_ACCOUNT_TYPE");
        Cell cell_10 = row.createCell(10);
        cell_10.setCellValue("BEN_ROUTING_NO");
        Cell cell_11 = row.createCell(11);
        cell_11.setCellValue("AMOUNT");
        Cell cell_12 = row.createCell(12);
        cell_12.setCellValue("PAYMENT_DESCRIPTION");
        while(iterator.hasNext()){
            BeftnModel beftnModel = iterator.next();

            row = sheet.createRow(rowIndex++);

            Cell cell0 = row.createCell(0);
            cell0.setCellValue(count);

            Cell cell1 = row.createCell(1);
            cell1.setCellValue("Not Applicable");

            Cell cell2 = row.createCell(2);
            cell2.setCellValue("Not Applicable");

            Cell cell3 = row.createCell(3);
            cell3.setCellValue(beftnModel.getOrgCustomerNo().trim());

            Cell cell4 = row.createCell(4);
            cell4.setCellValue(beftnModel.getOrgName().trim());

            Cell cell5 = row.createCell(5);
            cell5.setCellValue(beftnModel.getTransactionNo().replaceAll("[^a-zA-Z0-9]", "").trim());

            Cell cell6 = row.createCell(6);
            cell6.setCellValue(beftnModel.getOrgAccountType().trim());

            Cell cell7 = row.createCell(7);
            cell7.setCellValue(beftnModel.getBeneficiaryName().trim());

            Cell cell8 = row.createCell(8);
            cell8.setCellValue(CommonService.removeAllSpecialCharacterFromString(beftnModel.getBeneficiaryAccount().trim()));

            Cell cell9 = row.createCell(9);
            cell9.setCellValue(beftnModel.getBeneficiaryAccountType().trim());

            Cell cell10 = row.createCell(10);
            cell10.setCellValue(beftnModel.getRoutingNo().trim());

            Cell cell11 = row.createCell(11);
            cell11.setCellValue(beftnModel.getAmount());

            Cell cell12 = row.createCell(12);
            cell12.setCellValue(CommonService.removeAllSpecialCharacterFromString(beftnModel.getTransactionNo().trim()));

            count++;
        }
        ByteArrayOutputStream fos = new ByteArrayOutputStream();
        ByteArrayInputStream is = null;
        try {
            workbook.write(fos);
            byte[] xls = fos.toByteArray();
            is = new ByteArrayInputStream(xls);
            fos.close();
            is.close();
            workbook.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    return is;
    }

    public static ByteArrayInputStream BeftnIncentiveModelsToExcel(List<BeftnModel> beftnModelList) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Beftn_Incentive");
        Iterator<BeftnModel> iterator = beftnModelList.iterator();
        int rowIndex = 1;
        int count=1;
        Row row = null;

        row = sheet.createRow(0);
        Cell cell_0 = row.createCell(0);
        cell_0.setCellValue("REFERENCE_NO");
        Cell cell_1 = row.createCell(1);
        cell_1.setCellValue("ORG_COMPANY_ID");
        Cell cell_2 = row.createCell(2);
        cell_2.setCellValue("ORG_COMPANY_NAME");
        Cell cell_3 = row.createCell(3);
        cell_3.setCellValue("ORG_CUSTOMER_NO");
        Cell cell_4 = row.createCell(4);
        cell_4.setCellValue("ORG_NAME");
        Cell cell_5 = row.createCell(5);
        cell_5.setCellValue("ORG_ACCOUNT_NO");
        Cell cell_6 = row.createCell(6);
        cell_6.setCellValue("ORG_ACCOUNT_TYPE");
        Cell cell_7 = row.createCell(7);
        cell_7.setCellValue("BEN_NAME");
        Cell cell_8 = row.createCell(8);
        cell_8.setCellValue("BEN_ACCOUNT_NO");
        Cell cell_9 = row.createCell(9);
        cell_9.setCellValue("BEN_ACCOUNT_TYPE");
        Cell cell_10 = row.createCell(10);
        cell_10.setCellValue("BEN_ROUTING_NO");
        Cell cell_11 = row.createCell(11);
        cell_11.setCellValue("AMOUNT");
        Cell cell_12 = row.createCell(12);
        cell_12.setCellValue("PAYMENT_DESCRIPTION");
        while(iterator.hasNext()){
            BeftnModel beftnModel = iterator.next();

            row = sheet.createRow(rowIndex++);

            Cell cell0 = row.createCell(0);
            cell0.setCellValue(count);

            Cell cell1 = row.createCell(1);
            cell1.setCellValue("Not Applicable");

            Cell cell2 = row.createCell(2);
            cell2.setCellValue("Not Applicable");

            Cell cell3 = row.createCell(3);
            cell3.setCellValue(beftnModel.getOrgCustomerNo().trim());

            Cell cell4 = row.createCell(4);
            cell4.setCellValue("FRD Incentive");

            Cell cell5 = row.createCell(5);
            cell5.setCellValue(beftnModel.getTransactionNo().replaceAll("[^a-zA-Z0-9]", "").trim());

            Cell cell6 = row.createCell(6);
            cell6.setCellValue(beftnModel.getOrgAccountType().trim());

            Cell cell7 = row.createCell(7);
            cell7.setCellValue(beftnModel.getBeneficiaryName().trim());

            Cell cell8 = row.createCell(8);
            cell8.setCellValue(CommonService.removeAllSpecialCharacterFromString(beftnModel.getBeneficiaryAccount().trim()));

            Cell cell9 = row.createCell(9);
            cell9.setCellValue(beftnModel.getBeneficiaryAccountType().trim());

            Cell cell10 = row.createCell(10);
            cell10.setCellValue(beftnModel.getRoutingNo().trim());

            Cell cell11 = row.createCell(11);
            cell11.setCellValue(beftnModel.getIncentive());

            Cell cell12 = row.createCell(12);
            cell12.setCellValue(CommonService.removeAllSpecialCharacterFromString(beftnModel.getTransactionNo().trim()));

            count++;
        }

        ByteArrayOutputStream fos = new ByteArrayOutputStream();
        ByteArrayInputStream is = null;
        try {
            workbook.write(fos);
            byte[] xls = fos.toByteArray();
            is = new ByteArrayInputStream(xls);
            fos.close();
            is.close();
            workbook.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }

}
