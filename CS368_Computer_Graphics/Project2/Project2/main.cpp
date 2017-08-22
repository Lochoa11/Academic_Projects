//
//  main.cpp
//  Project2
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

float sliderVert[3][4];
float trackVert[3][4];
float gradientBarVert[4];
float bigSquareVert[4];
int width = 800, height = 650, numberOfSliders = 0, type = GL_LINE_STRIP, v, sliderY[3], selectedTrack, distanceBetweenCursorandSliderY1, distanceBetweenCursorandSliderY2, distanceBetweenCursorandGradientX1, distanceBetweenCursorandGradientX2;
bool rubberbanding = false, clickedOnTrack, onSlider, clickedOnGradient = false;
float globalRGBControls[3] = {0, 0, 0};
float leftGradientColor[3], rightGradientColor[3];


//locate the track
bool onTrack(int sliderY1, int trackY1, int sliderY2, int trackY2) {
    for (int i = 0; i < numberOfSliders; i++) {
        if ((sliderY1 >= trackY1) && (sliderY2 < trackY2)) {
            return true;
        }
    }
    if (sliderVert[v][2] < trackY1) { //if under the track snapback
        sliderVert[v][2] = trackY1;
        sliderVert[v][3] = sliderVert[v][2] + 20;
    }
    else{
        sliderVert[v][3] = trackY2 - 1; //if over track snapback
        sliderVert[v][2] = sliderVert[v][3] - 20;
    }
    
    return false;
}

//only true if x y coordinates are within gradient
bool insideGradientBar(int x, int y){
    if (x >= gradientBarVert[0] && x <= gradientBarVert[1] && y >= gradientBarVert[2] && y <= gradientBarVert[3]) {
        return true;
    }
    return false;
}

//Slider class
class Slider {
    float r;
    float g;
    float b;
    int bottomLeftX;
    int bottomLeftY;
    int x1, x2, y1, y2;
    int sliderID;
    int colorID;
    char label;
public:
    Slider(float r, float g, float b, int bottomLeftX, int bottomLeftY, int colorID, char label){
        this->r = r;
        this->g = g;
        this->b = b;
        this->bottomLeftX = bottomLeftX;
        this->bottomLeftY = bottomLeftY;
        this->label = label;
        this->colorID = colorID;
        
        //set local variables
        x1 = bottomLeftX;
        x2 = bottomLeftX + 40;
        y1 = bottomLeftY;
        y2 = bottomLeftY + 20;
        
        //id is equal to the number of sliders
        sliderID = numberOfSliders;
        
        //set global slider variables
        sliderVert[sliderID][0] = x1;
        sliderVert[sliderID][1] = x2;
        sliderVert[sliderID][2] = y1;
        sliderVert[sliderID][3] = y2;
        
        //set global track variable
        trackVert[sliderID][0] = x1 - 20;
        trackVert[sliderID][1] = x2 + 20;
        trackVert[sliderID][2] = y1;
        trackVert[sliderID][3] = y2 + 512;
        
        //increase number of sliders after created
        numberOfSliders++;
    }
    
    //draw track
    void drawTrack() {
        glBegin(GL_LINES);
        glColor3f(r, g, b);
        glVertex3f((x1 + x2)/2, bottomLeftY, 0.0);
        glVertex3f((x1 + x2)/2, y2 + 512, 0.0);
        glEnd();
        
    }
    
    // drawLabel
    void drawLabel(){
        glRasterPos2i((x1 + x2)/2, y1 - 30);
        glutBitmapCharacter(GLUT_BITMAP_TIMES_ROMAN_24, label);
    }
    
    //draws track click area - mainly used for debuging
    void drawTrackClickArea() {
        glBegin(GL_QUADS);
        glColor3f(1, 1, 1);
        glVertex3f(trackVert[sliderID][0], trackVert[sliderID][2], 0.0);
        glVertex3f(trackVert[sliderID][1], trackVert[sliderID][2], 0.0);
        glVertex3f(trackVert[sliderID][1], trackVert[sliderID][3], 0.0);
        glVertex3f(trackVert[sliderID][0], trackVert[sliderID][3], 0.0);
        glEnd();
    }
    
