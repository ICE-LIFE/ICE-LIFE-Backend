package life.inha.icemarket.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import life.inha.icemarket.domain.Item;
import life.inha.icemarket.dto.ItemSaveRequestDto;
import life.inha.icemarket.respository.ItemRepository;
import life.inha.icemarket.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerTest {

    @InjectMocks
    ItemController itemController;

    @Autowired
    MockMvc mockMvc;

    @Mock
    ItemService itemService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(itemController)
                .alwaysExpect(status().isOk())
                .build();
    }

    @Test
    @WithMockUser(username = "테스트 계정",roles = {"ADMIN"})
    public void 복지물품_추가() throws Exception{
        Integer amount = 100;
        ItemSaveRequestDto requestDto = new ItemSaveRequestDto("복지물품 이름", "복지물품 이미지 url", amount);
        Item item = new Item("복지물품 이름", "복지물품 이미지 url",amount);
        item.setId(1);
        String content = objectMapper.writeValueAsString(requestDto);

        when(itemService.create(any())).thenReturn(item);
        String responseBody = mockMvc.perform(post("/item/create")
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(responseBody).contains("\"name\":\"복지물품 이름\"");
        assertThat(responseBody).contains("\"image\":\"복지물품 이미지 url\"");
        assertThat(responseBody).contains("\"amount\":100");
    }

    @Test
    @WithMockUser(username = "테스트 계정",roles = {"ADMIN"})
    public void 복지물품_목록_읽기() throws Exception{
        Integer amount = 100;
        Item item1 = new Item("복지물품 이름 1", "복지물품 이미지 url 1", amount);
        Item item2 = new Item("복지물품 이름 2", "복지물품 이미지 url 2", amount);
        item1.setId(1);
        item2.setId(2);
        Iterable<Item> items = new ArrayList<Item>(Arrays.asList(item1, item2));

        when(itemService.readAll()).thenReturn(items);
        String responseBody = mockMvc.perform(get("/item/read"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(responseBody).contains(objectMapper.writeValueAsString(item1));
        assertThat(responseBody).contains(objectMapper.writeValueAsString(item2));
    }

    @Test
    @WithMockUser(username = "테스트 계정",roles = {"ADMIN"})
    public void 복지물품_수정() throws Exception {
        Integer newAmount = 40;
        ItemSaveRequestDto requestDto = new ItemSaveRequestDto("수정된 복지물품 이름", "수정된 복지물품 이미지 url", newAmount);
        Item newItem = new Item("수정된 복지물품 이름", "수정된 복지물품 이미지 url", newAmount);
        newItem.setId(1);
        String content = objectMapper.writeValueAsString(requestDto);

        when(itemService.update(any(), any())).thenReturn(newItem);
        String responseBody = mockMvc.perform(put("/item/update/1")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(responseBody).contains(objectMapper.writeValueAsString(newItem));
    }

    @Test
    @WithMockUser(username = "테스트 계정",roles = {"ADMIN"})
    public void 복지물품_삭제() throws Exception {
        Integer id = 1;
        doNothing().when(itemService).delete(eq(id));

        mockMvc.perform(delete("/item/delete/1"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(itemService, times(1)).delete(eq(id));
    }

}
