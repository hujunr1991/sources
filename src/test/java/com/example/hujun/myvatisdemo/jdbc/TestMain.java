package com.example.hujun.myvatisdemo.jdbc;

import com.example.hujun.myvatisdemo.dao.IUserDao;
import com.example.hujun.myvatisdemo.io.Resources;
import com.example.hujun.myvatisdemo.pojo.User;
import com.example.hujun.myvatisdemo.sqlSession.SqlSession;
import com.example.hujun.myvatisdemo.sqlSession.SqlSessionFactory;
import com.example.hujun.myvatisdemo.sqlSession.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.sql.*;
import java.util.List;

public class TestMain {

    /**
     * 手动执行方式
     * @param args
     */
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            //加载数据库驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            //通过驱动获取连接
            connection = DriverManager.getConnection("jdbc:mysql://192.168.110.128:3306/mybatis?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai", "root", "lilishop");

            ////定义sql
            String sql = "select * from user where username = ?";
            //预处理
            preparedStatement = connection.prepareStatement(sql);

            //设置参数
            preparedStatement.setString(1, "tom");
            //执行
            resultSet = preparedStatement.executeQuery();
            User user = new User();
            //遍历结果
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                user.setId(id);
                user.setUsername(username);
            }
            System.out.println(user);
            //封装dto


            //释放资源
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


    }



    /**
     * 传统方式（不使用mapper代理）测试
     */
    @Test
    public void test1() throws Exception {

        // 1.根据配置文件的路径，加载成字节输入流，存到内存中 注意：配置文件还未解析
        InputStream resourceAsSteam = Resources.getResourceAsSteam("sqlMapConfig.xml");

        // 2.解析了配置文件，封装了Configuration对象  2.创建sqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsSteam);

        // 3.生产sqlSession 创建了执行器对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 4.调用sqlSession方法
        User user = new User();
        user.setId(1);
        user.setUsername("tom");
    /*    User user2 = sqlSession.selectOne("user.selectOne", user);

        System.out.println(user2);*/
        List<User> list = sqlSession.selectList("user.selectList", null);
        for (User user1 : list) {
            System.out.println(user1);
        }

        // 5.释放资源
        sqlSession.close();
    }

    /**
     * mapper代理测试
     */
    @Test
    public void test2() throws Exception {

        // 1.根据配置文件的路径，加载成字节输入流，存到内存中 注意：配置文件还未解析
        InputStream resourceAsSteam = Resources.getResourceAsSteam("sqlMapConfig.xml");

        // 2.解析了配置文件，封装了Configuration对象  2.创建sqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsSteam);

        // 3.生产sqlSession 创建了执行器对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 4.调用sqlSession方法
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

      /*  User user1 = new User();
        user1.setId(1);
        user1.setUsername("tom");
        User user3 = userDao.findByCondition(user1);
        System.out.println(user3);*/
        List<User> all = userDao.findAll();
        for (User user : all) {
            System.out.println(user);
        }

        // 5.释放资源
        sqlSession.close();


    }
}
