module com.example.canvasejemplo {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens com.example.canvasejemplo to javafx.fxml;
    exports com.example.canvasejemplo;
    exports com.example.canvasejemplo.model;
    opens com.example.canvasejemplo.model to javafx.fxml;
}