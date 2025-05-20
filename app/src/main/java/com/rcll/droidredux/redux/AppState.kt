package com.rcll.droidredux.redux

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.rcll.droidredux.redux.domain.DomainData
import com.rcll.droidredux.redux.domain.MutableDomainData
import com.rcll.droidredux.redux.entities.Entities
import com.rcll.droidredux.redux.entities.MutableEntities
import com.rcll.mainscreen.MainScreen
import com.rcll.mainscreen.MutableMainScreen

@Stable
interface AppState {
    val domain: DomainData
    val entities: Entities
    val ui: State<MainScreen>
}

data class MutableAppState(
    override val domain: MutableDomainData = MutableDomainData(),
    override val entities: MutableEntities = MutableEntities(),
    override val ui: MutableState<MutableMainScreen> = mutableStateOf(MutableMainScreen.Init)
) : AppState {

    fun smartCopy(
        domain: MutableDomainData,
        entities: MutableEntities,
    ): MutableAppState {
        if (domain !== this.domain ||
            entities !== this.entities
        ) {
            return MutableAppState(
                domain = domain,
                entities = entities,
            )
        }
        return this
    }
}