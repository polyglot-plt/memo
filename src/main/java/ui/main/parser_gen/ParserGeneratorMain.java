/**
 * Author José Albert Cruz Almaguer <jalbertcruz@gmail.com>
 * Copyright 2015 by José Albert Cruz Almaguer.
 * <p>
 * This program is licensed to you under the terms of version 3 of the
 * GNU Affero General Public License. This program is distributed WITHOUT
 * ANY EXPRESS OR IMPLIED WARRANTY, INCLUDING THOSE OF NON-INFRINGEMENT,
 * MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE. Please refer to the
 * AGPL (http:www.gnu.org/licenses/agpl-3.0.txt) for more details.
 */

package ui.main.parser_gen;

import memo_lang.compiler.TokenKind;
import tools.syntax_analyzer.ParserGenerator;

import java.io.IOException;

public class ParserGeneratorMain {


    public static void main(String[] args) throws IOException {

        ParserGenerator p = new ParserGenerator(
                "expressions_lang",
                "grammars/exp_lang.txt",
                TokenKind.class);

        p.generate();

    }

}
