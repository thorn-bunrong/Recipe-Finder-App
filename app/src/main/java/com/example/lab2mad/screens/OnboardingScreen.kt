package com.example.lab2mad.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab2mad.R
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.example.lab2mad.ui.theme.Lab2MADTheme
import com.google.accompanist.pager.ExperimentalPagerApi

// 1. Data class for a single onboarding page
data class OnboardingPage(
    val imageRes: Int,
    val title: String,
    val description: String
)

// 2. Content for your pages
// (Remember to add your own images to the res/drawable folder)
val onboardingPages = listOf(
    OnboardingPage(
        imageRes = R.drawable.ic_launcher_foreground, // Replace with your image
        title = "Welcome to Your App!",
        description = "This is a simple introduction to the app's main features."
    ),
    OnboardingPage(
        imageRes = R.drawable.ic_launcher_foreground, // Replace with your image
        title = "Explore and Discover",
        description = "Find amazing content tailored just for you."
    ),
    OnboardingPage(
        imageRes = R.drawable.ic_launcher_foreground, // Replace with your image
        title = "You're All Set!",
        description = "Press 'Get Started' to jump into the main experience."
    )
)

// 3. The complete Onboarding Screen composable
@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnboardingScreen(onFinish: () -> Unit) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        // HorizontalPager for swiping
        HorizontalPager(
            count = onboardingPages.size,
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { pageIndex ->
            OnboardingPageUi(page = onboardingPages[pageIndex])
        }

        // Pager indicators (dots)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            HorizontalPagerIndicator(
                pagerState = pagerState,
            )
        }

        // "Get Started" button, visible only on the last page
        if (pagerState.currentPage == onboardingPages.size - 1) {
            Button(
                onClick = onFinish,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp, vertical = 24.dp)
            ) {
                Text("Get Started")
            }
        }
    }
}

// 4. UI for a single page
@Composable
fun OnboardingPageUi(page: OnboardingPage) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = page.imageRes),
            contentDescription = page.title,
            modifier = Modifier.size(150.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = page.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = page.description,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

// 5. Preview for Android Studio
@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    Lab2MADTheme {
        OnboardingScreen(onFinish = {})
    }
}
