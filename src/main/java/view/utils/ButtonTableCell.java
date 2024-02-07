package view.utils;

import engineering.bean.PlaylistBean;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;
import view.PendingPlaylistCtrlGrafico;

// Classe TableCell personalizzata per aggiungere due bottoni nella cella
public class ButtonTableCell extends TableCell<PlaylistBean, Boolean> {

    private final Button approveButton = new Button("V");
    private final Button rejectButton = new Button("X");

    public ButtonTableCell() {

        approveButton.setOnAction(event -> {
            PlaylistBean playlist = getTableView().getItems().get(getIndex());
            PendingPlaylistCtrlGrafico.handlePendingButton(playlist,true);
        });

        rejectButton.setOnAction(event -> {
            PlaylistBean playlist = getTableView().getItems().get(getIndex());
            PendingPlaylistCtrlGrafico.handlePendingButton(playlist,false);
        });

        approveButton.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-pref-height: 25px; -fx-pref-width: 25px; " +
                "-fx-min-width: -1; -fx-min-height: -1; -fx-background-radius: 50%; -fx-stroke: 50; -fx-border-radius: 50%;");

        rejectButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-pref-height: 25px; -fx-pref-width: 25px; " +
                "-fx-min-width: -1; -fx-min-height: -1; -fx-background-radius: 50%; -fx-stroke: 50; -fx-border-radius: 50%;");

    }

    @Override
    protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(createButtonBox(approveButton, rejectButton));
        }
    }

    private HBox createButtonBox(Button... buttons) {
        HBox buttonBox = new HBox(5); // 5 è lo spazio tra i bottoni
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(buttons);
        return buttonBox;
    }


}