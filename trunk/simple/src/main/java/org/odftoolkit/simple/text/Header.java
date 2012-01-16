/* 
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
*/

package org.odftoolkit.simple.text;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.odftoolkit.odfdom.dom.OdfContentDom;
import org.odftoolkit.odfdom.dom.OdfDocumentNamespace;
import org.odftoolkit.odfdom.dom.OdfStylesDom;
import org.odftoolkit.odfdom.dom.element.style.StyleHeaderElement;
import org.odftoolkit.odfdom.dom.element.style.StyleTableCellPropertiesElement;
import org.odftoolkit.odfdom.dom.element.style.StyleTablePropertiesElement;
import org.odftoolkit.odfdom.dom.element.style.StyleTextPropertiesElement;
import org.odftoolkit.odfdom.dom.element.table.TableTableCellElement;
import org.odftoolkit.odfdom.dom.element.table.TableTableElement;
import org.odftoolkit.odfdom.dom.element.text.TextPElement;
import org.odftoolkit.odfdom.dom.style.OdfStyleFamily;
import org.odftoolkit.odfdom.incubator.doc.office.OdfOfficeAutomaticStyles;
import org.odftoolkit.odfdom.incubator.doc.style.OdfStyle;
import org.odftoolkit.odfdom.pkg.OdfElement;
import org.odftoolkit.odfdom.pkg.OdfFileDom;
import org.odftoolkit.simple.Component;
import org.odftoolkit.simple.common.field.AbstractVariableContainer;
import org.odftoolkit.simple.common.field.VariableContainer;
import org.odftoolkit.simple.common.field.VariableField;
import org.odftoolkit.simple.common.field.VariableField.VariableType;
import org.odftoolkit.simple.table.AbstractTableContainer;
import org.odftoolkit.simple.table.Table;
import org.odftoolkit.simple.table.TableContainer;
import org.odftoolkit.simple.table.Table.TableBuilder;
import org.w3c.dom.NodeList;

/**
 * This class represents header definition in text document. It provides methods
 * to manipulate header in text document, such as, set text, add table.
 * 
 * @since 0.4.5
 */
public class Header extends Component implements TableContainer, VariableContainer {

	private StyleHeaderElement headerEle;
	private TableContainerImpl tableContainerImpl;
	private VariableContainerImpl variableContainerImpl;

	/**
	 * Create a header instance by an object of <code>StyleHeaderElement</code>.
	 * 
	 * @param element
	 *            - an object of <code>StyleHeaderElement</code>
	 */
	public Header(StyleHeaderElement element) {
		headerEle = element;
	}

	/**
	 * Return an instance of <code>StyleHeaderElement</code> which represents
	 * this feature.
	 * 
	 * @return an instance of <code>StyleHeaderElement</code>
	 */
	public StyleHeaderElement getOdfElement() {
		return headerEle;
	}

	/**
	 * Get this header is visible or not.
	 * 
	 * @return If this header is visible return <code>true</code>, otherwise
	 *         return <code>false</code>.
	 * @since 0.5.5
	 */
	public boolean isVisible() {
		boolean isVisible = headerEle.getStyleDisplayAttribute();
		return isVisible;
	}

	/**
	 * Set this header visible or not.
	 * 
	 * @param isVisible
	 *            If <code>isVisible</code> is true, the header of this document
	 *            is visible, otherwise is invisible.
	 * @since 0.5.5
	 */
	public void setVisible(boolean isVisible) {
		if (isVisible) {
			headerEle.removeAttributeNS(OdfDocumentNamespace.STYLE.getUri(), "display");
		} else {
			headerEle.setStyleDisplayAttribute(false);
		}
		NodeList nodeList = headerEle.getElementsByTagName(TextPElement.ELEMENT_NAME.getQName());
		for (int i = 0; i < nodeList.getLength(); i++) {
			TextPElement textEle = (TextPElement) nodeList.item(i);
			String stylename = textEle.getStyleName();
			OdfFileDom dom = (OdfFileDom) headerEle.getOwnerDocument();
			OdfOfficeAutomaticStyles styles = null;
			if (dom instanceof OdfContentDom) {
				styles = ((OdfContentDom) dom).getAutomaticStyles();
			} else if (dom instanceof OdfStylesDom) {
				styles = ((OdfStylesDom) dom).getAutomaticStyles();
			}

			OdfStyle newStyle = styles.newStyle(OdfStyleFamily.Paragraph);
			OdfStyle style = styles.getStyle(stylename, OdfStyleFamily.Paragraph);
			if (style != null) {
				String styleName = newStyle.getStyleNameAttribute();
				styles.removeChild(newStyle);
				newStyle = (OdfStyle) style.cloneNode(true);
				newStyle.setStyleNameAttribute(styleName);
				styles.appendChild(newStyle);
			}
			if (isVisible) {
				if (newStyle.hasProperty(StyleTextPropertiesElement.Display)) {
					newStyle.removeProperty(StyleTextPropertiesElement.Display);
				}
			} else {
				newStyle.setProperty(StyleTextPropertiesElement.Display, "none");
			}
			textEle.setStyleName(newStyle.getStyleNameAttribute());
		}
	}

