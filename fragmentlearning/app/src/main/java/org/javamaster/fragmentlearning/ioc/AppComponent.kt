package org.javamaster.fragmentlearning.ioc

import dagger.Component
import org.javamaster.fragmentlearning.fragment.DiscussFragment
import org.javamaster.fragmentlearning.fragment.LearnFragment
import org.javamaster.fragmentlearning.fragment.PlayFragment
import org.javamaster.fragmentlearning.testActivity.SQLiteActivity
import org.javamaster.fragmentlearning.testProvider.MyContentProvider
import org.javamaster.fragmentlearning.ui.activities.*

/**
 * @author yudong
 * @date 2019/8/18
 */
@ActivityScope
@Component(
    modules = [AppModule::class],
    dependencies = [GlobalComponent::class]
)
interface AppComponent {
    fun inject(loginActivity: LoginActivity)
    fun inject(sqLiteActivity: SQLiteActivity)
    fun inject(myContentProvider: MyContentProvider)
    fun inject(signUpActivity: SignUpActivity)
    fun inject(mainActivity: MainActivity)
    fun inject(sectionsActivity: SectionsActivity)
    fun inject(knowledgesActivity: KnowledgesActivity)
    fun inject(knowledgePointsActivity: KnowledgePointsActivity)
    fun inject(archiveActivity: ArchiveActivity)
    fun inject(changePwdActivity: ChangePwdActivity)
    fun inject(examingActivity: ExamingActivity)
    fun inject(messagesActivity: MessagesActivity)
    fun inject(newDiscussActivity: NewDiscussActivity)
    fun inject(learnFragment: LearnFragment)
    fun inject(playFragment: PlayFragment)
    fun inject(discussFragment: DiscussFragment)
}