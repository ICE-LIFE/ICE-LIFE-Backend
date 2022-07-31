package life.inha.icemarket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import life.inha.icemarket.config.swagger.ApiDocumentResponse;
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
    @ApiDocumentResponse
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
    @ApiDocumentResponse
    @PostMapping("/update/{id}")
    public ResponseEntity<Item> updateItem(@RequestBody ItemSaveRequestDto requestDto, @PathVariable Integer id) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return new ResponseEntity<Item>(itemService.update(requestDto, id), header, HttpStatus.OK);
    }

    @Operation(description = "복지물품 삭제")
    @ApiDocumentResponse
    @GetMapping("/delete/{id}")
    public void deleteItem(@PathVariable Integer id) {
        itemService.delete(id);
    }
}
