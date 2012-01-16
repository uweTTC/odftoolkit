/************************************************************************
 *
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER
 *
 * Copyright 2008, 2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Use is subject to license terms.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at http://www.apache.org/licenses/LICENSE-2.0. You can also
 * obtain a copy of the License at http://odftoolkit.org/docs/license.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ************************************************************************/

/*
 * This file is automatically generated.
 * Don't edit manually.
 */
package org.odftoolkit.odfdom.dom.attribute.xlink;

import org.odftoolkit.odfdom.dom.OdfDocumentNamespace;
import org.odftoolkit.odfdom.pkg.OdfAttribute;
import org.odftoolkit.odfdom.pkg.OdfElement;
import org.odftoolkit.odfdom.pkg.OdfFileDom;
import org.odftoolkit.odfdom.pkg.OdfName;

import org.odftoolkit.odfdom.dom.element.db.DbConnectionResourceElement;
import org.odftoolkit.odfdom.dom.element.draw.DrawAElement;
import org.odftoolkit.odfdom.dom.element.draw.DrawAppletElement;
import org.odftoolkit.odfdom.dom.element.draw.DrawFillImageElement;
import org.odftoolkit.odfdom.dom.element.draw.DrawFloatingFrameElement;
import org.odftoolkit.odfdom.dom.element.draw.DrawImageElement;
import org.odftoolkit.odfdom.dom.element.draw.DrawObjectElement;
import org.odftoolkit.odfdom.dom.element.draw.DrawObjectOleElement;
import org.odftoolkit.odfdom.dom.element.draw.DrawPluginElement;
import org.odftoolkit.odfdom.dom.element.form.FormFormElement;
import org.odftoolkit.odfdom.dom.element.meta.MetaAutoReloadElement;
import org.odftoolkit.odfdom.dom.element.meta.MetaTemplateElement;
import org.odftoolkit.odfdom.dom.element.presentation.PresentationEventListenerElement;
import org.odftoolkit.odfdom.dom.element.presentation.PresentationSoundElement;
import org.odftoolkit.odfdom.dom.element.script.ScriptEventListenerElement;
import org.odftoolkit.odfdom.dom.element.style.StyleBackgroundImageElement;
import org.odftoolkit.odfdom.dom.element.svg.SvgDefinitionSrcElement;
import org.odftoolkit.odfdom.dom.element.svg.SvgFontFaceUriElement;
import org.odftoolkit.odfdom.dom.element.table.TableCellRangeSourceElement;
import org.odftoolkit.odfdom.dom.element.table.TableTableSourceElement;
import org.odftoolkit.odfdom.dom.element.text.TextAElement;
import org.odftoolkit.odfdom.dom.element.text.TextListLevelStyleImageElement;
/**
 * DOM implementation of OpenDocument attribute  {@odf.attribute xlink:actuate}.
 *
 */
public class XlinkActuateAttribute extends OdfAttribute {

	public static final OdfName ATTRIBUTE_NAME = OdfName.newName(OdfDocumentNamespace.XLINK, "actuate");
	public static final String DEFAULT_VALUE_ONREQUEST = Value.ONREQUEST.toString();
	public static final String DEFAULT_VALUE_ONLOAD = Value.ONLOAD.toString();

	/**
	 * Create the instance of OpenDocument attribute {@odf.attribute xlink:actuate}.
	 *
	 * @param ownerDocument       The type is <code>OdfFileDom</code>
	 */
	public XlinkActuateAttribute(OdfFileDom ownerDocument) {
		super(ownerDocument, ATTRIBUTE_NAME);
	}

	/**
	 * Returns the attribute name.
	 *
	 * @return the <code>OdfName</code> for {@odf.attribute xlink:actuate}.
	 */
	@Override
	public OdfName getOdfName() {
		return ATTRIBUTE_NAME;
	}

	/**
	 * @return Returns the name of this attribute.
	 */
	@Override
	public String getName() {
		return ATTRIBUTE_NAME.getLocalName();
	}

	/**
	 * The value set of {@odf.attribute xlink:actuate}.
	 */
	public enum Value {
		ONLOAD("onLoad"), ONREQUEST("onRequest") ;

		private String mValue;

		Value(String value) {
			mValue = value;
		}

		@Override
		public String toString() {
			return mValue;
		}

		public static Value enumValueOf(String value) {
			for(Value aIter : values()) {
				if (value.equals(aIter.toString())) {
				return aIter;
				}
			}
			return null;
		}
	}

