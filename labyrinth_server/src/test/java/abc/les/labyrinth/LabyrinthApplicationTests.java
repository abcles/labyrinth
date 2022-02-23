package abc.les.labyrinth;

import abc.les.labyrinth.service.LabyrinthService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class
LabyrinthApplicationTests {

	@Autowired
	private LabyrinthController labyrinthController;

	@Autowired
	private LabyrinthService labyrinthService;

	@Test
	public void contextLoads() throws Exception {
		assertNotNull(labyrinthController);
		assertNotNull(labyrinthService);
	}

	@Test
	public void main() {
		LabyrinthApplication.main(new String[] {});
		assertTrue(true);
	}
}
