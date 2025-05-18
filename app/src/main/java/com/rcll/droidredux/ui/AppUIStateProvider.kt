package com.rcll.droidredux.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import com.rcll.compose.lateinitMutableState
import com.rcll.core.ui.UIStateProvider
import org.koin.core.component.KoinComponent

class AppUIStateProvider : UIStateProvider<AppUI>, KoinComponent {
    internal val mutableUIState: MutableState<AppUI> = lateinitMutableState()
    override val uiState: State<AppUI> = mutableUIState
}