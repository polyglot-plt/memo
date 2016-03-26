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

package tools.syntax_analyzer;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.common.io.Resources;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Clase encargada de la generación automática de analizadores descendentes
 * recursivos LL1.
 * <p>
 * 1- El generador usa internamente dos valores: "eot" y "e" (cadena vacía); no deben usarse no terminalaes
 * que tengan estos nombres.
 */
public class ParserGenerator {

    private final Class tokensType;
    private final String langName,
            grammarPath;

    /**
     * Constructor, encargado de la inicialización de los atributos.
     * <p>
     * Toma el fichero texto en el que se describe la gramática del lenguaje en cuestión,
     * transformándola y cargándola.
     *
     * @param langName    Nombre del lenguaje al que se le quiere generar el parser, necesario
     *                    para la estructura de paquetes que se usará.
     * @param grammarPath Fichero texto, relativo a la carpeta "resources", que contendrá la
     *                    gramática a usar.
     * @param tokensType  Objeto class de los tokens del lenguaje.
     */
    public ParserGenerator(String langName, String grammarPath, Class tokensType) throws IOException {
        this.tokensType = tokensType;
        this.langName = langName;
        this.grammarPath = grammarPath;
        loadGrammar();
    }

    public boolean isLL1() {
        return (boolean) Clojure.var("tools.compiler.grammar", "LL1?").invoke();
    }

    /**
     * Método ejecutor de la generación.  Usando la gramática, y los nombres
     * de los tipos de tokens genera una clase Parser en el paquete
     * {{langName}}.compiler.syntax_analyzer
     * <p>
     */
    public void generate() throws IOException {

        if (isLL1()) {
            final String msg = (String) Clojure.var("tools.compiler.grammar", "get-LL1-parser-java-memo").invoke();

            final String outPath = "./src/main/java/" + langName + "/compiler/syntax_analyzer/";

            File outFile = new File(outPath);
            if (!outFile.exists())
                outFile.mkdirs();

            Files.write(msg, new File(outFile, "Parser.java"), Charsets.UTF_8);
        } else
            System.out.println("The grammar is not LL1.");
    }

    private void loadGrammar() throws IOException {

        String grammar = Resources.toString(Resources.getResource(grammarPath), Charsets.UTF_8);

        grammar = grammar.replaceAll("[\n\t\r]+", "");

        final String S = Arrays.stream(grammar.split(";"))
                .map(e -> e.split("->")[0]).findFirst().get();

        String res = "'{ \n P {" + Arrays.stream(grammar.split(";"))
                .filter(e -> !e.trim().equals(""))
                .map(e -> {
                    String[] prod = e.split("->");
                    String[] pder = prod[1].split("\\|");
                    return prod[0].trim() + " [" +
                            Arrays.stream(pder)
                                    .map(l -> "[" + l.trim() + "]")
                                    .reduce("", (a, b) -> a + " " + b) + "]";
                }).reduce("", (a, b) -> a + "\n" + b) + " \n }";

        res += "\n sigma #{" + Arrays.stream(tokensType.getFields())
                .map(e -> e.getName())
                .reduce("", (a, b) -> a + " " + b) + " }";

        res += "\n S " + S + "\n" +
                " config { \n" +
                "   :tokensTypeName \"" + tokensType.getCanonicalName() + "\"" +
                "   :langName \"" + langName + "\"" +
                " } \n " +
                "}";


        IFn require = Clojure.var("clojure.core", "require");
        IFn load = Clojure.var("clojure.core", "load-string");

        require.invoke(Clojure.read("tools.compiler.grammar"));

        Clojure.var("tools.compiler.grammar", "setGrammar").invoke(
                load.invoke(res)
        );
    }
}
