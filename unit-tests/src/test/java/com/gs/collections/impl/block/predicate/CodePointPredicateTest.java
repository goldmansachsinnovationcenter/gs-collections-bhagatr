/*
 * Copyright 2015 Goldman Sachs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gs.collections.impl.block.predicate;

import org.junit.Assert;
import org.junit.Test;

public class CodePointPredicateTest
{
    @Test
    public void isUppercase()
    {
        Assert.assertTrue(CodePointPredicate.IS_UPPERCASE.accept('A'));
        Assert.assertTrue(CodePointPredicate.IS_UPPERCASE.accept('Z'));
        Assert.assertFalse(CodePointPredicate.IS_UPPERCASE.accept('a'));
        Assert.assertFalse(CodePointPredicate.IS_UPPERCASE.accept('z'));
        Assert.assertFalse(CodePointPredicate.IS_UPPERCASE.accept('0'));
        Assert.assertFalse(CodePointPredicate.IS_UPPERCASE.accept(' '));
    }
    
    @Test
    public void isLowercase()
    {
        Assert.assertTrue(CodePointPredicate.IS_LOWERCASE.accept('a'));
        Assert.assertTrue(CodePointPredicate.IS_LOWERCASE.accept('z'));
        Assert.assertFalse(CodePointPredicate.IS_LOWERCASE.accept('A'));
        Assert.assertFalse(CodePointPredicate.IS_LOWERCASE.accept('Z'));
        Assert.assertFalse(CodePointPredicate.IS_LOWERCASE.accept('0'));
        Assert.assertFalse(CodePointPredicate.IS_LOWERCASE.accept(' '));
    }
    
    @Test
    public void isDigit()
    {
        Assert.assertTrue(CodePointPredicate.IS_DIGIT.accept('0'));
        Assert.assertTrue(CodePointPredicate.IS_DIGIT.accept('9'));
        Assert.assertFalse(CodePointPredicate.IS_DIGIT.accept('a'));
        Assert.assertFalse(CodePointPredicate.IS_DIGIT.accept('Z'));
        Assert.assertFalse(CodePointPredicate.IS_DIGIT.accept(' '));
        Assert.assertFalse(CodePointPredicate.IS_DIGIT.accept('-'));
    }
    
    @Test
    public void isLetter()
    {
        Assert.assertTrue(CodePointPredicate.IS_LETTER.accept('a'));
        Assert.assertTrue(CodePointPredicate.IS_LETTER.accept('Z'));
        Assert.assertFalse(CodePointPredicate.IS_LETTER.accept('0'));
        Assert.assertFalse(CodePointPredicate.IS_LETTER.accept(' '));
        Assert.assertFalse(CodePointPredicate.IS_LETTER.accept('-'));
    }
    
    @Test
    public void isLetterOrDigit()
    {
        Assert.assertTrue(CodePointPredicate.IS_LETTER_OR_DIGIT.accept('a'));
        Assert.assertTrue(CodePointPredicate.IS_LETTER_OR_DIGIT.accept('Z'));
        Assert.assertTrue(CodePointPredicate.IS_LETTER_OR_DIGIT.accept('0'));
        Assert.assertTrue(CodePointPredicate.IS_LETTER_OR_DIGIT.accept('9'));
        Assert.assertFalse(CodePointPredicate.IS_LETTER_OR_DIGIT.accept(' '));
        Assert.assertFalse(CodePointPredicate.IS_LETTER_OR_DIGIT.accept('-'));
    }
    
    @Test
    public void isWhitespace()
    {
        Assert.assertTrue(CodePointPredicate.IS_WHITESPACE.accept(' '));
        Assert.assertTrue(CodePointPredicate.IS_WHITESPACE.accept('\t'));
        Assert.assertTrue(CodePointPredicate.IS_WHITESPACE.accept('\n'));
        Assert.assertFalse(CodePointPredicate.IS_WHITESPACE.accept('a'));
        Assert.assertFalse(CodePointPredicate.IS_WHITESPACE.accept('0'));
        Assert.assertFalse(CodePointPredicate.IS_WHITESPACE.accept('-'));
    }
    
    @Test
    public void isUndefined()
    {
        Assert.assertFalse(CodePointPredicate.IS_UNDEFINED.accept('a'));
        Assert.assertFalse(CodePointPredicate.IS_UNDEFINED.accept('0'));
        Assert.assertFalse(CodePointPredicate.IS_UNDEFINED.accept(' '));
        
        // Test with an undefined code point (outside the valid Unicode range)
        int undefinedCodePoint = 0x110000; // First code point outside valid Unicode range
        Assert.assertTrue(CodePointPredicate.IS_UNDEFINED.accept(undefinedCodePoint));
    }
    
    @Test
    public void isBmp()
    {
        Assert.assertTrue(CodePointPredicate.IS_BMP.accept('a'));
        Assert.assertTrue(CodePointPredicate.IS_BMP.accept('0'));
        Assert.assertTrue(CodePointPredicate.IS_BMP.accept(' '));
        Assert.assertTrue(CodePointPredicate.IS_BMP.accept('\u0000')); // Null character
        Assert.assertTrue(CodePointPredicate.IS_BMP.accept('\uFFFF')); // Last BMP character
        
        // Test with a supplementary code point (outside BMP)
        int supplementaryCodePoint = 0x10000; // First supplementary code point
        Assert.assertFalse(CodePointPredicate.IS_BMP.accept(supplementaryCodePoint));
    }
    
    @Test
    public void testWithNonAsciiCharacters()
    {
        // Test with non-ASCII characters
        Assert.assertTrue(CodePointPredicate.IS_LETTER.accept('\u00E9')); // é
        Assert.assertTrue(CodePointPredicate.IS_LETTER.accept('\u00C4')); // Ä
        Assert.assertTrue(CodePointPredicate.IS_UPPERCASE.accept('\u00C4')); // Ä
        Assert.assertTrue(CodePointPredicate.IS_LOWERCASE.accept('\u00E9')); // é
        
        // Test with emoji (outside BMP)
        int emojiCodePoint = 0x1F600; // 😀
        Assert.assertFalse(CodePointPredicate.IS_BMP.accept(emojiCodePoint));
        Assert.assertFalse(CodePointPredicate.IS_DIGIT.accept(emojiCodePoint));
        Assert.assertFalse(CodePointPredicate.IS_LETTER.accept(emojiCodePoint));
    }
    
    @Test
    public void testWithSupplementaryCodePoints()
    {
        // Test with supplementary code points
        int mathematicalAlphaCodePoint = 0x1D400; // 𝐀 (Mathematical Bold Capital A)
        Assert.assertFalse(CodePointPredicate.IS_BMP.accept(mathematicalAlphaCodePoint));
        Assert.assertTrue(CodePointPredicate.IS_LETTER.accept(mathematicalAlphaCodePoint));
        Assert.assertTrue(CodePointPredicate.IS_LETTER_OR_DIGIT.accept(mathematicalAlphaCodePoint));
    }
}
