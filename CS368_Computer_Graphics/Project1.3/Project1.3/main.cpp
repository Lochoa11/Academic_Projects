//
//  main.cpp
//  Project1.3
//
//  Created by Lin Ochoa on 8/21/17.
//  Copyright © 2017 Lin. All rights reserved.
//

#include <iostream>
#include <OpenGL/gl.h>
#include <OpenGl/glu.h>
#include <GLUT/glut.h>
#include <stdlib.h>
#include <math.h>

using namespace std;

void display(void){
    glClear(GL_COLOR_BUFFER_BIT);
    glColor3f(0.0, 0.0, 0.0);
    
    //  counter for horizontal lines
    int horizontalBorders = 700;
    int counter = 0;
    
    while (horizontalBorders > 0) {
        glLineWidth(3.0);
        glBegin(GL_LINES);
        glColor3f(0.5, 0.5, 0.5);
        
        //grey horizontal lines
        glVertex3f(0.0, horizontalBorders, 0.0);
        glVertex3f(1000.0, horizontalBorders, 0.0);
        
        int sideBorders = 80;
        if (counter % 4 == 0) {
            sideBorders -= 00;
        }else if(counter % 4 == 1){
            sideBorders -= 20;
        }
        else if(counter % 4 == 2){
            sideBorders += 0;
        }
        else{
            sideBorders += 20;
        }
        counter++;
        
        glEnd();
        
        //cordinates for square
        
        int x1 = sideBorders;
        int x2 = sideBorders + 70;
        int y1 = horizontalBorders-0.5;
        int y2 = horizontalBorders-69.5;
        
        for (int j = 7; j > 0; j--) {
            glLineWidth(3.0);
            glColor3f(0.5, 0.5, 0.5);
            glBegin(GL_LINES);
            //first grey vertical line in the row
            glVertex3f(sideBorders, horizontalBorders, 0.0);
            glVertex3f(sideBorders, horizontalBorders-70, 0.0);
            
            glEnd();
            sideBorders += 140;
            
            glBegin(GL_QUADS);
            glColor3f(0.0, 0.0, 0.0);
            
            glVertex3f(x1, y1, 0.0);
            glVertex3f(x1, y2, 0.0);
            glVertex3f(x2, y2, 0.0);
            glVertex3f(x2, y1, 0.0);
            x1 += 140;
            x2 += 140;
            
            glEnd();
            
            glColor3f(0.5, 0.5, 0.5);
            glLineWidth(3.0);
            glBegin(GL_LINES);
            //grey lines following the black squares
            glVertex3f(sideBorders+71, horizontalBorders, 0.0);
            glVertex3f(sideBorders+71, horizontalBorders-70, 0.0);
            
            glEnd();
        }
        horizontalBorders-=70;
    }
    
    
    
    glFlush();
}

int main(int argc, char* argv[]){
    
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
    glutInitWindowSize(1000, 800);
    glutInitWindowPosition(200, 100);
    glutCreateWindow("p1.3");
    glClearColor(1.0, 1.0, 1.0, 0.0);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluOrtho2D(0.0, 1000.0, 0.0, 800.0);
    glutDisplayFunc(display);
    glutMainLoop();
    
    return 0;
}

