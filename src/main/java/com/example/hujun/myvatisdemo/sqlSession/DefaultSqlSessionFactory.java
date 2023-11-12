package com.example.hujun.myvatisdemo.sqlSession;

import com.example.hujun.myvatisdemo.executor.Executor;
import com.example.hujun.myvatisdemo.executor.SimpleExecutor;
import com.example.hujun.myvatisdemo.pojo.Configuration;

public class DefaultSqlSessionFactory implements SqlSessionFactory{

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        // 1.创建执行器对象
        Executor simpleExecutor = new SimpleExecutor();

        // 2.生产sqlSession对象
        DefaultSqlSession defaultSqlSession = new DefaultSqlSession(configuration,simpleExecutor);

        return defaultSqlSession;
    }
}
