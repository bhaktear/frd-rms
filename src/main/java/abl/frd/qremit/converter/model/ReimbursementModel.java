package abl.frd.qremit.converter.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="reimbursement", uniqueConstraints = { @UniqueConstraint(name = "exchange_code_transaction_no_main_amount_beneficiary_account", columnNames = { "exchange_code", "transaction_no","main_amount", "beneficiary_account"})},
indexes = { @Index(name = "idx_exchange_code", columnList = "exchange_code"),
        @Index(name = "idx_transaction_no", columnList = "transaction_no"),
        @Index(name = "idx_main_amount", columnList = "main_amount"),
        @Index(name = "idx_beneficiary_account", columnList = "beneficiary_account")})

public class ReimbursementModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int  id;
    @Column(name = "exchange_code", length = 20)
    private String exchangeCode;
    @Column(name = "transaction_no", length = 30)
    private String transactionNo;
    @Column(name = "report_date", length=30, nullable = false)
    private LocalDate reportDate;
    @Column(name = "reimbursement_date", length=30, nullable = false)
    private LocalDate reimbursementDate;
    @Column(name = "beneficiary_name", length = 128)
    private String beneficiaryName;
    @Column(name = "beneficiary_account", length = 20)
    private String beneficiaryAccount;
    @Column(name = "remitter_name", length = 128)
    private String remitterName;
    @Column(name = "branch_code", length = 20)
    private String branchCode;
    @Column(name = "branch_name", length = 40)
    private String branchName;
    @Column(name = "main_amount", length=30, nullable = false)
    private Double mainAmount = 0.0;
    @Column(name = "govt_incentive", length=30, nullable = false)
    private Double govtIncentive = 0.0;
    @Column(name = "agrani_incentive", length=30, nullable = false)
    private Double agraniIncentive = 0.0;
    @Column(name = "incentive", length = 15)
    private Double incentive = 0.0;
    @Column(name = "type", length = 10)
    private String type;
    @Transient
    private int sl;

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ReimbursementModel() {
    }
    public ReimbursementModel(int sl, String exchangeCode, String transactionNo, LocalDate reportDate, String beneficiaryName, String beneficiaryAccount, String remitterName, String branchCode, String branchName, Double mainAmount, Double govtIncentive, Double agraniIncentive, Double incentive, String type, LocalDate reimbursementDate) {
        this.sl = sl;
        this.exchangeCode = exchangeCode;
        this.transactionNo = transactionNo;
        this.reportDate = reportDate;
        this.beneficiaryName = beneficiaryName;
        this.beneficiaryAccount = beneficiaryAccount;
        this.remitterName = remitterName;
        this.branchCode = branchCode;
        this.branchName = branchName;
        this.mainAmount = mainAmount;
        this.govtIncentive = govtIncentive;
        this.agraniIncentive = agraniIncentive;
        this.incentive = incentive;
        this.type = type;
        this.reimbursementDate = reimbursementDate;
    }

    public Double getIncentive() {
        return incentive;
    }

    public void setIncentive(Double incentive) {
        this.incentive = incentive;
    }

    public LocalDate getReimbursementDate() {
        return reimbursementDate;
    }

    public void setReimbursementDate(LocalDate reimbursementDate) {
        this.reimbursementDate = reimbursementDate;
    }

    public String getBeneficiaryAccount() {
        return beneficiaryAccount;
    }

    public void setBeneficiaryAccount(String accountNumber) {
        this.beneficiaryAccount = accountNumber;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Double getMainAmount() {
        return mainAmount;
    }

    public void setMainAmount(Double mainAmount) {
        this.mainAmount = mainAmount;
    }

    public Double getGovtIncentive() {
        return govtIncentive;
    }

    public void setGovtIncentive(Double govtIncentiveAmount) {
        this.govtIncentive = govtIncentiveAmount;
    }

    public Double getAgraniIncentive() {
        return agraniIncentive;
    }

    public void setAgraniIncentive(Double agraniIncentiveAmount) {
        this.agraniIncentive = agraniIncentiveAmount;
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

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getRemitterName() {
        return remitterName;
    }

    public void setRemitterName(String remitterName) {
        this.remitterName = remitterName;
    }
}
