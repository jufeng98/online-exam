package org.javamaster.fragmentlearning.ioc

import com.fasterxml.jackson.databind.ObjectMapper
import dagger.Module
import dagger.Provides
import org.javamaster.fragmentlearning.service.DiscussService
import org.javamaster.fragmentlearning.service.LearnService
import org.javamaster.fragmentlearning.service.LoginService
import org.javamaster.fragmentlearning.service.MessagesService
import org.javamaster.fragmentlearning.service.impl.DiscussServiceImpl
import org.javamaster.fragmentlearning.service.impl.LearnServiceImpl
import org.javamaster.fragmentlearning.service.impl.LoginServiceImpl
import org.javamaster.fragmentlearning.service.impl.MessagesServiceImpl
import org.javamaster.fragmentlearning.ui.viewModel.*

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

    @Provides
    @ActivityScope
    fun learnService(objectMapper: ObjectMapper): LearnService {
        return LearnServiceImpl(objectMapper)
    }

    @Provides
    @ActivityScope
    fun messagesService(objectMapper: ObjectMapper): MessagesService {
        return MessagesServiceImpl(objectMapper)
    }

    @Provides
    @ActivityScope
    fun discussService(objectMapper: ObjectMapper): DiscussService {
        return DiscussServiceImpl(objectMapper)
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

    @Provides
    @ActivityScope
    fun examingViewPageModel(learnService: LearnService): ExamingViewPageModel {
        return ExamingViewPageModel(learnService)
    }

    @Provides
    @ActivityScope
    fun archiveViewModel(loginService: LoginService): ArchiveViewModel {
        return ArchiveViewModel(loginService)
    }

    @Provides
    @ActivityScope
    fun discussViewModel(): DiscussViewModel {
        return DiscussViewModel()
    }

}