	public Table addTable() {
		Table table = getTableContainerImpl().addTable();
		updateTableToNone(table);
		return table;
	}

	public Table addTable(int numRows, int numCols) {
		Table table = getTableContainerImpl().addTable(numRows, numCols);
		updateTableToNone(table);
		return table;
	}

	public Table getTableByName(String name) {
		return getTableContainerImpl().getTableByName(name);
	}

	public java.util.List<Table> getTableList() {
		return getTableContainerImpl().getTableList();
	}

	public TableBuilder getTableBuilder() {
		return getTableContainerImpl().getTableBuilder();
	}

	public OdfElement getTableContainerElement() {
		return getTableContainerImpl().getTableContainerElement();
	}

	public OdfElement getVariableContainerElement() {
		return getVariableContainerImpl().getVariableContainerElement();
	}

	public VariableField declareVariable(String name, VariableType type) {
		return getVariableContainerImpl().declareVariable(name, type);
	}

	public VariableField getVariableFieldByName(String name) {
		return getVariableContainerImpl().getVariableFieldByName(name);
	}

	private TableContainer getTableContainerImpl() {
		if (tableContainerImpl == null) {
			tableContainerImpl = new TableContainerImpl();
		}
		return tableContainerImpl;
	}

	private VariableContainer getVariableContainerImpl() {
		if (variableContainerImpl == null) {
			variableContainerImpl = new VariableContainerImpl();
		}
		return variableContainerImpl;
	}

	private void updateTableToNone(Table table) {
		OdfFileDom dom = (OdfFileDom) getTableContainerElement().getOwnerDocument();
		TableTableElement tableEle = table.getOdfElement();
		String stylename = tableEle.getStyleName();
		OdfOfficeAutomaticStyles styles = null;
		if (dom instanceof OdfContentDom) {
			styles = ((OdfContentDom) dom).getAutomaticStyles();
		} else if (dom instanceof OdfStylesDom) {
			styles = ((OdfStylesDom) dom).getAutomaticStyles();
		}
		OdfStyle tableStyle = styles.getStyle(stylename, OdfStyleFamily.Table);
		tableStyle.setProperty(StyleTablePropertiesElement.Shadow, "none");
		NodeList cells = tableEle.getElementsByTagNameNS(OdfDocumentNamespace.TABLE.getUri(), "table-cell");
		if (cells != null && cells.getLength() > 0) {
			OdfStyle cellStyleWithoutBorder = styles.newStyle(OdfStyleFamily.TableCell);
			cellStyleWithoutBorder.setProperty(StyleTableCellPropertiesElement.Border, "none");
			cellStyleWithoutBorder.removeProperty(StyleTableCellPropertiesElement.Padding);
			String cellStyleName = cellStyleWithoutBorder.getStyleNameAttribute();
			for (int i = 0; i < cells.getLength(); i++) {
				TableTableCellElement cell = (TableTableCellElement) cells.item(i);
				cell.setStyleName(cellStyleName);
			}
		}
	}

	private class TableContainerImpl extends AbstractTableContainer {

		public OdfElement getTableContainerElement() {
			return headerEle;
		}
	}

	private class VariableContainerImpl extends AbstractVariableContainer {

		public OdfElement getVariableContainerElement() {
			try {
				return headerEle;
			} catch (Exception e) {
				Logger.getLogger(Header.class.getName()).log(Level.SEVERE, null, e);
				return null;
			}
		}
	}
}
