//
//  main.cpp
//  Project1.1
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
    glLineWidth(0.5);
    glBegin(GL_LINES);
    
    //red parallel lines
    glColor3f(1.0, 0.0, 0.0);
    glVertex3f(0.0, 180.0, 0.0);
    glVertex3f(399, 180.0, 0);
    glVertex3f(0.0, 120.0, 0.0);
    glVertex3f(399, 120.0, 0);
    
    //black intersecting Lines
    glColor3f(0.0, 0.0, 0.0);
    int x1 = 199;
    int x2 = 199;
    int y1 = 300;
    int y2 = 0;
    
    //lines going counter clock wise from middle
    while(y1 > 175){
        
        if(x1 > 0){
            glVertex3f(x1, y1, 0);
            glVertex3f(x2, y2, 0);
            x1 -=20;
            x2 +=20;
        }
        else {
            glVertex3f(x1, y1, 0);
            glVertex3f(x2, y2, 0);
            y1 -=20;
            y2 +=20;
        }
    }
    
    //reset the endpoints of the lines
    x1 = 199;
    x2 = 199;
    y1 = 300;
    y2 = 0;
    
    //lines going clock wise from middle
    while(y1 > 175){
        
        if(x1 > 0){
            glVertex3f(x1, y2, 0);
            glVertex3f(x2, y1, 0);
            x1 -=20;
            x2 +=20;
        }
        else {
            glVertex3f(x1, y2, 0);
            glVertex3f(x2, y1, 0);
            y1 -=20;
            y2 +=20;
        }
    }
    
    
    glEnd();
    glFlush();
}

int main(int argc, char* argv[]){
    
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
    glutInitWindowSize(400, 200);
    glutInitWindowPosition(200, 100);
    glutCreateWindow("p1.1");
    glClearColor(1.0, 1.0, 1.0, 0.0);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluOrtho2D(0.0, 400.0, 0.0, 300.0);
    glutDisplayFunc(display);
    glutMainLoop();
    
    return 0;
}

