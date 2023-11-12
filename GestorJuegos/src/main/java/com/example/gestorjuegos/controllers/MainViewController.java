package com.example.gestorjuegos.controllers;

import com.example.gestorjuegos.App;
import com.example.gestorjuegos.Session;
import com.example.gestorjuegos.domain.juego.Game;
import com.example.gestorjuegos.domain.usuario.UserDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable
{
    @javafx.fxml.FXML
    private Label info;
    @javafx.fxml.FXML
    private TableView<Game> tabla;
    @javafx.fxml.FXML
    private TableColumn<Game,String> cNombre;
    @javafx.fxml.FXML
    private TableColumn<Game,String> cPlataforma;
    @javafx.fxml.FXML
    private TableColumn<Game,String> cCategoria;
    @javafx.fxml.FXML
    private TableColumn<Game,String> cAño;
    @javafx.fxml.FXML
    private TableColumn<Game,String> cEstudio;
    @javafx.fxml.FXML
    private TableColumn<Game,String> cFormato;
    @javafx.fxml.FXML
    private Label lTotal;
    @javafx.fxml.FXML
    private MenuItem mSalir;
    @javafx.fxml.FXML
    private Button btnNuevo;

    @javafx.fxml.FXML
    public void salir(ActionEvent actionEvent) {
        Session.setCurrentUser(null);
        try {
            App.changeScene("login-view.fxml","Login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cAño.setCellValueFactory( (fila)->{
            return new SimpleStringProperty(fila.getValue().getYear()+"");
        } );
        cNombre.setCellValueFactory( (fila)->{
            return new SimpleStringProperty(fila.getValue().getName());
        } );
        cPlataforma.setCellValueFactory( (fila)->{
            return new SimpleStringProperty(fila.getValue().getPlatform());
        } );
        cCategoria.setCellValueFactory( (fila)->{
            return new SimpleStringProperty(fila.getValue().getCategory());
        } );
        cEstudio.setCellValueFactory( (fila)->{
            return new SimpleStringProperty(fila.getValue().getStudio());
        } );
        cFormato.setCellValueFactory( (fila)->{
            return new SimpleStringProperty(fila.getValue().getFormat());
        } );

        tabla.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Game>() {
            @Override
            public void changed(ObservableValue<? extends Game> observableValue, Game game, Game t1) {
                Session.setCurrentGame(t1);
                try {
                    App.changeScene("game-view.fxml", t1.getName());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        //Actualizo el usuario desde la bbdd
        Session.setCurrentUser(
                new UserDAO().get(
                        Session.getCurrentUser().getId()
                )
        );//Cojo el usuario y lo asigno a sí mismo
        tabla.getItems().addAll(Session.getCurrentUser().getGames());

        lTotal.setText( Session.getCurrentUser().getGamesQuantity()+ " juegos");
        info.setText("Bienvenido "+Session.getCurrentUser().getUsername());

    }

    @javafx.fxml.FXML
    public void newGame(ActionEvent actionEvent) {

        var g = new Game();
        g.setUser(Session.getCurrentUser() ) ;
        Session.setCurrentGame( g );

        try {
            App.changeScene("game-view.fxml", "Nuevo juego");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}