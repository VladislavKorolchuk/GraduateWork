package ru.work.graduatework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import ru.work.graduatework.Entity.*;
import ru.work.graduatework.dto.AdsDto;
import ru.work.graduatework.dto.CommentDto;
import ru.work.graduatework.dto.CreateAdsDto;
import ru.work.graduatework.dto.FullAdsDto;
import ru.work.graduatework.dto.repository.AdsRepository;
import ru.work.graduatework.service.AdsService;

import java.util.Collection;

@RestController()
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/ads")
public class AdsController {

    private final Logger logger = LoggerFactory.getLogger(AdsController.class);

    private final AdsRepository adsRepository;

    private final AdsService adsService;


    @Operation(
            operationId = "getALLAds",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Ads.class))))
            },
            tags = "Объявления")
    @GetMapping()    // Получить объявление
    public Collection<AdsDto> getAllAds() {
        logger.info("Current Method is - getAds");
        return adsService.getAds();
    }

    @Operation(summary = "addAds", operationId = "addAds",
            responses = {@ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            schema = @Schema(implementation = Ads.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "Объявления")
    @PostMapping()   // Добавить объявления
    public ResponseEntity<AdsDto> addAds(@RequestBody CreateAdsDto createAdsDto, @RequestParam MultipartFile adsImage) {
        logger.info("Current Method is - addAds");
        AdsDto adsDto = adsService.addAds(createAdsDto, adsImage);
        if (adsDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else return ResponseEntity.ok(adsDto);
    }

    @Operation(summary = "getComments", operationId = "getComments",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ResponseWrapperComment.class)))),
                    @ApiResponse(responseCode = "404",
                            description = "Not Found")}, tags = "Объявления")
    @GetMapping("/{ad_pk}/comments")  // Получить комментарии
    public ResponseEntity<ResponseWrapperComment> getComments(@PathVariable("ad_pk") String add_pk) {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "addComments", operationId = "addComments",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            schema = @Schema(implementation = Ads.class))), //Comments.класс
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "Объявления")
    @PostMapping("/{ad_pk}/comments") // Добавить Комментарии
    public ResponseEntity<CommentDto> addComments(@PathVariable("ad_pk") int ad_pk, @RequestBody CommentDto commentDto) {
        logger.info("Current Method is - addComments");
        CommentDto comment = adsService.addComments(ad_pk, commentDto);
        if (comment == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else return ResponseEntity.ok(comment);
    }

    @Operation(summary = "getFullAd", operationId = "getAds",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            schema = @Schema(implementation = FullAds.class))), // FullAds.class
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),}, tags = "Объявления")
    @GetMapping("/{id}")
    public ResponseEntity<FullAdsDto> getFullAd(@PathVariable int id) { // параметры и  required: true
        logger.info("Current Method is - getFullAd");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "removeAds", operationId = "removeAds",
            responses = {@ApiResponse(responseCode = "204", description = "No Content", content = {}),
                    @ApiResponse(responseCode = "401",
                            description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
            }, tags = "Объявления")
    @DeleteMapping("/{id}")   // Убрать рекламу
    public AdsDto removeAds(@PathVariable int id) { // параметры
        logger.info("Current Method is - removeAds");
        return adsService.removeAds(id);
    }

    @Operation(summary = "updateAds", operationId = "updateAds",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            schema = @Schema(implementation = Ads.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "Объявления")
    @PatchMapping("/{id}")   // Обновить рекламу
    public ResponseEntity<AdsDto> updateAds(@PathVariable int id, @RequestBody AdsDto adsDto) {
        logger.info("Current Method is - updateAds");
        AdsDto adsDtoUpdate = adsService.updateAds(id, adsDto);
        if (adsDtoUpdate == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else return ResponseEntity.ok(adsDtoUpdate);

    }

    @Operation(summary = "getComments", operationId = "getComments_1",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            schema = @Schema(implementation = Ads.class))), // Comment.class
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
            }, tags = "Объявления")
    @GetMapping("/{ad_pk}/comments/{id}")   // Получить комментарии по id
    public ResponseEntity<CommentDto> getCommentsId(@PathVariable("ad_pk") String ad_pk,
                                                    @PathVariable int id,
                                                    @RequestBody CommentDto commentDto) {
        logger.info("Current Method is - getCommentsId");
        return ResponseEntity.ok(commentDto);
    }

    @Operation(summary = "deleteComments", operationId = "deleteComments",
            responses = {@ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "Объявления")
    @DeleteMapping("/{ad_pk}/comments/{id}")   // Удалить комментарии по id
    public ResponseEntity<CommentDto> deleteCommentsId(@PathVariable("ad_pk") String ad_pk,
                                                       @PathVariable int id) {
        logger.info("Current Method is - deleteCommentsId");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "updateComments", operationId = "updateComments",
            responses = {@ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            schema = @Schema(implementation = Ads.class))), //  Comment.class
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "Объявления")
    @PatchMapping("/{ad_pk}/comments/{id}")    // Обновление комментария по id
    public ResponseEntity<CommentDto> updateCommentsId(@PathVariable("ad_pk") String adPk,
                                                       @PathVariable int id,
                                                       @RequestBody CommentDto commentDto) { // параметры, required: true
        logger.info("Current Method is - updateCommentsId");
        return ResponseEntity.ok(commentDto);

    }

    @Operation(summary = "getAdsMe", operationId = "getAdsMeUsingGET",
            responses = {@ApiResponse(responseCode = "200",
                    content = @Content(
                            mediaType = MediaType.ALL_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Ads.class)))), //ResponseWrapperAds
                    @ApiResponse(responseCode = "404",
                            description = "Not Found"),
                    @ApiResponse(responseCode = "403", description = "Forbidden", content = {}),
                    @ApiResponse(responseCode = "401", description = "Unauthorized", content = {})}, tags = "Объявления")
    @GetMapping("/me")   // Получить рекламу
    public ResponseEntity<ResponseWrapperAds> getAdsMe() { // параметры нужно разобрать и дописать
        logger.info("Current Method is - getAdsMe");
        return ResponseEntity.ok(new ResponseWrapperAds());
    }

}
