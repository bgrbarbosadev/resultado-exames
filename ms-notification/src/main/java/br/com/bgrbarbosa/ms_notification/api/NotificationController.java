package br.com.bgrbarbosa.ms_notification.api;

import br.com.bgrbarbosa.ms_notification.api.dto.converter.NotificationMapper;
import br.com.bgrbarbosa.ms_notification.api.dto.response.NotificationResponseDTO;
import br.com.bgrbarbosa.ms_notification.business.NotificationService;
import br.com.bgrbarbosa.ms_notification.business.impl.MailServiceImpl;
import br.com.bgrbarbosa.ms_notification.infraestruture.entity.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final MailServiceImpl mailService;
    private final NotificationService service;
    private final NotificationMapper mapper;

    @GetMapping
    public ResponseEntity<Page<NotificationResponseDTO>>findAll(
            @PageableDefault(page = 0, size = 10, sort = "uuid", direction = Sort.Direction.ASC) Pageable page) {

        List<NotificationResponseDTO> listDTO = mapper.parseToListResponseDTO(service.findAll(page));
        Page<NotificationResponseDTO> pageDTO = mapper.toPageResponseDTO(listDTO, page);
        return ResponseEntity.ok(pageDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponseDTO>findById(@PathVariable String id) {
        NotificationResponseDTO notification = mapper.parseToResponseDto(service.findById(id));
        return ResponseEntity.status(HttpStatus.OK).body(notification);
    }

    @PostMapping(value = "/re-send/{id}")
    public ResponseEntity<Object> sendNotification(@PathVariable String id) {
        Notification notification = service.findById(id);
        mailService.sendMail(mapper.parseToResponseDto(notification));
        return ResponseEntity.status(HttpStatus.OK).body(notification);
    }
}
