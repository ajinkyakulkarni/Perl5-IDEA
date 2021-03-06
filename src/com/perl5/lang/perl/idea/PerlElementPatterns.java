/*
 * Copyright 2015 Alexandr Evstigneev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.perl5.lang.perl.idea;

import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.perl5.lang.mojolicious.psi.impl.MojoliciousFileImpl;
import com.perl5.lang.perl.lexer.PerlElementTypes;
import com.perl5.lang.perl.psi.*;

import static com.intellij.patterns.PlatformPatterns.psiElement;
import static com.intellij.patterns.PlatformPatterns.psiFile;

/**
 * Created by hurricup on 31.05.2015.
 */
public interface PerlElementPatterns extends PerlElementTypes
{
	PsiElementPattern.Capture<PsiElement> WHITE_SPACE_AND_COMMENTS = psiElement().whitespaceCommentOrError();

	PsiElementPattern.Capture<PerlLabel> LABEL_PATTERN = psiElement(PerlLabel.class);
	PsiElementPattern.Capture<PerlLabel> LABEL_DECLARATION_PATTERN = LABEL_PATTERN.withParent(psiElement(PerlLabelDeclaration.class));
	PsiElementPattern.Capture<PerlLabel> LABEL_IN_GOTO_PATTERN = LABEL_PATTERN.withParent(psiElement(PsiPerlGotoExpr.class));
	PsiElementPattern.Capture<PerlLabel> LABEL_IN_NEXT_LAST_REDO_PATTERN = LABEL_PATTERN.andOr(
			psiElement().withParent(psiElement(PsiPerlNextExpr.class)),
			psiElement().withParent(psiElement(PsiPerlLastExpr.class)),
			psiElement().withParent(psiElement(PsiPerlRedoExpr.class))
	);

	PsiElementPattern.Capture<PerlStringContentElement> STRING_CONTENT_PATTERN = psiElement(PerlStringContentElement.class);

	PsiElementPattern.Capture<PerlStringContentElement> STRING_CONTENT_IN_STRING_BARE = STRING_CONTENT_PATTERN.inside(PsiPerlStringBare.class);
	PsiElementPattern.Capture<PerlStringContentElement> STRING_CONTENT_IN_SQ_STRING_BEGIN = STRING_CONTENT_PATTERN.afterLeaf(psiElement(QUOTE_SINGLE_OPEN)).inside(PsiPerlStringSq.class);
	PsiElementPattern.Capture<PerlStringContentElement> STRING_CONTENT_IN_DQ_STRING_BEGIN = STRING_CONTENT_PATTERN.afterLeaf(psiElement(QUOTE_DOUBLE_OPEN)).inside(PsiPerlStringDq.class);
	PsiElementPattern.Capture<PerlStringContentElement> STRING_CONTENT_IN_QW_STRING_LIST = STRING_CONTENT_PATTERN.inside(PsiPerlStringList.class);

	PsiElementPattern.Capture<PerlStringContentElement> STRING_CONTENT_IN_LIST_OR_STRING_START = STRING_CONTENT_PATTERN.andOr(
			STRING_CONTENT_IN_QW_STRING_LIST,
			STRING_CONTENT_IN_SQ_STRING_BEGIN,
			STRING_CONTENT_IN_DQ_STRING_BEGIN
	);


	PsiElementPattern.Capture<PerlStringContentElement> STRING_CONTENT_IN_HEREDOC_OPENER_PATTERN = STRING_CONTENT_PATTERN.withParent(
			psiElement(PerlString.class).withParent(psiElement(PerlHeredocOpener.class))
	);

	PsiElementPattern.Capture<PerlStringContentElement> SIMPLE_HASH_INDEX = STRING_CONTENT_PATTERN.withSuperParent(2, PsiPerlHashIndex.class).andOr(
			STRING_CONTENT_IN_STRING_BARE,
			STRING_CONTENT_IN_SQ_STRING_BEGIN,
			STRING_CONTENT_IN_DQ_STRING_BEGIN,
			STRING_CONTENT_IN_QW_STRING_LIST
	);

