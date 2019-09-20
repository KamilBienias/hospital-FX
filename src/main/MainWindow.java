package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
//import menuUtil.MenuUtilities;


public class MainWindow extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{//Stage to kontener

//ponizej zrobil w pierwszym odcinku, ale tego nie uzywac
//        Parent root = FXMLLoader.load(getClass().getResource("fxmlfiles/mainWindow.fxml"));
//        Scene scene = new Scene(root);//to dopisal po zrobieniu okna w SceneBuilderze

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("fxmlfiles/mainWindow.fxml"));

        StackPane stackPane = loader.load();
        Scene scene = new Scene(stackPane);

        MainWindowController controller = (MainWindowController) loader.getController();
        controller.setStage(primaryStage);

        primaryStage.getIcons().add(new Image("main/hospital.png"));//ikona na pasku programu
        primaryStage.setOnCloseRequest((event -> {MainWindowController.closeProgram();}));//event to klikniecie x, czyli ma wtedy zamknac program
        primaryStage.setScene(scene);//Scene to okno. Jak juz w fxml mam zdefiniowana szerokosc i wysokosc okna (zrobiona w SceneBuilderze), to moge pusty konstruktor zamiast new Scene(root, 600, 400)
        primaryStage.setResizable(false);
        primaryStage.setTitle("Hospital™ (made by Kamil Bienias)");//nazwa okna
        primaryStage.show();//pokazanie okna
            //to mozna
//        primaryStage.setFullScreen(true);//ustawia pelny ekran
//        primaryStage.setResizable(false);//okno nie bedzie skalowalne
    }


    public static void main(String[] args) {
        launch(args);//ona uruchamia program, szuka metody start()
    }
}
