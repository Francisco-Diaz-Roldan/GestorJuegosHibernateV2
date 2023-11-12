module com.example.gestorjuegos {
    requires javafx.controls;
    requires javafx.fxml;

    requires lombok;

    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;

    opens com.example.gestorjuegos.domain.usuario;
    opens com.example.gestorjuegos.domain.juego;

    opens com.example.gestorjuegos to javafx.fxml;
    opens com.example.gestorjuegos.controllers to javafx.fxml;

    exports com.example.gestorjuegos;
    exports com.example.gestorjuegos.controllers;
}