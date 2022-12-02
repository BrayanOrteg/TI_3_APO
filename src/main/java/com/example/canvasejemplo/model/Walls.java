package com.example.canvasejemplo.model;

import com.example.canvasejemplo.HelloApplication;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

public class Walls {

   private Canvas canvas;

   private GraphicsContext gc;

   private Image wall;
   private Rectangle wallShape;

   private int positionX, positionY, height, weight, life=3;

   public Walls(Canvas canvas, int positionX , int positionY, int weight, int height) {
      this.canvas = canvas;
      this.positionX=positionX;
      this.positionY=positionY;
      this.height=height;
      this.weight=weight;

      gc=canvas.getGraphicsContext2D();

      String path= "file:"+ HelloApplication.class.getResource("wall.png").getPath();
      wall= new Image(path);


      wallShape= new Rectangle(positionX,positionY,weight,height);



   }


   public void drawWall(){
      gc.save();
      gc.drawImage(wall,positionX,positionY,weight+10,height+10);
      gc.restore();
   }

   public Rectangle getWallShape() {
      return wallShape;
   }

   public int getPositionX() {
      return positionX;
   }

   public void setPositionX(int positionX) {
      this.positionX = positionX;
      wallShape.setX(positionX);
   }

   public int getPositionY() {
      return positionY;
   }

   public void setPositionY(int positionY) {
      this.positionY = positionY;
   }

   public int getLife() {
      return life;
   }

   public void setLife() {
      life--;
   }
}
