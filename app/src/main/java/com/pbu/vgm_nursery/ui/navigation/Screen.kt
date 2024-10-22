package com.pbu.vgm_nursery.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object RecordAdd : Screen("record_add/{operator_name}/{selected_tab_index}/{get_csv_file_immediately}") {
        fun createRoute(
            operatorName: String,
            selectedTabIndex: Int = 0,
            getCsvFileImmediately: Boolean = false,
        ) = "record_add/$operatorName/$selectedTabIndex/$getCsvFileImmediately"
    }
    object RecordList : Screen("record_list")
    object RecordDetail : Screen("record_detail")
    object Scan : Screen("scan")
}