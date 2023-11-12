package com.example.gestorjuegos.domain.usuario;

import com.example.gestorjuegos.domain.DAO;
import com.example.gestorjuegos.domain.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class UserDAO implements DAO<User> {
    @Override
    public ArrayList<User> getAll() {
        var salida = new ArrayList<User>(0);

        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            Query<User> q = s.createQuery("from User",User.class);
            salida = (ArrayList<User>) q.getResultList();
        }

        return salida;
    }

    @Override
    public User get(Long id) {
        var salida = new User();//Usuario puede que sea null

        try(Session s = HibernateUtil.getSessionFactory().openSession()){
            salida = s.get(User.class, id);//Aquí se machaca en caso de que el Usuario sea null
        }
        return salida;
    }

    @Override
    public User save(User data) {
        return null;
    }

    @Override
    public void update(User data) {

    }


    @Override
    public void delete(User data) {

    }

    public User validateUser(String username, String password){
        User result = null;
        try( Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<User> q = session.createQuery("from User where username=:u and password=:p",User.class);
            q.setParameter("u",username);
            q.setParameter("p",password);

            try {
                result = q.getSingleResult();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return result;

        /*
        HibernateUtil.getSessionFactory().inSession(
                (session)->{

                }
        );
        */
    }
}
