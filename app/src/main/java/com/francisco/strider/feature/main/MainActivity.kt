package com.francisco.strider.feature.main

import android.os.Bundle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.francisco.strider.commons.extensions.composeNavigate
import com.francisco.strider.commons.extensions.setStatusBarColor
import com.francisco.strider.commons.navigation.compose.setNavigationContent
import com.francisco.strider.commons.view.BaseComposeActivity
import com.francisco.strider.dsc.R
import com.francisco.strider.feature.home.HomeScreen
import com.francisco.strider.feature.main.MainViewModel.Navigation
import com.francisco.strider.feature.poster.PostersScreen
import com.francisco.strider.feature.user.UserScreen

class MainActivity : BaseComposeActivity<MainViewModel>() {

    override fun viewModelClass(): Class<MainViewModel> = MainViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarColor(R.color.support100)
        setNavigationContent(
            startDestination = Navigation.HomeScreen.route,
            navGraphBuilder = this::navGraphBuilder,
            navEventFlow = flowViewModel.eventsFlow,
            navEvent = this::navEvent
        )
    }

    private fun navGraphBuilder(builder: NavGraphBuilder) = builder.apply {
        composable(Navigation.HomeScreen.route) {
            HomeScreen(composeViewModel(), flowViewModel)
        }
        composable(Navigation.PosterScreen.route) {
            PostersScreen(composeViewModel(), flowViewModel)
        }
        composable(Navigation.UserScreen.route) {
            UserScreen(composeViewModel(), flowViewModel)
        }
    }

    private fun navEvent(navController: NavHostController, navScreen: Navigation) {
        navController.composeNavigate(
            route = navScreen.route,
            popStack = navScreen.popStack
        )
    }
}