	PsiElementPattern.Capture<PerlNamespaceElement> NAMESPACE_NAME_PATTERN = psiElement(PerlNamespaceElement.class);
	PsiElementPattern.Capture<PsiPerlUseStatement> USE_STATEMENT_PATTERN = psiElement(PsiPerlUseStatement.class);
	PsiElementPattern.Capture<PsiPerlNoStatement> NO_STATEMENT_PATTERN = psiElement(PsiPerlNoStatement.class);
	PsiElementPattern.Capture<PsiPerlRequireExpr> REQUIRE_EXPR_PATTERN = psiElement(PsiPerlRequireExpr.class);
	PsiElementPattern.Capture<PerlNamespaceDefinition> NAMESPACE_DEFINITION_PATTERN = psiElement(PerlNamespaceDefinition.class);

	PsiElementPattern.Capture<PerlNamespaceElement> NAMESPACE_IN_USE_PATTERN =
			NAMESPACE_NAME_PATTERN.andOr(
					psiElement().withParent(USE_STATEMENT_PATTERN),
					psiElement().withParent(NO_STATEMENT_PATTERN),
					psiElement().withParent(REQUIRE_EXPR_PATTERN)
			);

	PsiElementPattern.Capture<PerlNamespaceElement> NAMESPACE_IN_ANNOTATION_PATTERN =
			NAMESPACE_NAME_PATTERN.inside(psiElement(PerlAnnotationWithType.class));

	PsiElementPattern.Capture<PerlNamespaceElement> NAMESPACE_IN_DEFINITION_PATTERN =
			NAMESPACE_NAME_PATTERN.withParent(NAMESPACE_DEFINITION_PATTERN);

	PsiElementPattern.Capture<PerlNamespaceElement> NAMESPACE_IN_VARIABLE_DECLARATION_PATTERN =
			NAMESPACE_NAME_PATTERN.withParent(psiElement(PerlVariableDeclaration.class));

	PsiElementPattern.Capture<PerlSubNameElement> SUB_NAME_PATTERN = psiElement(PerlSubNameElement.class);

	PsiElementPattern.Capture<PsiPerlMethod> METHOD_PATTERN = psiElement(PsiPerlMethod.class);
	PsiElementPattern.Capture IN_OBJECT_CALL_PATTERN = psiElement().withParent(PsiPerlNestedCall.class);

	PsiElementPattern.Capture IN_STATIC_METHOD_PATTERN = psiElement().withParent(METHOD_PATTERN.andNot(IN_OBJECT_CALL_PATTERN));
	PsiElementPattern.Capture IN_OBJECT_METHOD_PATTERN = psiElement().withParent(METHOD_PATTERN.and(IN_OBJECT_CALL_PATTERN));

	PsiElementPattern.Capture<PerlVariable> VARIABLE_PATTERN = psiElement(PerlVariable.class);

	PsiElementPattern.Capture<PerlGlobVariable> GLOB_PATTERN = psiElement(PerlGlobVariable.class);

	PsiElementPattern.Capture IN_VARIABLE_PATTERN = psiElement().inside(VARIABLE_PATTERN);
	PsiElementPattern.Capture IN_GLOB_PATTERN = psiElement().inside(GLOB_PATTERN);

	// fixme move this to mojo patterns
	PsiElementPattern.Capture IN_MOJOLICIOUS_FILE = psiElement().inFile(psiFile(MojoliciousFileImpl.class));

	PsiElementPattern.Capture<PerlVariableNameElement> VARIABLE_NAME_PATTERN = psiElement(PerlVariableNameElement.class);
	PsiElementPattern.Capture<PerlVariableNameElement> VARIABLE_NAME_IN_DECLARATION_PATTERN = VARIABLE_NAME_PATTERN.withParent(
			VARIABLE_PATTERN.withParent(psiElement(PerlVariableDeclarationWrapper.class))
	);
	PsiElementPattern.Capture<PerlVariableNameElement> VARIABLE_NAME_IN_LOCAL_DECLARATION_PATTERN = VARIABLE_NAME_PATTERN.inside(true, psiElement(PsiPerlVariableDeclarationLocal.class));

	PsiElementPattern.Capture<PsiElement> UNKNOWN_ANNOTATION_PATTERN = psiElement(IDENTIFIER).afterLeaf(psiElement(ANNOTATION_PREFIX));

