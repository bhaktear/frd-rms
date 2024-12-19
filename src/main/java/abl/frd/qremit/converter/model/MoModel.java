package abl.frd.qremit.converter.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="mo")
public class MoModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int  id;
    @Column(name = "mo_date", length=30, nullable = false)
    private LocalDate moDate;


    @Column(name = "total_number_beftn", length = 20)
    private Long totalNumberBeftn;
    @Column(name = "total_amount_beftn", length=30, nullable = false)
    private BigDecimal totalAmountBeftn;

    @Column(name = "total_number_all_other_branch", length = 20)
    private Long totalNumberAllOtherBranch;
    @Column(name = "total_amount_all_other_branch", length=30, nullable = false)
    private BigDecimal totalAmountAllOtherBranch;

    @Column(name = "total_number_icash", length = 20)
    private Long totalNumberIcash;
    @Column(name = "total_amount_icash", length=30, nullable = false)
    private BigDecimal totalAmountIcash;

    @Column(name = "total_number_online", length = 20)
    private Long totalNumberOnline;
    @Column(name = "total_amount_online", length=30, nullable = false)
    private BigDecimal totalAmountOnline;

    @Column(name = "total_number_api", length = 20)
    private Long totalNumberApi;
    @Column(name = "total_amount_api", length=30, nullable = false)
    private BigDecimal totalAmountApi;

    public LocalDate getMoDate() {
        return moDate;
    }

    public void setMoDate(LocalDate moDate) {
        this.moDate = moDate;
    }

    public Long getTotalNumberBeftn() {
        return totalNumberBeftn;
    }

    public void setTotalNumberBeftn(Long totalNumberBeftn) {
        this.totalNumberBeftn = totalNumberBeftn;
    }

    public BigDecimal getTotalAmountBeftn() {
        return totalAmountBeftn;
    }

    public void setTotalAmountBeftn(BigDecimal totalAmountBeftn) {
        this.totalAmountBeftn = totalAmountBeftn;
    }

    public Long getTotalNumberAllOtherBranch() {
        return totalNumberAllOtherBranch;
    }

    public void setTotalNumberAllOtherBranch(Long totalNumberAllOtherBranch) {
        this.totalNumberAllOtherBranch = totalNumberAllOtherBranch;
    }

    public BigDecimal getTotalAmountAllOtherBranch() {
        return totalAmountAllOtherBranch;
    }

    public void setTotalAmountAllOtherBranch(BigDecimal totalAmountAllOtherBranch) {
        this.totalAmountAllOtherBranch = totalAmountAllOtherBranch;
    }

    public Long getTotalNumberIcash() {
        return totalNumberIcash;
    }

    public void setTotalNumberIcash(Long totalNumberIcash) {
        this.totalNumberIcash = totalNumberIcash;
    }

    public BigDecimal getTotalAmountIcash() {
        return totalAmountIcash;
    }

    public void setTotalAmountIcash(BigDecimal totalAmountIcash) {
        this.totalAmountIcash = totalAmountIcash;
    }

    public Long getTotalNumberOnline() {
        return totalNumberOnline;
    }

    public void setTotalNumberOnline(Long totalNumberOnline) {
        this.totalNumberOnline = totalNumberOnline;
    }

    public BigDecimal getTotalAmountOnline() {
        return totalAmountOnline;
    }

    public void setTotalAmountOnline(BigDecimal totalAmountOnline) {
        this.totalAmountOnline = totalAmountOnline;
    }

    public Long getTotalNumberApi() {
        return totalNumberApi;
    }

    public void setTotalNumberApi(Long totalNumberApi) {
        this.totalNumberApi = totalNumberApi;
    }

    public BigDecimal getTotalAmountApi() {
        return totalAmountApi;
    }

    public void setTotalAmountApi(BigDecimal totalAmountApi) {
        this.totalAmountApi = totalAmountApi;
    }
}
