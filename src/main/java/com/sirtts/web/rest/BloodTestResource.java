package com.sirtts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sirtts.service.BloodTestService;
import com.sirtts.web.rest.errors.BadRequestAlertException;
import com.sirtts.web.rest.util.HeaderUtil;
import com.sirtts.web.rest.util.PaginationUtil;
import com.sirtts.service.dto.BloodTestDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing BloodTest.
 */
@RestController
@RequestMapping("/api")
public class BloodTestResource {

    private final Logger log = LoggerFactory.getLogger(BloodTestResource.class);

    private static final String ENTITY_NAME = "bloodTest";

    private final BloodTestService bloodTestService;

    public BloodTestResource(BloodTestService bloodTestService) {
        this.bloodTestService = bloodTestService;
    }

    /**
     * POST  /blood-tests : Create a new bloodTest.
     *
     * @param bloodTestDTO the bloodTestDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bloodTestDTO, or with status 400 (Bad Request) if the bloodTest has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/blood-tests")
    @Timed
    public ResponseEntity<BloodTestDTO> createBloodTest(@Valid @RequestBody BloodTestDTO bloodTestDTO) throws URISyntaxException {
        log.debug("REST request to save BloodTest : {}", bloodTestDTO);
        if (bloodTestDTO.getId() != null) {
            throw new BadRequestAlertException("A new bloodTest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BloodTestDTO result = bloodTestService.save(bloodTestDTO);
        return ResponseEntity.created(new URI("/api/blood-tests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /blood-tests : Updates an existing bloodTest.
     *
     * @param bloodTestDTO the bloodTestDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bloodTestDTO,
     * or with status 400 (Bad Request) if the bloodTestDTO is not valid,
     * or with status 500 (Internal Server Error) if the bloodTestDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/blood-tests")
    @Timed
    public ResponseEntity<BloodTestDTO> updateBloodTest(@Valid @RequestBody BloodTestDTO bloodTestDTO) throws URISyntaxException {
        log.debug("REST request to update BloodTest : {}", bloodTestDTO);
        if (bloodTestDTO.getId() == null) {
            return createBloodTest(bloodTestDTO);
        }
        BloodTestDTO result = bloodTestService.save(bloodTestDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bloodTestDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /blood-tests : get all the bloodTests.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of bloodTests in body
     */
    @GetMapping("/blood-tests")
    @Timed
    public ResponseEntity<List<BloodTestDTO>> getAllBloodTests(Pageable pageable) {
        log.debug("REST request to get a page of BloodTests");
        Page<BloodTestDTO> page = bloodTestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/blood-tests");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /blood-tests/byUserid : get all the bloodTests by userid.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of bloodTests in body
     */
    @GetMapping("/blood-tests/byUserid")
    @Timed
    public ResponseEntity<List<BloodTestDTO>> getAllBloodTestsByUserid(String[] userids, String startDate, String endDate, Pageable pageable) {
        log.debug("REST request to get a page of BloodTests by userid");
        Page<BloodTestDTO> page = bloodTestService.findAllByUserid(userids, startDate, endDate, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/blood-tests/byUserid");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /blood-tests/:id : get the "id" bloodTest.
     *
     * @param id the id of the bloodTestDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bloodTestDTO, or with status 404 (Not Found)
     */
    @GetMapping("/blood-tests/{id}")
    @Timed
    public ResponseEntity<BloodTestDTO> getBloodTest(@PathVariable String id) {
        log.debug("REST request to get BloodTest : {}", id);
        BloodTestDTO bloodTestDTO = bloodTestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bloodTestDTO));
    }

    /**
     * DELETE  /blood-tests/:id : delete the "id" bloodTest.
     *
     * @param id the id of the bloodTestDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/blood-tests/{id}")
    @Timed
    public ResponseEntity<Void> deleteBloodTest(@PathVariable String id) {
        log.debug("REST request to delete BloodTest : {}", id);
        bloodTestService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * GET Columns  /blood-tests/:id : get bloodTest columns.
     *
     * @return the ResponseEntity with status 200 (OK)
     */
    @GetMapping("/blood-tests/columns")
    @Timed
    public ResponseEntity<List> getColumns() {
        log.debug("REST request to get columns");
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bloodTestService.findColumns()));
    }
}
