package com.example.prodfinal

import com.example.prodfinal.data.mapper.ValuteMapper
import com.example.prodfinal.domain.model.ValuteExchangeModel
import com.example.prodfinal.domain.state.ValuteRate
import org.junit.Test

import org.junit.Assert.*
import java.math.BigDecimal
import java.math.RoundingMode

// Тест маппера валюты
class ValuteUnitTest {
    // Круглое значение валюты + рост валюты
    @Test
    fun roundValuteRiseMapperTest() {
        val source = ValuteExchangeModel(
            BigDecimal(100.00),
            BigDecimal(90.00),
            ValuteRate.FALL,
            BigDecimal(110.00),
            BigDecimal(100.00),
            ValuteRate.FALL,
        )
        val expected = ValuteExchangeModel(
            BigDecimal(100).setScale(2, RoundingMode.HALF_EVEN),
            BigDecimal(90).setScale(2, RoundingMode.HALF_EVEN),
            ValuteRate.RISE,
            BigDecimal(110).setScale(2, RoundingMode.HALF_EVEN),
            BigDecimal(100).setScale(2, RoundingMode.HALF_EVEN),
            ValuteRate.RISE,
        )
        val result = ValuteMapper().invoke(source)
        assertEquals(expected, result)
    }

    // Дробное значение валюты + рост валюты
    @Test
    fun fractionalValuteRiseMapperTest() {
        val source = ValuteExchangeModel(
            BigDecimal(100.5434),
            BigDecimal(90.1375),
            ValuteRate.FALL,
            BigDecimal(110.1781),
            BigDecimal(100.8994),
            ValuteRate.FALL,
        )
        val expected = ValuteExchangeModel(
            BigDecimal(100.5434).setScale(2, RoundingMode.HALF_EVEN),
            BigDecimal(90.1375).setScale(2, RoundingMode.HALF_EVEN),
            ValuteRate.RISE,
            BigDecimal(110.1781).setScale(2, RoundingMode.HALF_EVEN),
            BigDecimal(100.8994).setScale(2, RoundingMode.HALF_EVEN),
            ValuteRate.RISE,
        )
        val result = ValuteMapper().invoke(source)
        assertEquals(expected, result)
    }

    // Круглое значение валюты + падение валюты
    @Test
    fun roundValuteFallMapperTest() {
        val source = ValuteExchangeModel(
            BigDecimal(90.00),
            BigDecimal(100.00),
            ValuteRate.FALL,
            BigDecimal(100.00),
            BigDecimal(110.00),
            ValuteRate.FALL,
        )
        val expected = ValuteExchangeModel(
            BigDecimal(90).setScale(2, RoundingMode.HALF_EVEN),
                BigDecimal(100).setScale(2, RoundingMode.HALF_EVEN),
            ValuteRate.FALL,
            BigDecimal(100).setScale(2, RoundingMode.HALF_EVEN),
            BigDecimal(110).setScale(2, RoundingMode.HALF_EVEN),
            ValuteRate.FALL,
        )
        val result = ValuteMapper().invoke(source)
        assertEquals(expected, result)
    }

    // Дробное значение валюты + падение валюты
    @Test
    fun fractionalValuteFallMapperTest() {
        val source = ValuteExchangeModel(
            BigDecimal(90.1375),
            BigDecimal(100.5434),
            ValuteRate.FALL,
            BigDecimal(100.8994),
            BigDecimal(110.1781),
            ValuteRate.FALL,
        )
        val expected = ValuteExchangeModel(
            BigDecimal(90.1375).setScale(2, RoundingMode.HALF_EVEN),
            BigDecimal(100.5434).setScale(2, RoundingMode.HALF_EVEN),
            ValuteRate.FALL,
            BigDecimal(100.8994).setScale(2, RoundingMode.HALF_EVEN),
            BigDecimal(110.1781).setScale(2, RoundingMode.HALF_EVEN),
            ValuteRate.FALL,
        )
        val result = ValuteMapper().invoke(source)
        assertEquals(expected, result)
    }

    // Круглое значение валюты + отсутствие изменений в цене валюты
    @Test
    fun roundValuteStableMapperTest() {
        val source = ValuteExchangeModel(
            BigDecimal(100.00),
            BigDecimal(100.00),
            ValuteRate.FALL,
            BigDecimal(100.00),
            BigDecimal(100.00),
            ValuteRate.FALL,
        )
        val expected = ValuteExchangeModel(
            BigDecimal(100).setScale(2, RoundingMode.HALF_EVEN),
            BigDecimal(100).setScale(2, RoundingMode.HALF_EVEN),
            ValuteRate.FALL,
            BigDecimal(100).setScale(2, RoundingMode.HALF_EVEN),
            BigDecimal(100).setScale(2, RoundingMode.HALF_EVEN),
            ValuteRate.FALL,
        )
        val result = ValuteMapper().invoke(source)
        assertEquals(expected, result)
    }

    // Дробное значение валюты + отсутствие изменений в цене валюты
    @Test
    fun fractionalValuteStableMapperTest() {
        val source = ValuteExchangeModel(
            BigDecimal(100.5434),
            BigDecimal(100.5434),
            ValuteRate.FALL,
            BigDecimal(110.1781),
            BigDecimal(110.1781),
            ValuteRate.FALL,
        )
        val expected = ValuteExchangeModel(
            BigDecimal(100.5434).setScale(2, RoundingMode.HALF_EVEN),
            BigDecimal(100.5434).setScale(2, RoundingMode.HALF_EVEN),
            ValuteRate.FALL,
            BigDecimal(110.1781).setScale(2, RoundingMode.HALF_EVEN),
            BigDecimal(110.1781).setScale(2, RoundingMode.HALF_EVEN),
            ValuteRate.FALL,
        )
        val result = ValuteMapper().invoke(source)
        assertEquals(expected, result)
    }
}