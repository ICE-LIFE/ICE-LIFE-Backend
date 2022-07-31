package life.inha.icemarket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import life.inha.icemarket.config.swagger.ApiDocumentResponse;
import life.inha.icemarket.domain.ItemHistory;
import life.inha.icemarket.dto.ItemHistorySaveRequestDto;
import life.inha.icemarket.service.ItemHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;

@Tag(name = "ItemHistory", description = "복지불품 대여 기록 API")
@RequiredArgsConstructor
@RestController
public class ItemHistoryController {
    private final ItemHistoryService itemHistoryService;

    @Operation(description = "복지물품 대여 기록 추가")
    @ApiDocumentResponse
    @PostMapping("/item-history/create")
    public ResponseEntity<ItemHistory> createItemHistory(@RequestBody ItemHistorySaveRequestDto requestDto) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        requestDto.setRentAt();
        return new ResponseEntity<ItemHistory>(itemHistoryService.create(requestDto), header, HttpStatus.OK);
    }

    @Operation(description = "복지물품 대여 기록에 반납 날짜 추가")
    @ApiDocumentResponse
    @GetMapping("/item-history/update/{id}")
    public ResponseEntity<ItemHistory> updateItemHistory(@PathVariable Integer id) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return new ResponseEntity<ItemHistory>(itemHistoryService.update(id, new Timestamp(System.currentTimeMillis())), header, HttpStatus.OK);
    }

    @Operation(description = "복지물품 대여 기록 전체 조회")
    @ApiDocumentResponse
    @GetMapping("/item-history/read")
    public ResponseEntity<Iterable<ItemHistory>> readItemHistory() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return new ResponseEntity<Iterable<ItemHistory>>(itemHistoryService.readAll(), header, HttpStatus.OK);
    }

    @Operation(description = "복지물품 대여 기록 삭제")
    @ApiDocumentResponse
    @GetMapping("/item-history/delete/{id}")
    public void deleteItemHistory(@PathVariable Integer id) {
        itemHistoryService.delete(id);
    }
}
