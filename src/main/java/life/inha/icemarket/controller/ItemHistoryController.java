package life.inha.icemarket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import life.inha.icemarket.domain.Item;
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
import java.sql.Timestamp;

@RequiredArgsConstructor
@RestController
public class ItemHistoryController {
    private final ItemHistoryService itemHistoryService;

    @Operation(description = "복지물품 대여 기록 추가")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "잘못된 유저 복지물품 대여 기록 추가 요청"),
            @ApiResponse(responseCode = "403", description = "클라이언트의 접근 권한이 없음")
    })
    @PostMapping("/item-history/create")
    public ResponseEntity<ItemHistory> createItemHistory(@RequestBody ItemHistorySaveRequestDto requestDto) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        requestDto.setRent_at();
        return new ResponseEntity<ItemHistory>(itemHistoryService.create(requestDto),header, HttpStatus.OK);
    }

    @Operation(description = "복지물품 대여 기록에 반납 날짜 추가")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "잘못된 유저 복지물품 대여 기록 업데이트 요청"),
            @ApiResponse(responseCode = "403", description = "클라이언트의 접근 권한이 없음")
    })
    @GetMapping("/item-history/update/{id}")
    public ResponseEntity<ItemHistory> updateItemHistory(@PathVariable Integer id) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        return new ResponseEntity<ItemHistory>(itemHistoryService.update(id, new Timestamp(System.currentTimeMillis())),header,HttpStatus.OK);
    }

    @Operation(description = "복지물품 대여 기록 전체 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "잘못된 유저 복지물품 대여 기록 조회 요청"),
            @ApiResponse(responseCode = "403", description = "클라이언트의 접근 권한이 없음")
    })
    @GetMapping("/item-history/read")
    public ResponseEntity<Iterable<ItemHistory>> readItemHistory(){
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));
        return new ResponseEntity<Iterable<ItemHistory>>(itemHistoryService.readAll(),header,HttpStatus.OK);
    }

    @Operation(description = "복지물품 대여 기록 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "잘못된 유저 복지물품 대여 기록 삭제 요청"),
            @ApiResponse(responseCode = "403", description = "클라이언트의 접근 권한이 없음")
    })
    @GetMapping("/item-history/delete/{id}")
    public void deleteItemHistory(@PathVariable Integer id){
        itemHistoryService.delete(id);
    }
}
