package digitalrazgrad.recipes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testListProduct() throws Exception {
        this.mockMvc.perform(get("/product/list")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("<th scope=\"col\">ID</th>")))
                .andExpect(content().string(containsString("<th scope=\"col\">Тип</th>")))
                .andExpect(content().string(containsString("<th scope=\"col\">Продукт</th>")))
                .andExpect(content().string(containsString("<th class=\"col-1\" scope=\"col\"></th>")))
                .andExpect(content().string(containsString("Шоколад")))
                .andExpect(content().string(containsString("<input class=\"btn btn-danger btn-block\" type=\"submit\" value=\"Изтрий\"/>")))
                .andExpect(content().string(containsString("<input class=\"btn btn-warning btn-block\" type=\"submit\" value=\"Редактирай\"/>")));
    }
    @Test
    void testAddProduct() throws Exception {
        this.mockMvc.perform(get("/product/add")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("<h2 class=\"text-center p-4 text-white\">ДОБАВЯНЕ НА ПРОДУКТ</h2>")))
                .andExpect(content().string(containsString("<input type=\"text\" class=\"form-control\" id=\"name\" placeholder=\"Име на продукт\" name=\"name\" value=\"\">")))
                .andExpect(content().string(containsString("<button class=\"btn btn-success btn-form\" type=\"submit\">Запиши</button>")))
                .andExpect(content().string(containsString("<button class=\"btn btn-danger btn-form\" type=\"button\" onclick=\"window.location.href='../'\">Отказ</button>")));
    }
    @Test
    void testEditProduct() throws Exception {
        this.mockMvc.perform(get("/product/edit?id=6")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("<h2 class=\"text-center p-4 text-white\">РЕДАКТИРАНЕ НА ПРОДУКТ</h2>")))
                .andExpect(content().string(containsString("<input type=\"text\" class=\"form-control\" id=\"name\" placeholder=\"Име на продукт\" name=\"name\" value=\"Шоколад\">")))
                .andExpect(content().string(containsString("<button class=\"btn btn-success btn-form\" type=\"submit\">Редактирай</button>")))
                .andExpect(content().string(containsString("<button class=\"btn btn-danger btn-form\" type=\"button\" onclick=\"window.location.href='../product/list'\">Отказ</button>")));
    }
}
