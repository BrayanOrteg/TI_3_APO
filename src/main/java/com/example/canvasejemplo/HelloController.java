package com.example.canvasejemplo;

import com.example.canvasejemplo.model.Avatar;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TableRow;
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


    private GraphicsContext gc;
    private boolean isRunning = true;


    //Elementos gráficos
    private Avatar avatar,avatar2;


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

    boolean shot_1_hit=false;

    boolean shot_2_hit=false;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);




        canvas.setOnMouseClicked(this::onMouseClicked);
        canvas.setOnKeyPressed(this::onKeyPressed);
        canvas.setOnKeyReleased(this::onKeyReleased);

        avatar = new Avatar(canvas);

        avatar2= new Avatar(canvas);
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
                        Platform.runLater(()->{
                            gc.setFill(Color.BLACK);

                            gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());

                            avatar.draw();

                            avatar2.draw();

                            if(Wpressed){
                                avatar.moveForward();
                            }
                            if(Apressed){
                                avatar.changeAngle(-4);
                            }
                            if(Spressed){
                                avatar.moveBackward();

                            }
                            if(Dpressed){
                                avatar.changeAngle(4);
                            }
                            if(UPpressed){
                                avatar2.moveForward();
                            }
                            if(LEFTpressed){
                                avatar2.changeAngle(-4);
                            }
                            if(DOWNpressed){
                                avatar2.moveBackward();

                            }
                            if(RIGHTpressed){
                                avatar2.changeAngle(4);
                            }

                            if(SPACEpressed){
                                if(!shot_2_InProgress){
                                    shotDraw_2();
                                }else {
                                    System.out.println("Disparo 2 en progreso");
                                }

                            }

                            if(Fpressed){
                                if(!shot_1_InProgress){
                                    shotDraw_1();
                                }else {
                                    System.out.println("Disparo 1 en progreso");
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
                for(int i=0; i<30 && !shot_2_hit; i++){
                    shot_2_InProgress=true;
                    avatar2.moveForwardShot();
                    avatar2.shot();
                    gc.restore();


                    if(avatar2.getShotPosition_X()+10==avatar.getTankPosition_X() && avatar2.getShotPosition_Y()+20== avatar.getTankPosition_Y()){
                        System.out.println("Le dio");
                        shot_2_hit=true;
                    }

                    try {
                        Thread.sleep(70);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                shot_2_InProgress=false;
                shot_2_hit=false;

                avatar2.setShot();

        }).start();
    }

    public void shotDraw_1(){
        new Thread(()->{


            avatar.setShot();
            avatar.setDirectionsShot();
            for(int i=0; i<30 && !shot_1_hit; i++){
                shot_1_InProgress=true;
                avatar.moveForwardShot();
                avatar.shot();
                gc.restore();

                    if((avatar.getTankPosition_X()>=avatar2.getTankPosition_X() && avatar.getTankPosition_Y()<avatar2.getTankPosition_Y())
                    || (avatar.getTankPosition_X()>=avatar2.getTankPosition_X() && avatar.getTankPosition_Y()>avatar2.getTankPosition_Y())
                    || (avatar.getTankPosition_X())>avatar2.getTankPosition_X() && avatar.getTankPosition_Y()>=avatar2.getTankPosition_Y()){
                        if(avatar.getShotPosition_X()+10<=avatar2.getTankPosition_X() && avatar.getShotPosition_Y()+10<= avatar2.getTankPosition_Y()
                        ||avatar.getShotPosition_X()+10<=avatar2.getTankPosition_X()+15 && avatar.getShotPosition_Y()+10<= avatar2.getTankPosition_Y()+15
                                ||avatar.getShotPosition_X()+10<=avatar2.getTankPosition_X()+20 && avatar.getShotPosition_Y()+10<= avatar2.getTankPosition_Y()+20){
                            System.out.println("Le diooo mayor mayorrr");
                            shot_1_hit=true;
                        }
                    }

                if((avatar.getTankPosition_X()<=avatar2.getTankPosition_X() && avatar.getTankPosition_Y()<avatar2.getTankPosition_Y())
                        || (avatar.getTankPosition_X()<=avatar2.getTankPosition_X() && avatar.getTankPosition_Y()>avatar2.getTankPosition_Y())
                        || (avatar.getTankPosition_X())<avatar2.getTankPosition_X() && avatar.getTankPosition_Y()>=avatar2.getTankPosition_Y()){

                    if(avatar.getTankPosition_X()>=avatar2.getTankPosition_X()&&avatar.getTankPosition_Y()>avatar2.getTankPosition_Y()){

                    }else {
                        if(avatar.getShotPosition_X()-5>=avatar2.getTankPosition_X() && avatar.getShotPosition_Y()>= avatar2.getTankPosition_Y()
                                ||avatar.getShotPosition_X()-5>=avatar2.getTankPosition_X()-15 && avatar.getShotPosition_Y()>= avatar2.getTankPosition_Y()-15
                                ||avatar.getShotPosition_X()-5>=avatar2.getTankPosition_X()-20 && avatar.getShotPosition_Y()>= avatar2.getTankPosition_Y()-20
                        ){
                            System.out.println("Le diooo menor menor");
                            shot_1_hit=true;
                        }
                    }

                }







                try {
                    Thread.sleep(70);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            shot_1_InProgress=false;
            shot_1_hit=false;

            avatar.setShot();

        }).start();
    }



}
