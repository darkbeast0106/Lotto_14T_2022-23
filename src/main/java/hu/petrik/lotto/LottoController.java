package hu.petrik.lotto;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class LottoController {

    @FXML
    private Button sorsolGomb;
    @FXML
    private Label sorsoltSzamLabel;
    @FXML
    private HBox sorsoltSzamok;
    private static final Random RND = new Random();
    private final List<Integer> sorsoltSzamokLista = new ArrayList<>();

    @FXML
    public void sorsolCLick(ActionEvent actionEvent) {
        if (sorsolGomb.getText().equals("Rendez")){
            rendez();
        } else {
            veletlenSzamokMegjelenitese();
        }
    }

    private void veletlenSzamokMegjelenitese() {
        LocalDateTime inditasIdopontja = LocalDateTime.now();
        sorsolGomb.setDisable(true);
        Timer myTimer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    int veletlenSzam = RND.nextInt(90) + 1;
                    sorsoltSzamLabel.setText(String.valueOf(veletlenSzam));
                    LocalDateTime aktualisIdopont = LocalDateTime.now();
                    Duration elteltIdo = Duration.between(inditasIdopontja, aktualisIdopont);
                    if (elteltIdo.getSeconds() >= 2){
                        myTimer.cancel();
                        sorsol();
                        sorsolGomb.setDisable(false);
                    }
                });
            }
        };
        myTimer.schedule(task, 0, 100);
    }

    private void sorsol() {
        int sorsoltSzam = RND.nextInt(90) + 1;
        while (sorsoltSzamokLista.contains(sorsoltSzam)) {
            sorsoltSzam = RND.nextInt(90) + 1;
        }
        sorsoltSzamokLista.add(sorsoltSzam);
        sorsoltSzamLabel.setText(String.valueOf(sorsoltSzam));
        szamokFrissiteseListaAlapjan();
        if (sorsoltSzamokLista.size() == 5) {
            sorsolGomb.setText("Rendez");
        }
    }

    private void rendez() {
        Collections.sort(sorsoltSzamokLista);
        szamokFrissiteseListaAlapjan();
        sorsoltSzamokLista.clear();
        sorsolGomb.setText("Sorsol");
    }

    private void szamokFrissiteseListaAlapjan() {
        sorsoltSzamok.getChildren().clear();
        for (int szam : sorsoltSzamokLista) {
            sorsoltSzamok.getChildren().add(new Label(String.valueOf(szam)));
        }
    }

}