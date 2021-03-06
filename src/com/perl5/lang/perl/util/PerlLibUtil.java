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

package com.perl5.lang.perl.util;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.roots.libraries.Library;
import com.intellij.openapi.roots.libraries.LibraryTable;
import com.intellij.openapi.vfs.VirtualFile;
import com.perl5.lang.perl.idea.modules.JpsPerlLibrarySourceRootType;

/**
 * Created by hurricup on 09.08.2016.
 */
public class PerlLibUtil
{
	@Deprecated // implement event-based configuration
	public static void applyClassPathsForAllProjects()
	{
//		for (Project project : ProjectManager.getInstance().getOpenProjects())
//		{
//			PerlLibUtil.applyClassPaths(project);
//		}
	}

	@Deprecated // implement event-based configuration
	public static void applyClassPaths(final Project project)
	{
//		if (project == null)
//		{
//			return;
//		}
//
//		ApplicationManager.getApplication().runWriteAction(new Runnable()
//		{
//			@Override
//			public void run()
//			{
//				for (Module module : ModuleManager.getInstance(project).getModules())
//				{
//					// fixme we could fix modules here. Any for micro-ide and perl for perl
//					ModifiableRootModel modifiableModel = ModuleRootManager.getInstance(module).getModifiableModel();
//					applyClassPaths(modifiableModel);
//					modifiableModel.commit();
//				}
//			}
//		});
	}

	/**
	 * Scans ModifiableRootModel and update shared settings with new list
	 *
	 * @param model module modifiable model
	 */
	@Deprecated // this should be done gently
	public static void updatePerlLibsForModel(ModifiableRootModel model)
	{

//		List<String> libRoots = new ArrayList<String>();
//
//		for (VirtualFile entry : model.getSourceRoots(JpsPerlLibrarySourceRootType.INSTANCE))
//		{
//			libRoots.add(entry.getUrl());
//		}
//
//		PerlSharedSettings.getInstance(model.getProject()).setLibRootUrlsForModule(model.getModule(), libRoots);

//		applyClassPaths(model);
	}

	@Deprecated // this should be done gently on events, not like this
	public static void applyClassPaths(ModifiableRootModel rootModel)
	{
//		for (OrderEntry entry : rootModel.getOrderEntries())
//		{
////			System.err.println("Checking " + entry + " of " + entry.getClass());
//			if (entry instanceof LibraryOrderEntry)
//			{
////				System.err.println("Removing " + entry);
//				rootModel.removeOrderEntry(entry);
//			}
//		}

		LibraryTable table = rootModel.getModuleLibraryTable();

		for (VirtualFile virtualFile : rootModel.getSourceRoots(JpsPerlLibrarySourceRootType.INSTANCE))
		{
			addClassRootLibrary(table, virtualFile);
		}

//		// add external annotations coming with plugin
//		VirtualFile pluginAnnotationsRootVirtualFile = PerlAnnotationsUtil.getPluginAnnotationsRoot();
//		if (pluginAnnotationsRootVirtualFile != null)
//		{
//			addClassRootLibrary(table, pluginAnnotationsRootVirtualFile, true);
//		}
//
//		// add application-level external annotations
//		VirtualFile applicationAnnotationsRoot = PerlAnnotationsUtil.getApplicationAnnotationsRoot();
//		if (applicationAnnotationsRoot != null)
//		{
//			addClassRootLibrary(table, applicationAnnotationsRoot, true);
//		}

//		// Add project-level external annotations
//		Project project = rootModel.getProject();
//		VirtualFile projectAnnotationsRoot = PerlAnnotationsUtil.getProjectAnnotationsRoot(project);
//		if (projectAnnotationsRoot != null)
//		{
//			addClassRootLibrary(table, projectAnnotationsRoot, true);
//		}
//
//		// Add deparsed XSubs
//		VirtualFile deparsedXSubs = PerlXSubsState.getXSubsFile(project);
//		if (deparsedXSubs != null)
//		{
//			addClassRootLibrary(table, deparsedXSubs, false);
//		}

//		if (!PlatformUtils.isIntelliJ())
//		{
//			// add perl @inc to the end of libs
//			PerlLocalSettings settings = PerlLocalSettings.getInstance(project);
//			if (!settings.PERL_PATH.isEmpty())
//			{
//				for (String incPath : PerlSdkType.getInstance().getINCPaths(settings.PERL_PATH))
//				{
//					VirtualFile incFile = LocalFileSystem.getInstance().findFileByIoFile(new File(incPath));
//					if (incFile != null)
//					{
//						addClassRootLibrary(table, incFile, true);
//					}
//				}
//			}
//		}

//		OrderEntry[] entries = rootModel.getOrderEntries();
//
//		ContainerUtil.sort(entries, new Comparator<OrderEntry>()
//		{
//			@Override
//			public int compare(OrderEntry orderEntry, OrderEntry t1)
//			{
//				int i1 = orderEntry instanceof LibraryOrderEntry ? 1 : 0;
//				int i2 = t1 instanceof LibraryOrderEntry ? 1 : 0;
//				return i2 - i1;
//			}
//		});
//		rootModel.rearrangeOrderEntries(entries);

	}

	private static void addClassRootLibrary(LibraryTable table, VirtualFile virtualFile)
	{
		Library tableLibrary = table.createLibrary();
		Library.ModifiableModel modifiableModel = tableLibrary.getModifiableModel();
		modifiableModel.addRoot(virtualFile, OrderRootType.CLASSES);
		modifiableModel.commit();
	}

}
