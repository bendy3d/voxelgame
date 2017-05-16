/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package groupVoxelGame;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.GLU;

/**
 *
 * @author ben
 */
public class VoxelGame {

	private FPCameraController fpc;
	private DisplayMode displayMode;

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		VoxelGame vg = new VoxelGame();
		vg.start();
	}

	private void start() {
		try {
			createWindow();
			initGL();
			fpc  = new FPCameraController(20, 20, 40);
			fpc.gameLoop();
		} catch (Exception e) {
			System.out.println("ERROR!!!");
//			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	private void createWindow() throws Exception {
		Display.setFullscreen(false);

		DisplayMode d[] = Display.getAvailableDisplayModes();
		for (int i= 0; i< d.length; i++) {
			if (d[i].getWidth() == 640
					&& d[i].getHeight() == 480
					&& d[i].getBitsPerPixel() == 32) {
				displayMode= d[i];
				break;
			}
		}
		
		Display.setDisplayMode(displayMode);
		Display.setTitle("Voxel Game");
		Display.create();
		}
	

	private void initGL() {
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		float aspect = displayMode.getWidth()/displayMode.getHeight();

		GLU.gluPerspective(100.0f, aspect, 0.1f, 300.0f);
		
		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_COLOR_ARRAY);
		glEnable(GL_DEPTH_TEST);
		glMatrixMode(GL_MODELVIEW);
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		
		//for texturing
		glEnable(GL_TEXTURE_2D);
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);
	}

}
