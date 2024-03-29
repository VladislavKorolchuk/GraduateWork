package ru.work.graduatework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/image")
@CrossOrigin(value = {"http://localhost:3000"})
public class ImageController {

    private final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Operation(summary = "updateAdsImage", operationId = "updateAdsImage",
            responses = {@ApiResponse(responseCode = "200", description = "OK"
                    , content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE
                    , schema = @Schema(type = "array", format = "byte"))),
                    @ApiResponse(responseCode = "404",
                            description = "Not Found")}, tags = "Image")
    @PostMapping("/{id}")
    public ResponseEntity<?> updateAdsImage(@PathVariable int id, @RequestParam MultipartFile imageFile) {
        logger.info("Class ImageController, current method is - updateAdsImage");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