	/**
	 * @param attrValue The <code>Enum</code> value of the attribute.
	 */
	public void setEnumValue(Value attrValue) {
		setValue(attrValue.toString());
	}

	/**
	 * @return Returns the <code>Enum</code> value of the attribute
	 */
	public Value getEnumValue() {
		return Value.enumValueOf(this.getValue());
	}

	/**
	 * Returns the default value of {@odf.attribute xlink:actuate}.
	 *
	 * @return the default value as <code>String</code> dependent of its element name
	 *         return <code>null</code> if the default value does not exist
	 */
	@Override
	public String getDefault() {
		OdfElement parentElement = (OdfElement)getOwnerElement();
		String defaultValue = null;
		if (parentElement != null) {
			if (parentElement instanceof DbConnectionResourceElement) {
				defaultValue = DEFAULT_VALUE_ONREQUEST;
			}
			if (parentElement instanceof DrawAElement) {
				defaultValue = DEFAULT_VALUE_ONREQUEST;
			}
			if (parentElement instanceof DrawAppletElement) {
				defaultValue = DEFAULT_VALUE_ONLOAD;
			}
			if (parentElement instanceof DrawFillImageElement) {
				defaultValue = DEFAULT_VALUE_ONLOAD;
			}
			if (parentElement instanceof DrawFloatingFrameElement) {
				defaultValue = DEFAULT_VALUE_ONLOAD;
			}
			if (parentElement instanceof DrawImageElement) {
				defaultValue = DEFAULT_VALUE_ONLOAD;
			}
			if (parentElement instanceof DrawObjectElement) {
				defaultValue = DEFAULT_VALUE_ONLOAD;
			}
			if (parentElement instanceof DrawObjectOleElement) {
				defaultValue = DEFAULT_VALUE_ONLOAD;
			}
			if (parentElement instanceof DrawPluginElement) {
				defaultValue = DEFAULT_VALUE_ONLOAD;
			}
			if (parentElement instanceof FormFormElement) {
				defaultValue = DEFAULT_VALUE_ONREQUEST;
			}
			if (parentElement instanceof MetaAutoReloadElement) {
				defaultValue = DEFAULT_VALUE_ONLOAD;
			}
			if (parentElement instanceof MetaTemplateElement) {
				defaultValue = DEFAULT_VALUE_ONREQUEST;
			}
			if (parentElement instanceof PresentationEventListenerElement) {
				defaultValue = DEFAULT_VALUE_ONREQUEST;
			}
			if (parentElement instanceof PresentationSoundElement) {
				defaultValue = DEFAULT_VALUE_ONREQUEST;
			}
			if (parentElement instanceof ScriptEventListenerElement) {
				defaultValue = DEFAULT_VALUE_ONREQUEST;
			}
			if (parentElement instanceof StyleBackgroundImageElement) {
				defaultValue = DEFAULT_VALUE_ONLOAD;
			}
			if (parentElement instanceof SvgDefinitionSrcElement) {
				defaultValue = DEFAULT_VALUE_ONREQUEST;
			}
			if (parentElement instanceof SvgFontFaceUriElement) {
				defaultValue = DEFAULT_VALUE_ONREQUEST;
			}
			if (parentElement instanceof TableCellRangeSourceElement) {
				defaultValue = DEFAULT_VALUE_ONREQUEST;
			}
			if (parentElement instanceof TableTableSourceElement) {
				defaultValue = DEFAULT_VALUE_ONREQUEST;
			}
			if (parentElement instanceof TextAElement) {
				defaultValue = DEFAULT_VALUE_ONREQUEST;
			}
			if (parentElement instanceof TextListLevelStyleImageElement) {
				defaultValue = DEFAULT_VALUE_ONLOAD;
			}
		}
		return defaultValue;
	}

	/**
	 * Default value indicator. As the attribute default value is dependent from its element, the attribute has only a default, when a parent element exists.
	 *
	 * @return <code>true</code> if {@odf.attribute xlink:actuate} has an element parent
	 *         otherwise return <code>false</code> as undefined.
	 */
	@Override
	public boolean hasDefault() {
		return getOwnerElement() == null ? false : true;
	}

	/**
	 * @return Returns whether this attribute is known to be of type ID (i.e. xml:id ?)
	 */
	@Override
	public boolean isId() {
		return false;
	}
}
