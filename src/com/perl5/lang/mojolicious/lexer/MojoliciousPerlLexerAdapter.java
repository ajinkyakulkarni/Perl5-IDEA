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

package com.perl5.lang.mojolicious.lexer;

import com.intellij.openapi.project.Project;
import com.perl5.lang.perl.lexer.PerlLexerWithCustomStatesAdapter;

/**
 * Created by hurricup on 21.07.2015.
 * Copy-pasted FlexAdapter because of private variables and no setters
 */
public class MojoliciousPerlLexerAdapter extends PerlLexerWithCustomStatesAdapter
{
	public MojoliciousPerlLexerAdapter(Project project)
	{
		super(new MojoliciousLexer(project));
	}
}
