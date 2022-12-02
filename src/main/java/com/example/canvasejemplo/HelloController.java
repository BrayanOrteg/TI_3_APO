package com.example.canvasejemplo;

import com.example.canvasejemplo.model.Avatar;
import com.example.canvasejemplo.model.Users;
import com.example.canvasejemplo.model.Walls;
import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private Canvas canvas;

    private Image img, heart_Image;

    @FXML
    private ImageView heart_1_1,heart_1_2,heart_1_3,heart_1_4,heart_1_5;
    @FXML
    private ImageView heart_2_1,heart_2_2,heart_2_3,heart_2_4,heart_2_5;
    @FXML
    private ImageView heart_3_1,heart_3_2,heart_3_3,heart_3_4,heart_3_5;
    @FXML
    private ImageView image_bullet1, image_bullet2;

    @FXML
    private Label player_1_name,player_2_name;

    private ArrayList<Avatar> avatars= new ArrayList<>();


    private GraphicsContext gc;
    private boolean isRunning = true;


    //Elementos gráficos
    private Avatar avatar,avatar2, avatarCPU;

    private Walls wall_1, wall_left_1 , wall_left_2, wall_right_1, wall_right_2;


    //Estados de las teclas
    boolean Wpressed = false;
    boolean Apressed = false;
    boolean Spressed = false;
    boolean Dpressed = false;
    boolean SPACEpressed = false;
    boolean Fpressed = false;

    boolean Rpressed=false;
    boolean ControlPressed=false;

    boolean UPpressed = false;
    boolean DOWNpressed = false;
    boolean RIGHTpressed = false;
    boolean LEFTpressed = false;


    boolean shot_2_InProgress= false;
    boolean shot_1_InProgress= false;
    boolean shot_CPU_InProgress=false;

    boolean shotWait=false, shotWait2=false;

    boolean w_Wall = false , s_Wall= false , up_Wall=false, down_Wall=false;

    private final ImageView[] hearts_player_1= new ImageView[5];
    private final ImageView[] hearts_player_2= new ImageView[5];
    private final ImageView[] hearts_player_3= new ImageView[5];
    private final Walls[] walls_Game= new Walls[5];

    public double count = -10, countMove=0, countRight=0;





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);

        player_1_name.setText(Users.getInstance().getJP1());
        player_2_name.setText(Users.getInstance().getJP2());

        String uri="file:"+HelloApplication.class.getResource("gameTablero.png").getPath();
        img=new Image(uri);


        String url_Heeart="file:"+HelloApplication.class.getResource("heart_Empty.jpg").getPath();
        heart_Image= new Image(url_Heeart);


        canvas.setOnMouseClicked(this::onMouseClicked);
        canvas.setOnKeyPressed(this::onKeyPressed);
        canvas.setOnKeyReleased(this::onKeyReleased);


        wall_1= new Walls(canvas,290,190,130,100);

        wall_left_1= new Walls(canvas, 85,70,70,120);
        wall_left_2=new Walls(canvas,85,290,70,120);

        wall_right_1= new Walls(canvas, 550,70,70,120);
        wall_right_2=new Walls(canvas,550,290,70,120);

        avatar = new Avatar(canvas,50,80,"tankRojo.png", Users.getInstance().getJP1());
        avatar2= new Avatar(canvas,500,350,"tankAzul.png", Users.getInstance().getJP2());
        avatarCPU= new Avatar(canvas,680, 60, "tankVerde.png", "CPU");


        avatars.add(avatar);
        avatars.add(avatar2);
        avatars.add(avatarCPU);

        for(int i=0; i<=10;i++){
            avatarCPU.changeAngle(4);
        }


        hearts_player_1[0] = heart_1_1;
        hearts_player_1[1] = heart_1_2;
        hearts_player_1[2] = heart_1_3;
        hearts_player_1[3] = heart_1_4;
        hearts_player_1[4] = heart_1_5;

        hearts_player_3[0] = heart_3_1;
        hearts_player_3[1] = heart_3_2;
        hearts_player_3[2] = heart_3_3;
        hearts_player_3[3] = heart_3_4;
        hearts_player_3[4] = heart_3_5;

        hearts_player_2[0] = heart_2_1;
        hearts_player_2[1] = heart_2_2;
        hearts_player_2[2] = heart_2_3;
        hearts_player_2[3] = heart_2_4;
        hearts_player_2[4] = heart_2_5;

        walls_Game[0]=wall_1;
        walls_Game[1]=wall_right_1;
        walls_Game[2]=wall_right_2;
        walls_Game[3]=wall_left_1;
        walls_Game[4]=wall_left_2;


        draw();

    }

    private void onKeyReleased(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.W){
            Wpressed = false;
        }
        if(keyEvent.getCode() == KeyCode.A){
            Apressed = false;
        }
        if(keyEvent.getCode() == KeyCode.D){
            Dpressed = false;
        }
        if(keyEvent.getCode() == KeyCode.F){
            Fpressed = false;
        }

        if(keyEvent.getCode() == KeyCode.R){
            Rpressed = false;
        }
        if(keyEvent.getCode() == KeyCode.CONTROL){
            ControlPressed = false;
        }


        if(keyEvent.getCode() == KeyCode.SPACE){
            SPACEpressed = false;
        }
        if(keyEvent.getCode() == KeyCode.UP){
            UPpressed = false;
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

        if(keyEvent.getCode() == KeyCode.D){
            Dpressed = true;
        }
        if(keyEvent.getCode() == KeyCode.F){
            Fpressed = true;
        }

        if(keyEvent.getCode() == KeyCode.R){
            Rpressed = true;
        }
        if(keyEvent.getCode() == KeyCode.CONTROL){
            ControlPressed = true;
        }


        if(keyEvent.getCode() == KeyCode.SPACE){
            SPACEpressed = true;
        }
        if(keyEvent.getCode() == KeyCode.UP){
            UPpressed = true;
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

                            // todoos los dibujos dentro del canvas
                            gc.drawImage(img, 0, 0, canvas.getWidth(), canvas.getHeight());

                            wall_1.drawWall();
                            wall_left_1.drawWall();
                            wall_left_2.drawWall();
                            wall_right_1.drawWall();
                            wall_right_2.drawWall();
                            avatar.draw();
                            avatar2.draw();
                            avatarCPU.draw();



                            if (avatarCPU.getLife()>0) {
                                count++;

                                if (count==10){
                                    avatarCPU.setShot();
                                    avatarCPU.setDirectionsShot();
                                    for (int i=0; i<30; i++){
                                        avatarCPU.moveForwardShot();

                                        if(avatarCPU.getRadar().intersects(avatar.getShape().getBoundsInParent())
                                        ||avatarCPU.getRadar().intersects(avatar2.getShape().getBoundsInParent())){

                                            i=30;
                                            if(!shot_CPU_InProgress) shotDraw_CPU();
                                        }
                                    }
                                }

                                if (count == 12) {
                                    countMove++;
                                    for (int i = 0; i <= 11; i++) {

                                        if ((avatarCPU.getTankPosition_Y() + 30 + avatarCPU.getDirection().y <= canvas.getHeight() && avatarCPU.getTankPosition_Y() - 30 + avatarCPU.getDirection().y >= 0
                                                && avatarCPU.getTankPosition_X() + 30 + avatarCPU.getDirection().x <= canvas.getWidth() && avatarCPU.getTankPosition_X() - 30 + avatarCPU.getDirection().x >= 0)) {

                                            boolean cpuHitWall = false;

                                            for (Walls walls : walls_Game) {
                                                if (avatarCPU.getShape().intersects(walls.getWallShape().getBoundsInParent())) {
                                                    for (int w = 0; w <= 12; w++) {
                                                        avatarCPU.moveBackward();
                                                    }

                                                    for (int e = 0; e <= 22; e++) {
                                                        avatarCPU.changeAngle(4);
                                                    }
                                                    i = 12;
                                                    count = 0;
                                                    countMove = 0;
                                                    cpuHitWall = true;

                                                } else {
                                                    cpuHitWall = false;
                                                }
                                            }

                                            if (!cpuHitWall) avatarCPU.moveForward();


                                        } else {
                                            avatarCPU.moveBackward();
                                            avatarCPU.moveBackward();
                                            for (int e = 0; e <= 22; e++) {
                                                avatarCPU.changeAngle(-4);
                                            }
                                            i = 12;
                                            count = 0;
                                            countMove = 0;
                                        }


                                    }
                                    count = 0;

                                    if (countMove == 5 && countRight == 0) {
                                        countRight = 1;
                                        countMove = 0;
                                        for (int i = 0; i <= 22; i++) {
                                            avatarCPU.changeAngle(4);

                                        }
                                    }

                                    if (countRight == 1 && countMove == 5) {
                                        for (int i = 0; i <= 22; i++) {
                                            avatarCPU.changeAngle(-4);

                                        }
                                        countRight = 0;
                                        countMove = 0;
                                    }
                                }
                            }else {
                                avatarCPU.setPosition(1000,1000);
                                avatars.remove(avatarCPU);
                            }





                            image_bullet1.setVisible(!shotWait);

                            image_bullet2.setVisible(!shotWait2);



                            //Desaparecer la pared cuando la dañan
                            for (Walls walls : walls_Game) {
                                if (walls.getLife() <= 0) {
                                    walls.setPositionX(1000);
                                }
                            }


                            //Desaparecer el tanque cuando muere.
                            if(avatar.getLife()<=0){
                                avatar.setPosition(1000,1000);
                                avatars.remove(avatar);
                            }

                            if(avatar2.getLife()<=0){
                                avatar2.setPosition(1000,1000);
                                avatars.remove(avatar2);
                            }



                            if(!avatar.getShape().intersects(wall_1.getWallShape().getBoundsInParent())
                                    || !avatar.getShape().intersects(wall_left_1.getWallShape().getBoundsInParent())
                                    ||!avatar.getShape().intersects(wall_left_2.getWallShape().getBoundsInParent())
                                    || !avatar.getShape().intersects(wall_right_1.getWallShape().getBoundsInParent())
                                    ||!avatar.getShape().intersects(wall_right_2.getWallShape().getBoundsInParent())){
                                w_Wall=false;
                                s_Wall=false;
                            }

                            if(!avatar2.getShape().intersects(wall_1.getWallShape().getBoundsInParent())
                                    || !avatar2.getShape().intersects(wall_left_1.getWallShape().getBoundsInParent())
                                    ||!avatar2.getShape().intersects(wall_left_2.getWallShape().getBoundsInParent())
                                    || !avatar2.getShape().intersects(wall_right_1.getWallShape().getBoundsInParent())
                                    ||!avatar2.getShape().intersects(wall_right_2.getWallShape().getBoundsInParent())){
                                up_Wall=false;
                                down_Wall=false;
                            }

                            if(avatar.getLife()>0) {

                                if (Wpressed && avatar.getTankPosition_Y() + 30 + avatar.getDirection().y <= canvas.getHeight() && avatar.getTankPosition_Y() - 30 + avatar.getDirection().y >= 0
                                        && avatar.getTankPosition_X() + 30 + avatar.getDirection().x <= canvas.getWidth() && avatar.getTankPosition_X() - 30 + avatar.getDirection().x >= 0
                                && !s_Wall && !w_Wall) {

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

                                if (Rpressed){
                                    shotWait=false;
                                }

                                if (ControlPressed){
                                    shotWait2=false;
                                }

                                if (Apressed) {
                                    avatar.changeAngle(-4);
                                }

                                if (Dpressed) {
                                    avatar.changeAngle(4);
                                }

                                if(Fpressed){
                                    if(!shot_1_InProgress && !shotWait){
                                        shotDraw_1();
                                    }else {
                                        System.out.println("Disparo 1 en progreso");
                                    }
                                }
                            }

                        if(avatar2.getLife()>0){
                            if (UPpressed && avatar2.getTankPosition_Y() + 30 + avatar2.getDirection().y <= canvas.getHeight() && avatar2.getTankPosition_Y() - 30 + avatar2.getDirection().y >= 0
                                    && avatar2.getTankPosition_X() + 30 + avatar2.getDirection().x <= canvas.getWidth() && avatar2.getTankPosition_X() - 30 + avatar2.getDirection().x >= 0
                                    && !down_Wall && !up_Wall) {

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

                            if (RIGHTpressed) {
                                avatar2.changeAngle(4);
                            }

                            if(SPACEpressed){
                                if(!shot_2_InProgress && !shotWait2){
                                    shotDraw_2();
                                }else {
                                    System.out.println("Disparo 2 en progreso");
                                }

                            }
                        }


                        if(avatars.size()==1){
                            isRunning=false;
                            finishGame();
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
                boolean hitWall_2= false;

                for(int i=0; i<30 && !hitShot_2 && !hitWall_2; i++){

                    shot_2_InProgress=true;
                    shotWait2=true;
                    avatar2.moveForwardShot();
                    avatar2.shot();
                    gc.restore();

                    if(avatar2.getShotShape().intersects(avatar.getShape().getBoundsInParent())){
                        hitShot_2=true;
                        System.out.println("avatar 2 le dio al 1");

                        avatar.drawShotTank();
                        avatar.drawShotTank();

                        avatar.setLife();

                        if(avatar.getLife()<5 && avatar.getLife()>=0){
                            hearts_player_1[avatar.getLife()].setImage(heart_Image);
                        }
                    }

                    if(avatar2.getShotShape().intersects(avatarCPU.getShape().getBoundsInParent())){
                        hitShot_2=true;
                        System.out.println("avatar 2 le dio a la cpu");

                        avatarCPU.drawShotTank();
                        avatarCPU.drawShotTank();

                        avatarCPU.setLife();

                        if(avatarCPU.getLife()<5 && avatarCPU.getLife()>=0){
                            hearts_player_2[avatarCPU.getLife()].setImage(heart_Image);
                        }
                    }


                    for (Walls walls : walls_Game) {
                        if (avatar2.getShotShape().intersects(walls.getWallShape().getBoundsInParent())) {
                            hitWall_2 = true;
                            walls.setLife();
                            break;
                        }
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
            boolean hitWall_1= false;

            for(int i=0; i<30 && !hitShot_1 && !hitWall_1; i++){
                shot_1_InProgress=true;
                shotWait=true;
                avatar.moveForwardShot();
                avatar.shot();
                gc.restore();

                if(avatar.getShotShape().intersects(avatar2.getShape().getBoundsInParent())){
                    hitShot_1=true;
                    System.out.println("avatar 1 le dio al 2");

                    avatar2.drawShotTank();
                    avatar2.drawShotTank();

                    avatar2.setLife();

                    if(avatar2.getLife()<5 && avatar2.getLife()>=0){
                        hearts_player_3[avatar2.getLife()].setImage(heart_Image);
                    }
                }

                if(avatar.getShotShape().intersects(avatarCPU.getShape().getBoundsInParent())){
                    hitShot_1=true;
                    System.out.println("avatar 1 le dio a la cpu");



                    avatarCPU.drawShotTank();
                    avatarCPU.drawShotTank();

                    avatarCPU.setLife();

                    if(avatarCPU.getLife()<5 && avatarCPU.getLife()>=0){
                        hearts_player_2[avatarCPU.getLife()].setImage(heart_Image);
                    }
                }


                for (Walls walls : walls_Game) {
                    if (avatar.getShotShape().intersects(walls.getWallShape().getBoundsInParent())) {
                        hitWall_1 = true;
                        walls.setLife();
                        break;
                    }
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


    public void shotDraw_CPU(){
        new Thread(()->{


            avatarCPU.setShot();
            avatarCPU.setDirectionsShot();

            boolean hitShot_CPU= false;
            boolean hitWall_CPU= false;

            for(int i=0; i<30 && !hitShot_CPU && !hitWall_CPU; i++){
                shot_CPU_InProgress=true;
                avatarCPU.moveForwardShot();
                avatarCPU.shot();
                gc.restore();

                if(avatarCPU.getShotShape().intersects(avatar2.getShape().getBoundsInParent())){
                    hitShot_CPU=true;
                    System.out.println("avatar 1 le dio al 2");

                    avatar2.drawShotTank();
                    avatar2.drawShotTank();

                    avatar2.setLife();

                    if(avatar2.getLife()<5 && avatar2.getLife()>=0){
                        hearts_player_3[avatar2.getLife()].setImage(heart_Image);
                    }
                }

                if(avatarCPU.getShotShape().intersects(avatar.getShape().getBoundsInParent())){
                    hitShot_CPU=true;
                    System.out.println("avatar 1 le dio a la cpu");

                    avatar.drawShotTank();
                    avatar.drawShotTank();

                    avatar.setLife();

                    if(avatar.getLife()<5 && avatar.getLife()>=0){
                        hearts_player_1[avatar.getLife()].setImage(heart_Image);
                    }
                }

                for (Walls walls : walls_Game) {
                    if (avatarCPU.getShotShape().intersects(walls.getWallShape().getBoundsInParent())) {
                        hitWall_CPU = true;
                        walls.setLife();
                        break;
                    }
                }

                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            shot_CPU_InProgress=false;


            avatarCPU.setShot();

        }).start();
    }




    public void finishGame(){
        Users.getInstance().setWinner(avatars.get(0).getUser());
        Stage stage = (Stage) player_1_name.getScene().getWindow();
        HelloApplication.showWindow("victory.fxml");
        stage.close();
    }



}
