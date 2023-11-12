package com.example.gestorjuegos.domain.usuario;

import com.example.gestorjuegos.domain.juego.Game;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Table(name="user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="usuario")
    private String username;

    @Column(name="contraseña")
    private String password;

    @Transient
    private Long gamesQuantity;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Game> games = new ArrayList<>(0);

    public Long getGamesQuantity() {
        gamesQuantity = (long) games.size();
        return gamesQuantity;
    }

    public void addGame(Game g){
        g.setUser(this);
        games.add(g);
    }

    public void removeGame(Game g){
        games.remove(g);
        g.setUser(null);
    }

}
