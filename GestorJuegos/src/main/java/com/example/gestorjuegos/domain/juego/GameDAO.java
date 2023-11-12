package com.example.gestorjuegos.domain.juego;

import com.example.gestorjuegos.domain.DAO;
import com.example.gestorjuegos.domain.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameDAO implements DAO<Game> {

    public static final HashMap<String, String> QUERYATTR;//Porque va a ser común a todos es final y estático y como es una constante va en mayusculas

    static {
        QUERYATTR = new HashMap<>();    //Mapeo un String con consultas, si le digo: studio me devuelve la sentencia "select  distinct(g.category) from Game g"
        QUERYATTR.put("studio", "select  distinct(g.category) from Game g");
        QUERYATTR.put("enterprise", "select  distinct(g.enterprise) from Game g");
        QUERYATTR.put("boxStatus", "select  distinct(g.boxStatus) from Game g");
        QUERYATTR.put("gameStatus", "select  distinct(g.gameStatus) from Game g");
        QUERYATTR.put("category", "select  distinct(g.category) from Game g");
        QUERYATTR.put("platform", "select  distinct(g.platform) from Game g");
        QUERYATTR.put("format", "select  distinct(g.format) from Game g");
    }

    @Override
    public ArrayList<Game> getAll() {
        return null;
    }

   /* public ArrayList<Game> getAllFromUser(User u) {
        ArrayList<Game> results = new ArrayList<>(0);

        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            Query<Game> q = s.createQuery("from Game where usuarioId=:userId",Game.class);
            q.setParameter("userId",u.getId());
            results = (ArrayList<Game>) q.getResultList();
        }
        return results;
    }*/

    @Override
    public Game get(Long id) {
        return null;
    }

    @Override
    public Game save(Game data) {
        return null;
    }

    @Override
    public void update(Game data) {

        try( org.hibernate.Session s = HibernateUtil.getSessionFactory().openSession()){
            Transaction t = s.beginTransaction();

            Game g = s.get(Game.class, data.getId());
            Game.merge(data, g);
            t.commit();

        }

    }



    @Override
    public void delete(Game data) {

    }

    public List<String> getCategories(){
        ArrayList<String> results = new ArrayList<>(0);

        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            Query<String> q = s.createQuery("select  distinct(g.category) from Game g", String.class);
            results = (ArrayList<String>) q.getResultList();
        }
        return results;
    }

    public List<String> getDistinctFromAttribute(String attr){
        ArrayList<String> results = new ArrayList<>(0);

        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            Query<String> q = s.createQuery(QUERYATTR.get(attr), String.class);
            results = (ArrayList<String>) q.getResultList();
        }
        return results;
    }
}
