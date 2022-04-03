package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App i guess?
 */
public class App extends Application {

    /**
     * Maven/Javafx, idk main meetod pmst
     * Saaksime teha javafx control paneliga boti?
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        // Loome uue boti objekti
        Noel noel = new Noel("OTU5NzU1ODYwNDc5OTkxODA4.YkggTA.ho4NBKoZsML1tmYvq-O3nxEg6ow");

        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Käivitamine
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }

}