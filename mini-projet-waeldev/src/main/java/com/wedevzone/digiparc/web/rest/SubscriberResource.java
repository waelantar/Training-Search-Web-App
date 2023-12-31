package com.wedevzone.digiparc.web.rest;

import com.wedevzone.digiparc.repository.SubscriberRepository;
import com.wedevzone.digiparc.service.SubscriberService;
import com.wedevzone.digiparc.service.dto.SubscriberDTO;
import com.wedevzone.digiparc.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.wedevzone.digiparc.domain.Subscriber}.
 */
@RestController
@RequestMapping("/api")
public class SubscriberResource {

    private final Logger log = LoggerFactory.getLogger(SubscriberResource.class);

    private static final String ENTITY_NAME = "subscriber";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubscriberService subscriberService;

    private final SubscriberRepository subscriberRepository;

    public SubscriberResource(SubscriberService subscriberService, SubscriberRepository subscriberRepository) {
        this.subscriberService = subscriberService;
        this.subscriberRepository = subscriberRepository;
    }

    /**
     * {@code POST  /subscribers} : Create a new subscriber.
     *
     * @param subscriberDTO the subscriberDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new subscriberDTO, or with status {@code 400 (Bad Request)} if the subscriber has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/subscribers")
    public ResponseEntity<SubscriberDTO> createSubscriber(@Valid @RequestBody SubscriberDTO subscriberDTO) throws URISyntaxException {
        log.debug("REST request to save Subscriber : {}", subscriberDTO);
        if (subscriberDTO.getId() != null) {
            throw new BadRequestAlertException("A new subscriber cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SubscriberDTO result = subscriberService.save(subscriberDTO);
        return ResponseEntity
            .created(new URI("/api/subscribers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /subscribers/:id} : Updates an existing subscriber.
     *
     * @param id the id of the subscriberDTO to save.
     * @param subscriberDTO the subscriberDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subscriberDTO,
     * or with status {@code 400 (Bad Request)} if the subscriberDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the subscriberDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/subscribers/{id}")
    public ResponseEntity<SubscriberDTO> updateSubscriber(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SubscriberDTO subscriberDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Subscriber : {}, {}", id, subscriberDTO);
        if (subscriberDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subscriberDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subscriberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SubscriberDTO result = subscriberService.update(subscriberDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subscriberDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /subscribers/:id} : Partial updates given fields of an existing subscriber, field will ignore if it is null
     *
     * @param id the id of the subscriberDTO to save.
     * @param subscriberDTO the subscriberDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated subscriberDTO,
     * or with status {@code 400 (Bad Request)} if the subscriberDTO is not valid,
     * or with status {@code 404 (Not Found)} if the subscriberDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the subscriberDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/subscribers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SubscriberDTO> partialUpdateSubscriber(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SubscriberDTO subscriberDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Subscriber partially : {}, {}", id, subscriberDTO);
        if (subscriberDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, subscriberDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!subscriberRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SubscriberDTO> result = subscriberService.partialUpdate(subscriberDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, subscriberDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /subscribers} : get all the subscribers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of subscribers in body.
     */
    @GetMapping("/subscribers")
    public List<SubscriberDTO> getAllSubscribers() {
        log.debug("REST request to get all Subscribers");
        return subscriberService.findAll();
    }

    /**
     * {@code GET  /subscribers/:id} : get the "id" subscriber.
     *
     * @param id the id of the subscriberDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the subscriberDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/subscribers/{id}")
    public ResponseEntity<SubscriberDTO> getSubscriber(@PathVariable Long id) {
        log.debug("REST request to get Subscriber : {}", id);
        Optional<SubscriberDTO> subscriberDTO = subscriberService.findOne(id);
        return ResponseUtil.wrapOrNotFound(subscriberDTO);
    }

    /**
     * {@code DELETE  /subscribers/:id} : delete the "id" subscriber.
     *
     * @param id the id of the subscriberDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/subscribers/{id}")
    public ResponseEntity<Void> deleteSubscriber(@PathVariable Long id) {
        log.debug("REST request to delete Subscriber : {}", id);
        subscriberService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
