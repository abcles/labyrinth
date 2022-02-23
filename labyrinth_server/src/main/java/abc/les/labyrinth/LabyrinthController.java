package abc.les.labyrinth;

import abc.les.labyrinth.service.LabyrinthService;
import abc.les.labyrinth.service.MatrixNode;
import com.google.gson.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class LabyrinthController {

    private final LabyrinthService labyrinthService;

    public LabyrinthController(LabyrinthService labyrinthService) {
        this.labyrinthService = labyrinthService;
    }

    @PostMapping(path="/getpath")
    public ResponseEntity getPath(@RequestBody String payload){
        Gson gson = new Gson();
        JsonElement body = null;
        try {
            body = gson.fromJson(payload, JsonElement.class);
        }
        catch (JsonSyntaxException ex) {
            return ResponseEntity.badRequest().body("Input data not in valid json format");
        }

        JsonObject bodycontent = null;
        try {
            bodycontent = body.getAsJsonObject();
        }
        catch (NullPointerException ex) {
            return ResponseEntity.badRequest().body("Null input data");
        }

        System.out.println(bodycontent);

        int fromRow = -1;
        int fromColumn = -1;
        try {
            JsonObject fromContent = bodycontent.get("from").getAsJsonObject();
            fromRow = fromContent.get("x").getAsInt();
            fromColumn = fromContent.get("y").getAsInt();
        }
        catch (NullPointerException ex) {
            return ResponseEntity.badRequest().body("<<from>> parameter isn't provided correctly. It's expected: " +
                    "'from' : { 'x': xVal, 'y': yVal})");
        }

        int toRow = -1;
        int toColumn = -1;
        try {
            JsonObject fromContent = bodycontent.get("to").getAsJsonObject();
            toRow = fromContent.get("x").getAsInt();
            toColumn = fromContent.get("y").getAsInt();
        }
        catch (NullPointerException ex) {
            return ResponseEntity.badRequest().body("<<to>> parameter isn't provided correctly. It's expected: " +
                    "'to' : { 'x': xVal, 'y': yVal})");
        }

        List<List<Boolean>> matrixValues = new ArrayList<>();
        try {
            JsonArray matrixContent = bodycontent.get("matrix").getAsJsonArray();
            for(JsonElement line : matrixContent) {
                JsonArray nodes = line.getAsJsonArray();
                List<Boolean> values = new ArrayList<>();
                for (JsonElement n : nodes) {
                    values.add(n.getAsBoolean());
                }
                matrixValues.add(values);
            }
        }
        catch (NullPointerException ex) {
            return ResponseEntity.badRequest().body("<<matrix>> parameter isn't provided correctly. It's expected: " +
                    "[[X11, X12, X13, ..., X1n]][X21, X22, X23, ..., X2n]...[Xm1, Xm2, Xm3, ..., Xmn]");
        }

        if (matrixValues.size() <= 0) {
            return ResponseEntity.badRequest().body("<<matrix>> parameter isn't provided correctly. It's expected: " +
                    "[[X11, X12, X13, ..., X1n]][X21, X22, X23, ..., X2n]...[Xm1, Xm2, Xm3, ..., Xmn]");
        }

        boolean[][] matrix = new boolean[matrixValues.size()][matrixValues.get(0).size()];
        for (int i = 0; i< matrixValues.size(); i++) {
            for (int j = 0; j < matrixValues.get(0).size(); j++) {
                matrix[i][j] = matrixValues.get(i).get(j);
            }
        }

        List<MatrixNode> result = this.labyrinthService.getPath(matrix, fromRow, fromColumn, toRow, toColumn);

        if (result.size() == 0) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(this.labyrinthService.getPath(matrix, fromRow, fromColumn, toRow, toColumn));
        }
    }

}
