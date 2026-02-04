package com.eyetech.events.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import com.eyetech.events.dtos.EventRequestDTO;
import com.eyetech.events.dtos.EventResponseDTO;
import com.eyetech.events.model.Event;
import com.eyetech.events.repositories.EventRepository;
import com.eyetech.events.repositories.EventSpecification;

@Service
public class EventService {

    @Value("${aws.bucket.name}")
    private String bucketName;

    private final S3Client s3Client;

    @Autowired
    private EventRepository eventRepository;

    public EventService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public Event createEvent(EventRequestDTO event) {
        String imgUrl = null;

        if (event.image() != null && !event.image().isEmpty()) {
            imgUrl = uploadImage(event.image());
        }

        Event newEvent = new Event();
        newEvent.setTitle(event.title());
        newEvent.setDescription(event.description());
        newEvent.setEventUrl(event.eventUrl());
        newEvent.setDate(new Date(event.date()));
        newEvent.setRemote(event.remote());
        newEvent.setImgUrl(imgUrl);

        eventRepository.save(newEvent);

        return newEvent;
    }

    private String uploadImage(MultipartFile multipartFile) {
        String fileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(multipartFile.getContentType())
                    .build();

            s3Client.putObject(
                    putObjectRequest,
                    RequestBody.fromBytes(multipartFile.getBytes())
            );

            return s3Client.utilities()
                    .getUrl(GetUrlRequest.builder()
                            .bucket(bucketName)
                            .key(fileName)
                            .build())
                    .toString();

        } catch (Exception e) {
            throw new RuntimeException("Error uploading image to S3", e);
        }
    }

    public Page<EventResponseDTO> filterEvents(
        String title,
        Date date,
        String city,
        int page,
        int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(
            "date").ascending());
        Date today = Date.valueOf(LocalDate.now());

        Specification<Event> spec = Specification.unrestricted();

        spec = spec.and(EventSpecification.upcomingEvents(today));

        if (title != null && !title.isBlank()) {
            spec = spec.and(EventSpecification.titleContains(title));
        }

        if (date != null) {
            spec = spec.and(EventSpecification.dateEquals(date));
        }

        if (city != null && !city.isBlank()) {
            spec = spec.and(EventSpecification.cityEquals(city));
        }

        return eventRepository.findAll(spec, pageable).map(
            event -> new EventResponseDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),
                event.getRemote(),
                event.getEventUrl(),
                event.getEventUrl()
            )
        );
    }

}
