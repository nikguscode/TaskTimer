package com.nikguscode.TaskTimer.model.dal;

import com.nikguscode.TaskTimer.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@Slf4j
public class AddUser {

    public void addUser() throws SQLException {
        Configuration configuration = new Configuration();
        configuration.configure();

        try {
            SessionFactory sessionFactory = configuration.buildSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            User user = User.builder()
                    .username("@asd")
                    .build();
            session.save(user);

            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("Ошибка в SessionFactory: ", e);
            throw new RuntimeException("Ошибка в SessionFactory: " + e.getMessage(), e);
        }

    }

}
