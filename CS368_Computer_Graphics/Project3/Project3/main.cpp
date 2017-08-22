//
//  main.cpp
//  Project3
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

GLfloat pos[] = {-2,4,5,1}, amb[] = {0.7, 0.7, 0.7, 1.0};
GLfloat boardColor[] = {0.35, 0.2, 0.1, 1.0};
GLfloat back_amb_diff[] = {0.5, 0.9, 0.1, 1.0};
GLfloat spe[] = {0.25, 0.25, 0.25, 1.0};
GLfloat theta = 0, dt = 0.5, axes[3][3] = {{1,0,0},{0, 1, 0},{0, 0, 1}};

GLfloat railColor [] = {0.5, 0.5, 0.5, 0.5};
GLfloat wireColor [] = {0.1, 0.1, 0.1, 0.0};

GLboolean up = false;
GLboolean rightSide = true;
GLfloat horizontalSpinAngle = 0;
GLfloat verticalSpinAngle = 0;


GLUquadricObj *qObj;

int axis = 1;

void init(void){
    qObj = gluNewQuadric();
}

void drawBoard(){
    glPushMatrix();
    glTranslatef(0, -1.5, 0);
    glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, boardColor);
    //    glTranslated(0, -0.75, -0.8);
    //    glRotated(15, 1, 0, 0);
    glScaled(11, 0.5, 5);
    glutSolidCube(.3);
    glPopMatrix();
}

void drawRail(){
    glPushMatrix();
    
    glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, railColor);
    glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 100);
    //    glMaterialfv(GL_BACK, GL_AMBIENT_AND_DIFFUSE, back_amb_diff);
    //    glRotated(45,axes[axis][0], axes[axis][1], axes[axis][2]);
    //rail posts
    glTranslatef(1.5, 1.5, 0.65);
    glRotated(90, 1, 0, 0);
    gluCylinder(qObj, 0.05, 0.05, 3, 100, 1);
    glTranslatef(0, -1.3, 0);
    gluCylinder(qObj, 0.05, 0.05, 3, 100, 1);
    glTranslatef(-3, 0, 0);
    gluCylinder(qObj, 0.05, 0.05, 3, 100, 1);
    glTranslatef(0, 1.3, 0);
    gluCylinder(qObj, 0.05, 0.05, 3, 100, 1);
    glPopMatrix();
    
    //top rails
    glPushMatrix();
    glRotatef(90, 0, 1, 0);
    glTranslatef(-0.65, 1.5, -1.5);
    gluCylinder(qObj, 0.05, 0.05, 3, 100, 1);
    glTranslatef(1.3, 0, 0);
    gluCylinder(qObj, 0.05, 0.05, 3, 100, 1);
    glPopMatrix();
    
    //rail corners
    glPushMatrix();
    glTranslatef(1.5, 1.5, 0.65);
    gluSphere(qObj, .05, 100, 20);
    glTranslatef(0, 0, -1.3);
    gluSphere(qObj, .05, 100, 20);
    glTranslatef(-3, 0, 0);
    gluSphere(qObj, .05, 100, 20);
    glTranslatef(0, 0, 1.3);
    gluSphere(qObj, .05, 100, 20);
    glPopMatrix();
}

void drawMiddleSpheres(){
    glPushMatrix();
    glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, railColor);
    //    glTranslatef(0, 10, 0);
    //    glRotated(theta, 0 , 0 , 1);
    glTranslatef(0, -1, 0);
    //    glRotatef(theta, 0, 0, 1);
    gluSphere(qObj, 0.15, 100, 20);
    glTranslatef(0.3, 0, 0);
    gluSphere(qObj, 0.15, 100, 20);
    glTranslatef(-0.6, 0, 0);
    gluSphere(qObj, 0.15, 100, 20);
    glPopMatrix();
    
    glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, wireColor);
    glPushMatrix();
    glLineWidth(5);
    glBegin(GL_LINES);
    glVertex3d(0, -1, 0);
    glVertex3d(0, 1.5, 0.6);
    
    glVertex3d(0.3, -1, 0);
    glVertex3d(0.3, 1.5, 0.6);
    
    glVertex3d(-0.3, -1, 0);
    glVertex3d(-0.3, 1.5, 0.6);
    
    glVertex3d(0, -1, 0);
    glVertex3d(0, 1.5, -0.6);
    
    glVertex3d(0.3, -1, 0);
    glVertex3d(0.3, 1.5, -0.6);
    
    glVertex3d(-0.3, -1, 0);
    glVertex3d(-0.3, 1.5, -0.6);
    
    glEnd();
    glPopMatrix();
}

