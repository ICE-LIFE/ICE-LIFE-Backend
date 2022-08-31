package life.inha.icemarket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import life.inha.icemarket.domain.ItemHistory;
import life.inha.icemarket.dto.ItemHistorySaveRequestDto;
import life.inha.icemarket.service.ItemHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;


@SpringBootTest
@AutoConfigureMockMvc
public class ItemHistoryControllerTest {

    @InjectMocks
    ItemHistoryController itemHistoryController;

    @Autowired
    MockMvc mockMvc;

    @Mock
    ItemHistoryService itemHistoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(itemHistoryController)
                .alwaysExpect(status().isOk())
                .build();
    }

    @Test
    @WithMockUser(username = "테스트 계정", roles = {"ADMIN"})
    public void 복지물품_대여_기록_추가() throws Exception {
        Integer itemId = 1;
        Integer managerId = 12224047;
        ItemHistorySaveRequestDto requestDto = new ItemHistorySaveRequestDto(itemId, managerId);
        Timestamp rentAt = requestDto.getRentAt();
        ItemHistory itemHistory = new ItemHistory(itemId,managerId,rentAt);
        itemHistory.setId(1);
        String content = objectMapper.writeValueAsString(requestDto);

        when(itemHistoryService.create(any())).thenReturn(itemHistory);

        String responseBody = mockMvc.perform(post("/item-history/create")
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        assertThat(responseBody).contains("\"itemId\":1");
        assertThat(responseBody).contains("\"managerId\":12224047");
        assertThat(responseBody).contains("\"rentAt\":"+rentAt.getTime());
    }

    @Test
    @WithMockUser(username = "테스트 계정", roles = {"ADMIN"})
    public void 복지물품_대여_기록에_반납_날짜_추가() throws Exception {
        Integer itemId = 1;
        Integer managerId = 12224047;
        ItemHistorySaveRequestDto requestDto = new ItemHistorySaveRequestDto(itemId, managerId);
        Timestamp rentAt = requestDto.getRentAt();
        ItemHistory itemHistory = new ItemHistory(itemId,managerId,rentAt);
        Timestamp returnAt = new Timestamp(System.currentTimeMillis());
        itemHistory.update(returnAt);
        itemHistory.setId(1);

        when(itemHistoryService.update(any(),any())).thenReturn(itemHistory);

        String responseBody = mockMvc.perform(get("/item-history/update/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(responseBody).contains("\"itemId\":1");
        assertThat(responseBody).contains("\"managerId\":12224047");
        assertThat(responseBody).contains("\"rentAt\":"+rentAt.getTime());
        assertThat(responseBody).contains("\"returnAt\":"+returnAt.getTime());
    }

    @Test
    @WithMockUser(username = "테스트 계정", roles = {"ADMIN"})
    public void 복지물품_대여_기록_전체_조회() throws Exception {
        Integer itemId1 = 1;
        Integer managerId1 = 12224047;
        ItemHistorySaveRequestDto requestDto1 = new ItemHistorySaveRequestDto(itemId1, managerId1);
        Timestamp rentAt1 = requestDto1.getRentAt();
        ItemHistory itemHistory1 = new ItemHistory(itemId1,managerId1,rentAt1);
        Timestamp returnAt1 = new Timestamp(System.currentTimeMillis());
        itemHistory1.update(returnAt1);
        itemHistory1.setId(1);

        Integer itemId2 = 2;
        Integer managerId2 = 12222222;
        ItemHistorySaveRequestDto requestDto2 = new ItemHistorySaveRequestDto(itemId2, managerId2);
        Timestamp rentAt2 = requestDto1.getRentAt();
        ItemHistory itemHistory2 = new ItemHistory(itemId2,managerId2,rentAt2);
        Timestamp returnAt2 = new Timestamp(System.currentTimeMillis());
        itemHistory2.update(returnAt2);
        itemHistory2.setId(2);

        Iterable<ItemHistory> itemHistories = new ArrayList<ItemHistory>(Arrays.asList(itemHistory1, itemHistory2));

        when(itemHistoryService.readAll()).thenReturn(itemHistories);

        String responseBody = mockMvc.perform(get("/item-history/read"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertThat(responseBody).contains("\"itemId\":1");
        assertThat(responseBody).contains("\"managerId\":12224047");
        assertThat(responseBody).contains("\"rentAt\":"+rentAt1.getTime());
        assertThat(responseBody).contains("\"returnAt\":"+returnAt1.getTime());

        assertThat(responseBody).contains("\"itemId\":2");
        assertThat(responseBody).contains("\"managerId\":12222222");
        assertThat(responseBody).contains("\"rentAt\":"+rentAt2.getTime());
        assertThat(responseBody).contains("\"returnAt\":"+returnAt2.getTime());
    }

    @Test
    @WithMockUser(username = "테스트 계정", roles = {"ADMIN"})
    public void 복지물품_대여_기록_삭제() throws Exception {
        Integer id = 1;
        doNothing().when(itemHistoryService).delete(eq(id));

        mockMvc.perform(delete("/item-history/delete/"+id))
                .andDo(print())
                .andExpect(status().isOk());

        verify(itemHistoryService, times(1)).delete(eq(id));
    }
}