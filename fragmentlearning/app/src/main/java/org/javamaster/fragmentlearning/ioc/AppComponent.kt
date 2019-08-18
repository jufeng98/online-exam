package org.javamaster.fragmentlearning.ioc

import dagger.Component
import org.javamaster.fragmentlearning.ui.activities.LoginActivity
import javax.inject.Singleton

/**
 * @author yudong
 * @date 2019/8/18
 */
@Singleton
@Component(
    modules = [AppModule::class]
)
interface AppComponent {
    fun inject(loginActivity: LoginActivity)
}