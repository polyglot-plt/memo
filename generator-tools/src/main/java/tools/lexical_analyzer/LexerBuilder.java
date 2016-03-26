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

package tools.lexical_analyzer;

import clojure.java.api.Clojure;
import clojure.lang.*;
import compiler.architecture_base.TokenKindBase;
import compiler.errors.ErrorReporter;
import compiler.errors.LexicalError;
import compiler.lexical_analyzer.LexicalAnalyzer;
import compiler.lexical_analyzer.Token;
import compiler.stream.SourceStream;
import tools.TkPattern;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Analizador léxico creado a partir de las expresiones regulares con que se decoren
 * los valores del enumerativo.
 * <br> <br>
 * A tener en cuenta:
 * <br>
 * 1- Solamente se usarán los valores del enum que se decoren con la anotación {@code @TkPattern}.
 * <br>
 * 2- El generador usa internamente tres valores: eot, space y error; no deben decorarse (usarse)
 * valores que tengan estos nombres.
 * <br>
 * 3- También se usa en la implementación (en expresiones regulares auxiliares)
 * los caracteres \t \r \n y el espacio en blanco:
 * no deben usarse patrones que tengan estos caracteres.
 * <br>
 * 4- El orden en que aparezcan los valores en el enumerativo determinará el orden en que se
 * apliquen los patrones en búsqueda de "matcheos", usándose siempre el primero para el que
 * se encuentre una subcadena, a partir del último token encontrado.
 * <br>
 * 5- Los que deban añadirse a la tabla de símbolos han de decorarse con la anotación {@code @Symbol}.
 *
 * @param <K>
 */
public class LexerBuilder<K extends Enum & TokenKindBase<K>> extends LexicalAnalyzer<K> {

    private final Class<K> tokensType;
    private ISeq seq;

    public LexerBuilder(Class<K> tokensType, SourceStream in, ErrorReporter er) {
        super(in, er);
        this.tokensType = tokensType;
        final Field[] df = tokensType.getFields();

        String params = "'[ " + Arrays.stream(df)
                .filter(f -> Arrays.stream(f.getAnnotations())
                        .anyMatch(g -> g instanceof TkPattern))
                .map(f -> "[" + f.getName() + " \"" + f.getAnnotation(TkPattern.class).value().replace("\\", "\\\\") + "\"]")
                .reduce("", (a, b) -> a + " " + b) + " ]";

        String code = "";
        char ch = in.read();
        while (ch != '\0') {
            code += Character.toString(ch);
            ch = in.read();
        }

        IFn require = Clojure.var("clojure.core", "require");
        IFn load = Clojure.var("clojure.core", "load-string");

        require.invoke(Clojure.read("tools.compiler.lexer"));

        RT.var("tools.compiler.lexer", "setTokens-desc").invoke(
                load.invoke(params)
        );

        seq = ((LazySeq) RT.var("tools.compiler.lexer", "tokens-in-str").invoke(code)).seq();

    }

    @Override
    public Token<K> nextToken() {
        PersistentVector a = (PersistentVector) seq.first();
        seq = seq.next();
        Token<K> result;
        String tokenSymbol = ((clojure.lang.Symbol) a.get(0)).getName();
        Long line = (Long) a.get(2);
        switch (tokenSymbol) {
            case "eot":
                result = new Token<>((tokensType.getEnumConstants()[0]).getEot(), "\0", line.intValue());
                break;
            case "error":
                result = new Token<>((tokensType.getEnumConstants()[0]).getError(), (String) a.get(1), line.intValue());
                er.add(new LexicalError(line.intValue(), "Unexpected character: " + a.get(1)));
                break;
            default:
                K[] enums = tokensType.getEnumConstants();
                K tk = enums[0];
                boolean flag = true;
                for (int i = 0; i < enums.length && flag; i++) {
                    if (enums[i].toString().equals(tokenSymbol)) {
                        tk = enums[i];
                        flag = false;
                    }
                }
                String lexeme = (String) a.get(1);
                result = new Token<>(tk, lexeme, line.intValue());
        }
        return result;
    }
}
