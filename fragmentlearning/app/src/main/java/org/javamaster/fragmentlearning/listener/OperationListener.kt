package org.javamaster.fragmentlearning.listener

/**
 * @author yudong
 * @date 2019/9/2
 */
interface OperationListener<T> {
    fun success(t: T)
}