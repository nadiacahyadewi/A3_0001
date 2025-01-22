package com.example.pam_tugasakhir.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pam_tugasakhir.ui.view.Merk.DestinasiMerkDetail
import com.example.pam_tugasakhir.ui.view.Merk.DestinasiMerkEntry
import com.example.pam_tugasakhir.ui.view.Merk.DestinasiMerkHome
import com.example.pam_tugasakhir.ui.view.Merk.DetailMerkScreen
import com.example.pam_tugasakhir.ui.view.Merk.EntryMerkScreen
import com.example.pam_tugasakhir.ui.view.Merk.HomeMerkScreen
import com.example.pam_tugasakhir.ui.view.kategori.DestinasiKategoriEntry
import com.example.pam_tugasakhir.ui.view.kategori.DestinasiKategoriHome
import com.example.pam_tugasakhir.ui.view.kategori.EntryKategoriScreen
import com.example.pam_tugasakhir.ui.view.kategori.HomeKategoriScreen
import com.example.pam_tugasakhir.ui.view.pemasok.DestinasiPemasokDetail
import com.example.pam_tugasakhir.ui.view.pemasok.DestinasiPemasokEntry
import com.example.pam_tugasakhir.ui.view.pemasok.DestinasiPemasokHome
import com.example.pam_tugasakhir.ui.view.pemasok.DetailPemasokScreen
import com.example.pam_tugasakhir.ui.view.pemasok.EntryPemasokScreen
import com.example.pam_tugasakhir.ui.view.pemasok.HomePemasokScreen
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
        // Produk Navigation
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                navigateToPemasok = { navController.navigate(DestinasiPemasokHome.route) },
                onPemasokClick = { navController.navigate(DestinasiPemasokHome.route) },
                navigateToMerk = { navController.navigate(DestinasiMerkHome.route) },
                onMerkClick = { navController.navigate(DestinasiMerkHome.route) },
                onDetailClick = { idProduk ->
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
                },
                onKategoriClick = {navController.navigate(DestinasiKategoriHome.route)}
            )
        }

        // Pemasok Navigation
        composable(DestinasiPemasokHome.route) {
            HomePemasokScreen(
                navigateToPemasokEntry = {
                    navController.navigate(DestinasiPemasokEntry.route)
                },
                onDetailClick = { idPemasok ->
                    navController.navigate("${DestinasiPemasokDetail.route}/$idPemasok")
                },
                navigateToMerk = { navController.navigate(DestinasiMerkHome.route) },
                onMerkClick = { navController.navigate(DestinasiMerkHome.route) },
                onBack = {
                    navController.navigate(DestinasiHome.route)
                },
            )
        }

        composable(DestinasiPemasokEntry.route) {
            EntryPemasokScreen(
                navigateBack = {
                    navController.navigate(DestinasiPemasokHome.route) {
                        popUpTo(DestinasiPemasokHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = "${DestinasiPemasokDetail.route}/{idPemasok}",
            arguments = listOf(navArgument("idPemasok") { type = NavType.StringType })
        ) { backStackEntry ->
            val idPemasok = backStackEntry.arguments?.getString("idPemasok") ?: ""
            DetailPemasokScreen(
                idPemasok = idPemasok,
                onNavigateBack = { navController.popBackStack() },
                onEditClick = {
                    //navController.navigate("${DestinasiPemasokUpdate.route}?idPemasok=$idPemasok")
                }
            )
        }

        composable(DestinasiMerkHome.route) {
            HomeMerkScreen(
                navigateToMerkEntry = {
                    navController.navigate(DestinasiMerkEntry.route)
                },
                onDetailClick = { idMerk ->
                    navController.navigate("${DestinasiMerkDetail.route}/$idMerk")
                },
                onBack = {
                    navController.navigate(DestinasiHome.route)
                },
            )
        }

        composable(DestinasiMerkEntry.route) {
            EntryMerkScreen(
                navigateBack = {
                    navController.navigate(DestinasiMerkHome.route) {
                        popUpTo(DestinasiMerkHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(
            route = "${DestinasiMerkDetail.route}/{idMerk}",
            arguments = listOf(navArgument("idMerk") { type = NavType.StringType })
        ) { backStackEntry ->
            val idMerk = backStackEntry.arguments?.getString("idMerk") ?: ""
            DetailMerkScreen(
                idMerk = idMerk,
                onNavigateBack = { navController.popBackStack() },
                onEditClick = {
                    //navController.navigate("${DestinasiPemasokUpdate.route}?idPemasok=$idPemasok")
                }
            )
        }

        composable(DestinasiKategoriHome.route) {
            HomeKategoriScreen(
                navigateToKategoriEntry = {
                    navController.navigate(DestinasiKategoriEntry.route)
                },
                onDetailClick = { //idMerk ->
                    //navController.navigate("${DestinasiMerkDetail.route}/$idMerk")
                },
                onBack = {
                    //navController.navigate(DestinasiHome.route)
                },
            )
        }
        composable(DestinasiKategoriEntry.route) {
            EntryKategoriScreen(
                navigateBack = {
                    navController.navigate(DestinasiKategoriHome.route) {
                        popUpTo(DestinasiKategoriHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }




    }
}
        /*omposable(
            route = "${DestinasiPemasokUpdate.route}/{idPemasok}"
        ) { backStackEntry ->
            val idPemasok = backStackEntry.arguments?.getString("idPemasok") ?: ""
            UpdatePemasokScreen(
                idPemasok = idPemasok,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
*/