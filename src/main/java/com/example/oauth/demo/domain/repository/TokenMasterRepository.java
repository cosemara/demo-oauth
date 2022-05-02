package com.example.oauth.demo.domain.repository;

import com.example.oauth.demo.domain.entity.TokenMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface TokenMasterRepository extends JpaRepository<TokenMaster, String> {
    @Transactional(readOnly = true)
    Optional<TokenMaster> findByTknId(String tknId);

    @Transactional(readOnly = true)
    Optional<TokenMaster> findByTknIdAndUserId(String tknId, Integer userId);

    @Modifying
    @Transactional
    @Query(value="delete from TokenMaster TM where TM.expTime <= :time")
    void deleteByExpTimeLessThan(@Param("time") String time);
}