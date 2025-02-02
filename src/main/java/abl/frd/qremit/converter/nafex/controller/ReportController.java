package abl.frd.qremit.converter.nafex.controller;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.*;
import abl.frd.qremit.converter.nafex.helper.NumberToWords;
import abl.frd.qremit.converter.nafex.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import abl.frd.qremit.converter.nafex.helper.MyUserDetails;
import abl.frd.qremit.converter.nafex.service.CommonService;
import abl.frd.qremit.converter.nafex.service.CustomQueryService;
import abl.frd.qremit.converter.nafex.service.ErrorDataModelService;
import abl.frd.qremit.converter.nafex.service.ExchangeHouseModelService;
import abl.frd.qremit.converter.nafex.service.FileInfoModelService;
import abl.frd.qremit.converter.nafex.service.LogModelService;
import abl.frd.qremit.converter.nafex.service.MyUserDetailsService;
import abl.frd.qremit.converter.nafex.service.ReportService;
@Controller
public class ReportController {

    private final MyUserDetailsService myUserDetailsService;
    private final FileInfoModelService fileInfoModelService;
    private final ReportService reportService;
    @Autowired
    CommonService commonService;
    @Autowired
    ExchangeHouseModelService exchangeHouseModelService;
    @Autowired
    ErrorDataModelService errorDataModelService;
    @Autowired
    LogModelService logModelService;
    @Autowired
    CustomQueryService customQueryService;
    
    public ReportController(MyUserDetailsService myUserDetailsService,FileInfoModelService fileInfoModelService,ReportService reportService){
        this.myUserDetailsService = myUserDetailsService;
        this.fileInfoModelService = fileInfoModelService;
        this.reportService = reportService;
    }

    @GetMapping("/getReportColumn")
    @ResponseBody
    public Map<String, Object> getReportColumnUrl(String type){
        Map<String, Object> resp = new HashMap<>();
        List<Map<String, String>> column = getReportColumn(type);
        resp.put("column", column);
        return resp;
    }

    public static List<Map<String, String>> getReportColumn(String type){
        String[] columnData = null;
        String[] columnTitles = null;
        switch(type){
            case "1":
            default:
                columnData = new String[] {"sl", "exchangeCode", "fileName", "cocCount", "beftnCount", "onlineCount", "accountPayeeCount", "totalCount", "errorCount", "uploadDateTime", "action"};
                columnTitles = new String[] {"SL", "Exchange Code", "File Name", "COC", "BEFTN", "Online", "Account Payee", "Total Processed", "Total Error", "Upload Date", "Action"};
                break;
            case "2":
                columnData = new String[] {"sl", "transactionNo", "exchangeCode", "beneficiaryName", "beneficiaryAccountNo", "bankName", "branchCode", "branchName","remitterName", "amount","processedDate","remType"};
                columnTitles = new String[] {"SL", "Transaction No", "Exchange Code", "Beneficiary Name", "Account No",  "Bank Name", "Routing No/ Branch Code", "Branch Name","Remitter Name","Amount","Processed Date","Type"};
                break;
            case "3":
            case "4":
                columnData = new String[] {"sl", "transactionNo", "exchangeCode", "beneficiaryName", "beneficiaryAccountNo", "bankName", "branchCode", "branchName","amount","uploadDateTime","errorMessage","action"};
                columnTitles = new String[] {"SL", "Transaction No", "Exchange Code", "Beneficiary Name", "Account No",  "Bank Name", "Routing No/ Branch Code", "Branch Name","Amount","Upload Date","Error Mesage","Action"};
                break;
        }
        return CommonService.createColumns(columnData, columnTitles);
    }
    
