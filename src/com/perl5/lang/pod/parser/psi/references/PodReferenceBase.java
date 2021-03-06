/*
 * Copyright 2016 Alexandr Evstigneev
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

package com.perl5.lang.pod.parser.psi.references;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiPolyVariantReferenceBase;
import org.jetbrains.annotations.NotNull;

/**
 * Created by hurricup on 07.04.2016.
 */
public abstract class PodReferenceBase<T extends PsiElement> extends PsiPolyVariantReferenceBase<T>
{
	public static final Object[] EMPTY_ARRAY = new Object[0];

	public PodReferenceBase(T psiElement)
	{
		super(psiElement);
	}

	public PodReferenceBase(T element, TextRange range)
	{
		super(element, range);
	}

	public PodReferenceBase(T psiElement, boolean soft)
	{
		super(psiElement, soft);
	}

	public PodReferenceBase(T element, TextRange range, boolean soft)
	{
		super(element, range, soft);
	}

	@NotNull
	@Override
	public Object[] getVariants()
	{
		return EMPTY_ARRAY;
	}

}
