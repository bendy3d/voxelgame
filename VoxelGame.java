/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package voxelgame;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author ben
 */
public class VoxelGame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        VoxelGame vg = new VoxelGame();
        vg.start();
    }

    private void start() {
        try {
            createWindow();
            initGL();
            render();
        } catch (Exception e) {
        
        }
    }

    private void createWindow() throws Exception{
        Display.setFullscreen(false);

        Display.setDisplayMode(new DisplayMode(640, 480));
        Display.setTitle("Program 2");
        Display.create();
    }
    
    private void initGL() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();

        glOrtho(-320, 320, -240, 240, 12, -12);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    }
    
    private void render() {
        
        while (!Display.isCloseRequested()) {
            try{
                glBegin(GL_LINE_LOOP); 
                //Top
                glColor3f(0.0f,0.0f,1.0f); 
                glVertex3f( 10.0f, 10.0f,-10.0f); 
                glVertex3f(-10.0f, 10.0f,-10.0f); 
                glVertex3f(-10.0f, 10.0f, 10.0f); 
                glVertex3f( 10.0f, 10.0f, 10.0f); 
                glEnd();
                
                glBegin(GL_LINE_LOOP);
                //Bottom
                glColor3f(1.0f, 0.0f, 0.0f);
                glVertex3f( 10.0f,-10.0f, 10.0f); 
                glVertex3f(-10.0f,-10.0f, 10.0f); 
                glVertex3f(-10.0f,-10.0f,-10.0f); 
                glVertex3f( 10.0f,-10.0f,-10.0f);
                glEnd();
                
                glBegin(GL_LINE_LOOP);
                //Front
                glColor3f(0.0f, 1.0f, 0.0f);
                glVertex3f( 10.0f, 10.0f, 10.0f); 
                glVertex3f(-10.0f, 10.0f, 10.0f); 
                glVertex3f(-10.0f,-10.0f, 10.0f); 
                glVertex3f( 10.0f,-10.0f, 10.0f);
                glEnd();
                
                glBegin(GL_LINE_LOOP);
                //Back
                glColor3f(1.0f, 1.0f, 0.0f);
                glVertex3f( 10.0f,-10.0f,-10.0f); 
                glVertex3f(-10.0f,-10.0f,-10.0f); 
                glVertex3f(-10.0f, 10.0f,-10.0f); 
                glVertex3f( 10.0f, 10.0f,-10.0f);
                glEnd();
                
                glBegin(GL_LINE_LOOP);
                //Left
                glColor3f(0.0f, 1.0f, 1.0f);
                glVertex3f(-10.0f, 10.0f, 10.0f); 
                glVertex3f(-10.0f, 10.0f,-10.0f); 
                glVertex3f(-10.0f,-10.0f,-10.0f); 
                glVertex3f(-10.0f,-10.0f, 10.0f);
                glEnd(); 
                
                glBegin(GL_LINE_LOOP);
                //Right
                glColor3f(1.0f, 1.0f, 1.0f);
                glVertex3f( 10.0f, 10.0f,-10.0f); 
                glVertex3f( 10.0f, 10.0f, 10.0f); 
                glVertex3f( 10.0f,-10.0f, 10.0f); 
                glVertex3f( 10.0f,-10.0f,-10.0f);
                glEnd();
                
            } catch (Exception e) {
                
            }
        }
    } 
    
}
