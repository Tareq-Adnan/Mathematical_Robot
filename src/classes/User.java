package classes;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class User extends Application {
    public static final TextArea showMessage=new TextArea();
    public static final TextField inputMsg=new TextField();
    public static final Button sendBtn=new Button();
    private static final Circle image=new Circle();
    private static final Label Name = new Label();
    private static Socket socket;
    private static Font font=new Font("Roboto",15);
    private static DataInputStream din;
    private static DataOutputStream dos;


    public static void main(String[] args) throws IOException {
        Connection();
        launch(args);

    }

    private static void Connection() throws IOException {
        socket=new Socket("localhost",12);
        din=new DataInputStream(socket.getInputStream());
        dos=new DataOutputStream(socket.getOutputStream());

        handleEvent();
        reading();
    }

    private static void reading() {
        Runnable r1=()->{
            while (!socket.isClosed()){
                try {
                    String msg=din.readUTF();
                    if (msg.equals("bye")){
                        socket.close();
                        System.exit(0);
                        break;
                    }
                    else{
                        showMessage.appendText("  "+msg+"\n");
                    }


                } catch (IOException e) {

                }
            }
        };
        new Thread(r1).start();
    }

    private static void handleEvent() throws IOException {

        inputMsg.setOnAction(event -> {
            String msg=inputMsg.getText();
            showMessage.appendText("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+msg + "\n");

            try {
             dos.writeUTF(msg);
            inputMsg.setText("");
            inputMsg.requestFocus();
            } catch (IOException e) {

            }
        });

        sendBtn.setOnAction(event -> {
            String msg=inputMsg.getText();
            showMessage.appendText("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+msg + "\n");
            try {
                dos.writeUTF(msg);
                inputMsg.setText("");
                inputMsg.requestFocus();
            } catch (IOException e) {

            }
        });

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setScene(new Scene(create()));
        primaryStage.setTitle("Mathematical Robot");
        primaryStage.show();
    }

    private Parent create() {

        HBox topBar=new HBox(10,Name);
        topBar.setPrefHeight(50);
        topBar.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        topBar.getStyleClass().add("bg");
        topBar.setAlignment(Pos.CENTER);
        topBar.paddingProperty().set(new Insets(5,10,5,20));




        showMessage.setPrefHeight(490);
        showMessage.setFont(font);
        showMessage.setEditable(false);


        Name.setText("MATHEMATiCA");
        Name.setFont(Font.font("Britannic Bold", FontWeight.BOLD,50));
        Name.setTextFill(Paint.valueOf("WHITE"));
        Name.alignmentProperty().set(Pos.CENTER);




        HBox bottomBar=new HBox(20,inputMsg,sendBtn);
        bottomBar.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        bottomBar.getStyleClass().add("bg");
        bottomBar.setAlignment(Pos.CENTER_LEFT);
        bottomBar.setPrefHeight(55);

        inputMsg.setPrefHeight(40);
        inputMsg.setPrefWidth(520);
        inputMsg.getStyleClass().add("textfield");

        sendBtn.setText("SEND");
        sendBtn.setTextFill(Paint.valueOf("WHITE"));
        sendBtn.setFont(font);
        sendBtn.setPrefHeight(40);
        sendBtn.setPrefWidth(160);
        sendBtn.getStyleClass().add("btn");

        VBox root=new VBox(0,topBar,showMessage,bottomBar);


        root.setPrefSize(700,600);
        return root;
    }
}

