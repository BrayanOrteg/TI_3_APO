package com.example.canvasejemplo.model;

import com.example.canvasejemplo.HelloApplication;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Avatar extends Thread {

    private Canvas canvas, canvas2;
    private GraphicsContext gc, gc2;

    private Image tank;

    private Vector pos;

    private Vector posShot;
    private Vector direction;

    private double auxiliar_X=0,auxiliar_Y=0;

    private Rectangle shape , shapeShot, radar;

    private String user;

    public int life=5;

    public Avatar(Canvas canvas, int positionX, int positionY, String imagePath, String user){
        this.canvas = canvas;

        gc = canvas.getGraphicsContext2D();

        String uri = "file:"+HelloApplication.class.getResource(imagePath).getPath();
        tank = new Image(uri);

        pos = new Vector(positionX,positionY);
        posShot = new Vector(100,100);
        direction = new Vector(2,2);

        shape=new Rectangle(100,100,48,48);
        shapeShot=new Rectangle(100,100, 10, 10);
        radar= new Rectangle (100,100,20,20);

        this.user=user;
    }

    public void draw(){
        gc.save();
        gc.translate(pos.x, pos.y);
        gc.rotate(90+direction.getAngle());
        shape.setX(pos.x-25);
        shape.setY(pos.y-20);

        gc.drawImage(tank, -25,-25, 50,50);

        gc.restore();
    }

    public void drawShotTank(){
        gc.save();
        gc.translate(pos.x, pos.y);
        gc.rotate(90+direction.getAngle());
        gc.fillRect(-25,-25,50,50);
        gc.setFill(Color.WHITE);

        gc.restore();
    }

    public void setPosition(double x, double y) {
        pos.x = (int) x - 25;
        pos.y = (int) y - 25;
    }

    public void changeAngle(double a){
        double amp = direction.getAmplitude();
        double angle = direction.getAngle();
        angle += a;
        direction.x = amp*Math.cos(Math.toRadians(angle));
        direction.y = amp*Math.sin(Math.toRadians(angle));
    }

    public void moveForward(){
        pos.x += direction.x;
        pos.y += direction.y;
    }

    public void moveBackward(){
        pos.x -= direction.x;
        pos.y -= direction.y;
    }

    public void shot(){
        gc.setFill(Color.WHITE);
        gc.fillOval(posShot.x, posShot.y, 10,10);
        gc.restore();
    }

    public void setShot(){
        posShot.x = pos.x;
        posShot.y = pos.y;
    }

    public void setDirectionsShot(){
        auxiliar_X= direction.x * 2;
        auxiliar_Y= direction.y * 2;
    }

    public void moveForwardShot(){
        posShot.x += auxiliar_X;
        posShot.y += auxiliar_Y;

        shapeShot.setX(posShot.x);
        shapeShot.setY(posShot.y);

        radar.setX(posShot.x);
        radar.setY(posShot.y);
    }

    public double getShotPosition_X(){
        return posShot.x;
    }

    public double getShotPosition_Y(){
        return posShot.y;
    }

    public double getTankPosition_X(){
        return pos.x;
    }

    public double getTankPosition_Y(){
        return pos.y;
    }

    public Vector getPos(){ return  pos;}

    public Rectangle getShape(){return  shape;}

    public Rectangle getShotShape(){return shapeShot;}

    public int getLife() {
        return life;
    }

    public void setLife() {
        life--;
    }

    public Vector getDirection() {
        return direction;
    }

    public String getUser(){return user;}


    public Rectangle getRadar() {
        return radar;
    }
}