	PsiElementPattern.Capture<PerlVariableNameElement> VARIABLE_COMPLETION_PATTERN =
			VARIABLE_NAME_PATTERN
					.andOr(IN_VARIABLE_PATTERN, IN_GLOB_PATTERN)
					.andOr(
							VARIABLE_NAME_IN_LOCAL_DECLARATION_PATTERN,
							psiElement().andNot(VARIABLE_NAME_IN_DECLARATION_PATTERN)
					);


	PsiElementPattern.Capture<PsiPerlIfCompound> INCOMPLETED_IF_COMPOUND =
			psiElement(PsiPerlIfCompound.class)
					.andNot(
							psiElement()
									.withLastChild(psiElement(PsiPerlUnconditionalBlock.class)));

	PsiElementPattern.Capture<PsiElement> ELSE_ELSIF_PLACE =
			psiElement().inside(
					psiElement(PsiPerlStatement.class).afterSiblingSkipping(WHITE_SPACE_AND_COMMENTS, INCOMPLETED_IF_COMPOUND)
			);

	// @ISA = ()
	PsiElementPattern.Capture<PsiPerlArrayVariable> ISA_VARIABLE = psiElement(PsiPerlArrayVariable.class);
	PsiElementPattern.Capture<PsiPerlVariableDeclarationGlobal> ISA_DECLARATION = psiElement(PsiPerlVariableDeclarationGlobal.class)
			.withChild(
					psiElement(PerlVariableDeclarationWrapper.class).withFirstChild(ISA_VARIABLE)
			);

	PsiElementPattern.Capture<PsiPerlStatement> ISA_ASSIGN_STATEMENT = psiElement(PsiPerlStatement.class).withFirstChild(
			psiElement(PsiPerlAssignExpr.class).andOr(
					psiElement().withFirstChild(ISA_VARIABLE),
					psiElement().withFirstChild(ISA_DECLARATION)
			)
	);

	// @EXPORTER scanner
	// this one is to speed up the scanning
	PsiElementPattern.Capture<PsiPerlStatement> ASSIGN_STATEMENT = psiElement(PsiPerlStatement.class).withFirstChild(
			psiElement(PsiPerlAssignExpr.class)
	);

	PsiElementPattern.Capture<PsiPerlArrayVariable> EXPORT_VARIABLE = psiElement(PsiPerlArrayVariable.class).withText("@EXPORT");
	PsiElementPattern.Capture<PsiPerlVariableDeclarationGlobal> EXPORT_DECLARATION = psiElement(PsiPerlVariableDeclarationGlobal.class)
			.withChild(
					psiElement(PerlVariableDeclarationWrapper.class).withFirstChild(EXPORT_VARIABLE)
			);
	PsiElementPattern.Capture<PsiPerlStatement> EXPORT_ASSIGN_STATEMENT = psiElement(PsiPerlStatement.class).withFirstChild(
			psiElement(PsiPerlAssignExpr.class).andOr(
					psiElement().withFirstChild(EXPORT_VARIABLE),
					psiElement().withFirstChild(EXPORT_DECLARATION)
			)
	);

	PsiElementPattern.Capture<PsiPerlArrayVariable> EXPORT_OK_VARIABLE = psiElement(PsiPerlArrayVariable.class).withText("@EXPORT_OK");
	PsiElementPattern.Capture<PsiPerlVariableDeclarationGlobal> EXPORT_OK_DECLARATION = psiElement(PsiPerlVariableDeclarationGlobal.class)
			.withChild(
					psiElement(PerlVariableDeclarationWrapper.class).withFirstChild(EXPORT_OK_VARIABLE)
			);

	PsiElementPattern.Capture<PsiPerlStatement> EXPORT_OK_ASSIGN_STATEMENT = psiElement(PsiPerlStatement.class).withFirstChild(
			psiElement(PsiPerlAssignExpr.class).andOr(
					psiElement().withFirstChild(EXPORT_OK_VARIABLE),
					psiElement().withFirstChild(EXPORT_OK_DECLARATION)
			)
	);

	// @EXPORT = ();
	PsiElementPattern.Capture<PsiPerlArrayVariable> EXPORT_ARRAY_VARIABLE_PATTERN =
			psiElement(PsiPerlArrayVariable.class).andOr(
					psiElement().withText("@EXPORT"),
					psiElement().withText("@EXPORT_OK")
			);