void drawRightEndSphere(){
    if(rightSide){
        glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, railColor);
        glPushMatrix();
        glTranslatef(0.6, 1, 0);
        glRotated(theta, 0 , 0 , 1);
        glTranslatef(0, -2, 0);
        gluSphere(qObj, 0.15, 100, 20);
        glPopMatrix();
        
        glPushMatrix();
        glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, wireColor);
        glTranslatef(0.6, 1.5, 1);
        glRotatef(theta/1.25, 0, 0, 1);
        glTranslatef(-0.6, -1.5, -1);
        glLineWidth(5);
        glBegin(GL_LINES);
        glVertex3d(0.6, -1, 0);
        glVertex3d(0.6, 1.5, 0.6);
        glVertex3d(0.6, -1, 0);
        glVertex3d(0.6, 1.5, -0.6);
        glEnd();
        glPopMatrix();
        
        
        if(theta-1 < 0){
            rightSide = false;
        }
    }
    else{
        glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, railColor);
        glPushMatrix();
        glTranslatef(0.6, -1, 0);
        gluSphere(qObj, 0.15, 100, 20);
        glPopMatrix();
        
        glPushMatrix();
        glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, wireColor);
        glTranslatef(-0.6, 1.5, 1);
        glRotatef(-theta/1.25, 0, 0, 1);
        glTranslatef(0.6, -1.5, -1);
        glLineWidth(5);
        glBegin(GL_LINES);
        glVertex3d(-0.6, -1, 0);
        glVertex3d(-0.6, 1.5, 0.6);
        glVertex3d(-0.6, -1, 0);
        glVertex3d(-0.6, 1.5, -0.6);
        glEnd();
        glPopMatrix();
        if(theta-1 < 0){
            rightSide = true;
        }
    }
}

void drawleftEndSphere (){
    if(!rightSide) {
        glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, railColor);
        glPushMatrix();
        glTranslatef(-0.6, 1, 0);
        glRotated(-theta, 0, 0, 1);
        glTranslatef(0, -2, 0);
        gluSphere(qObj, 0.15, 100, 20);
        glPopMatrix();
        
        glPushMatrix();
        glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, wireColor);
        glLineWidth(5);
        glBegin(GL_LINES);
        glVertex3d(0.6, -1, 0);
        glVertex3d(0.6, 1.5, 0.6);
        glVertex3d(0.6, -1, 0);
        glVertex3d(0.6, 1.5, -0.6);
        glEnd();
        glPopMatrix();
    }
    else{
        glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, railColor);
        glPushMatrix();
        glTranslatef(-0.6, -1, 0);
        gluSphere(qObj, 0.15, 100, 20);
        glPopMatrix();
        
        glPushMatrix();
        glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, wireColor);
        glLineWidth(5);
        glBegin(GL_LINES);
        glVertex3d(-0.6, -1, 0);
        glVertex3d(-0.6, 1.5, 0.6);
        glVertex3d(-0.6, -1, 0);
        glVertex3d(-0.6, 1.5, -0.6);
        glEnd();
        glPopMatrix();
    }
}

void display (void) {
    glEnable(GL_NORMALIZE);
    glClear(GL_COLOR_BUFFER_BIT|GL_DEPTH_BUFFER_BIT);
    glPushMatrix();
    glRotated(horizontalSpinAngle, 0, 1, 0);
    glRotated(verticalSpinAngle, 1, 0, 0);
    drawRail();
    drawBoard();
    drawMiddleSpheres();
    drawRightEndSphere();
    drawleftEndSphere();
    glPopMatrix();
    glutSwapBuffers();
}

void idle(void){
    //    if(theta >= 360) axis = (axis + 1) % 4;
    if(up == false){
        theta+= 0.5;
        if(theta >= 30){
            up = true;
        }
    }else{
        theta -= 0.5;
        if(theta <= 0){
            up = false;
        }
    }
    //    theta = (theta < 90) ? theta + dt : dt;
    glutPostRedisplay();
}

void keyboard(unsigned char key, int x, int y){
    switch (key) {
        case 'r':
            horizontalSpinAngle += 5;
            break;
            
        case 'l':
            horizontalSpinAngle -= 5;
            break;
        case 'u':
            verticalSpinAngle += 5;
            break;
        case 'd':
            verticalSpinAngle -= 5;
            break;
        default:
            break;
    }
}

int main(int argc, char * argv[]){
    cout << "Press L to turn left" << endl;
    cout << "Press R to turn right" << endl;
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_DOUBLE|GLUT_RGB|GLUT_DEPTH);
    glutInitWindowSize(700, 700);
    glutInitWindowPosition(200, 100);
    glutCreateWindow("Newtons Craddle");
    glClearColor(0.3, 0.3, 0.3, 0);
    glEnable(GL_DEPTH_TEST);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluPerspective(45, 1.0, 2, 8);
    //    glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff);
    //    glMaterialfv(GL_BACK, GL_AMBIENT_AND_DIFFUSE, back_amb_diff);
    glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, spe);
    glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 100);
    glLightfv(GL_LIGHT0, GL_AMBIENT, amb);
    glLightModeli(GL_LIGHT_MODEL_TWO_SIDE, GL_TRUE);
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
    glTranslated(0, 0, -5);
    glLightfv(GL_LIGHT0, GL_POSITION, pos);
    glEnable(GL_LIGHTING);
    glEnable(GL_LIGHT0);
    glutDisplayFunc(display);
    glutKeyboardFunc(keyboard);
    glutIdleFunc(idle);
    init();
    glutMainLoop();
}
