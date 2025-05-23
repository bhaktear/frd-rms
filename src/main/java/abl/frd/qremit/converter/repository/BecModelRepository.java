package abl.frd.qremit.converter.repository;

import abl.frd.qremit.converter.model.BecModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BecModelRepository extends JpaRepository<BecModel, Integer> {
    /*
@Query("SELECT n FROM NafexEhMstModel n WHERE n.checkT24 = '1'")
    List<NafexEhMstModel> findAllNafexModelHavingOnlineAccount();
@Query("SELECT n FROM NafexEhMstModel n WHERE n.checkCoc = '1'")
    List<NafexEhMstModel> findAllNafexModelHavingCoc();
@Query("SELECT n FROM NafexEhMstModel n WHERE n.checkBeftn = '1'")
    List<NafexEhMstModel> findAllNafexModelHavingBeftn();
@Query("SELECT n FROM NafexEhMstModel n WHERE n.checkAccPayee = '1'")
    List<NafexEhMstModel> findAllNafexModelHavingAccountPayee();

     */
    BecModel findByTransactionNo(String transactionNo);
    Optional<BecModel> findByTransactionNoIgnoreCaseAndAmountAndExchangeCode(String transactionNo, double amount, String exchangeCode);
}
