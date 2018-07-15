package com.sirtts.service;

import com.sirtts.service.dto.VsRespiratoryRateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing VsRespiratoryRate.
 */
public interface VsRespiratoryRateService {

    /**
     * Save a vsRespiratoryRate.
     *
     * @param vsRespiratoryRateDTO the entity to save
     * @return the persisted entity
     */
    VsRespiratoryRateDTO save(VsRespiratoryRateDTO vsRespiratoryRateDTO);

    /**
     * Get all the vsRespiratoryRates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<VsRespiratoryRateDTO> findAll(Pageable pageable);

    /**
     * Get all the vsRespiratoryRates by userid.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<VsRespiratoryRateDTO> findAllByUserid(String[] userids, String startDate, String endDate, Pageable pageable);

    /**
     * Get the "id" vsRespiratoryRate.
     *
     * @param id the id of the entity
     * @return the entity
     */
    VsRespiratoryRateDTO findOne(String id);

    /**
     * Delete the "id" vsRespiratoryRate.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
