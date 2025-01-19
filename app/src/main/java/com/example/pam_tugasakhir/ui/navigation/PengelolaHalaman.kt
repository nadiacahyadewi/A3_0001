package com.example.pam_tugasakhir.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pam_tugasakhir.ui.view.produk.DestinasiDetail
import com.example.pam_tugasakhir.ui.view.produk.DestinasiEntry
import com.example.pam_tugasakhir.ui.view.produk.DestinasiHome
import com.example.pam_tugasakhir.ui.view.produk.DetailScreen
import com.example.pam_tugasakhir.ui.view.produk.EntryProdukScreen
import com.example.pam_tugasakhir.ui.view.produk.HomeScreen

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier,
    ) {
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = {
                    navController.navigate(DestinasiEntry.route)
                },
                onDetailClick = { idProduk ->  // Handle onDetailClick here
                    navController.navigate("${DestinasiDetail.route}/$idProduk")
                }
            )
        }

        composable(DestinasiEntry.route) {
            EntryProdukScreen(
                navigateBack = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // Detail Screen
        composable(
            route = "${DestinasiDetail.route}/{idProduk}",
            arguments = listOf(navArgument("idProduk") { type = NavType.StringType })
        ) { backStackEntry ->
            val idProduk = backStackEntry.arguments?.getString("idProduk") ?: ""
            DetailScreen(
                idProduk = idProduk,
                onNavigateBack = { navController.navigateUp() },
                onEditClick = {
                    navController.navigate("${DestinasiEntry.route}?idProduk=$idProduk")
                }
            )
        }
    }
}
