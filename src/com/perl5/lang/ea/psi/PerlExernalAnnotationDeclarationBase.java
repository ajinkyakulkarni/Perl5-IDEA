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

package com.perl5.lang.ea.psi;

import com.perl5.lang.perl.psi.utils.PerlSubAnnotations;
import org.jetbrains.annotations.Nullable;

/**
 * Created by hurricup on 07.08.2016.
 */
public interface PerlExernalAnnotationDeclarationBase
{
	/**
	 * Returns stubbed, local or external sub annotations
	 *
	 * @return PerlSubAnnotation object
	 */
	@Nullable
	PerlSubAnnotations getAnnotations();

	/**
	 * Returns function name for current function definition
	 *
	 * @return function name or null
	 */
	String getSubName();

	/**
	 * Trying to get the package name from explicit specification or by traversing
	 *
	 * @return package name for current element
	 */
	@Nullable
	String getPackageName();

	/**
	 * Returns fullname package::element
	 *
	 * @return name
	 */
	@Nullable
	String getCanonicalName();

}
