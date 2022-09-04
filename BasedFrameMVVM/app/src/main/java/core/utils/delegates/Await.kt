package core.utils.delegates

import java.util.concurrent.CountDownLatch
import kotlin.reflect.KProperty

class Await<T> {

    private val countDownLatch = CountDownLatch(1)
    var value: T? = null

    operator fun getValue(thisRef: Any, property: KProperty<*>): T{
        countDownLatch.await()
        return value!!
    }

    operator fun setValue(thisRef: Any, property: KProperty<*>, value: T){
        if (this.value == null && value != null){
            this.value = value
            countDownLatch.countDown()
        }
    }
}