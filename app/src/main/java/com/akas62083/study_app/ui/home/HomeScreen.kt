package com.akas62083.study_app.ui.home

import android.R.attr.onClick
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.akas62083.study_app.Route
import com.akas62083.study_app.ui.detail.DetailViewModel
import com.akas62083.study_app.ui.home.component.AddFolderDialog
import com.akas62083.study_app.ui.home.component.AddTextDialog
import com.akas62083.study_app.ui.home.component.ContentList
import com.akas62083.study_app.ui.home.component.DeleteFolderDialog
import com.akas62083.study_app.ui.home.component.DeleteTextDialog
import com.akas62083.study_app.ui.home.component.MiddleButtons
import com.akas62083.study_app.ui.home.component.RenameFolderDialog
import com.akas62083.study_app.ui.home.component.TopCard

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(

    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
                .fillMaxSize()
        ) {
            TopCard(
                modifier = Modifier.fillMaxWidth()
                    .height(100.dp)
            )
            MiddleButtons(
                modifier = Modifier.fillMaxWidth(),
                uiState = uiState,
                closeAllButtonClicked = { viewModel.onEvent(HomeEvent.CloseAllButtonClicked) },
                addFolderButtonClicked = { viewModel.onEvent(HomeEvent.AddFolderButtonClicked) }
            )
            ContentList(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp),
                uiState = uiState,
                addTextButtonClicked = { viewModel.onEvent(HomeEvent.AddTextButtonClicked(it)) },
                deleteFolderButtonClicked = { viewModel.onEvent(HomeEvent.DeleteFolderButtonClicked(it)) },
                deleteTextButtonClicked = { viewModel.onEvent(HomeEvent.DeleteTextButtonClicked(it)) },
                renameFolderButtonClicked = { viewModel.onEvent(HomeEvent.RenameFolderName(it)) },
                onFolderClicked = { viewModel.onEvent(HomeEvent.OnFolderClicked(it)) },
                textClicked = { navController.navigate(Route.DETAIL(it)) }
            )
        }
    }

    when(uiState.dialogState) {
        is HomeDialogState.None -> {}
        is HomeDialogState.AddFolder -> {
            AddFolderDialog(
                uiState = uiState,
                cancelButtonClicked = { viewModel.onEvent(HomeEvent.AddFolderDialog.DismissClicked) },
                confirmButtonClicked = { viewModel.onEvent(HomeEvent.AddFolderDialog.ConfirmClicked) },
                onValueChange = { viewModel.onEvent(HomeEvent.AddFolderDialog.NameChanged(it)) },
                iconClicked = { viewModel.onEvent(HomeEvent.AddFolderDialog.IconClicked) }
            )
        }
        is HomeDialogState.AddText -> {
            AddTextDialog(
                uiState = uiState,
                modifier = Modifier,
                cancelButtonClicked = { viewModel.onEvent(HomeEvent.AddTextDialog.DismissClicked) },
                confirmButtonClicked = { viewModel.onEvent(HomeEvent.AddTextDialog.ConfirmClicked) },
                onValueChange = { viewModel.onEvent(HomeEvent.AddTextDialog.NameChanged(it)) },
                changeTextType = { viewModel.onEvent(HomeEvent.AddTextDialog.ChangeTextType(it)) }
            )
        }
        is HomeDialogState.DeleteFolder -> {
            DeleteFolderDialog(
                uiState = uiState,
                cancel = { viewModel.onEvent(HomeEvent.DeleteFolderDialog.DismissClicked) },
                confirmButtonClicked = { viewModel.onEvent(HomeEvent.DeleteFolderDialog.ConfirmClicked) }
            )
        }
        is HomeDialogState.DeleteText -> {
            DeleteTextDialog(
                uiState = uiState,
                cancel = { viewModel.onEvent(HomeEvent.DeleteTextDialog.DismissClicked) },
                confirmButtonClicked = { viewModel.onEvent(HomeEvent.DeleteTextDialog.ConfirmClicked) }
            )
        }
        is HomeDialogState.RenameFolder -> {
            RenameFolderDialog(
                uiState = uiState,
                cancelButtonClicked = { viewModel.onEvent(HomeEvent.RenameFolderDialog.DismissClicked) },
                confirmButtonClicked = { viewModel.onEvent(HomeEvent.RenameFolderDialog.ConfirmClicked) },
                onValueChange = { viewModel.onEvent(HomeEvent.RenameFolderDialog.NameChanged(it)) },
                iconClicked = { viewModel.onEvent(HomeEvent.RenameFolderDialog.IconClicked) }
            )
        }
    }
}