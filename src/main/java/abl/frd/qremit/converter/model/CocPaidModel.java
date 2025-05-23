package abl.frd.qremit.converter.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="base_data_table_coc_paid", uniqueConstraints = { @UniqueConstraint(columnNames = { "transaction_no", "amount", "exchange_code"})},
    indexes = { @Index(name = "idx_file_info_model_id", columnList = "file_info_model_id") }
)
public class CocPaidModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int  id;
    @Column(name = "exchange_code", length = 20)
    private String exchangeCode;
    @Column(name = "transaction_no", unique = true, nullable = false, length=64)
    private String transactionNo;
    @Column(name = "amount", length = 20, nullable = false)
    private Double amount;
    @Column(name = "entered_date", length=30)
    private String enteredDate;
    @Column(name = "paid_date", columnDefinition = "DATETIME")
    private LocalDateTime paidDate;
    @Column(name = "remitter_name", length = 128)
    private String remitterName;
    @Column(name = "beneficiary_name", length = 128)
    private String beneficiaryName;
    @Column(name = "beneficiary_account_no", length = 20, nullable = false)
    private String beneficiaryAccount;
    @Column(name = "beneficiary_mobile_no", length = 15)
    private String beneficiaryMobile;
    @Column(name = "branch_code", length = 5)
    private String branchCode;
    @Column(name = "branch_name", length = 64)
    private String branchName;
    @Column(name = "routing_no", length = 10)
    private String routingNo;
    @Column(name = "bank_code", length = 5)
    private String bankCode;
    @Column(name = "bank_name", length = 64)
    private String bankName;
    @Column(name = "tr_mode", length = 2)
    private String trMode;
    @Column(name = "upload_date_time", columnDefinition = "DATETIME")
    private LocalDateTime uploadDateTime;
    @Column(name = "report_date", columnDefinition = "DATETIME")
    private LocalDateTime reportDate;
    @Column(name = "is_updated", columnDefinition = "TINYINT(1) DEFAULT 0")
    private int isUpdated = 0;
    @Column(name = "is_voucher_generated", columnDefinition = "TINYINT(1) DEFAULT 0")
    private int isVoucherGenerated = 0;
    @Column(name = "govt_incentive")
    private Double govtIncentive = 0.0;
    @Column(name = "agrani_incentive")
    private Double agraniIncentive = 0.0;
    @Column(name = "incentive", length = 15)
    private Double incentive = 0.0;
    @Column(name = "type_flag")
    private String typeFlag;
    @Column(name = "temp_status", columnDefinition = "TINYINT(1) DEFAULT 0")
    private int tempStatus = 0;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="upload_user_id")
    private User userModel;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="file_info_model_id")
    private FileInfoModel fileInfoModel;

    public CocPaidModel(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getEnteredDate() {
        return this.enteredDate;
    }

    public void setEnteredDate(String enteredDate) {
        this.enteredDate = enteredDate;
    }

    public LocalDateTime getPaidDate() {
        return this.paidDate;
    }

    public void setPaidDate(LocalDateTime paidDate) {
        this.paidDate = paidDate;
    }

    public String getRoutingNo() {
        return this.routingNo;
    }

    public void setRoutingNo(String routingNo) {
        this.routingNo = routingNo;
    }

    public String getRemitterName() {
        return remitterName;
    }

    public void setRemitterName(String remitterName) {
        this.remitterName = remitterName;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getBeneficiaryAccount() {
        return beneficiaryAccount;
    }

    public void setBeneficiaryAccount(String beneficiaryAccount) {
        this.beneficiaryAccount = beneficiaryAccount;
    }

    public String getBeneficiaryMobile() {
        return beneficiaryMobile;
    }

    public void setBeneficiaryMobile(String beneficiaryMobile) {
        this.beneficiaryMobile = beneficiaryMobile;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getTrMode() {
        return trMode;
    }

    public void setTrMode(String trMode) {
        this.trMode = trMode;
    }

    public LocalDateTime getUploadDateTime() {
        return uploadDateTime;
    }

    public void setUploadDateTime(LocalDateTime uploadDateTime) {
        this.uploadDateTime = uploadDateTime;
    }

    public LocalDateTime getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDateTime reportDate) {
        this.reportDate = reportDate;
    }

    public int getIsUpdated() {
        return isUpdated;
    }

    public void setIsUpdated(int isUpdated) {
        this.isUpdated = isUpdated;
    }

    public User getUserModel() {
        return userModel;
    }

    public void setUserModel(User userModel) {
        this.userModel = userModel;
    }

    public FileInfoModel getFileInfoModel() {
        return fileInfoModel;
    }

    public void setFileInfoModel(FileInfoModel fileInfoModel) {
        this.fileInfoModel = fileInfoModel;
    }

    public int getIsVoucherGenerated() {
        return this.isVoucherGenerated;
    }

    public void setIsVoucherGenerated(int isVoucherGenerated) {
        this.isVoucherGenerated = isVoucherGenerated;
    }

    public String getBranchName() {
        return this.branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBankCode() {
        return this.bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return this.bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Double getIncentive() {
        return this.incentive;
    }

    public void setIncentive(Double incentive) {
        this.incentive = incentive;
    }

    public String getTypeFlag() {
        return this.typeFlag;
    }

    public void setTypeFlag(String typeFlag) {
        this.typeFlag = typeFlag;
    }

    public int getTempStatus() {
        return this.tempStatus;
    }

    public void setTempStatus(int tempStatus) {
        this.tempStatus = tempStatus;
    }

    public Double getGovtIncentive() {
        return this.govtIncentive;
    }

    public void setGovtIncentive(Double govtIncentive) {
        this.govtIncentive = govtIncentive;
    }

    public Double getAgraniIncentive() {
        return this.agraniIncentive;
    }

    public void setAgraniIncentive(Double agraniIncentive) {
        this.agraniIncentive = agraniIncentive;
    }

    public CocPaidModel(String exchangeCode, String transactionNo, Double amount, String enteredDate, LocalDateTime paidDate, String remitterName, 
        String beneficiaryName, String beneficiaryAccount, String routingNo, String beneficiaryMobile, String bankName, String bankCode, String branchName, 
        String branchCode, String trMode, LocalDateTime uploadDateTime, String typeFlag) {
        this.exchangeCode = exchangeCode;
        this.transactionNo = transactionNo;
        this.amount = amount;
        this.enteredDate = enteredDate;
        this.paidDate = paidDate;
        this.remitterName = remitterName;
        this.beneficiaryName = beneficiaryName;
        this.beneficiaryAccount = beneficiaryAccount;
        this.beneficiaryMobile = beneficiaryMobile;
        this.branchCode = branchCode;
        this.trMode = trMode;
        this.uploadDateTime = uploadDateTime;
        this.routingNo = routingNo;
        this.bankCode = bankCode;
        this.bankName = bankName;
        this.branchName = branchName;
        this.typeFlag = typeFlag;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", exchangeCode='" + getExchangeCode() + "'" +
            ", transactionNo='" + getTransactionNo() + "'" +
            ", amount='" + getAmount() + "'" +
            ", enteredDate='" + getEnteredDate() + "'" +
            ", paidDate='" + getPaidDate() + "'" +
            ", remitterName='" + getRemitterName() + "'" +
            ", beneficiaryName='" + getBeneficiaryName() + "'" +
            ", beneficiaryAccount='" + getBeneficiaryAccount() + "'" +
            ", beneficiaryMobile='" + getBeneficiaryMobile() + "'" +
            ", branchCode='" + getBranchCode() + "'" +
            ", branchName='" + getBranchName() + "'" +
            ", routingNo='" + getRoutingNo() + "'" +
            ", bankCode='" + getBankCode() + "'" +
            ", bankName='" + getBankName() + "'" +
            ", trMode='" + getTrMode() + "'" +
            ", uploadDateTime='" + getUploadDateTime() + "'" +
            ", reportDate='" + getReportDate() + "'" +
            ", isUpdated='" + getIsUpdated() + "'" +
            ", isVoucherGenerated='" + getIsVoucherGenerated() + "'" +
            ", userModel='" + getUserModel() + "'" +
            ", fileInfoModel='" + getFileInfoModel() + "'" +
            "}";
    }
    
}
