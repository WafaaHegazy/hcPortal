package com.sirtts.service;

import com.sirtts.service.dto.VsBodyTemperatureDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing VsBodyTemperature.
 */
public interface VsBodyTemperatureService {

    /**
     * Save a vsBodyTemperature.
     *
     * @param vsBodyTemperatureDTO the entity to save
     * @return the persisted entity
     */
    VsBodyTemperatureDTO save(VsBodyTemperatureDTO vsBodyTemperatureDTO);

    /**
     * Get all the vsBodyTemperatures.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<VsBodyTemperatureDTO> findAll(Pageable pageable);

    /**
     * Get all the vsBodyTemperatures by userid.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<VsBodyTemperatureDTO> findAllByUserid(String[] userids, String startDate, String endDate, Pageable pageable);


    /**
     * Get the "id" vsBodyTemperature.
     *
     * @param id the id of the entity
     * @return the entity
     */
    VsBodyTemperatureDTO findOne(String id);

    /**
     * Delete the "id" vsBodyTemperature.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
