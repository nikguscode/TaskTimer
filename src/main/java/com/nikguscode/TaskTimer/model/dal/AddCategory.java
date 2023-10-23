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
@Getter
@Setter
@Slf4j
public class AddCategory {

    private TelegramData telegramData;
    private boolean isTransacted;

    public AddCategory(TelegramData telegramData) {
        this.telegramData = telegramData;
    }

    public void transaction() {
        telegramData.getFormattedCategory();
        Configuration configuration = new Configuration();
        configuration.configure();

        try {
            SessionFactory sessionFactory = configuration.buildSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            String hql = "FROM Category c WHERE c.categoryName LIKE :searchText";
            Query query = session.createQuery(hql);
            query.setParameter("searchText", telegramData.getCategoryName());
            List<Category> result = query.list();

            if (result.isEmpty()) {
                Category category = Category.builder()
                        .categoryName(telegramData.getCategoryName())
                        .categoryDescription(telegramData.getCategoryDescription())
                        .userId(telegramData.getChatId())
                        .isActive(false)
                        .build();
                session.save(category);

                session.getTransaction().commit();
                session.close();
                isTransacted = true;
            } else {
                session.close();
                log.warn("[Adding Category] Данная категория уже создана");
            }

        } catch (Exception e) {
            log.error("Ошибка в SessionFactory: ", e);
            throw new RuntimeException("Ошибка в SessionFactory: " + e.getMessage(), e);
        }
    }

}
