package com.sirtts.service;

import com.sirtts.service.dto.BloodTestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing BloodTest.
 */
public interface BloodTestService {

    /**
     * Save a bloodTest.
     *
     * @param bloodTestDTO the entity to save
     * @return the persisted entity
     */
    BloodTestDTO save(BloodTestDTO bloodTestDTO);

    /**
     * Get all the bloodTests.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BloodTestDTO> findAll(Pageable pageable);

    /**
     * Get all the bloodTests by userid.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<BloodTestDTO> findAllByUserid(String[] userids, String startDate, String endDate, Pageable pageable);

    /**
     * Get the "id" bloodTest.
     *
     * @param id the id of the entity
     * @return the entity
     */
    BloodTestDTO findOne(String id);

    /**
     * Delete the "id" bloodTest.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Get the bloodTest columns.
     *
     * @return the entity
     */
    List findColumns();

}
