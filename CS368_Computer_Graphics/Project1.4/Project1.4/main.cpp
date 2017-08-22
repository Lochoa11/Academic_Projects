//
//  main.cpp
//  Project1.4
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
    float x1 = 40;
    float x2 = 350;
    float y1 = 40;
    float y2 = 350;
    float color = 0;
    for (int i = 0; i < 20; i++) {
        glColor3f(color, color, color);
        glBegin(GL_QUADS);
        glVertex3f(x1, y1, 0.0);
        glVertex3f(x1, y2, 0.0);
        glVertex3f(x2, y2, 0.0);
        glVertex3f(x2, y1, 0.0);
        color += .05;
        x1 += 7.5;
        x2 -= 7.5;
        y1 += 7.5;
        y2 -= 7.5;
        glEnd();
    }
    glFlush();
}

int main(int argc, char* argv[]){
    
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
    glutInitWindowSize(400, 400);
    glutInitWindowPosition(200, 100);
    glutCreateWindow("p1.4");
    glClearColor(1.0, 1.0, 1.0, 0.0);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluOrtho2D(0.0, 400.0, 0.0, 400.0);
    glutDisplayFunc(display);
    glutMainLoop();
    
    return 0;
}

