package com.nikguscode.TaskTimer.model.dal;

import com.nikguscode.TaskTimer.model.entity.Category;
import com.nikguscode.TaskTimer.model.service.TelegramData;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@Getter
@Setter
public class GetCategory {

    private TelegramData telegramData;
    private boolean isTransacted;
    private List<Category> result;

    public GetCategory(TelegramData telegramData) {
        this.telegramData = telegramData;
    }

    public void getActiveCategory() {
        Configuration configuration = new Configuration();
        configuration.configure();

        try {
            SessionFactory sessionFactory = configuration.buildSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            String hql = "SELECT c.categoryName FROM Category c WHERE c.userId = :userId AND c.isActive = true";
            Query query = session.createQuery(hql);
            query.setParameter("userId", telegramData.getChatId());
            result = query.list();

            if (!result.isEmpty()) {
                isTransacted = true;
            } else {
                session.close();
                log.warn("[Active Category] У пользователя нет активных категорий");
            }

        } catch (Exception e) {
            log.error("Ошибка в GetCategory: ", e);
            throw new RuntimeException("Ошибка в GetCategory: " + e.getMessage(), e);
        }
    }
}
