package com.sirtts.service;

import com.sirtts.service.dto.VsBloodPressureDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing VsBloodPressure.
 */
public interface VsBloodPressureService {

    /**
     * Save a vsBloodPressure.
     *
     * @param vsBloodPressureDTO the entity to save
     * @return the persisted entity
     */
    VsBloodPressureDTO save(VsBloodPressureDTO vsBloodPressureDTO);

    /**
     * Get all the vsBloodPressures.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<VsBloodPressureDTO> findAll(Pageable pageable);

    /**
     * Get all the vsBloodPressures by userid.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<VsBloodPressureDTO> findAllByUserid(String[] userids, String startDate, String endDate, Pageable pageable);


    /**
     * Get the "id" vsBloodPressure.
     *
     * @param id the id of the entity
     * @return the entity
     */
    VsBloodPressureDTO findOne(String id);

    /**
     * Delete the "id" vsBloodPressure.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
