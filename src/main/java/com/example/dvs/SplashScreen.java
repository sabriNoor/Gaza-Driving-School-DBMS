package com.example.dvs;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class SplashScreen {

    @FXML
    private static ProgressBar loading;

    public static void showSplash() {
        Platform.runLater(() -> {
            Stage splashStage = new Stage();
            splashStage.initStyle(StageStyle.UNDECORATED);

            try {
                FXMLLoader loader = new FXMLLoader(SplashScreen.class.getResource("splash.fxml"));
                AnchorPane root = loader.load();
                loading = (ProgressBar) loader.getNamespace().get("loading");
                Scene scene = new Scene(root, 1112, 760);

                splashStage.setScene(scene);
                splashStage.show();

                // Simulate a time-consuming task (replace this with your actual initialization logic)
                simulateTask(() -> {
                    // Close the splash screen
                    Platform.runLater(() -> {
                        splashStage.close();

                        // Launch the main application with "hello-view.fxml"
                        try {
                            FXMLLoader mainLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
                            BorderPane mainRoot = mainLoader.load();
                            Scene mainScene = new Scene(mainRoot);

                            Stage primaryStage = new Stage();
                            primaryStage.setTitle("Hello JavaFX");
                            primaryStage.setScene(mainScene);
                            primaryStage.show();

                            // Access the loaded BorderPane using fx:id
                            BorderPane loadedPane = (BorderPane) mainLoader.getNamespace().get("pane");

                            // Now you can use 'loadedPane' as needed
                        } catch (IOException e) {
                            e.printStackTrace(System.out);
                        }
                    });
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private static void simulateTask(Runnable onTaskFinished) {
        // Simulate a time-consuming task here
        // Replace this with your actual initialization logic

        // For demonstration purposes, we'll just sleep for a few seconds
        new Thread(() -> {

            double progress = 0.0;
            while (progress < 1.0) {
                final double currentProgress = progress;
                Platform.runLater(() -> loading.setProgress(currentProgress));

                // Simulate some work being done
                try {
                    Thread.sleep(60); // Simulate a half-second task
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Increment the progress
                progress += 0.009;
            }

            // Ensure the progress reaches 100%
            Platform.runLater(() -> loading.setProgress(1.0));

            // Notify that the task is finished
            onTaskFinished.run();
        }).start();
    }
}