    @GetMapping("/report")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getUploadedFileInfo(@AuthenticationPrincipal MyUserDetails userDetails,Model model,@RequestParam(defaultValue = "") String date){
        Map<String, Object> resp = new HashMap<>();
        if(date.isEmpty()){
            date = CommonService.getCurrentDate("yyyy-MM-dd");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails myUserDetails = (MyUserDetails)authentication.getPrincipal();
        Map<String, Object> userData = myUserDetailsService.getLoggedInUserDetails(authentication, myUserDetails);
        if(userData.get("status") == HttpStatus.UNAUTHORIZED)   return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        int userId = (int) userData.get("userid");
        String baseUrl = (userId == 0) ? "/adminReport": "/user-home-page";
        if(userData.containsKey("exchangeMap")) model.addAttribute("exchangeMap", userData.get("exchangeMap"));
        List<FileInfoModel> fileInfoModel = fileInfoModelService.getUploadedFileDetails(userId, date);
        List<Map<String, Object>> dataList = new ArrayList<>();
        if(fileInfoModel.isEmpty())     return ResponseEntity.ok(CommonService.getResp(1, "No data found", dataList));
        int sl = 1;
        int totalCount = 0;
        int totalCocCount = 0;
        int totalBeftnCount = 0;
        int totalOnlineCount = 0;
        int totalAccountPayeeCount = 0;
        int totalErrorCount = 0;
        String action = "";
        for (FileInfoModel fModel : fileInfoModel) {
            Map<String, Object> dataMap = new HashMap<>();
            action = CommonService.generateTemplateBtn("template-viewBtn.txt","#","btn-info btn-sm round view_exchange", String.valueOf(fModel.getId()),"View");
            action += "<input type='hidden' id='exCode_" + fModel.getId() + "' value='" + fModel.getExchangeCode() + "' />";
            action += "<input type='hidden' id='base_url' value='" + baseUrl + "' />";
            int cocCount = CommonService.convertStringToInt(fModel.getCocCount());
            totalCocCount += cocCount;
            int beftnCount = CommonService.convertStringToInt(fModel.getBeftnCount());
            totalBeftnCount += beftnCount;
            int onlineCount = CommonService.convertStringToInt(fModel.getOnlineCount());
            totalOnlineCount += onlineCount;
            int accountPayeeCount = CommonService.convertStringToInt(fModel.getAccountPayeeCount());
            totalAccountPayeeCount += accountPayeeCount;
            int errorCount = fModel.getErrorCount();
            totalErrorCount += errorCount;
            String errorStr = CommonService.convertIntToString(errorCount);
            String totalError = (errorCount >= 1) ? CommonService.generateClassForText(errorStr, "text-danger fw-bold"): errorStr;
            int total = CommonService.convertStringToInt(fModel.getTotalCount());
            totalCount += total;
            dataMap.put("sl", sl++);
            dataMap.put("id", fModel.getId());
            dataMap.put("exchangeCode", fModel.getExchangeCode());
            dataMap.put("uploadDateTime", CommonService.convertDateToString(fModel.getUploadDateTime()));
            dataMap.put("fileName", fModel.getFileName());
            dataMap.put("cocCount", cocCount);
            dataMap.put("beftnCount", beftnCount);
            dataMap.put("onlineCount", onlineCount);
            dataMap.put("accountPayeeCount", accountPayeeCount);
            dataMap.put("errorCount", totalError);
            dataMap.put("totalCount", total);
            dataMap.put("action", action);
            dataList.add(dataMap);
        }
        Map<String, Object> totalData = reportService.calculateTotalUploadFileInfo(totalCocCount, totalBeftnCount, totalOnlineCount, totalAccountPayeeCount, totalErrorCount, totalCount);
        dataList.add(totalData);
        resp.put("data", dataList);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/fileReport")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getFileDetails(@AuthenticationPrincipal MyUserDetails userDetails,Model model,@RequestParam String id,@RequestParam String exchangeCode){
        Map<String, Object> resp = new HashMap<>();
        List<Map<String, String>> reportColumn = getReportColumn("2");
        List<String> columnDataList = new ArrayList<>();
        for(Map<String, String> column: reportColumn){
            columnDataList.add(column.get("data"));
        }
        String[] columnData = columnDataList.toArray(new String[0]);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails myUserDetails = (MyUserDetails)authentication.getPrincipal();
        Map<String, Object> userData = myUserDetailsService.getLoggedInUserDetails(authentication, myUserDetails);
        if(userData.get("status") == HttpStatus.UNAUTHORIZED)   return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if(userData.containsKey("exchangeMap")) model.addAttribute("exchangeMap", userData.get("exchangeMap"));
        ExchangeHouseModel exchangeHouseModel = exchangeHouseModelService.findByExchangeCode(exchangeCode);
        String tbl = CommonService.getBaseTableName(exchangeHouseModel.getBaseTableName());
        Map<String,Object> fileInfo = customQueryService.getFileDetails(tbl,id);
        if((Integer) fileInfo.get("err") == 1)  return ResponseEntity.ok(fileInfo);
        resp = reportService.getFileDetails(CommonService.convertStringToInt(id), fileInfo, columnData);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/errorReport")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getErrorReport(@AuthenticationPrincipal MyUserDetails userDetails,Model model, 
        @RequestParam(defaultValue = "") String id){
        model.addAttribute("exchangeMap", myUserDetailsService.getLoggedInUserMenu(userDetails));
        Map<String, Object> resp = new HashMap<>();
        int fileInfoModelId = 0;
        if(!id.isEmpty())  fileInfoModelId = CommonService.convertStringToInt(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        int userId;
        String exchangeCode;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            MyUserDetails myUserDetails = (MyUserDetails)authentication.getPrincipal();
            User user = myUserDetails.getUser();
            userId = user.getId();
            exchangeCode = user.getExchangeCode();
            List<Map<String, Object>> dataList = errorDataModelService.getErrorReport(userId, fileInfoModelId, exchangeCode);
            resp.put("data", dataList);
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/getErrorUpdateReport")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getErrorUpdateReport(@AuthenticationPrincipal MyUserDetails userDetails, Model model){
        //model.addAttribute("exchangeMap", myUserDetailsService.getLoggedInUserMenu(userDetails));
        Map<String, Object> resp = new HashMap<>();
        List<Map<String, Object>> dataList = errorDataModelService.getErrorUpdateReport();
        resp.put("data", dataList);
        return ResponseEntity.ok(resp);
    }

    @RequestMapping(value="/summaryOfDailyStatement", method= RequestMethod.GET)
    public String generateSummaryOfDailyStatement(Model model, @RequestParam(defaultValue = "") String date) {
        if(date.isEmpty())  date = CommonService.getCurrentDate("yyyy-MM-dd");
        List<ExchangeReportDTO> exchangeReport = reportService.generateSummaryOfDailyStatement(date);
        Double grandTotalAmount = 0.00;
        String commaFormattedGrandTotalAmount="";
        int grandTotalRemittances=0;
        for(ExchangeReportDTO exchangeReportDTO: exchangeReport){
            exchangeReportDTO.setExchangeName(exchangeHouseModelService.findByExchangeCode(exchangeReportDTO.getExchangeCode()).getExchangeName());
            grandTotalAmount = grandTotalAmount+exchangeReportDTO.getTotalAmountCount();
            grandTotalRemittances = grandTotalRemittances+exchangeReportDTO.getTotalRowCount();
            commaFormattedGrandTotalAmount = exchangeReportDTO.formattedAmount.format(grandTotalAmount);
        }
        model.addAttribute("summaryReportContent", exchangeReport);
        model.addAttribute("grandTotalAmount", commaFormattedGrandTotalAmount);
        model.addAttribute("grandTotalRemittances", grandTotalRemittances);
        model.addAttribute("date", date);
        return "report/summaryOfDailyRemittance";
    }

    @RequestMapping(value="/detailsOfDailyStatement", method= RequestMethod.GET)
    public String generateDetailsOfDailyStatement(@RequestParam(defaultValue = "html") String format, Model model, @RequestParam(defaultValue = "") String date) throws FileNotFoundException {
        if(date.isEmpty())  date = CommonService.getCurrentDate("yyyy-MM-dd");
        List<ExchangeReportDTO> exchangeReport = reportService.generateDetailsOfDailyStatement(date);
        for(ExchangeReportDTO exchangeReportDTO: exchangeReport){
            exchangeReportDTO.setExchangeName(exchangeHouseModelService.findByExchangeCode(exchangeReportDTO.getExchangeCode()).getExchangeName());
        }
        model.addAttribute("detailsReportContent", exchangeReport);
        model.addAttribute("date", date);
        return "report/detailsOfDailyRemittance";
    }

    @RequestMapping(value="/downloadSummaryOfDailyStatementInPdfFormat", method= RequestMethod.GET)
    public ResponseEntity<byte[]> downloadDailyStatementInPdfFormat(@RequestParam(defaultValue = "") String date) throws Exception {
        if(date.isEmpty())  date = CommonService.getCurrentDate("yyyy-MM-dd");
        List<ExchangeReportDTO> data = reportService.generateSummaryOfDailyStatement(date);
        if(data.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        byte[] pdfReport = reportService.generateDailyStatementInPdfFormat(data, date);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        String fileName = CommonService.generateFileName("summary_report_", date, ".pdf");
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"" );
        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfReport);
    }
    @RequestMapping(value = "/downloadDetailsOfDailyStatement", method= RequestMethod.GET)
    public ResponseEntity<byte[]> downloadDetailsReportInPdf(@RequestParam("type") String format, @RequestParam(defaultValue = "") String date){
        if(date.isEmpty())  date = CommonService.getCurrentDate("yyyy-MM-dd");
        try {
            List<ExchangeReportDTO> dataList = reportService.generateDetailsOfDailyStatement(date);
            byte[] reportBytes = reportService.generateDetailsJasperReport(dataList, format, date);
            String fileName = CommonService.generateFileName("details_report_", date, "." + format.toLowerCase());
            MediaType mediaType = format.equalsIgnoreCase("pdf") ? MediaType.APPLICATION_PDF : MediaType.TEXT_PLAIN;
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentType(mediaType)
                    .body(reportBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @RequestMapping(value="/downloaDailyVoucherInPdfFormat", method= RequestMethod.GET)
    public ResponseEntity<byte[]> downloaDailyVoucherInPdfFormat(@RequestParam(defaultValue = "") String date) throws Exception {
        if(date.isEmpty())  date = CommonService.getCurrentDate("yyyy-MM-dd");
        List<ExchangeReportDTO> data = reportService.generateSummaryOfDailyStatement(date);
        for(int i=0; i<data.size();i++){
            data.get(i).setTotalAmountInWords(NumberToWords.convertDoubleToWords(data.get(i).getSumOfAmount()));
        }
        String fileName = CommonService.generateFileName("daily_voucher_", date, ".pdf");
        byte[] pdfReport = reportService.generateDailyVoucherInPdfFormat(data, date);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfReport);
    }

    @GetMapping("/generateReport")
    public ResponseEntity<?> viewPdf() throws IOException {
        String date = CommonService.getCurrentDate("yyyy-MM-dd");
        Path filePath = CommonService.getReportFile(CommonService.generateFileName("summary_report_", date, ".pdf"));
        String fileName = filePath.toString();
        try {
            // Construct the full file path
            File file = new File(fileName);
            // Check if the file exists
            if (!file.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("PDF file not found.");
            }

            // Open the file as a resource
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamResource resource = new InputStreamResource(fileInputStream);

            // Prepare response with PDF content
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + file.getName());

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);

        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("File not found.");
        }
    }

    @GetMapping("/processReport")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> generateReport(@AuthenticationPrincipal MyUserDetails userDetails){
        Map<String, Object> resp = new HashMap<>();
        String currentDate = CommonService.getCurrentDate("yyyy-MM-dd");
        resp = reportService.processReport(currentDate);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/getReportFile")
    public ResponseEntity<Resource> getReportFile(@RequestParam String fileName){
        try{
            Path filePath = CommonService.generateOutputFile(fileName);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        }catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build(); // Handle any IO exceptions
        }
    }

}
