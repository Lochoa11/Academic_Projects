//
//  main.cpp
//  Project1.2
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
    glBegin(GL_QUADS);
    
    int x1 = 50;
    int x2 = 150;
    int y1 = 50;
    int y2 = 150;
    
    for (int i = 0; i < 5; i++) {
        for (int j = 0; j < 5; j++) {
            glVertex3f(x1, y1, 0.0);
            glVertex3f(x1, y2, 0.0);
            glVertex3f(x2, y2, 0.0);
            glVertex3f(x2, y1, 0.0);
            x1 += 120;
            x2 += 120;
        }
        x1 = 50;
        x2 = 150;
        y1 += 120;
        y2 += 120;
    }
    
    glEnd();
    glFlush();
}

int main(int argc, char* argv[]){
    
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
    glutInitWindowSize(700, 700);
    glutInitWindowPosition(200, 100);
    glutCreateWindow("p1.2");
    glClearColor(1.0, 1.0, 1.0, 0.0);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluOrtho2D(0.0, 700.0, 0.0, 700.0);
    glutDisplayFunc(display);
    glutMainLoop();
    
    return 0;
}

