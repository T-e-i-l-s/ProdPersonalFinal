package com.example.prodfinal.domain.model

import java.math.BigDecimal

data class BudgetGoalModel(
    val name: String,
    val goal: BigDecimal,
    val received: BigDecimal
)
