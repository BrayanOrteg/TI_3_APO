package com.example.canvasejemplo;

import com.example.canvasejemplo.model.Avatar;
import com.example.canvasejemplo.model.Walls;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TableRow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.security.Key;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private Canvas canvas;

    private Image img;


    private GraphicsContext gc;
    private boolean isRunning = true;


    //Elementos gráficos
    private Avatar avatar,avatar2;

    private Walls wall_1, wall_left_1 , wall_left_2, wall_right_1, wall_right_2;


    //Estados de las teclas
    boolean Wpressed = false;
    boolean Apressed = false;
    boolean Spressed = false;
    boolean Dpressed = false;
    boolean SPACEpressed = false;
    boolean Fpressed = false;

    boolean UPpressed = false;
    boolean DOWNpressed = false;
    boolean RIGHTpressed = false;
    boolean LEFTpressed = false;


    boolean shot_2_InProgress= false;
    boolean shot_1_InProgress= false;

    boolean w_Wall = false , s_Wall= false , up_Wall=false, down_Wall=false;





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);

        String uri="file:"+HelloApplication.class.getResource("gameTablero.png").getPath();
        img=new Image(uri);


        canvas.setOnMouseClicked(this::onMouseClicked);
        canvas.setOnKeyPressed(this::onKeyPressed);
        canvas.setOnKeyReleased(this::onKeyReleased);


        wall_1= new Walls(canvas,290,190,130,100);

        wall_left_1= new Walls(canvas, 85,70,70,120);
        wall_left_2=new Walls(canvas,85,290,70,120);

        wall_right_1= new Walls(canvas, 550,70,70,120);
        wall_right_2=new Walls(canvas,550,290,70,120);

        avatar = new Avatar(canvas,100,100);


        avatar2= new Avatar(canvas,500,350);
        draw();

    }

    private void onKeyReleased(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.W){
            Wpressed = false;
        }
        if(keyEvent.getCode() == KeyCode.A){
            Apressed = false;
        }
        if(keyEvent.getCode() == KeyCode.S){
            Spressed = false;
        }
        if(keyEvent.getCode() == KeyCode.D){
            Dpressed = false;
        }
        if(keyEvent.getCode() == KeyCode.F){
            Fpressed = false;
        }
        if(keyEvent.getCode() == KeyCode.SPACE){
            SPACEpressed = false;
        }
        if(keyEvent.getCode() == KeyCode.UP){
            UPpressed = false;
        }
        if(keyEvent.getCode() == KeyCode.DOWN){
            DOWNpressed = false;
        }
        if(keyEvent.getCode() == KeyCode.LEFT){
            LEFTpressed = false;
        }
        if(keyEvent.getCode() == KeyCode.RIGHT){
            RIGHTpressed = false;
        }
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        System.out.println(keyEvent.getCode());
        if(keyEvent.getCode() == KeyCode.W){
            Wpressed = true;
        }
        if(keyEvent.getCode() == KeyCode.A){
            Apressed = true;
        }
        if(keyEvent.getCode() == KeyCode.S){
            Spressed = true;
        }
        if(keyEvent.getCode() == KeyCode.D){
            Dpressed = true;
        }
        if(keyEvent.getCode() == KeyCode.F){
            Fpressed = true;
        }
        if(keyEvent.getCode() == KeyCode.SPACE){
            SPACEpressed = true;
        }
        if(keyEvent.getCode() == KeyCode.UP){
            UPpressed = true;
        }
        if(keyEvent.getCode() == KeyCode.DOWN){
            DOWNpressed = true;
        }
        if(keyEvent.getCode() == KeyCode.LEFT){
            LEFTpressed = true;
        }
        if(keyEvent.getCode() == KeyCode.RIGHT){
            RIGHTpressed = true;
        }
    }

    private void onMouseClicked(MouseEvent mouseEvent) {
        avatar.setPosition(mouseEvent.getX(), mouseEvent.getY());
    }

    public void draw(){
        new Thread(
                ()->{
                    while(isRunning){
                        //Dibujo
                        Platform.runLater(()-> {

                            gc.drawImage(img, 0, 0, canvas.getWidth(), canvas.getHeight());

                            wall_1.drawWall();
                            wall_left_1.drawWall();
                            wall_left_2.drawWall();
                            wall_right_1.drawWall();
                            wall_right_2.drawWall();
                            avatar.draw();

                            avatar2.draw();




                            if(avatar.getLife()>0) {

                                if (Wpressed && avatar.getTankPosition_Y() + 30 + avatar.getDirection().y <= canvas.getHeight() && avatar.getTankPosition_Y() - 30 + avatar.getDirection().y >= 0
                                        && avatar.getTankPosition_X() + 30 + avatar.getDirection().x <= canvas.getWidth() && avatar.getTankPosition_X() - 30 + avatar.getDirection().x >= 0
                                && !s_Wall) {

                                    if(avatar.getShape().intersects(wall_1.getWallShape().getBoundsInParent())
                                    || avatar.getShape().intersects(wall_left_1.getWallShape().getBoundsInParent())
                                    ||avatar.getShape().intersects(wall_left_2.getWallShape().getBoundsInParent())
                                            || avatar.getShape().intersects(wall_right_1.getWallShape().getBoundsInParent())
                                            ||avatar.getShape().intersects(wall_right_2.getWallShape().getBoundsInParent())){

                                        w_Wall=true;
                                        avatar.moveBackward();
                                        avatar.moveBackward();
                                        avatar.moveBackward();
                                        avatar.moveBackward();

                                    }else {
                                        w_Wall=false;
                                    }

                                        avatar.moveForward();


                                }
                                if (Apressed) {
                                    avatar.changeAngle(-4);
                                }
                                if (Spressed && avatar.getTankPosition_Y() - 30 - avatar.getDirection().y <= canvas.getHeight() && avatar.getTankPosition_Y() - 30 - avatar.getDirection().y >= 0
                                        && avatar.getTankPosition_X() - 30 - avatar.getDirection().x <= canvas.getWidth() && avatar.getTankPosition_X() - 30 - avatar.getDirection().x >= 0
                                && !w_Wall) {

                                    if(avatar.getShape().intersects(wall_1.getWallShape().getBoundsInParent())
                                            || avatar.getShape().intersects(wall_left_1.getWallShape().getBoundsInParent())
                                            ||avatar.getShape().intersects(wall_left_2.getWallShape().getBoundsInParent())
                                            || avatar.getShape().intersects(wall_right_1.getWallShape().getBoundsInParent())
                                            ||avatar.getShape().intersects(wall_right_2.getWallShape().getBoundsInParent())){

                                        s_Wall=true;
                                        avatar.moveForward();
                                        avatar.moveForward();
                                        avatar.moveForward();
                                        avatar.moveForward();

                                    }else {
                                        s_Wall=false;
                                    }


                                        avatar.moveBackward();


                                }
                                if (Dpressed) {
                                    avatar.changeAngle(4);
                                }

                                if(Fpressed){
                                    if(!shot_1_InProgress){
                                        shotDraw_1();
                                    }else {
                                        System.out.println("Disparo 1 en progreso");
                                    }
                                }
                            }

                        if(avatar2.getLife()>0){
                            if (UPpressed && avatar2.getTankPosition_Y() + 30 + avatar2.getDirection().y <= canvas.getHeight() && avatar2.getTankPosition_Y() - 30 + avatar2.getDirection().y >= 0
                                    && avatar2.getTankPosition_X() + 30 + avatar2.getDirection().x <= canvas.getWidth() && avatar2.getTankPosition_X() - 30 + avatar2.getDirection().x >= 0
                                    && !down_Wall) {

                                if(avatar2.getShape().intersects(wall_1.getWallShape().getBoundsInParent())
                                        || avatar2.getShape().intersects(wall_left_1.getWallShape().getBoundsInParent())
                                        ||avatar2.getShape().intersects(wall_left_2.getWallShape().getBoundsInParent())
                                        || avatar2.getShape().intersects(wall_right_1.getWallShape().getBoundsInParent())
                                        ||avatar2.getShape().intersects(wall_right_2.getWallShape().getBoundsInParent())){

                                    up_Wall=true;
                                    avatar2.moveBackward();
                                    avatar2.moveBackward();
                                    avatar2.moveBackward();
                                    avatar2.moveBackward();

                                }else {
                                    up_Wall=false;
                                }
                                avatar2.moveForward();
                            }
                            if (LEFTpressed) {
                                avatar2.changeAngle(-4);
                            }
                            if (DOWNpressed && avatar2.getTankPosition_Y() - 30 - avatar2.getDirection().y <= canvas.getHeight() && avatar2.getTankPosition_Y() - 30 - avatar2.getDirection().y >= 0
                                    && avatar2.getTankPosition_X() - 30 - avatar2.getDirection().x <= canvas.getWidth() && avatar2.getTankPosition_X() - 30 - avatar2.getDirection().x >= 0
                            && !up_Wall) {

                                if(avatar2.getShape().intersects(wall_1.getWallShape().getBoundsInParent())
                                        || avatar2.getShape().intersects(wall_left_1.getWallShape().getBoundsInParent())
                                        ||avatar2.getShape().intersects(wall_left_2.getWallShape().getBoundsInParent())
                                        || avatar2.getShape().intersects(wall_right_1.getWallShape().getBoundsInParent())
                                        ||avatar2.getShape().intersects(wall_right_2.getWallShape().getBoundsInParent())){

                                    down_Wall=true;
                                    avatar2.moveForward();
                                    avatar2.moveForward();
                                    avatar2.moveForward();
                                    avatar2.moveForward();

                                }else {
                                    down_Wall=false;
                                }
                                avatar2.moveBackward();

                            }
                            if (RIGHTpressed) {
                                avatar2.changeAngle(4);
                            }

                            if(SPACEpressed){
                                if(!shot_2_InProgress){
                                    shotDraw_2();
                                }else {
                                    System.out.println("Disparo 2 en progreso");
                                }

                            }
                        }







                        });
                        //Sleep
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        ).start();
    }

    public void shotDraw_2(){
        new Thread(()->{


                avatar2.setShot();
                avatar2.setDirectionsShot();
                boolean hitShot_2= false;
                for(int i=0; i<30 && !hitShot_2; i++){
                    shot_2_InProgress=true;
                    avatar2.moveForwardShot();
                    avatar2.shot();
                    gc.restore();

                    if(avatar2.getShotShape().intersects(avatar.getShape().getBoundsInParent())){
                        hitShot_2=true;
                        System.out.println("avatar 2 le dio al 1");

                        avatar.drawShotTank();
                        avatar.drawShotTank();

                        avatar.setLife();


                    }



                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                shot_2_InProgress=false;


                avatar2.setShot();

        }).start();
    }

    public void shotDraw_1(){
        new Thread(()->{


            avatar.setShot();
            avatar.setDirectionsShot();

            boolean hitShot_1= false;
            boolean shotWall= false;

            for(int i=0; i<30 && !hitShot_1 && !shotWall; i++){
                shot_1_InProgress=true;
                avatar.moveForwardShot();
                avatar.shot();
                gc.restore();

                if(avatar.getShotShape().intersects(avatar2.getShape().getBoundsInParent())){
                    hitShot_1=true;
                    System.out.println("avatar 1 le dio al 2");

                    avatar2.drawShotTank();
                    avatar2.drawShotTank();

                    avatar2.setLife();
                }

                if(avatar.getShotShape().intersects(wall_1.getWallShape().getBoundsInParent())){
                    shotWall=true;
                }



                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            shot_1_InProgress=false;


            avatar.setShot();

        }).start();
    }



}
