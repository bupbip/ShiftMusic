package sample;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Arrays;

public class DayMenu {

    /**
     * Открываем меню дня, доступ к заметкам, запрещает нажимать кнопки
     *
     * @param buttonID Айди кнопки(дата)
     */

    public void cellSelected(String buttonID, ImageView placeToBlockImage, ImageView flower, ImageView dayMenuImage, ImageView exitButton, TextArea textArea, ImageView saveButton, Node... elements) {
        blur(true, elements);
        String cellDate = buttonID.split(",")[0];
        String blockType = buttonID.split(",")[1];
        placeToBlockImage.setImage(new Image("images/blocks/" + blockType + ".jpg"));
        visible(true, exitButton, textArea, saveButton, dayMenuImage, placeToBlockImage, flower);
        blur(false, textArea);
        String notes = FileWorker.searchNotesInFile(cellDate).trim();
        textArea.setText(notes);
        int numOfNotes = getNumOfNotes(notes);
        flower.setImage(new Image("images/flowers/цветок" + numOfNotes + ".png"));
        saveButton.setOnMouseClicked(e -> {
            FileWorker.addNotesToFile(cellDate, "\n" + textArea.getText() + "\nEOT\n");
        });
        exitButton.setOnMouseClicked(t -> {
            textArea.setText("");
            blur(false, elements);
            visible(false, exitButton, textArea, saveButton, dayMenuImage, placeToBlockImage, flower);
        });
    }

    /**
     * Блюрит и разблюривает фон
     *
     * @param blur       Блюрить или разблюрить
     * @param needToBlur Объекты, которые нужно отключить
     */

    public void blur(boolean blur, Node... needToBlur) {
        if (blur) {
            Arrays.stream(needToBlur).forEach(obj -> obj.setEffect(new GaussianBlur()));
        } else {
            Arrays.stream(needToBlur).forEach(obj -> obj.setEffect(null));
        }
        Arrays.stream(needToBlur).forEach(obj -> obj.setDisable(blur));
    }

    public void visible(boolean visible, Node... needToVisible) {
        Arrays.stream(needToVisible).forEach(obj -> obj.setVisible(visible));
    }

    public int getNumOfNotes(String notes) {
        int counter = 0;
        for (int i = 0; i < notes.length(); i++) {
            if (notes.charAt(i) == '\n') {
                counter++;
            }
        }
        return Math.min(counter, 3);
    }

}
