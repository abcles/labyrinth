package abc.les.labyrinth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LabyrinthController.class)
public class LabyrinthControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateMockMvc() {
        assert mockMvc != null;
    }

    @Test
    public void getPathNormalCase() throws Exception {
        this.mockMvc
                .perform(post("/api/getpath")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{'from':{'x':0, 'y':0}}, 'to':{'x':1, 'y':1}}, 'matrix': [[true,true,true,true]]}"))
                .andExpect(status().isOk());
    }

    @Test
    void getPathInputMissingTo() throws Exception {
        this.mockMvc
                .perform(post("/api/getpath")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{'from':{'x':0, 'y':0}}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getPathNullInput() throws Exception {
        this.mockMvc
                .perform(post("/api/getpath")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getPathWrongJsonInput() throws Exception {
        this.mockMvc
                .perform(post("/api/getpath")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"))
                .andExpect(status().isBadRequest());
    }

}
