module hu.petrik.lotto {
    requires javafx.controls;
    requires javafx.fxml;


    opens hu.petrik.lotto to javafx.fxml;
    exports hu.petrik.lotto;
}