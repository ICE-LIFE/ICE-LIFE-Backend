package life.inha.icemarket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import life.inha.icemarket.domain.Item;
import life.inha.icemarket.dto.ItemSaveRequestDto;
import life.inha.icemarket.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@Tag(name = "items", description = "복지물품 목록 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;

    @Operation(description = "복지물품 추가")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "잘못된 유저 복지물품 추가 요청"),
            @ApiResponse(responseCode = "403", description = "클라이언트의 접근 권한이 없음")
    })
    @PostMapping("/create")
    public ResponseEntity<Item> createItem(@RequestBody ItemSaveRequestDto requestDto) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return new ResponseEntity<Item>(itemService.create(requestDto), header, HttpStatus.OK);
    }

    @Operation(description = "복지물품 목록 읽기")
    @GetMapping("/read")
    public ResponseEntity<Iterable<Item>> readAllItems() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return new ResponseEntity<Iterable<Item>>(itemService.readAll(), header, HttpStatus.OK);
    }

    @Operation(description = "복지물품 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "잘못된 유저 복지물품 수정 요청"),
            @ApiResponse(responseCode = "403", description = "클라이언트의 접근 권한이 없음")
    })
    @PostMapping("/update/{id}")
    public ResponseEntity<Item> updateItem(@RequestBody ItemSaveRequestDto requestDto, @PathVariable Integer id) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return new ResponseEntity<Item>(itemService.update(requestDto, id), header, HttpStatus.OK);
    }

    @Operation(description = "복지물품 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "잘못된 유저 복지물품 삭제 요청"),
            @ApiResponse(responseCode = "403", description = "클라이언트의 접근 권한이 없음")
    })
    @GetMapping("/delete/{id}")
    public void deleteItem(@PathVariable Integer id) {
        itemService.delete(id);
    }
}