    //draws slider
    void drawSlider() {
        if (onTrack(sliderVert[sliderID][2], trackVert[sliderID][2], sliderVert[sliderID][3], trackVert[sliderID][3])) {
            glBegin(GL_QUADS);
            glColor3f(r, g, b);
            glVertex3f(x1, sliderVert[sliderID][2], 0.0);
            glVertex3f(x2, sliderVert[sliderID][2], 0.0);
            glVertex3f(x2, sliderVert[sliderID][3], 0.0);
            glVertex3f(x1, sliderVert[sliderID][3], 0.0);
            glEnd();
        }
    }
    
    //sets global RGB controls used only when using the slider
    void setIntensity(){
        globalRGBControls[colorID] = ((roundf(sliderVert[sliderID][2] - trackVert[sliderID][2])/512));
    }
    
    //write down the color at the top of the track
    void writeIntensity(){
        //calculate color base on distance of bottom slider to bottom of track
        int rgbINT = (sliderVert[sliderID][2] - trackVert[sliderID][2])/2;
        char c;
        int numDigits = 0;
        glColor3f(r, g, b);
        if (rgbINT == 0) {// only when value is 0 we print 0
            int x = rgbINT%10;
            c = '0' + x;
            glRasterPos2i((x1 + x2)/2, bottomLeftY + 552);
            glutBitmapCharacter(GLUT_BITMAP_TIMES_ROMAN_24, c);
        }
        else{
            while(numDigits < 3 && rgbINT > 0){
                int x = rgbINT%10;
                c = '0' + x;
                glRasterPos2i((x1 + x2)/2-(numDigits*15), bottomLeftY + 552);
                glutBitmapCharacter(GLUT_BITMAP_TIMES_ROMAN_24, c);
                rgbINT/=10;
                numDigits++;
            }
            
        }
    }
    
    void setSlider() {
        sliderVert[sliderID][2] = (globalRGBControls[colorID] * 512) + y1;
        sliderVert[sliderID][3] = sliderVert[sliderID][2] + 20;
    }
    
};

class GradientBar {
public:
    GradientBar(float x1, float x2, float y1, float y2){
        for (int i = 0; i < 3; i++) {
            leftGradientColor[i] = 0;
            rightGradientColor[i] = 0;
        }
        gradientBarVert[0] = x1;
        gradientBarVert[1] = x2;
        gradientBarVert[2] = y1;
        gradientBarVert[3] = y2;
        
    }
    void setGradientBar(){
        glBegin(GL_QUADS);
        glColor3f(leftGradientColor[0], leftGradientColor[1], leftGradientColor[2]);
        glVertex3f(gradientBarVert[0], gradientBarVert[2], 0);
        glVertex3f(gradientBarVert[0], gradientBarVert[3], 0);
        glColor3f(rightGradientColor[0], rightGradientColor[1], rightGradientColor[2]);
        glVertex3f(gradientBarVert[1], gradientBarVert[3], 0);
        glVertex3f(gradientBarVert[1], gradientBarVert[2], 0);
        glEnd();
    }
};

class BigSquare{
public:
    BigSquare (float x1, float x2, float y1, float y2){
        bigSquareVert[0] = x1;
        bigSquareVert[1] = x2;
        bigSquareVert[2] = y1;
        bigSquareVert[3] = y2;
    }
    void updateBigSquare(float x1, float x2, float y1, float y2){
        glBegin(GL_QUADS);
        glColor3f(globalRGBControls[0], globalRGBControls[1], globalRGBControls[2]);
        glVertex3f(x1, y1, 0);
        glVertex3f(x2, y1, 0);
        glVertex3f(x2, y2, 0);
        glVertex3f(x1, y2, 0);
        glEnd();
    }
};

//create sliders
Slider red   = *new Slider(1, 0, 0, 50, 50, 0, 'R');
Slider green = *new Slider(0, 1, 0, 150, 50, 1, 'G');
Slider blue  = *new Slider(0, 0, 1, 250, 50, 2, 'B');

//create gradientBar
GradientBar gradientBar = *new GradientBar(350, 750, 100, 200);

//create big square
BigSquare bigSquare = *new BigSquare(400, 700, 250, 500);


