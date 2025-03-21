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

package com.gs.collections.impl.block.predicate.primitive;

import org.junit.Assert;
import org.junit.Test;

public class CharPredicateTest
{
    @Test
    public void isUppercase()
    {
        Assert.assertTrue(CharPredicate.IS_UPPERCASE.accept('A'));
        Assert.assertTrue(CharPredicate.IS_UPPERCASE.accept('Z'));
        Assert.assertFalse(CharPredicate.IS_UPPERCASE.accept('a'));
        Assert.assertFalse(CharPredicate.IS_UPPERCASE.accept('z'));
        Assert.assertFalse(CharPredicate.IS_UPPERCASE.accept('0'));
        Assert.assertFalse(CharPredicate.IS_UPPERCASE.accept(' '));
    }
    
    @Test
    public void isLowercase()
    {
        Assert.assertTrue(CharPredicate.IS_LOWERCASE.accept('a'));
        Assert.assertTrue(CharPredicate.IS_LOWERCASE.accept('z'));
        Assert.assertFalse(CharPredicate.IS_LOWERCASE.accept('A'));
        Assert.assertFalse(CharPredicate.IS_LOWERCASE.accept('Z'));
        Assert.assertFalse(CharPredicate.IS_LOWERCASE.accept('0'));
        Assert.assertFalse(CharPredicate.IS_LOWERCASE.accept(' '));
    }
    
    @Test
    public void isDigit()
    {
        Assert.assertTrue(CharPredicate.IS_DIGIT.accept('0'));
        Assert.assertTrue(CharPredicate.IS_DIGIT.accept('9'));
        Assert.assertFalse(CharPredicate.IS_DIGIT.accept('a'));
        Assert.assertFalse(CharPredicate.IS_DIGIT.accept('Z'));
        Assert.assertFalse(CharPredicate.IS_DIGIT.accept(' '));
        Assert.assertFalse(CharPredicate.IS_DIGIT.accept('-'));
    }
    
    @Test
    public void isDigitOrDot()
    {
        Assert.assertTrue(CharPredicate.IS_DIGIT_OR_DOT.accept('0'));
        Assert.assertTrue(CharPredicate.IS_DIGIT_OR_DOT.accept('9'));
        Assert.assertTrue(CharPredicate.IS_DIGIT_OR_DOT.accept('.'));
        Assert.assertFalse(CharPredicate.IS_DIGIT_OR_DOT.accept('a'));
        Assert.assertFalse(CharPredicate.IS_DIGIT_OR_DOT.accept('Z'));
        Assert.assertFalse(CharPredicate.IS_DIGIT_OR_DOT.accept(' '));
        Assert.assertFalse(CharPredicate.IS_DIGIT_OR_DOT.accept('-'));
    }
    
    @Test
    public void isLetter()
    {
        Assert.assertTrue(CharPredicate.IS_LETTER.accept('a'));
        Assert.assertTrue(CharPredicate.IS_LETTER.accept('Z'));
        Assert.assertFalse(CharPredicate.IS_LETTER.accept('0'));
        Assert.assertFalse(CharPredicate.IS_LETTER.accept(' '));
        Assert.assertFalse(CharPredicate.IS_LETTER.accept('-'));
    }
    
    @Test
    public void isLetterOrDigit()
    {
        Assert.assertTrue(CharPredicate.IS_LETTER_OR_DIGIT.accept('a'));
        Assert.assertTrue(CharPredicate.IS_LETTER_OR_DIGIT.accept('Z'));
        Assert.assertTrue(CharPredicate.IS_LETTER_OR_DIGIT.accept('0'));
        Assert.assertTrue(CharPredicate.IS_LETTER_OR_DIGIT.accept('9'));
        Assert.assertFalse(CharPredicate.IS_LETTER_OR_DIGIT.accept(' '));
        Assert.assertFalse(CharPredicate.IS_LETTER_OR_DIGIT.accept('-'));
    }
    
    @Test
    public void isWhitespace()
    {
        Assert.assertTrue(CharPredicate.IS_WHITESPACE.accept(' '));
        Assert.assertTrue(CharPredicate.IS_WHITESPACE.accept('\t'));
        Assert.assertTrue(CharPredicate.IS_WHITESPACE.accept('\n'));
        Assert.assertFalse(CharPredicate.IS_WHITESPACE.accept('a'));
        Assert.assertFalse(CharPredicate.IS_WHITESPACE.accept('0'));
        Assert.assertFalse(CharPredicate.IS_WHITESPACE.accept('-'));
    }
    
    @Test
    public void isUndefined()
    {
        Assert.assertFalse(CharPredicate.IS_UNDEFINED.accept('a'));
        Assert.assertFalse(CharPredicate.IS_UNDEFINED.accept('0'));
        Assert.assertFalse(CharPredicate.IS_UNDEFINED.accept(' '));
        
        // Test with an undefined character (outside the valid Unicode range)
        // Note: Since char is 16-bit in Java, we can't directly test with values > 0xFFFF
        char undefinedChar = (char) 0xFFFF; // Last possible char value
        Assert.assertFalse(CharPredicate.IS_UNDEFINED.accept(undefinedChar));
        
        // Test with a control character
        Assert.assertFalse(CharPredicate.IS_UNDEFINED.accept('\u0000')); // Null character
    }
    
    @Test
    public void testWithNonAsciiCharacters()
    {
        // Test with non-ASCII characters
        Assert.assertTrue(CharPredicate.IS_LETTER.accept('\u00E9')); // é
        Assert.assertTrue(CharPredicate.IS_LETTER.accept('\u00C4')); // Ä
        Assert.assertTrue(CharPredicate.IS_UPPERCASE.accept('\u00C4')); // Ä
        Assert.assertTrue(CharPredicate.IS_LOWERCASE.accept('\u00E9')); // é
    }
}
