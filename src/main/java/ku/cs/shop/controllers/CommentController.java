package ku.cs.shop.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import ku.cs.MyListener;
import ku.cs.shop.models.Commentator;

public class CommentController {
    @FXML private Label nameLabel;
    @FXML private Label pointLabel;
    @FXML private Text textLabel;
    private Commentator commentator;

    public void setData(Commentator commentator){
        this.commentator = commentator;
        nameLabel.setText(commentator.getName());
        pointLabel.setText(String.valueOf(commentator.getScore()));
        textLabel.setText(commentator.getText());
    }
}
