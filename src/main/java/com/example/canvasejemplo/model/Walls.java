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

   private int positionX, positionY, height, weight;

   public Walls(Canvas canvas, int positionX , int positionY, int height, int weight) {
      this.canvas = canvas;
      this.positionX=positionX;
      this.positionY=positionY;
      this.height=height;
      this.weight=weight;

      gc=canvas.getGraphicsContext2D();

      String path= "file:"+ HelloApplication.class.getResource("wall.png").getPath();
      wall= new Image(path);


      wallShape= new Rectangle(positionX,positionY,height,weight);



   }


   public void drawWall(){
      gc.save();

      gc.drawImage(wall,positionX,positionY,height+10,weight+10);
      gc.restore();
   }

   public Rectangle getWallShape() {
      return wallShape;
   }
}