void display (){
    glClear(GL_COLOR_BUFFER_BIT);
    
    gradientBar.setGradientBar();
    
    //if user uses the gradient bar
    if(clickedOnGradient){
        
        red.drawTrack();
        red.setSlider();
        red.drawSlider();
        red.writeIntensity();
        red.drawLabel();
        
        
        cout << "sliderVert[0][0] = " << sliderVert[0][0] << endl;
        cout << "sliderVert[0][1] = " << sliderVert[0][1] << endl;
        cout << "sliderVert[0][2] = " << sliderVert[0][2] << endl;
        cout << "sliderVert[0][3] = " << sliderVert[0][3] << endl << endl;
        
        green.drawTrack();
        green.setSlider();
        green.drawSlider();
        green.writeIntensity();
        green.drawLabel();
        
        blue.drawTrack();
        blue.setSlider();
        blue.drawSlider();
        blue.writeIntensity();
        blue.drawLabel();
        
        bigSquare.updateBigSquare(bigSquareVert[0], bigSquareVert[1], bigSquareVert[2], bigSquareVert[3]);
    }
    //other wiser user is using sliders
    else{
        red.drawTrack();
        red.drawSlider();
        red.setIntensity();
        red.writeIntensity();
        red.drawLabel();
        
        green.drawTrack();
        green.drawSlider();
        green.setIntensity();
        green.writeIntensity();
        green.drawLabel();
        
        
        blue.drawTrack();
        blue.drawSlider();
        blue.setIntensity();
        blue.writeIntensity();
        blue.drawLabel();
        
        bigSquare.updateBigSquare(bigSquareVert[0], bigSquareVert[1], bigSquareVert[2], bigSquareVert[3]);
    }
    
    //highlight slider if global sliderVariable is true
    if(onSlider){
        glBegin(GL_QUADS);
        glColor3f(1, 1, 1);
        glVertex3f(sliderVert[v][0], sliderVert[v][2], 0);
        glVertex3f(sliderVert[v][1], sliderVert[v][2], 0);
        glVertex3f(sliderVert[v][1], sliderVert[v][3], 0);
        glVertex3f(sliderVert[v][0], sliderVert[v][3], 0);
        glEnd();
    }
    
    
    string mixture = "mixture", gradient = "gradient";
    
    // draw "mixture"
    glColor3f(0, 0, 0);
    for (int i = 0;  i < mixture.length(); i++) {
        glRasterPos2i(480 + (i*25),520);
        glutBitmapCharacter(GLUT_BITMAP_TIMES_ROMAN_24, mixture[i]);
    }
    
    //draw "gradient"
    for(int i = 0; i < gradient.length();i++){
        glRasterPos2i(450 + (i*25),220);
        glutBitmapCharacter(GLUT_BITMAP_TIMES_ROMAN_24, gradient[i]);
    }
    glutSwapBuffers();
}

void keyboard(unsigned char key, int x, int y){
    
    switch(key) {
        case 'l':
            for (int i = 0; i < 3; i++) {
                leftGradientColor[i] = globalRGBControls[i];
            }
            break;
        case 'r':
            for (int i = 0; i < 3; i++) {
                rightGradientColor[i] = globalRGBControls[i];
            }
            break;
    }
    glutPostRedisplay();
}

//look for slider
int findSlider(int x, int y){
    for (int i = 0; i < numberOfSliders; i++) {
        if ((x >= (sliderVert[i][0] - 5)) && (x <= (sliderVert[i][1] + 5)) && (y >= (sliderVert[i][2] - 5)) && (y <= (sliderVert[i][3] + 5))) {
            return i;
        }
    }
    return -1;
}

//look for track
int findTrack(int x , int y){
    
    for(int i = 0; i < numberOfSliders; i++){
        if ((x >= trackVert[i][0]) && (x <= trackVert[i][1]) && (y >= (trackVert[i][2])) && (y <= (trackVert[i][3]))) {
            return i;
        }
    }
    return -1;
}


