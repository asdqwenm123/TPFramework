package kr.tpmc.db;

import kr.tpmc.config.Config;
import org.bukkit.plugin.Plugin;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.ArrayList;
import java.util.List;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    protected static List<Class<?>> entities = new ArrayList<>();

    public static void onEnable() {
        try {
            Configuration configuration = new Configuration();

            // MySQL 데이터베이스 연결 설정
            configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            configuration.setProperty("hibernate.connection.url", Config.config.MySQL_url);
            configuration.setProperty("hibernate.connection.username", Config.config.MySQL_user);
            configuration.setProperty("hibernate.connection.password", Config.config.MySQL_password);

            configuration.setProperty("hibernate.show_sql", "true"); //TODO 이건 테스트 코드임 변경해야함
            configuration.setProperty("hibernate.format_sql", "true"); //TODO 이건 테스트 코드임 변경해야함
            configuration.setProperty("hibernate.hbm2ddl.auto", "create"); //TODO 이건 테스트 코드임 변경해야함 create 를 update 로

            configuration.setProperty("hibernate.jakarta.persistence", "true");


            // 엔티티 매핑
            for (Class<?> entity : HibernateUtil.entities) {
                configuration.addAnnotatedClass(entity);
            }

            // 세션 팩토리 생성
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError("Hibernate: " + ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void onDisable() {
        sessionFactory.close();
    }
}
