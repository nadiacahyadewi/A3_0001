package com.example.pam_tugasakhir.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pam_tugasakhir.ui.view.DestinasiSplashScreen
import com.example.pam_tugasakhir.ui.view.Merk.DestinasiMerkDetail
import com.example.pam_tugasakhir.ui.view.Merk.DestinasiMerkEntry
import com.example.pam_tugasakhir.ui.view.Merk.DestinasiMerkHome
import com.example.pam_tugasakhir.ui.view.Merk.DestinasiUpdateMerk
import com.example.pam_tugasakhir.ui.view.Merk.DetailMerkScreen
import com.example.pam_tugasakhir.ui.view.Merk.EntryMerkScreen
import com.example.pam_tugasakhir.ui.view.Merk.HomeMerkScreen
import com.example.pam_tugasakhir.ui.view.Merk.UpdateScreenMerk
import com.example.pam_tugasakhir.ui.view.SplashScreen
import com.example.pam_tugasakhir.ui.view.kategori.DestinasiKategoriDetail
import com.example.pam_tugasakhir.ui.view.kategori.DestinasiKategoriEntry
import com.example.pam_tugasakhir.ui.view.kategori.DestinasiKategoriHome
import com.example.pam_tugasakhir.ui.view.kategori.DestinasiUpdateKategori
import com.example.pam_tugasakhir.ui.view.kategori.DetailKategoriScreen
import com.example.pam_tugasakhir.ui.view.kategori.EntryKategoriScreen
import com.example.pam_tugasakhir.ui.view.kategori.HomeKategoriScreen
import com.example.pam_tugasakhir.ui.view.kategori.UpdateScreenKategori
import com.example.pam_tugasakhir.ui.view.pemasok.DestinasiPemasokDetail
import com.example.pam_tugasakhir.ui.view.pemasok.DestinasiPemasokEntry
import com.example.pam_tugasakhir.ui.view.pemasok.DestinasiPemasokHome
import com.example.pam_tugasakhir.ui.view.pemasok.DestinasiUpdatePemasok
import com.example.pam_tugasakhir.ui.view.pemasok.DetailPemasokScreen
import com.example.pam_tugasakhir.ui.view.pemasok.EntryPemasokScreen
import com.example.pam_tugasakhir.ui.view.pemasok.HomePemasokScreen
import com.example.pam_tugasakhir.ui.view.pemasok.UpdateScreenPemasok
import com.example.pam_tugasakhir.ui.view.produk.DestinasiDetail
import com.example.pam_tugasakhir.ui.view.produk.DestinasiEntry
import com.example.pam_tugasakhir.ui.view.produk.DestinasiHome
import com.example.pam_tugasakhir.ui.view.produk.DestinasiUpdateProduk
import com.example.pam_tugasakhir.ui.view.produk.DetailScreen
import com.example.pam_tugasakhir.ui.view.produk.EntryProdukScreen
import com.example.pam_tugasakhir.ui.view.produk.HomeScreen
import com.example.pam_tugasakhir.ui.view.produk.UpdateScreenProduk

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiSplashScreen.route,  // Ubah start destination
        modifier = Modifier,
    ) {
        // Home Start Screen
        composable(DestinasiSplashScreen.route) {
            SplashScreen(
                navigateToHome = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiSplashScreen.route) { inclusive = true }
                    }
                }
            )
        }
        // Produk Navigation
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                navigateToPemasok = { navController.navigate(DestinasiPemasokHome.route) },
                navigateToMerk = { navController.navigate(DestinasiMerkHome.route) },
                navigateToKategori = { navController.navigate(DestinasiKategoriHome.route) },
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
                onEditClick = { idProduk ->
                    navController.navigate("${DestinasiUpdateProduk.route}/$idProduk")
                },

                onKategoriClick = {navController.navigate(DestinasiKategoriHome.route)}
            )
        }

        // Edit Produk
        composable(
            route = "${DestinasiUpdateProduk.route}/{idProduk}",
            arguments = listOf(navArgument("idProduk") { type = NavType.StringType })
        ) { backStackEntry ->
            val idProduk = backStackEntry.arguments?.getString("idProduk") ?: ""
            UpdateScreenProduk(
                idProduk = idProduk,
                onNavigateBack = { navController.navigateUp() }
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
                onBack = {
                    navController.navigate(DestinasiHome.route)
                },
                navigateToKategori = { navController.navigate(DestinasiKategoriHome.route) },

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
            arguments = listOf(navArgument("idPemasok") { type = NavType.IntType })
        ) { backStackEntry ->
            val idPemasok = backStackEntry.arguments?.getInt("idPemasok") ?: 0
            DetailPemasokScreen(
                idPemasok = idPemasok,
                onNavigateBack = { navController.popBackStack() },
                onEditClick = {
                    navController.navigate("${DestinasiUpdatePemasok.route}/$idPemasok")
                }
            )
        }

        // Edit Pemasok
        composable(
            route = "${DestinasiUpdatePemasok.route}/{idPemasok}",
            arguments = listOf(navArgument("idPemasok") { type = NavType.IntType })
        ) { backStackEntry ->
            val idPemasok = backStackEntry.arguments?.getInt("idPemasok") ?: 0
            UpdateScreenPemasok(
                idPemasok = idPemasok,
                onNavigateBack = { navController.navigateUp() }
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
                navigateToPemasok = { navController.navigate(DestinasiPemasokHome.route) },

                navigateToKategori = { navController.navigate(DestinasiKategoriHome.route) },
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
            arguments = listOf(navArgument("idMerk") { type = NavType.IntType })
        ) { backStackEntry ->
            val idMerk = backStackEntry.arguments?.getInt("idMerk") ?: 0
            DetailMerkScreen(
                idMerk = idMerk,
                onNavigateBack = { navController.popBackStack() },
                onEditClick = {
                    navController.navigate("${DestinasiUpdateMerk.route}/$idMerk")
                }
            )
        }

        // Edit Merk
        composable(
            route = "${DestinasiUpdateMerk.route}/{idMerk}",
            arguments = listOf(navArgument("idMerk") { type = NavType.IntType })
        ) { backStackEntry ->
            val idMerk = backStackEntry.arguments?.getInt("idMerk") ?: 0
            UpdateScreenMerk(
                idMerk = idMerk,
                onNavigateBack = { navController.navigateUp() }
            )
        }


        composable(DestinasiKategoriHome.route) {
            HomeKategoriScreen(
                navigateToKategoriEntry = {
                    navController.navigate(DestinasiKategoriEntry.route)
                },
                onDetailClick = { idMerk ->
                    navController.navigate("${DestinasiKategoriDetail.route}/$idMerk")
                },
                onBack = {
                    navController.navigate(DestinasiHome.route)
                },
                navigateToPemasok = { navController.navigate(DestinasiPemasokHome.route) },

                navigateToMerk = { navController.navigate(DestinasiMerkHome.route) },

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

        composable(
            route = "${DestinasiKategoriDetail.route}/{idKategori}",
            arguments = listOf(navArgument("idKategori") { type = NavType.StringType })
        ) { backStackEntry ->
            val idKategori = backStackEntry.arguments?.getString("idKategori") ?: ""
            DetailKategoriScreen(
                idKategori = idKategori,
                onNavigateBack = { navController.popBackStack() },
                onEditClick = {navController.navigate("${DestinasiUpdateKategori.route}/$idKategori")
                }
            )
        }

        // Edit Kategori
        composable(
            route = "${DestinasiUpdateKategori.route}/{idKategori}",
            arguments = listOf(navArgument("idKategori") { type = NavType.StringType })
        ) { backStackEntry ->
            val idKategori = backStackEntry.arguments?.getString("idKategori") ?: ""
            UpdateScreenKategori(
                idKategori = idKategori,
                onNavigateBack = { navController.navigateUp() }
            )
        }
    }
}