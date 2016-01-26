/**
 * Author José Albert Cruz Almaguer <jalbertcruz@gmail.com>
 * Copyright 2015 by José Albert Cruz Almaguer.
 * <p/>
 * This program is licensed to you under the terms of version 3 of the
 * GNU Affero General Public License. This program is distributed WITHOUT
 * ANY EXPRESS OR IMPLIED WARRANTY, INCLUDING THOSE OF NON-INFRINGEMENT,
 * MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE. Please refer to the
 * AGPL (http:www.gnu.org/licenses/agpl-3.0.txt) for more details.
 */

package memo_lang.compiler.syntax_analyzer;

import compiler.errors.ErrorReporter;
import compiler.errors.SyntacticError;
import compiler.lexical_analyzer.LexicalAnalyzer;
import compiler.syntax_analyzer.SyntaxAnalyzer;
import memo_lang.compiler.TokenKind;

import java.util.Set;

import static memo_lang.compiler.TokenKind.*;
import static memo_lang.compiler.TokenKind.Float;

public class Parser extends SyntaxAnalyzer<TokenKind> {

    private Set<TokenKind>
            firstIs1, firstI1,
            firstI2, firstI3,
            firstAI1, firstDI1,
            firstType1, firstType2,
            firstWI1, firstE1,
            firstMt1, firstT1,
            firstMf1, firstF1,
            firstF2, firstF3,
            firstF4;

    public Parser(LexicalAnalyzer<TokenKind> in, ErrorReporter er) {
        super(in, er);

        firstIs1 = mkFirsts(
                Write, Int,
                Float, Id
        );
        firstI1 = mkFirsts(Write);
        firstI2 = mkFirsts(
                Int, Float
        );
        firstI3 = mkFirsts(Id);
        firstAI1 = mkFirsts(Id);
        firstDI1 = mkFirsts(
                Int, Float
        );
        firstType1 = mkFirsts(Int);
        firstType2 = mkFirsts(Float);
        firstWI1 = mkFirsts(Write);
        firstE1 = mkFirsts(
                LeftParen, IntLiteral,
                FloatLiteral, Id
        );
        firstMt1 = mkFirsts(Sum);
        firstT1 = mkFirsts(
                LeftParen, IntLiteral,
                FloatLiteral, Id
        );
        firstMf1 = mkFirsts(Multiplication);
        firstF1 = mkFirsts(LeftParen);
        firstF2 = mkFirsts(Id);
        firstF3 = mkFirsts(IntLiteral);
        firstF4 = mkFirsts(FloatLiteral);

    }

    @Override
    public boolean parse() {
        consume();
        Is();
        match(EOT);
        return er.size() == 0;
    }

    // I -> WI | DI | AI ;
    public void I() {
        int productionSelected = selectProduction(firstI1, firstI2, firstI3);
        switch (productionSelected) {
            case 1:
                WI();
                break;
            case 2:
                DI();
                break;
            case 3:
                AI();
                break;
            default:
                er.add(new SyntacticError(ct.line));
                recuperateUntil(SemiColon);
        }
    }

    // Is -> I Is | ;
    public void Is() {
        int productionSelected = selectProduction(firstIs1);
        switch (productionSelected) {
            case 1:
                I();
                Is();
                break;
            default:
        }
    }

    // AI -> Id Assignment E SemiColon ;
    public void AI() {
        int productionSelected = selectProduction(firstAI1);
        switch (productionSelected) {
            case 1:
                match(Id);
                match(Assignment);
                E();
                match(SemiColon);
                break;
            default:
                er.add(new SyntacticError(ct.line));
        }
    }

    //    DI -> Type Id SemiColon ;
    public void DI() {
        int productionSelected = selectProduction(firstDI1);
        switch (productionSelected) {
            case 1:
                Type();
                match(Id);
                match(SemiColon);
                break;
            default:
                er.add(new SyntacticError(ct.line));
        }
    }

    //    Type -> Int | Float ;
    public void Type() {
        int productionSelected = selectProduction(firstType1, firstType2);
        switch (productionSelected) {
            case 1:
                match(Int);
                break;
            case 2:
                match(Float);
                break;
            default:
                er.add(new SyntacticError(ct.line));
        }
    }

    //    WI -> Write E SemiColon ;
    public void WI() {
        int productionSelected = selectProduction(firstWI1);
        switch (productionSelected) {
            case 1:
                match(Write);
                E();
                match(SemiColon);
                break;
            default:
                er.add(new SyntacticError(ct.line));
        }
    }

    //    E -> T Mt ;
    public void E() {
        int productionSelected = selectProduction(firstE1);
        switch (productionSelected) {
            case 1:
                T();
                Mt();
                break;
            default:
                er.add(new SyntacticError(ct.line));
        }
    }

    //    Mt -> Sum T Mt | ;
    public void Mt() {
        int productionSelected = selectProduction(firstMt1);
        switch (productionSelected) {
            case 1:
                match(Sum);
                T();
                Mt();
                break;
            default:
        }
    }

    //    T -> F Mf ;
    public void T() {
        int productionSelected = selectProduction(firstT1);
        switch (productionSelected) {
            case 1:
                F();
                Mf();
                break;
            default:
                er.add(new SyntacticError(ct.line));
        }
    }

    //    Mf -> Multiplication F Mf | ;
    public void Mf() {
        int productionSelected = selectProduction(firstMf1);
        switch (productionSelected) {
            case 1:
                match(Multiplication);
                F();
                Mf();
                break;
            default:
        }
    }

    //    F -> LeftParen E RightParen | Id | IntLiteral | FloatLiteral ;
    public void F() {
        int productionSelected = selectProduction(firstF1, firstF2, firstF3, firstF4);
        switch (productionSelected) {
            case 1:
                match(LeftParen);
                E();
                match(RightParen);
                break;
            case 2:
                match(Id);
                break;
            case 3:
                match(IntLiteral);
                break;
            case 4:
                match(FloatLiteral);
                break;
            default:
                er.add(new SyntacticError(ct.line, "Incorrect expression"));
                recuperateUntil(SemiColon);
        }
    }
}
