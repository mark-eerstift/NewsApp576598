package nl.vanderneut.mark.ui.screen

import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import nl.vanderneut.mark.Screens

@Composable
fun SplashScreen(navController: NavController) {

    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true ){
        scale.animateTo(targetValue = 0.9f,
            animationSpec = tween(durationMillis = 100,
                easing = {
                    OvershootInterpolator(8f)
                        .getInterpolation(it)
                })
        )
        delay(250)

        if (FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()){

            navController.navigate(Screens.LoginScreen.name)

        }else {

            Log.d("splash", "hitting else")
            Log.d("curruser", FirebaseAuth.getInstance().currentUser?.email.toString())
            //HERE IS THE ISSUE, this needs to point to the homepage.
            navController.navigate(Screens.TopNews.name)

        }




    }

    Surface(modifier = Modifier
        .padding(15.dp)
        .size(330.dp)
        .scale(scale.value),
        shape = CircleShape,
        color = Color.White,
        border = BorderStroke(width = 2.dp,
            color = Color.LightGray)
    ) {
        Column(
            modifier = Modifier.padding(1.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.height(15.dp))
            Text(text = "Real news!",
                style = MaterialTheme.typography.h5,
                color = Color.LightGray)



        }


    }

}