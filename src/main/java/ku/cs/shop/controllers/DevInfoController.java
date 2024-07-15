package ku.cs.shop.controllers;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import ku.cs.FXRouter;

import java.io.IOException;

public class DevInfoController {
    @FXML
    public void handleBackButton(MouseEvent event){
        try {
            // เปลี่ยนการแสดงผลไปที่ route ที่ชื่อ market
            FXRouter.goTo("market");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า market ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด market");
        }
    }
}