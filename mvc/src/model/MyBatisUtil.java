package model;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;

import java.io.IOException;

public class MyBatisUtil {
    private static SqlSessionFactory SqlSessionFactory;

    static {
        try{
            SqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourcesAsStream("mybatis-config.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SqlSession getSqlSession() {
        return SqlSessionFactory.openSession();
    }
}
