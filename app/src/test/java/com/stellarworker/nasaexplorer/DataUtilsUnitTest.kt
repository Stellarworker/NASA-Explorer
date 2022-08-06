package com.stellarworker.nasaexplorer

import com.stellarworker.nasaexplorer.utils.dropAfterDot
import com.stellarworker.nasaexplorer.utils.substituteIfEmpty
import junit.framework.Assert.*
import org.junit.Assert.assertNotEquals
import org.junit.Test

private const val EMPTY_STRING = ""
private const val SUBST_STRING = "EMPTY_DATA!"
private const val NOT_EMPTY_STRING = "NOT_EMPTY_STRING"
private const val SPACE_STRING = "          "
private const val PARTS_WITH_DOT = "PART1.PART2"
private const val PART1_WITHOUT_DOT = "PART1"
private const val PART1_WITH_DOT = "PART1."

class DataUtilsUnitTest {

    @Test
    fun dataUtils_EmptyString_ReturnsString() {
        assertSame(NOT_EMPTY_STRING, NOT_EMPTY_STRING.substituteIfEmpty(SUBST_STRING))
    }

    @Test
    fun dataUtils_NotEmptyString_ReturnsString() {
        assertNotSame(NOT_EMPTY_STRING, EMPTY_STRING.substituteIfEmpty(SUBST_STRING))
    }

    @Test
    fun dataUtils_SpaceString_ReturnsFalse() {
        assertFalse(SPACE_STRING == SPACE_STRING.substituteIfEmpty(SUBST_STRING))
    }

    @Test
    fun dataUtils_PartsWithDotString_ReturnsTrue() {
        assertEquals(PART1_WITHOUT_DOT, PARTS_WITH_DOT.dropAfterDot())
    }

    @Test
    fun dataUtils_PartsWithDotString_ReturnsFalse() {
        assertNotEquals(PART1_WITH_DOT, PARTS_WITH_DOT.dropAfterDot())
    }
}