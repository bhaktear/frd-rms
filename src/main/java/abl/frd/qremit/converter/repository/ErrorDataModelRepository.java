package abl.frd.qremit.converter.repository;
import java.util.*;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import abl.frd.qremit.converter.model.ErrorDataModel;

@Repository
public interface ErrorDataModelRepository extends JpaRepository<ErrorDataModel, Integer>{

    List<ErrorDataModel> findByUserModelId(int userId);
    List<ErrorDataModel> findByUserModelIdAndUpdateStatus(int userId, int updateStatus);
    List<ErrorDataModel> findByUserModelIdAndUpdateStatusAndFileInfoModelId(int userId, int updateStatus, int fileInfoModelId);
    @Query("SELECT n from ErrorDataModel n where n.updateStatus=:updateStatus AND n.exchangeCode in :exchangeCode ORDER BY n.id desc")
    List<ErrorDataModel> findErrorByExchangeCode(@Param("exchangeCode") List<String> exchangeCode, @Param("updateStatus") int updateStatus);
    @Query("SELECT n from ErrorDataModel n where n.updateStatus=:updateStatus AND n.fileInfoModel.id =:fileInfoModelId AND n.exchangeCode in :exchangeCode ORDER BY n.id desc")
    List<ErrorDataModel> findErrorByExchangeCodeAndFileId(@Param("exchangeCode") List<String> exchangeCode, @Param("updateStatus") int updateStatus, 
        @Param("fileInfoModelId") int fileInfoModelId);
    List<ErrorDataModel> findByUpdateStatus(int updateStatus);
    ErrorDataModel findById(int id);
    @Transactional
    @Modifying
    @Query("UPDATE ErrorDataModel e SET e.updateStatus= :updateStatus where e.id= :id")
    void updateUpdateStatusById(int id, int updateStatus);
    void deleteById(int id);
    Optional<ErrorDataModel> findByTransactionNoIgnoreCaseAndAmountAndExchangeCode(String transactionNo, double amount, String exchangeCode);
    @Query("SELECT n from ErrorDataModel n where n.fileInfoModel.id =:fileInfoModelId and n.updateStatus != 0")
    List<ErrorDataModel> getErrorSubmittedByFileInfoModelId(@Param("fileInfoModelId") int fileInfoModelId);
    @Transactional
    @Modifying
    @Query("DELETE FROM ErrorDataModel n WHERE n.fileInfoModel.id = :fileInfoModelId")
    void deleteByFileInfoModelId(@Param("fileInfoModelId") int fileInfoModelId);
    List<ErrorDataModel> findByTransactionNoIn(List<String> transactionList);
}
