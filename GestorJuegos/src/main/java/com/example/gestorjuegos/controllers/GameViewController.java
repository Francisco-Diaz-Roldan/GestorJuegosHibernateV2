package com.example.gestorjuegos.controllers;

import com.example.gestorjuegos.App;
import com.example.gestorjuegos.Session;
import com.example.gestorjuegos.domain.HibernateUtil;
import com.example.gestorjuegos.domain.juego.Game;
import com.example.gestorjuegos.domain.juego.GameDAO;
import com.example.gestorjuegos.domain.usuario.User;
import com.example.gestorjuegos.domain.usuario.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import org.hibernate.Transaction;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameViewController implements Initializable
{
    private final GameDAO gameDAO = new GameDAO();

    @javafx.fxml.FXML
    private MenuItem mVolver;
    @javafx.fxml.FXML
    private MenuItem mCerrar;
    @javafx.fxml.FXML
    private Label gameInfo;
    @javafx.fxml.FXML
    private Label labelTitle;
    @javafx.fxml.FXML
    private TextField txtName;
    @javafx.fxml.FXML
    private Spinner<Integer> spinnerYear;
    @javafx.fxml.FXML
    private Spinner<Integer> spinnerPlayers;
    @javafx.fxml.FXML
    private ChoiceBox<User> comboUser;
    @javafx.fxml.FXML
    private Button btnSave;
    @javafx.fxml.FXML
    private Button btnDelete;
    @javafx.fxml.FXML
    private Button btnReturn;
    @javafx.fxml.FXML
    private ComboBox<String> comboCategory;
    @javafx.fxml.FXML
    private ComboBox<String> comboStudio;
    @javafx.fxml.FXML
    private ComboBox<String> comboFormat;
    @javafx.fxml.FXML
    private ComboBox<String>  comboPlatform;
    @javafx.fxml.FXML
    private ComboBox<String>  comboEnterprise;
    @javafx.fxml.FXML
    private ComboBox<String>  comboGameStatus;
    @javafx.fxml.FXML
    private ComboBox<String>  comboBoxStatus;


    @javafx.fxml.FXML
    public void volver(ActionEvent actionEvent) {

        try {
            Session.setCurrentGame(null);//Siempre que vuelvo setea a null el currentGame
            App.changeScene("main-view.fxml","Colección de videojuegos");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @javafx.fxml.FXML
    public void salir(ActionEvent actionEvent) {
        try {
            App.changeScene("login-view.fxml","Login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        gameInfo.setText(Session.getCurrentGame().toString());

        spinnerPlayers.setValueFactory( new SpinnerValueFactory.IntegerSpinnerValueFactory(1,4, Math.toIntExact(Session.getCurrentGame().getPlayers()),1) );
        spinnerYear.setValueFactory( new SpinnerValueFactory.IntegerSpinnerValueFactory(1970,2023, Math.toIntExact(Session.getCurrentGame().getYear()),1));
        comboUser.setConverter(new StringConverter<User>() {
            @Override
            public String toString(User user) {
                return (user!=null)? user.getUsername() : "";
            }

            @Override
            public User fromString(String s) {
                return null;
            }
        });

        txtName.setText( Session.getCurrentGame().getName() );
        //txtCategory.setText( Session.getCurrentGame().getCategory());
        comboBoxStatus.setValue( Session.getCurrentGame().getBoxStatus());
        comboEnterprise.setValue( Session.getCurrentGame().getEnterprise());
        comboGameStatus.setValue( Session.getCurrentGame().getGameStatus());
        comboFormat.setValue( Session.getCurrentGame().getFormat());
        comboPlatform.setValue( Session.getCurrentGame().getPlatform());
        comboStudio.setValue( Session.getCurrentGame().getStudio());
        labelTitle.setText( Session.getCurrentGame().getName());

        comboUser.getItems().addAll( (new UserDAO()).getAll() );
        comboUser.setValue(Session.getCurrentGame().getUser());

        //Declarar arriba (new GameDAO().getCategories())

        //Para el combobox de categorías
        var categories = gameDAO.getCategories();//La variable categories es un array de String
        comboCategory.getItems().addAll(categories );

        comboCategory.setValue(Session.getCurrentGame().getCategory());//Seteo el combo
        comboStudio.getItems().addAll(gameDAO.getDistinctFromAttribute("studio") );
        comboFormat.getItems().addAll(gameDAO.getDistinctFromAttribute("format") );
        comboGameStatus.getItems().addAll(gameDAO.getDistinctFromAttribute("gameStatus") );
        comboPlatform.getItems().addAll(gameDAO.getDistinctFromAttribute("platform") );
        comboEnterprise.getItems().addAll(gameDAO.getDistinctFromAttribute("enterprise") );


    }

    @javafx.fxml.FXML
    public void save(ActionEvent actionEvent) {

        Game g = Session.getCurrentGame();

        if(txtName.getText().length()>1) g.setName( txtName.getText() );
        if(comboCategory.getValue().length()>1) g.setCategory(comboCategory.getValue());
        if(comboStudio.getValue().length()>1) g.setStudio( comboStudio.getValue() );
        if(comboPlatform.getValue().length()>1) g.setPlatform( comboPlatform.getValue() );
        if(comboEnterprise.getValue().length()>1) g.setEnterprise( comboEnterprise.getValue() );
        if(comboBoxStatus.getValue().length()>1) g.setBoxStatus( comboBoxStatus.getValue() );
        if(comboGameStatus.getValue().length()>1) g.setGameStatus( comboGameStatus.getValue() );
        if(comboFormat.getValue().length()>1) g.setFormat( comboFormat.getValue() );
        g.setYear(Long.valueOf(spinnerYear.getValue()));
        g.setPlayers(Long.valueOf(spinnerPlayers.getValue()));

        if (g.getId()!=null){//Campo Ide en juego nuevo = null, en un juego seleccionado !=null -> importante paraa hacer un update o un save
            gameDAO.update(g);//Si selecciono en la tabla el Id no es nulo, por lo que actualiza, en caso contrario, guarda
        }else{
            gameDAO.save(g);
        }

        volver(null);//Si no se volviera atrás habría que setear para tener sincronizado el currentGame con la base de datos

    }

    @javafx.fxml.FXML
    public void delete(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("¿Deseas borrar "+Session.getCurrentGame().getName()+ " del listado?");

        var result = alert.showAndWait().get();     //Como es un optional le pongo un get para acceder al componente. Me va a dar la información result del botón que se ha pulsado

        System.out.println(result);//Para comprobar que me lo imprime bien
        if (result.getButtonData()== ButtonBar.ButtonData.OK_DONE){ //Me aseguro de que solo cuando le dé a aceptar lo borro
            gameDAO.delete( Session.getCurrentGame() );
            volver(null);
        }


    }


}