module yefimov483.figurepaint {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens yefimov483.figurepaint to javafx.fxml;
    exports yefimov483.figurepaint;
    exports yefimov483.figurepaint.GraphicsHelper;
    opens yefimov483.figurepaint.GraphicsHelper to javafx.fxml;
    exports yefimov483.figurepaint.Figures;
    opens yefimov483.figurepaint.Figures to javafx.fxml;
}