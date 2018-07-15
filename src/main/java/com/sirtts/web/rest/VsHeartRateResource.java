package com.sirtts.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sirtts.service.VsHeartRateService;
import com.sirtts.web.rest.errors.BadRequestAlertException;
import com.sirtts.web.rest.util.HeaderUtil;
import com.sirtts.web.rest.util.PaginationUtil;
import com.sirtts.service.dto.VsHeartRateDTO;
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
 * REST controller for managing VsHeartRate.
 */
@RestController
@RequestMapping("/api")
public class VsHeartRateResource {

    private final Logger log = LoggerFactory.getLogger(VsHeartRateResource.class);

    private static final String ENTITY_NAME = "vsHeartRate";

    private final VsHeartRateService vsHeartRateService;

    public VsHeartRateResource(VsHeartRateService vsHeartRateService) {
        this.vsHeartRateService = vsHeartRateService;
    }

    /**
     * POST  /vs-heart-rates : Create a new vsHeartRate.
     *
     * @param vsHeartRateDTO the vsHeartRateDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vsHeartRateDTO, or with status 400 (Bad Request) if the vsHeartRate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vs-heart-rates")
    @Timed
    public ResponseEntity<VsHeartRateDTO> createVsHeartRate(@Valid @RequestBody VsHeartRateDTO vsHeartRateDTO) throws URISyntaxException {
        log.debug("REST request to save VsHeartRate : {}", vsHeartRateDTO);
        if (vsHeartRateDTO.getId() != null) {
            throw new BadRequestAlertException("A new vsHeartRate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VsHeartRateDTO result = vsHeartRateService.save(vsHeartRateDTO);
        return ResponseEntity.created(new URI("/api/vs-heart-rates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vs-heart-rates : Updates an existing vsHeartRate.
     *
     * @param vsHeartRateDTO the vsHeartRateDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vsHeartRateDTO,
     * or with status 400 (Bad Request) if the vsHeartRateDTO is not valid,
     * or with status 500 (Internal Server Error) if the vsHeartRateDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vs-heart-rates")
    @Timed
    public ResponseEntity<VsHeartRateDTO> updateVsHeartRate(@Valid @RequestBody VsHeartRateDTO vsHeartRateDTO) throws URISyntaxException {
        log.debug("REST request to update VsHeartRate : {}", vsHeartRateDTO);
        if (vsHeartRateDTO.getId() == null) {
            return createVsHeartRate(vsHeartRateDTO);
        }
        VsHeartRateDTO result = vsHeartRateService.save(vsHeartRateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vsHeartRateDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vs-heart-rates : get all the vsHeartRates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of vsHeartRates in body
     */
    @GetMapping("/vs-heart-rates")
    @Timed
    public ResponseEntity<List<VsHeartRateDTO>> getAllVsHeartRates(Pageable pageable) {
        log.debug("REST request to get a page of VsHeartRates");
        Page<VsHeartRateDTO> page = vsHeartRateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vs-heart-rates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /vs-heart-rates/byUserid : get all the vsHeartRates by userid.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of vsHeartRates in body
     */
    @GetMapping("/vs-heart-rates/byUserid")
    @Timed
    public ResponseEntity<List<VsHeartRateDTO>> getAllVsHeartRatesByUserid(String[] userids, String startDate, String endDate, Pageable pageable) {
        log.debug("REST request to get a page of VsHeartRates by userid");
        Page<VsHeartRateDTO> page = vsHeartRateService.findAllByUserid(userids, startDate, endDate, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vs-heart-rates/byUserid");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /vs-heart-rates/:id : get the "id" vsHeartRate.
     *
     * @param id the id of the vsHeartRateDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vsHeartRateDTO, or with status 404 (Not Found)
     */
    @GetMapping("/vs-heart-rates/{id}")
    @Timed
    public ResponseEntity<VsHeartRateDTO> getVsHeartRate(@PathVariable String id) {
        log.debug("REST request to get VsHeartRate : {}", id);
        VsHeartRateDTO vsHeartRateDTO = vsHeartRateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vsHeartRateDTO));
    }

    /**
     * DELETE  /vs-heart-rates/:id : delete the "id" vsHeartRate.
     *
     * @param id the id of the vsHeartRateDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vs-heart-rates/{id}")
    @Timed
    public ResponseEntity<Void> deleteVsHeartRate(@PathVariable String id) {
        log.debug("REST request to delete VsHeartRate : {}", id);
        vsHeartRateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
