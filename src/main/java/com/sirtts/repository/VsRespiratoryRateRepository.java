package com.sirtts.repository;

import com.sirtts.domain.VsRespiratoryRate;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the VsRespiratoryRate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VsRespiratoryRateRepository extends MongoRepository<VsRespiratoryRate, String> {

}
