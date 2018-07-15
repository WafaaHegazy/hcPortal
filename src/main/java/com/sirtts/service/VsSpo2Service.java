package com.sirtts.service;

import com.sirtts.service.dto.VsSpo2DTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing VsSpo2.
 */
public interface VsSpo2Service {

    /**
     * Save a vsSpo2.
     *
     * @param vsSpo2DTO the entity to save
     * @return the persisted entity
     */
    VsSpo2DTO save(VsSpo2DTO vsSpo2DTO);

    /**
     * Get all the vsSpo2S.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<VsSpo2DTO> findAll(Pageable pageable);

    /**
     * Get all the vsSpo2S by userid.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<VsSpo2DTO> findAllByUserid(String[] userids, String startDate, String endDate, Pageable pageable);

    /**
     * Get the "id" vsSpo2.
     *
     * @param id the id of the entity
     * @return the entity
     */
    VsSpo2DTO findOne(String id);

    /**
     * Delete the "id" vsSpo2.
     *
     * @param id the id of the entity
     */
    void delete(String id);
}
