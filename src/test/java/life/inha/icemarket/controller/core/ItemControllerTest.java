package life.inha.icemarket.controller.core;

import life.inha.icemarket.controller.ItemController;
import life.inha.icemarket.domain.Item;
import life.inha.icemarket.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
public class ItemControllerTest {
    @Autowired
    private MockMvc mvc;

    @InjectMocks
    ItemController itemController;

    @Mock
    ItemService itemService;

    @BeforeEach
    public void init(){
        mvc = MockMvcBuilders.standaloneSetup(itemController)
                .alwaysExpect(status().isOk())
                .build();
    }

    @Test
    public void 복지물품_생성(){
        //given
        Item item = new Item("이름", "이미지 주소", 10);
        given(itemService.create(any(ItemSaveRequestDto))).willReturn()

        //when


        //then
    }
}
