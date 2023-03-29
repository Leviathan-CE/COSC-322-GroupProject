package Simulation;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

public class TestNueron {

	
	Nueron n1 = new Nueron(new double[] {.2,1.4,5f});
	
	@Test
	public void testForawrdChaining() {		
		Nueron n2 = new Nueron(n1);
		Nueron n3 = new Nueron(n2);
		
		assertTrue(n1.next().ID == n2.ID);
		assertTrue(n2.next().ID == n3.ID);
	}
	@Test
	public void testbackwardChaining() {
	
		Nueron n2 = new Nueron(n1);
		Nueron n3 = new Nueron(n2);
		
		assertTrue(n1.prevoius() == null);
		assertTrue(n2.prevoius().ID == n1.ID);
	}
	@Test
	public void testValuechaining() {
		Nueron n2 = new Nueron(n1);
	
		
		assertTrue(n1.Wieghts.length == n2.Wieghts.length);
		assertTrue(n1.Wieghts[0] == n2.Wieghts[0]);
		assertTrue(n1.Wieghts[1] == n2.Wieghts[1]);
		assertTrue(n1.Wieghts[2] == n2.Wieghts[2]);
	}
}
