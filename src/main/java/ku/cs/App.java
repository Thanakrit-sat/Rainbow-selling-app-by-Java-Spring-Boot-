package ku.cs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXRouter.bind(this, stage, "Rainbow", 800, 600);
        configRoute();
        FXRouter.goTo("login");
    }

    private static void configRoute() {
        String packageStr = "ku/cs/";
        FXRouter.when("login", packageStr+"login.fxml");
        FXRouter.when("register", packageStr+"register.fxml");
        FXRouter.when("market", packageStr+"market.fxml");
        FXRouter.when("devinfo", packageStr+"devinfo.fxml");
        FXRouter.when("suggestion", packageStr+"suggestion.fxml");
        FXRouter.when("store", packageStr+"store.fxml");
        FXRouter.when("profile",packageStr+"profile.fxml");
        FXRouter.when("admin", packageStr+"admin.fxml");
        FXRouter.when("openshop",packageStr+"openshop.fxml");
        FXRouter.when("addproduct",packageStr+"addproduct.fxml");
        FXRouter.when("productedit", packageStr+"productedit.fxml");
        FXRouter.when("productdetail", packageStr+"productdetail.fxml");
        FXRouter.when("ordercheck", packageStr+"ordercheck.fxml");
        FXRouter.when("report", packageStr+"report.fxml");
        FXRouter.when("reportinfo", packageStr+"reportinfo.fxml");
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}