	PsiElementPattern.Capture<PsiPerlVariableDeclarationGlobal> EXPORT_ARRAY_VARIABLE_DECLARATION =
			psiElement(PsiPerlVariableDeclarationGlobal.class).withChild(
					psiElement(PerlVariableDeclarationWrapper.class).withChild(EXPORT_ARRAY_VARIABLE_PATTERN)
			);


	PsiElementPattern.Capture<PsiElement> EXPORT_ASSIGNMENT =
			psiElement()
					.afterLeafSkipping(WHITE_SPACE_AND_COMMENTS, psiElement(OPERATOR_ASSIGN))
					.withParent(
							psiElement(PsiPerlAssignExpr.class).withFirstChild(
									psiElement().andOr(
											EXPORT_ARRAY_VARIABLE_PATTERN,
											EXPORT_ARRAY_VARIABLE_DECLARATION
									)
							)
					);

	PsiElementPattern.Capture<PsiPerlParenthesisedExpr> EXPORT_ASSIGNMENT_PARENTHESISED = psiElement(PsiPerlParenthesisedExpr.class).and(EXPORT_ASSIGNMENT);

	PsiElementPattern.Capture<PsiElement> EXPORT_ASSIGNED_STRING_CONTENT = psiElement().andOr(
			STRING_CONTENT_IN_QW_STRING_LIST.andOr(
					psiElement().withParent(EXPORT_ASSIGNMENT),
					psiElement().withSuperParent(2, EXPORT_ASSIGNMENT_PARENTHESISED)
			),
			STRING_CONTENT_PATTERN.withParent(
					psiElement(PerlString.class).andOr(
							psiElement().withParent(psiElement(PsiPerlCommaSequenceExpr.class).withParent(EXPORT_ASSIGNMENT_PARENTHESISED)),
							psiElement().withParent(EXPORT_ASSIGNMENT_PARENTHESISED)
					)
			)
	);

	PsiElementPattern.Capture<PerlStringContentElement> USE_PARAMETERS_PATTERN = STRING_CONTENT_PATTERN.andOr(
			psiElement().inside(USE_STATEMENT_PATTERN),
			psiElement().inside(NO_STATEMENT_PATTERN)
	);

	// pattern for shift;
	PsiElementPattern.Capture<PsiPerlNamedUnaryExpr> SHIFT_PATTERN =
			psiElement(PsiPerlNamedUnaryExpr.class).withFirstChild(
					psiElement(PsiPerlMethod.class).withText("shift")
			);

	PsiElementPattern.Capture<PsiPerlArrayVariable> ALL_ARGUMENTS_PATTERN = psiElement(PsiPerlArrayVariable.class).withText("@_");
	PsiElementPattern.Capture<PsiPerlScalarArrayElement> ALL_ARGUMENTS_ELEMENT_PATTERN =
			psiElement(PsiPerlScalarArrayElement.class)
					.withFirstChild(psiElement(PsiPerlScalarVariable.class).withText("$_"));

	PsiElementPattern.Capture<PsiPerlNamedUnaryExpr> TAILING_SHIFT_PATTERN =
			SHIFT_PATTERN.beforeLeaf(psiElement(SEMICOLON));


	PsiElementPattern.Capture<PsiPerlStatement> EMPTY_SHIFT_STATEMENT_PATTERN =
			psiElement(PsiPerlStatement.class).withFirstChild(TAILING_SHIFT_PATTERN);


	PsiElementPattern.Capture<PsiPerlStatement> DECLARATION_ASSIGNING_PATTERN =
			psiElement(PsiPerlStatement.class).withFirstChild(
					psiElement(PerlAssignExpression.class).withFirstChild(
							psiElement(PerlVariableDeclaration.class)
					)/*.andOr(
							psiElement().withLastChild(TAILING_SHIFT_PATTERN),
							psiElement().withLastChild(ALL_ARGUMENTS_PATTERN)
					)*/
			);

	PsiElementPattern.Capture<PsiPerlStatement> ARGUMENTS_LAST_UNPACKING_PATTERN =
			psiElement(PsiPerlStatement.class).withFirstChild(
					psiElement(PsiPerlAssignExpr.class).withFirstChild(
							psiElement(PerlVariableDeclaration.class)
					).withLastChild(ALL_ARGUMENTS_PATTERN)
			);

}
