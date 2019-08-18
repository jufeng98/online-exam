package org.javamaster.fragmentlearning.ioc

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import dagger.Module
import dagger.Provides
import org.javamaster.fragmentlearning.data.LoginService
import org.javamaster.fragmentlearning.data.impl.LoginServiceImpl
import org.javamaster.fragmentlearning.ui.login.LoginViewModel
import javax.inject.Singleton

/**
 * @author yudong
 * @date 2019/8/18
 */
@Module
class AppModule {

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
    fun loginService(objectMapper: ObjectMapper): LoginService {
        return LoginServiceImpl(objectMapper)
    }

    @Provides
    @Singleton
    fun loginViewModel(loginService: LoginService): LoginViewModel {
        return LoginViewModel(loginService)
    }

}