package com.nikguscode.TaskTimer.model.dal;

import com.nikguscode.TaskTimer.model.entity.Category;
import com.nikguscode.TaskTimer.model.service.TelegramData;
import com.nikguscode.TaskTimer.model.service.commands.Launch;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
@Getter
@Setter
@Slf4j
public class Add {

    private TelegramData telegramData;
    private Launch launch;
    private boolean isTransacted;

    public Add(TelegramData telegramData,
               Launch launch) {
        this.telegramData = telegramData;
        this.launch = launch;
    }

    public void addCategory(Update update) {
        Configuration configuration = new Configuration();
        configuration.configure();

        try {
            SessionFactory sessionFactory = configuration.buildSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            String hql = "FROM Category c WHERE c.categoryName LIKE :searchText";
            Query<Category> query = session.createQuery(hql, Category.class);
            query.setParameter("searchText", telegramData.getCategoryName());
            List<Category> categoryName = query.list();

            if (update.hasMessage()) {
                telegramData.getFormattedCategory();
            }

            if (categoryName.isEmpty()) {
                Category category = Category.builder()
                        .categoryName(telegramData.getCategoryName())
                        .categoryDescription(telegramData.getCategoryDescription())
                        .userId(telegramData.getChatId())
                        .isActive(false)
                        .build();
                session.persist(category);

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
