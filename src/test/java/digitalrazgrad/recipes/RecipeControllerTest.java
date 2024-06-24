//package digitalrazgrad.recipes;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.hamcrest.core.StringContains.containsString;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class RecipeControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @Test
//    void testAddRecipe() throws Exception {
//        this.mockMvc.perform(get("/recipe/add")).andDo(print()).andExpect(status().isOk())
//                .andExpect(content().string(containsString("<h2 class=\"text-center p-4 text-white\">ДОБАВЯНЕ НА РЕЦЕПТА</h2>")))
//                .andExpect(content().string(containsString("<input type=\"text\" class=\"form-control\" id=\"name\" placeholder=\"Име на рецептата\" name=\"name\" value=\"\">")))
//                .andExpect(content().string(containsString("<input type=\"text\" class=\"form-control\" id=\"description\" placeholder=\"Описание на рецептата\" name=\"description\" value=\"\">")))
//                .andExpect(content().string(containsString("<button class=\"btn btn-success btn-form\" type=\"submit\">Запиши</button>")))
//                .andExpect(content().string(containsString("<button class=\"btn btn-danger btn-form\" type=\"button\" onclick=\"window.location.href='../'\">Отказ</button>")));
//    }
//    @Test
//    void testEditRecipe() throws Exception {
//        this.mockMvc.perform(get("/recipe/edit?id=1")).andDo(print()).andExpect(status().isOk())
//                .andExpect(content().string(containsString("<h2 class=\"text-center p-4 text-white\">РЕДАКТИРАНЕ НА РЕЦЕПТА</h2>")))
//                .andExpect(content().string(containsString("<input type=\"text\" class=\"form-control\" id=\"name\" placeholder=\"Име на рецептата\" name=\"name\" value=\"Торта\">")))
//                .andExpect(content().string(containsString("<input type=\"text\" class=\"form-control\" id=\"description\" placeholder=\"Описание на рецептата\" name=\"description\" value=\"Смесват се яйцата и брашното. Разбиват се 20 минути след което се добавя маслото и бананите. Пече се на умерена фурна 30 минути.\">")))
//                .andExpect(content().string(containsString("<button class=\"btn btn-success btn-form\" type=\"submit\">Редактирай</button>")))
//                .andExpect(content().string(containsString("<button class=\"btn btn-danger btn-form\" type=\"button\" onclick=\"window.location.href='../recipe/list'\">Отказ</button>")));
//    }
//    @Test
//    void testListRecipe() throws Exception {
//        this.mockMvc.perform(get("/recipe/list")).andDo(print()).andExpect(status().isOk())
//                .andExpect(content().string(containsString("<th scope=\"col\">ID</th>")))
//                .andExpect(content().string(containsString("<th scope=\"col\">Рецепта</th>")))
//                .andExpect(content().string(containsString("<th scope=\"col\">Тип</th>")))
//                .andExpect(content().string(containsString("<th scope=\"col\">Описание</th>")))
//                .andExpect(content().string(containsString("<th scope=\"col\">Рейтинг</th>")))
//                .andExpect(content().string(containsString("<th class=\"col-1\" scope=\"col\"></th>")))
//                .andExpect(content().string(containsString("Торта")))
//                .andExpect(content().string(containsString("Шоколад")))
//                .andExpect(content().string(containsString("Смесват се яйцата и брашното. Разбиват се 20 минути след което се добавя маслото и бананите. Пече се на умерена фурна 30 минути.")))
//                .andExpect(content().string(containsString("<input class=\"btn btn-danger btn-block\" type=\"submit\" value=\"Изтрий\"/>")))
//                .andExpect(content().string(containsString("<input class=\"btn btn-warning btn-block\" type=\"submit\" value=\"Редактирай\"/>")));
//    }
//}
