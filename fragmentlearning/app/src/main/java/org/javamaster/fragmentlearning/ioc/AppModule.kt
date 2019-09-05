package org.javamaster.fragmentlearning.ioc

import com.fasterxml.jackson.databind.ObjectMapper
import dagger.Module
import dagger.Provides
import org.javamaster.fragmentlearning.data.LoginService
import org.javamaster.fragmentlearning.data.impl.LoginServiceImpl
import org.javamaster.fragmentlearning.ui.login.LoginViewModel
import org.javamaster.fragmentlearning.ui.signup.SignupViewModel

/**
 * 单例对象只能在同一个Activity中有效,不同的Activity注入的不是同一个对象.所以这里提供的对象仅仅是Activity范围内单例
 * @author yudong
 * @date 2019/8/18
 */
@Module
class AppModule {

    @Provides
    @ActivityScope
    fun loginService(objectMapper: ObjectMapper): LoginService {
        return LoginServiceImpl(objectMapper)
    }

    //  @Named("dev")
    @Provides
    @ActivityScope
    fun loginViewModel(loginService: LoginService): LoginViewModel {
        return LoginViewModel(loginService)
    }

    @Provides
    @ActivityScope
    fun signupViewModel(loginService: LoginService): SignupViewModel {
        return SignupViewModel(loginService)
    }

}