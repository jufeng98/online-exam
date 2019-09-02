package org.javamaster.fragmentlearning.ioc

import com.fasterxml.jackson.databind.ObjectMapper
import dagger.Component
import org.javamaster.fragmentlearning.testDatabase.MyDatabaseHelper
import javax.inject.Singleton

/**
 * @author yudong
 * @date 2019/8/18
 */
@Singleton
@Component(
    modules = [GlobalModule::class]
)
interface GlobalComponent {
    fun objectMapper(): ObjectMapper
    fun myDatabaseHelper(): MyDatabaseHelper
}