package br.com.bgrbarbosa.ms_scheduling.exams.aws.S3Controller;

import br.com.bgrbarbosa.ms_scheduling.exams.aws.S3Controller.dto.UploadResponseDTO;
import br.com.bgrbarbosa.ms_scheduling.exams.aws.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/exam-result")
public class S3Controller {

    @Autowired
    private S3Service s3Service;

    @PostMapping("/upload")
    public ResponseEntity<UploadResponseDTO> upload(@RequestParam("file") MultipartFile file) throws IOException, IOException {

        String s3Url = s3Service.uploadFile(file);
        UploadResponseDTO response = new UploadResponseDTO(file.getOriginalFilename(), s3Url);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<byte[]> download(@PathVariable String filename) {
        byte[] data = s3Service.downloadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .body(data);
    }
}
