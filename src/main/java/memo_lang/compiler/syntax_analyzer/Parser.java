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

package memo_lang.compiler.syntax_analyzer;

import compiler.abstract_syntax_tree.AST;
import compiler.errors.ErrorReporter;
import compiler.errors.SyntacticError;
import compiler.lexical_analyzer.LexicalAnalyzer;
import compiler.symbols_table.*;
import compiler.syntax_analyzer.SyntaxAnalyzer;
import memo_lang.compiler.MemoTypes;
import memo_lang.compiler.TokenKind;
import memo_lang.compiler.abstract_syntax_tree.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static memo_lang.compiler.TokenKind.*;
import static memo_lang.compiler.TokenKind.Float;

public class Parser extends SyntaxAnalyzer<TokenKind, AST> {

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

    public Parser(LexicalAnalyzer<TokenKind> in, SymbolsTable<TokenKind, SymbolInfo<TokenKind>> st, ErrorReporter er) {
        super(in, st, er);

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
    public ASTProgram parse() {
        consume();
        ASTProgram program = new ASTProgram(Is());
        match(EOT);
        return program;
    }

    // I -> WI | DI | AI ;
    public ASTInstruction I() {
        ASTInstruction result = null;
        int productionSelected = selectProduction(firstI1, firstI2, firstI3);
        switch (productionSelected) {
            case 1:
                result = WI();
                break;
            case 2:
                result = DI();
                break;
            case 3:
                result = AI();
                break;
            default:
                er.add(new SyntacticError(ct.line));
                recuperateUntil(SemiColon);
        }
        return result;
    }

    // Is -> I Is | ;
    public List<ASTInstruction> Is() {
        List<ASTInstruction> result = new LinkedList<ASTInstruction>();
        int productionSelected = selectProduction(firstIs1);
        switch (productionSelected) {
            case 1:
                result.add(I());
                result.addAll(Is());
                break;
            default:
        }
        return result;
    }

    // AI -> Id Assignment E SemiColon ;
    public ASTInstructionAssig AI() {
        ASTInstructionAssig result = null;
        int productionSelected = selectProduction(firstAI1);
        switch (productionSelected) {
            case 1:
                st.define(new VariableSymbol<TokenKind>(ct.lexeme, (TokenKind) ct.kind));
                ct.entry = st.resolve(ct.lexeme);
                ASTIdentifierReference idref = new ASTIdentifierReference(
                        ct.entry, ct.line
                );
                match(Id);
                match(Assignment);
                result = new ASTInstructionAssig(
                        idref, E(), ct.line
                );
                match(SemiColon);
                break;
            default:
                er.add(new SyntacticError(ct.line));
        }
        return result;
    }

    //    DI -> Type Id SemiColon ;
    public ASTInstructionDeclaration DI() {
        ASTInstructionDeclaration result = null;
        int productionSelected = selectProduction(firstDI1);
        switch (productionSelected) {
            case 1:
                MemoTypes type = Type() == Int ? MemoTypes.Int : MemoTypes.Float;
                st.define(new BuiltInTypeSymbol<TokenKind>(ct.lexeme, (TokenKind) ct.kind));
                ct.entry = st.resolve(ct.lexeme);
                result = new ASTInstructionDeclaration(
                        type,
                        new ASTIdentifierDeclaration(
                                ct.entry, ct.line
                        ),
                        ct.line
                );
                match(Id);
                match(SemiColon);
                break;
            default:
                er.add(new SyntacticError(ct.line));
        }
        return result;
    }

    //    Type -> Int | Float ;
    public TokenKind Type() {
        TokenKind result = null;
        int productionSelected = selectProduction(firstType1, firstType2);
        switch (productionSelected) {
            case 1:
                result = Int;
                match(Int);
                break;
            case 2:
                result = Float;
                match(Float);
                break;
            default:
                er.add(new SyntacticError(ct.line));
        }
        return result;
    }

    //    WI -> Write E SemiColon ;
    public ASTInstructionWrite WI() {
        ASTInstructionWrite result = null;
        int productionSelected = selectProduction(firstWI1);
        switch (productionSelected) {
            case 1:
                match(Write);
                result = new ASTInstructionWrite(E(), ct.line);
                match(SemiColon);
                break;
            default:
                er.add(new SyntacticError(ct.line));
        }
        return result;
    }

    //    E -> T Mt ;
    public ASTExpression E() {
        ASTExpression result = null;
        int productionSelected = selectProduction(firstE1);
        switch (productionSelected) {
            case 1:
                result = Mt(T());
                break;
            default:
                er.add(new SyntacticError(ct.line));
        }
        return result;
    }

    //    Mt -> Sum T Mt | ;
    public ASTExpression Mt(ASTExpression term) {
        ASTExpression result;
        int productionSelected = selectProduction(firstMt1);
        switch (productionSelected) {
            case 1:
                match(Sum);
                ASTExpression izq = term;
                ASTExpression der = T();
                result = Mt(new ASTExpressionSuma(
                        izq, der, ct.line
                ));
                break;
            default:
                result = term;
        }
        return result;
    }

    //    T -> F Mf ;
    public ASTExpression T() {
        ASTExpression result = null;
        int productionSelected = selectProduction(firstT1);
        switch (productionSelected) {
            case 1:
                ASTExpression factor = F();
                result = Mf(factor);
                break;
            default:
                er.add(new SyntacticError(ct.line));
        }
        return result;
    }

    //    Mf -> Multiplication F Mf | ;
    public ASTExpression Mf(ASTExpression factor) {
        ASTExpression result;
        int productionSelected = selectProduction(firstMf1);
        switch (productionSelected) {
            case 1:
                match(Multiplication);
                ASTExpression izq = factor;
                ASTExpression der = F();
                result = Mf(new ASTExpressionMult(izq, der, ct.line));
                break;
            default:
                result = factor;
        }
        return result;
    }

    //    F -> LeftParen E RightParen | Id | IntLiteral | FloatLiteral ;
    public ASTExpression F() {
        ASTExpression result = null;
        int productionSelected = selectProduction(firstF1, firstF2, firstF3, firstF4);
        switch (productionSelected) {
            case 1:
                match(LeftParen);
                result = E();
                match(RightParen);
                break;
            case 2:
                st.define(new VariableSymbol<TokenKind>(ct.lexeme, (TokenKind) ct.kind));
                ct.entry = st.resolve(ct.lexeme);
                result = new ASTIdentifierValue(
                        ct.entry, ct.line
                );
                match(Id);
                break;
            case 3:
                st.define(new LliteralSymbol<TokenKind>(ct.lexeme, (TokenKind) ct.kind));
                ct.entry = st.resolve(ct.lexeme);
                result = new ASTIntValue(
                        ct.entry, ct.line
                );
                match(IntLiteral);
                break;
            case 4:
                st.define(new LliteralSymbol<TokenKind>(ct.lexeme, (TokenKind) ct.kind));
                ct.entry = st.resolve(ct.lexeme);
                result = new ASTFloatValue(
                        ct.entry, ct.line
                );
                match(FloatLiteral);
                break;
            default:
                er.add(new SyntacticError(ct.line, "Incorrect expression"));
                recuperateUntil(SemiColon);
        }
        return result;
    }
}
