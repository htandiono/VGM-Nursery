package com.pbu.vgm_nursery.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pbu.vgm_nursery.ui.navigation.Screen
import com.pbu.vgm_nursery.ui.screen.login.LoginScreen
import com.pbu.vgm_nursery.ui.screen.record_add.RecordAddScreen
import com.pbu.vgm_nursery.ui.screen.record_detail.RecordDetailScreen
import com.pbu.vgm_nursery.ui.screen.scan.ScanScreen
import com.pbu.vgm_nursery.ui.screen.splash.SplashScreen

@Composable
fun SPULabApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = modifier,
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onTimeOut = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(
            route = Screen.RecordAdd.route,
            arguments = listOf(
                navArgument("operator_name") { type = NavType.StringType },
                navArgument("selected_tab_index") { type = NavType.IntType },
                navArgument("get_csv_file_immediately") { type = NavType.BoolType }
            )
        ) {
            val operatorName = it.arguments?.getString("operator_name")
            val scanResult by it.savedStateHandle.getLiveData<String>("scan_result").observeAsState()
            val selectedTabIndex = it.arguments?.getInt("selected_tab_index") ?: 0
            val getCsvFileImmediately = it.arguments?.getBoolean("get_csv_file_immediately") ?: false

            if (operatorName != null) {
                RecordAddScreen(
                    operatorName = operatorName,
                    navController = navController,
                    scanResult = scanResult,
                    selectedTabIndexDef = selectedTabIndex,
                    getCsvFileImmediately = getCsvFileImmediately
                )
            }
        }
        composable(Screen.RecordDetail.route) {
            val scanResult by it.savedStateHandle.getLiveData<String>("scan_result")
                .observeAsState()
            RecordDetailScreen(navController = navController, scanResult = scanResult)
        }
        composable(Screen.Scan.route) {
            ScanScreen(navController = navController, onQrCodeScanned = {
                navController.previousBackStackEntry?.savedStateHandle?.set("scan_result", it)
                navController.navigateUp()
            })
        }
    }
}