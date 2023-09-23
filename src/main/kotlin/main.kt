import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

fun main() {
    val content = MutableStateFlow<(@Composable () -> Unit)?>(null)

    fun updateContent() {
        // Simulate some time to update content
        Thread.sleep(1000)
        content.value = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text("Second Screen")
            }
        }
    }

    // init content
    content.value = {
        val scope = rememberCoroutineScope()

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("First Screen")
                Button(
                    onClick = {
                        scope.launch { updateContent() }
                    }
                ) {
                    Text("Update Content")
                }
            }
        }
    }

    application {
        Window(onCloseRequest = ::exitApplication) {
            val contentState = content.collectAsState()
            contentState.value!!.invoke()
        }
    }
}
