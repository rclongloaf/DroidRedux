package com.rcll.core.composition

import androidx.compose.runtime.AbstractApplier

class BasicApplier() : AbstractApplier<Any>(Unit) {
    override fun insertBottomUp(index: Int, instance: Any) {
        // noting
    }

    override fun insertTopDown(index: Int, instance: Any) {
        // noting
    }

    override fun move(from: Int, to: Int, count: Int) {
        // noting
    }

    override fun remove(index: Int, count: Int) {
        // noting
    }

    override fun onClear() {
        // noting
    }
}
