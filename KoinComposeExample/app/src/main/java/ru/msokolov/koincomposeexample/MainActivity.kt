package ru.msokolov.koincomposeexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import ru.msokolov.koincomposeexample.ui.theme.KoinComposeExampleTheme

class MainActivity : ComponentActivity(), AndroidScopeComponent {

    //private val api by inject<MyApi>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KoinComposeExampleTheme {

                val viewModel = getViewModel<MainViewModel>()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }

    override val scope: Scope by activityScope()
    private val hello by inject<String>(named("hello"))
    private val bye by inject<String>(named("bye"))
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KoinComposeExampleTheme {
        Greeting("Android")
    }
}