package com.rcll.core.middlewares.parallel

import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.snapshots.Snapshot
import androidx.compose.runtime.snapshots.SnapshotApplyConflictException
import androidx.compose.runtime.snapshots.SnapshotApplyResult
import kotlinx.coroutines.ThreadContextElement
import kotlin.coroutines.CoroutineContext

class ConflictAwareSnapshot {
    private val readStates = mutableSetOf<Any>()
    private val parentModified = mutableSetOf<Any>()
    private var childSnapshot = Snapshot.takeMutableSnapshot(readObserver = ::onReadState)
    private val observerHandler = Snapshot.registerApplyObserver(::onAppliedChanges)

    private fun onReadState(state: Any) {
        readStates.add(state)
    }

    private fun onAppliedChanges(changedStates: Set<Any>, snapshot: Snapshot) {
        parentModified.addAll(changedStates)
    }

    fun <T> enter(block: () -> T): T {
        return childSnapshot.enter(block)
    }

    @ExperimentalComposeApi
    fun unsafeEnter(): Snapshot? {
        return childSnapshot.unsafeEnter()
    }

    @ExperimentalComposeApi
    fun unsafeLeave(oldState: Snapshot?) {
        childSnapshot.unsafeLeave(oldState)
    }

    fun apply(): SnapshotApplyResult {
        checkForConflicts() // Проверка ДО применения

        return childSnapshot.apply().also { result ->
            if (result is SnapshotApplyResult.Failure) {
                dispose()
            }
        }
    }

    private fun checkForConflicts() {
        val conflicts = readStates.intersect(parentModified)
        if (conflicts.isNotEmpty()) {
            dispose()
            throw SnapshotApplyConflictException(childSnapshot)
        }
    }

    fun dispose() {
        observerHandler.dispose()
        childSnapshot.dispose()
    }
}

@ExperimentalComposeApi
class ConflictAwareSnapshotContextElement(
    private val snapshot: ConflictAwareSnapshot
) : ThreadContextElement<Snapshot?>, CoroutineContext.Element {

    companion object Key : CoroutineContext.Key<ConflictAwareSnapshotContextElement>

    override val key: CoroutineContext.Key<*> get() = Key

    override fun updateThreadContext(context: CoroutineContext): Snapshot? {
        return snapshot.unsafeEnter()
    }

    override fun restoreThreadContext(context: CoroutineContext, oldState: Snapshot?) {
        snapshot.unsafeLeave(oldState)
    }
}

@ExperimentalComposeApi
fun ConflictAwareSnapshot.asContextElement() = ConflictAwareSnapshotContextElement(this)