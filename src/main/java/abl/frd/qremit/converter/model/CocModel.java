package abl.frd.qremit.converter.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

@Entity
@Table(name="converted_data_coc",
    indexes = { @Index(name = "idx_report_date", columnList = "report_date"), @Index(name = "idx_is_processed", columnList = "is_processed"),
        @Index(name = "idx_is_voucher_generated", columnList = "is_voucher_generated"), @Index(name = "idx_upload_date_time", columnList = "upload_date_time"),
        @Index(name = "idx_is_downloaded", columnList = "is_downloaded"), @Index(name = "idx_temp_status", columnList = "temp_status"), 
        @Index(name = "idx_beneficiary_account_no", columnList = "beneficiary_account_no"), @Index(name = "idx_govt_incentive", columnList = "govt_incentive"),@Index(name = "idx_agrani_incentive", columnList = "agrani_incentive"), @Index(name = "idx_incentive", columnList = "incentive")
    }
)
public class CocModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(name = "transaction_no")
    private String transactionNo;
    @Column(name = "credit_mark")
    private String creditMark;
    @Column(name = "entered_date", length=30)
    private String enteredDate;
    @Column(name = "currency", length=32)
    private String currency;
    @Column(name = "amount", length = 15)
    private Double amount;
    @Column(name = "beneficiary_name", length=128)
    private String beneficiaryName;
    @Column(name = "exchange_code", length = 20)
    private String exchangeCode;
    @Column(name = "bank_name", length=64)
    private String bankName;
    @Column(name = "bank_code", length=10)
    private String bankCode;
    @Column(name = "branch_name", length=128)
    private String branchName;
    @Column(name = "branch_code", length=15)
    private String branchCode;
    @Column(name = "beneficiary_account_no",  length=32)
    private String beneficiaryAccount;
    @Column(name = "remitter_name", length=128)
    private String remitterName;
    @Column(name = "govt_incentive")
    private Double govtIncentive = 0.0;
    @Column(name = "agrani_incentive")
    private Double agraniIncentive = 0.0;
    @Column(name = "incentive")
    private Double incentive = 0.0;
    @Column(name = "coc_code")
    private String cocCode;
    @Column(name = "is_processed", columnDefinition = "TINYINT(1) DEFAULT 0")
    private int isProcessed = 0;
    @Column(name = "is_downloaded", columnDefinition = "TINYINT(1) DEFAULT 0")
    private int isDownloaded = 0;
    @Column(name = "download_date_time")
    private LocalDateTime downloadDateTime;
    @Column(name = "download_user_id")
    private int downloadUserId;
    @Column(name = "upload_date_time", columnDefinition = "DATETIME")
    private LocalDateTime uploadDateTime;
    @Column(name = "temp_status", columnDefinition = "TINYINT(1) DEFAULT 0")
    private int tempStatus = 0;
    @Column(name = "is_voucher_generated", columnDefinition = "TINYINT(1) DEFAULT 0")
    private int isVoucherGenerated = 0;
    @Column(name = "report_date", columnDefinition = "DATETIME")
    private LocalDateTime reportDate;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    //@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="upload_user_id")
    @JsonIgnore
    private User userModel;

    public User getUserModel() {
        return userModel;
    }

    public void setUserModel(User userModel) {
        this.userModel = userModel;
    }

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    //@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="file_info_model_id")
    @JsonIgnore
    private FileInfoModel fileInfoModel;

    public FileInfoModel getFileInfoModel() {
        return fileInfoModel;
    }

    public void setFileInfoModel(FileInfoModel fileInfoModel) {
        this.fileInfoModel = fileInfoModel;
    }

    public CocModel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getCreditMark() {
        return creditMark;
    }

    public void setCreditMark(String creditMark) {
        this.creditMark = creditMark;
    }

    public String getEnteredDate() {
        return enteredDate;
    }

    public void setEnteredDate(String enteredDate) {
        this.enteredDate = enteredDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBeneficiaryAccount() {
        return beneficiaryAccount;
    }

    public void setBeneficiaryAccount(String beneficiaryAccount) {
        this.beneficiaryAccount = beneficiaryAccount;
    }

    public String getRemitterName() {
        return remitterName;
    }

    public void setRemitterName(String remitterName) {
        this.remitterName = remitterName;
    }

    public Double getGovtIncentive() {
        return govtIncentive;
    }

    public void setGovtIncentive(Double incentive) {
        this.govtIncentive = incentive;
    }

    public String getCocCode() {
        return cocCode;
    }

    public void setCocCode(String cocCode) {
        this.cocCode = cocCode;
    }

    public int getIsProcessed() {
        return this.isProcessed;
    }

    public void setIsProcessed(int isProcessed) {
        this.isProcessed = isProcessed;
    }

    public int getIsDownloaded() {
        return this.isDownloaded;
    }

    public void setIsDownloaded(int isDownloaded) {
        this.isDownloaded = isDownloaded;
    }
    
    public LocalDateTime getDownloadDateTime() {
        return downloadDateTime;
    }

    public void setDownloadDateTime(LocalDateTime extraC) {
        this.downloadDateTime = extraC;
    }

    public int getDownloadUserId() {
        return downloadUserId;
    }

    public void setDownloadUserId(int downloadUserId) {
        this.downloadUserId = downloadUserId;
    }


    public int getTempStatus() {
        return this.tempStatus;
    }

    public void setTempStatus(int tempStatus) {
        this.tempStatus = tempStatus;
    }


    public int getIsVoucherGenerated() {
        return this.isVoucherGenerated;
    }

    public void setIsVoucherGenerated(int isVoucherGenerated) {
        this.isVoucherGenerated = isVoucherGenerated;
    }

    public LocalDateTime getReportDate() {
        return this.reportDate;
    }

    public void setReportDate(LocalDateTime reportDate) {
        this.reportDate = reportDate;
    }

    public LocalDateTime getUploadDateTime() {
        return this.uploadDateTime;
    }

    public void setUploadDateTime(LocalDateTime uploadDateTime) {
        this.uploadDateTime = uploadDateTime;
    }

    public Double getAgraniIncentive() {
        return agraniIncentive;
    }

    public void setAgraniIncentive(Double agraniIncentive) {
        this.agraniIncentive = agraniIncentive;
    }

    public Double getIncentive() {
        return incentive;
    }

    public void setIncentive(Double incentive) {
        this.incentive = incentive;
    }

    public CocModel(int id, String transactionNo, String creditMark, String enteredDate, String currency, Double amount, String beneficiaryName, String exchangeCode, String bankName, String bankCode, String branchName, String branchCode, String beneficiaryAccount, String remitterName, Double govtIncentive, Double agraniIncentive, Double incentive, String cocCode, int extraA, int extraB, LocalDateTime downloadDateTime, int downloadUserId, LocalDateTime uploadDateTime) {
        this.id = id;
        this.transactionNo = transactionNo;
        this.creditMark = creditMark;
        this.enteredDate = enteredDate;
        this.currency = currency;
        this.amount = amount;
        this.beneficiaryName = beneficiaryName;
        this.exchangeCode = exchangeCode;
        this.bankName = bankName;
        this.bankCode = bankCode;
        this.branchName = branchName;
        this.branchCode = branchCode;
        this.beneficiaryAccount = beneficiaryAccount;
        this.remitterName = remitterName;
        this.govtIncentive = govtIncentive;
        this.agraniIncentive = agraniIncentive;
        this.incentive = incentive;
        this.cocCode = cocCode;
        this.isProcessed = extraA;
        this.isDownloaded = extraB;
        this.downloadDateTime = downloadDateTime;
        this.downloadUserId = downloadUserId;
        this.uploadDateTime = uploadDateTime;
    }

    @Override
    public String toString() {
        return "CocModel{" +
                "id=" + id +
                ", transactionNo='" + transactionNo + '\'' +
                ", creditMark='" + creditMark + '\'' +
                ", enteredDate='" + enteredDate + '\'' +
                ", currency='" + currency + '\'' +
                ", amount=" + amount +
                ", beneficiaryName='" + beneficiaryName + '\'' +
                ", exchangeCode='" + exchangeCode + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", branchName='" + branchName + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", beneficiaryAccount='" + beneficiaryAccount + '\'' +
                ", remitterName='" + remitterName + '\'' +
                ", govtIncentive=" + govtIncentive +
                ", agraniIncentive=" + agraniIncentive +
                ", incentive=" + incentive +
                ", cocCode='" + cocCode + '\'' +
                ", extraA='" + isProcessed + '\'' +
                ", extraB='" + isDownloaded + '\'' +
                ", extraC='" + downloadDateTime + '\'' +
                ", extraD='" + downloadUserId + '\'' +
                ", uploadDateTime='" + uploadDateTime + '\'' +
                '}';
    }
}