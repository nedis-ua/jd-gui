/*
 * Copyright (c) 2008-2023 Emmanuel Dupuy.
 * This project is distributed under the GPLv3 license.
 * This is a Copyleft license that gives the user the right to use,
 * copy and modify the code freely for non-commercial purposes.
 */

package org.jd.gui.service.treenode;

public class JspTagTreeNodeFactoryProvider extends JspFileTreeNodeFactoryProvider {
    @Override public String[] getSelectors() { return appendSelectors("*:file:*.tag"); }
}
