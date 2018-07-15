package com.sirtts.service;

import com.sirtts.service.dto.VsHeartRateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing VsHeartRate.
 */
public interface VsHeartRateService {

    /**
     * Save a vsHeartRate.
     *
     * @param vsHeartRateDTO the entity to save
     * @return the persisted entity
     */
    VsHeartRateDTO save(VsHeartRateDTO vsHeartRateDTO);

    /**
     * Get all the vsHeartRates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<VsHeartRateDTO> findAll(Pageable pageable);

    /**
     * Get all the vsHeartRates by userid.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<VsHeartRateDTO> findAllByUserid(String[] userids, String startDate, String endDate, Pageable pageable);

    /**
     * Get the "id" vsHeartRate.
     *
     * @param id the id of the entity
     * @return the entity
     */
    VsHeartRateDTO findOne(String id);

    /**
     * Delete the "id" vsHeartRate.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
