package org.javamaster.fragmentlearning.ioc

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import dagger.Module
import dagger.Provides
import org.javamaster.fragmentlearning.common.App
import org.javamaster.fragmentlearning.testDatabase.MyDatabaseHelper
import javax.inject.Singleton

/**
 * 实现应用范围内单例,所有Activity等注入的都是同一个对象,所以这里提供的对象是应用范围内单例
 * @author yudong
 * @date 2019/8/18
 */
@Module
class GlobalModule {
    //    Provide 如果是单例模式 对应的Component 也要是单例模式
//    inject(Activity act) 不能放父类
//    即使使用了单利模式，在不同的Activity 对象还是不一样的
//    依赖component， component之间的Scoped 不能相同
//    子类component 依赖父类的component ，子类component的Scoped 要小于父类的Scoped，Singleton的级别是Application
//    多个Module 之间不能提供相同的对象实例
//    Module 中使用了自定义的Scoped 那么对应的Component 使用同样的Scoped
    @Provides
    @Singleton
    fun objectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS)
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        objectMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false)
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
        return objectMapper
    }

    @Provides
    @Singleton
    fun myDatabaseHelper(): MyDatabaseHelper {
        return MyDatabaseHelper(App.context, "BookStore.db", null, 3)
    }

}