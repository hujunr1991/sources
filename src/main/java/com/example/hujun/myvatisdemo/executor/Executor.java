package com.example.hujun.myvatisdemo.executor;



import com.example.hujun.myvatisdemo.pojo.Configuration;
import com.example.hujun.myvatisdemo.pojo.MappedStatement;

import java.util.List;

public interface Executor { 

    <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object param) throws Exception;

    void close();
}