void mouse(int button, int state, int x, int y){
    cout << "x = " << x << endl;
    cout << "y = " << height - y - 1 << endl;
    
    //if user clicks down on slider
    if(state == GLUT_DOWN && (v = findSlider(x, height-y-1)) >= 0){
        //distingushes which track is currently being engaged
        selectedTrack = v;
        distanceBetweenCursorandSliderY1 = height - y - 1 - sliderVert[v][2];
        distanceBetweenCursorandSliderY2 = 20 - distanceBetweenCursorandSliderY1;
        sliderVert[v][2] = height - 1 - y - distanceBetweenCursorandSliderY1;
        sliderVert[v][3] = height - 1 - y + distanceBetweenCursorandSliderY2;
        rubberbanding = true;
        clickedOnGradient = false;
        onSlider = true;
        glutPostRedisplay();
    }
    
    //if user clicks down on track
    else if(state == GLUT_DOWN && ((v = findTrack(x, height - y - 1)) >= 0)){
        selectedTrack = v;
        sliderVert[v][2] = height - 1 - y - 10;
        sliderVert[v][3] = height - 1 - y + 10;
        onSlider = true;
        clickedOnTrack = true;
        clickedOnGradient = false;
        rubberbanding = true;
        glutPostRedisplay();
    }
    
    //if user clicks with in the gradient bar
    else if((state == GLUT_DOWN && insideGradientBar(x, height - y - 1)) || (state == GLUT_UP && insideGradientBar(x, height - y - 1))){
        clickedOnGradient = true;
        distanceBetweenCursorandGradientX1 = x - gradientBarVert[0];
        distanceBetweenCursorandGradientX2 = gradientBarVert[1] - x;
        float totalDist = distanceBetweenCursorandGradientX2 + distanceBetweenCursorandGradientX1;
        float percentOfLeft = distanceBetweenCursorandGradientX1/totalDist;
        float percentOfRight = distanceBetweenCursorandGradientX2/totalDist;
        for(int i = 0; i < 3; i++){
            globalRGBControls[i] =((rightGradientColor[i]*percentOfLeft)+(leftGradientColor[i]*percentOfRight));
        }
        glutPostRedisplay();
    }
    
    //if user releases click outside of slider this will make the highlight dissapear
    else if (state == GLUT_UP && findSlider(x, height - y - 1) < 0){
        cout << "entered GLUT UP " << endl;
        onSlider = false;
        glutPostRedisplay();
    }
    
    //all unwanted actions
    else {
        clickedOnGradient = false;
        rubberbanding = false;
        clickedOnTrack = false;
        onSlider = false;
    }
}


void passive(int x, int y){
    if((v = findSlider(x, height - y - 1)) >= 0){
        onSlider = true;
        glutPostRedisplay();
    }else{
        onSlider = false;
        glutPostRedisplay();
    }
}

void motion(int x, int y){
    
    //if you're moving on the track and engaged with the right slider
    if(rubberbanding && selectedTrack == v&& findTrack(x, height - y - 1) == v){
        if (clickedOnTrack) {
            sliderVert[v][2] = height - 1 - y - 10;
            sliderVert[v][3] = height - 1 - y + 10;
        }
        else{
            sliderVert[v][2] = height - 1 - y - distanceBetweenCursorandSliderY1; //stops slider from jumping
            sliderVert[v][3] = height - 1 - y + distanceBetweenCursorandSliderY2;
        }
        glutPostRedisplay();
    }
    
    //calculate color based on distance between the x values of the gradient bar
    if (clickedOnGradient && insideGradientBar(x, height - y -1) && findSlider(x, height - y - 1)) {
        clickedOnGradient = true;
        distanceBetweenCursorandGradientX1 = x - gradientBarVert[0];
        distanceBetweenCursorandGradientX2 = gradientBarVert[1] - x;
        float totalDist = distanceBetweenCursorandGradientX2 + distanceBetweenCursorandGradientX1;
        float percentOfLeft = distanceBetweenCursorandGradientX1/totalDist;
        float percentOfRight = distanceBetweenCursorandGradientX2/totalDist;
        for(int i = 0; i < 3; i++){
            globalRGBControls[i] =((rightGradientColor[i]*percentOfLeft)+(leftGradientColor[i]*percentOfRight));
        }
        glutPostRedisplay();
    }
    if(findSlider(x, height - y - 1)){
        onSlider = true;
        glutPostRedisplay();
    }
    glutPostRedisplay();
}
int main(int argc, char * argv[]) {
    glutInit(&argc, argv);
    glutInitDisplayMode(GLUT_RGB|GLUT_DOUBLE);
    glutInitWindowSize(width, height);
    glutInitWindowPosition(50, 100);
    glutCreateWindow("Edit polygons");
    glClearColor(0.5, 0.5, 0.5, 0.0);
    glColor3f(1, 1, 0);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluOrtho2D(0, width, 0, height);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    glutDisplayFunc(display);
    glutKeyboardFunc(keyboard);
    glutMouseFunc(mouse);
    glutPassiveMotionFunc(passive);
    glutMotionFunc(motion);
    glutMainLoop();
}
