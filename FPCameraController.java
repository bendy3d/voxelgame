package voxelgame;

import static org.lwjgl.opengl.GL11.*;
import java.nio.FloatBuffer;
import org.lwjgl.*;
import org.lwjgl.input.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

public class FPCameraController {

	// 3d vector to store the camera's position in
	private Vector3f position = null;
	private Vector3f lPosition = null;
	// the rotation around the Y axis of the camera
	private float yaw = 0.0f;
	// the rotation around the X axis of the camera
	private float pitch = 0.0f;
	private Vector3Float me;
	private Chunk chunk;

	public FPCameraController(float x, float y, float z) {
		// instantiate position Vector3f to the x y z params.
		position = new Vector3f(10, -100, -5);
		lPosition = new Vector3f(x, y, z);
		lPosition.x = -40f;
		lPosition.y = 15f;
		lPosition.z = 40f;
//		yaw = 138.5f;
//		pitch = 24.3f;
		
		chunk = new Chunk((int)x, (int)y, (int)z);
	}

	// increment the camera's current yaw rotation
	public void yaw(float amount) {
		// increment the yaw by the amount param
		yaw += amount;
	}

	// increment the camera's current yaw rotation
	public void pitch(float amount) {
		// increment the pitch by the amount param
		pitch -= amount;
	}

	// moves the camera forward relative to its current rotation (yaw)
	public void walkForward(float distance) {
		float xOffset = distance * (float) Math.sin(Math.toRadians(yaw));
		float zOffset = distance * (float) Math.cos(Math.toRadians(yaw));
		position.x -= xOffset;
		position.z += zOffset;
	}

	// moves the camera backward relative to its current rotation (yaw)
	public void walkBackwards(float distance) {
		float xOffset = distance * (float) Math.sin(Math.toRadians(yaw));
		float zOffset = distance * (float) Math.cos(Math.toRadians(yaw));
		position.x += xOffset;
		position.z -= zOffset;
	}

	// strafes the camera left relative to its current rotation (yaw)
	public void strafeLeft(float distance) {
		float xOffset = distance * (float) Math.sin(Math.toRadians(yaw - 90));
		float zOffset = distance * (float) Math.cos(Math.toRadians(yaw - 90));
		FloatBuffer lightPosition= BufferUtils.createFloatBuffer(4);
		lightPosition.put(lPosition.x-=xOffset).put(lPosition.y).put(lPosition.z+=zOffset).put(1.0f).flip();
		glLight(GL_LIGHT0, GL_POSITION, lightPosition);
		position.x -= xOffset;
		position.z += zOffset;
	}

	// strafes the camera right relative to its current rotation (yaw)
	public void strafeRight(float distance) {
		float xOffset = distance * (float) Math.sin(Math.toRadians(yaw + 90));
		float zOffset = distance * (float) Math.cos(Math.toRadians(yaw + 90));
		FloatBuffer lightPosition= BufferUtils.createFloatBuffer(4);
		lightPosition.put(lPosition.x-=xOffset).put(lPosition.y).put(lPosition.z+=zOffset).put(1.0f).flip();
		glLight(GL_LIGHT0, GL_POSITION, lightPosition);
		position.x -= xOffset;
		position.z += zOffset;
	}

	// moves the camera up relative to its current rotation (yaw)
	public void moveUp(float distance) {
		FloatBuffer lightPosition= BufferUtils.createFloatBuffer(4);
		lightPosition.put(lPosition.x).put(lPosition.y -= distance).put(lPosition.z).put(1.0f).flip();
		glLight(GL_LIGHT0, GL_POSITION, lightPosition);
		position.y -= distance;
	}

	// moves the camera down
	public void moveDown(float distance) {
		FloatBuffer lightPosition= BufferUtils.createFloatBuffer(4);
		lightPosition.put(lPosition.x).put(lPosition.y += distance).put(lPosition.z).put(1.0f).flip();
		glLight(GL_LIGHT0, GL_POSITION, lightPosition);
		position.y += distance;
	}

	// translates and rotate the matrix so that it looks through the camera
	// this does basically what gluLookAt() does
	public void lookThrough() {
		// Rotate the pitch around the X axis
		glRotatef(pitch, 1.0f, 0.0f, 0.0f);
		// Rotate the yaw around the Y axis
		glRotatef(yaw, 0.0f, 1.0f, 0.0f);
		// translate to the position vector's location
		glTranslatef(position.x, position.y, position.z);
		
		FloatBuffer lightPosition= BufferUtils.createFloatBuffer(4);
		lightPosition.put(lPosition.x).put(lPosition.y).put(lPosition.z).put(1.0f).flip();
		glLight(GL_LIGHT0, GL_POSITION, lightPosition);
	}

	@SuppressWarnings("unused")
	public void gameLoop() {
		FPCameraController camera = new FPCameraController(0, 0, 0);
		camera.yaw = 138.5f;
		camera.pitch = 24.3f;
		float dx = 0.0f;
		float dy = 0.0f;
		float dt = 0.0f; // length of frame
		float lastTime = 0.0f; // when the last frame was
		long time = 0;
		float mouseSensitivity = 0.10f;
		float movementSpeed = .35f;
		// hide the mouse
		Mouse.setGrabbed(true);

		// keep looping till the display window is closed the ESC key is down
		while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			time = Sys.getTime();
			lastTime = time;
			// distance in mouse movement //from the last getDX() call.
			dx = Mouse.getDX();
			// distance in mouse movement //from the last getDY() call.
			dy = Mouse.getDY();
			// Control camera yaw from x movement from the mouse
			camera.yaw(dx * mouseSensitivity);
			// Control camera pitch from y movement from the mouse
			camera.pitch(dy * mouseSensitivity);

			// when passing in the distance to move
			// we times the movementSpeed with dt this is a time scale
			// so if its a slow frame u move more then a fast frame
			// so on a slow computer you move just as fast as on a fast computer

			// move forward
			if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
				camera.walkForward(movementSpeed);
			}
			// move backwards
			if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
				camera.walkBackwards(movementSpeed);
			}
			// strafe left
			if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
				camera.strafeLeft(movementSpeed);
			}
			// strafe right
			if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
				camera.strafeRight(movementSpeed);
			}
			// move up
			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
				camera.moveUp(movementSpeed);

			}
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				camera.moveDown(movementSpeed);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
				System.out.println("\ncam pitch: " + camera.pitch);
				System.out.println("cam yaw: " + camera.yaw);
				System.out.println("look: " + camera.lPosition.toString());
				System.out.println("Position: " + camera.position.toString());
			}

			// set the model view matrix back to the identity
			glLoadIdentity();
			// look through the camera before you draw anything
			camera.lookThrough();
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			// you would draw your scene here.
			chunk.render();
			// draw the buffer to the screen
			Display.update();
			Display.sync(60);
		}
		Display.destroy();
	}
}
