package abc.les.labyrinth;

import abc.les.labyrinth.service.LabyrinthService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class LabyrinthControllerTests {
    private final LabyrinthService labyrinthService = new LabyrinthService();
    private final LabyrinthController labyrinthController = new LabyrinthController(labyrinthService);

    @Test
    void getPathNormalCase(){
        String payload = "{'from':{'x':0, 'y':0}}, 'to':{'x':1, 'y':1}}, 'matrix': [[true,true,true,true]]}";
        ResponseEntity res = labyrinthController.getPath(payload);
        System.out.println(res);
        assert res.getBody() != null;
    }

    @Test
    void getPathInputMissingTo(){
        String payload = "{'from':{'x':0, 'y':0}}";
        ResponseEntity res = labyrinthController.getPath(payload);
        System.out.println(res);
        assert res.getStatusCode() == HttpStatus.BAD_REQUEST;
    }

    @Test
    void getPathNullInput(){
        String payload = "";
        ResponseEntity res = labyrinthController.getPath(payload);
        System.out.println(res);
        assert res.getStatusCode() == HttpStatus.BAD_REQUEST;
    }

    @Test
    void getPathWrongJsonInput(){
        String payload = "{";
        ResponseEntity res = labyrinthController.getPath(payload);
        System.out.println(res);
        assert res.getStatusCode() == HttpStatus.BAD_REQUEST;
    }

}
