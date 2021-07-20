//import static org.junit.Assert.assertEquals;
//
//import java.awt.Point;
//
//import org.junit.Test;
////import static org.junit.Assert.*;
//
//public class GridTest {
//	// primarily checks the validity of the isVehicleMovable method
//	@Test
//	public void isMovableTest() {
//		/*Grid grid = new Grid();
//		Vehicle v1 = new GoalVehicle(new Point(0, 2), new Point(4, 2), 2, "img/red.png");
//		grid.addVehicle(v1);
//		assertEquals(true,v1.equals(grid.getVehicle(new Point(1, 2))));
//		assertEquals(true,v1.equals(grid.getVehicle(new Point(0, 2))));
//		
//		Vehicle v2 = new VerticalVehicle(new Point(2,2), 3, "img/3x_purple_vertical.png");
//		grid.addVehicle(v2);
//		assertEquals(true,v2.equals(grid.getVehicle(new Point(2, 2))));
//		assertEquals(true,v2.equals(grid.getVehicle(new Point(2, 3))));
//		assertEquals(true,v2.equals(grid.getVehicle(new Point(2, 4))));
//		
//		assertEquals(false, grid.isVehicleMovable(v2, -4));
//		assertEquals(false, grid.isVehicleMovable(v2, -3));
//		assertEquals(true, grid.isVehicleMovable(v2, -2));
//		assertEquals(true, grid.isVehicleMovable(v2, -1));
//		assertEquals(true, grid.isVehicleMovable(v2, 1));
//		assertEquals(false, grid.isVehicleMovable(v2, 2));
//		assertEquals(false, grid.isVehicleMovable(v1, 1));
//		assertEquals(false, grid.isVehicleMovable(v1, -1));
//		assertEquals(false, grid.isVehicleMovable(v1, 3));
//		assertEquals(false, grid.isGameSolved());
//		grid.moveVehicle(v2, 1);
//		
//		assertEquals(true,v1.equals(grid.getVehicle(new Point(1, 2))));
//		assertEquals(true,v1.equals(grid.getVehicle(new Point(0, 2))));
//		assertEquals(true,v2.equals(grid.getVehicle(new Point(2, 3))));
//		assertEquals(true,v2.equals(grid.getVehicle(new Point(2, 4))));
//		assertEquals(true,v2.equals(grid.getVehicle(new Point(2, 5))));
//		
//		assertEquals(true, grid.isGameSolved());
//		grid.moveVehicle(v2, -2);
//		
//		assertEquals(true,v1.equals(grid.getVehicle(new Point(1, 2))));
//		assertEquals(true,v1.equals(grid.getVehicle(new Point(0, 2))));
//		assertEquals(true,v2.equals(grid.getVehicle(new Point(2, 1))));
//		assertEquals(true,v2.equals(grid.getVehicle(new Point(2, 2))));
//		assertEquals(true,v2.equals(grid.getVehicle(new Point(2, 3))));
//		
//		assertEquals(false, grid.isGameSolved());
//	}
//	
//	@Test
//	public void isEqualsTest() {
//		Grid grid1 = new Grid();
//		Vehicle v1 = new GoalVehicle(new Point(0, 2), new Point(4, 2), 2, "img/red.png");
//		grid1.addVehicle(v1);
//		
//		Vehicle v2 = new VerticalVehicle(new Point(2, 2), 3, "img/3x_purple_vertical.png");
//		grid1.addVehicle(v2);
//		
//		Grid grid2 = grid1.clone();
//		
//		Vehicle v11 = grid1.getVehicle(new Point(0, 2));
//		Vehicle v12 = grid1.getVehicle(new Point(2, 2));
//		Vehicle v21 = grid2.getVehicle(new Point(0, 2));
//		Vehicle v22 = grid2.getVehicle(new Point(2, 2));
//		
//		assertEquals(true,v11.equals(grid1.getVehicle(new Point(1, 2))));
//		assertEquals(true,v11.equals(grid1.getVehicle(new Point(0, 2))));
//		assertEquals(true,v12.equals(grid1.getVehicle(new Point(2, 2))));
//		assertEquals(true,v12.equals(grid1.getVehicle(new Point(2, 3))));
//		assertEquals(true,v12.equals(grid1.getVehicle(new Point(2, 4))));
//		
//		assertEquals(true,v21.equals(grid2.getVehicle(new Point(1, 2))));
//		assertEquals(true,v21.equals(grid2.getVehicle(new Point(0, 2))));
//		assertEquals(true,v22.equals(grid2.getVehicle(new Point(2, 2))));
//		assertEquals(true,v22.equals(grid2.getVehicle(new Point(2, 3))));
//		assertEquals(true,v22.equals(grid2.getVehicle(new Point(2, 4))));
//		
//		assertEquals(true, grid1.equals(grid2));
//		grid2.moveVehicle(v22, 1);
//		
//		assertEquals(true,v11.equals(grid1.getVehicle(new Point(1, 2))));
//		assertEquals(true,v11.equals(grid1.getVehicle(new Point(0, 2))));
//		assertEquals(true,v12.equals(grid1.getVehicle(new Point(2, 2))));
//		assertEquals(true,v12.equals(grid1.getVehicle(new Point(2, 3))));
//		assertEquals(true,v12.equals(grid1.getVehicle(new Point(2, 4))));
//		
//		assertEquals(true,v21.equals(grid2.getVehicle(new Point(1, 2))));
//		assertEquals(true,v21.equals(grid2.getVehicle(new Point(0, 2))));
//		assertEquals(true,v22.equals(grid2.getVehicle(new Point(2, 3))));
//		assertEquals(true,v22.equals(grid2.getVehicle(new Point(2, 4))));
//		assertEquals(true,v22.equals(grid2.getVehicle(new Point(2, 5))));
//		
//		assertEquals(false, grid1.equals(grid2));
//		grid2.moveVehicle(v22,-1);
//		
//		assertEquals(true,v11.equals(grid1.getVehicle(new Point(1, 2))));
//		assertEquals(true,v11.equals(grid1.getVehicle(new Point(0, 2))));
//		assertEquals(true,v12.equals(grid1.getVehicle(new Point(2, 2))));
//		assertEquals(true,v12.equals(grid1.getVehicle(new Point(2, 3))));
//		assertEquals(true,v12.equals(grid1.getVehicle(new Point(2, 4))));
//		
//		assertEquals(true,v21.equals(grid2.getVehicle(new Point(1, 2))));
//		assertEquals(true,v21.equals(grid2.getVehicle(new Point(0, 2))));
//		assertEquals(true,v22.equals(grid2.getVehicle(new Point(2, 2))));
//		assertEquals(true,v22.equals(grid2.getVehicle(new Point(2, 3))));
//		assertEquals(true,v22.equals(grid2.getVehicle(new Point(2, 4))));
//		
//		assertEquals(true, grid1.equals(grid2));
//		*/
//	}
//}
