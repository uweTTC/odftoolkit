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
package org.odftoolkit.odfdom.dom.element.text;

import org.odftoolkit.odfdom.pkg.OdfElement;
import org.odftoolkit.odfdom.pkg.ElementVisitor;
import org.odftoolkit.odfdom.pkg.OdfFileDom;
import org.odftoolkit.odfdom.pkg.OdfName;
import org.odftoolkit.odfdom.dom.OdfDocumentNamespace;
import org.odftoolkit.odfdom.dom.DefaultElementVisitor;
import org.odftoolkit.odfdom.dom.attribute.text.TextConditionAttribute;
import org.odftoolkit.odfdom.dom.attribute.text.TextCurrentValueAttribute;
import org.odftoolkit.odfdom.dom.attribute.text.TextStringValueIfFalseAttribute;
import org.odftoolkit.odfdom.dom.attribute.text.TextStringValueIfTrueAttribute;

/**
 * DOM implementation of OpenDocument element  {@odf.element text:conditional-text}.
 *
 */
public class TextConditionalTextElement extends OdfElement {

	public static final OdfName ELEMENT_NAME = OdfName.newName(OdfDocumentNamespace.TEXT, "conditional-text");

	/**
	 * Create the instance of <code>TextConditionalTextElement</code>
	 *
	 * @param  ownerDoc     The type is <code>OdfFileDom</code>
	 */
	public TextConditionalTextElement(OdfFileDom ownerDoc) {
		super(ownerDoc, ELEMENT_NAME);
	}

	/**
	 * Get the element name
	 *
	 * @return  return   <code>OdfName</code> the name of element {@odf.element text:conditional-text}.
	 */
	public OdfName getOdfName() {
		return ELEMENT_NAME;
	}

	/**
	 * Receives the value of the ODFDOM attribute representation <code>TextConditionAttribute</code> , See {@odf.attribute text:condition}
	 *
	 * Attribute is mandatory.
	 *
	 * @return - the <code>String</code> , the value or <code>null</code>, if the attribute is not set and no default value defined.
	 */
	public String getTextConditionAttribute() {
		TextConditionAttribute attr = (TextConditionAttribute) getOdfAttribute(OdfDocumentNamespace.TEXT, "condition");
		if (attr != null) {
			return String.valueOf(attr.getValue());
		}
		return null;
	}

	/**
	 * Sets the value of ODFDOM attribute representation <code>TextConditionAttribute</code> , See {@odf.attribute text:condition}
	 *
	 * @param textConditionValue   The type is <code>String</code>
	 */
	public void setTextConditionAttribute(String textConditionValue) {
		TextConditionAttribute attr = new TextConditionAttribute((OdfFileDom) this.ownerDocument);
		setOdfAttribute(attr);
		attr.setValue(textConditionValue);
	}

	/**
	 * Receives the value of the ODFDOM attribute representation <code>TextCurrentValueAttribute</code> , See {@odf.attribute text:current-value}
	 *
	 * @return - the <code>Boolean</code> , the value or <code>null</code>, if the attribute is not set and no default value defined.
	 */
	public Boolean getTextCurrentValueAttribute() {
		TextCurrentValueAttribute attr = (TextCurrentValueAttribute) getOdfAttribute(OdfDocumentNamespace.TEXT, "current-value");
		if (attr != null) {
			return Boolean.valueOf(attr.booleanValue());
		}
		return null;
	}

	/**
	 * Sets the value of ODFDOM attribute representation <code>TextCurrentValueAttribute</code> , See {@odf.attribute text:current-value}
	 *
	 * @param textCurrentValueValue   The type is <code>Boolean</code>
	 */
	public void setTextCurrentValueAttribute(Boolean textCurrentValueValue) {
		TextCurrentValueAttribute attr = new TextCurrentValueAttribute((OdfFileDom) this.ownerDocument);
		setOdfAttribute(attr);
		attr.setBooleanValue(textCurrentValueValue.booleanValue());
	}

	/**
	 * Receives the value of the ODFDOM attribute representation <code>TextStringValueIfFalseAttribute</code> , See {@odf.attribute text:string-value-if-false}
	 *
	 * Attribute is mandatory.
	 *
	 * @return - the <code>String</code> , the value or <code>null</code>, if the attribute is not set and no default value defined.
	 */
	public String getTextStringValueIfFalseAttribute() {
		TextStringValueIfFalseAttribute attr = (TextStringValueIfFalseAttribute) getOdfAttribute(OdfDocumentNamespace.TEXT, "string-value-if-false");
		if (attr != null) {
			return String.valueOf(attr.getValue());
		}
		return null;
	}

	/**
	 * Sets the value of ODFDOM attribute representation <code>TextStringValueIfFalseAttribute</code> , See {@odf.attribute text:string-value-if-false}
	 *
	 * @param textStringValueIfFalseValue   The type is <code>String</code>
	 */
	public void setTextStringValueIfFalseAttribute(String textStringValueIfFalseValue) {
		TextStringValueIfFalseAttribute attr = new TextStringValueIfFalseAttribute((OdfFileDom) this.ownerDocument);
		setOdfAttribute(attr);
		attr.setValue(textStringValueIfFalseValue);
	}

	/**
	 * Receives the value of the ODFDOM attribute representation <code>TextStringValueIfTrueAttribute</code> , See {@odf.attribute text:string-value-if-true}
	 *
	 * Attribute is mandatory.
	 *
	 * @return - the <code>String</code> , the value or <code>null</code>, if the attribute is not set and no default value defined.
	 */
	public String getTextStringValueIfTrueAttribute() {
		TextStringValueIfTrueAttribute attr = (TextStringValueIfTrueAttribute) getOdfAttribute(OdfDocumentNamespace.TEXT, "string-value-if-true");
		if (attr != null) {
			return String.valueOf(attr.getValue());
		}
		return null;
	}

	/**
	 * Sets the value of ODFDOM attribute representation <code>TextStringValueIfTrueAttribute</code> , See {@odf.attribute text:string-value-if-true}
	 *
	 * @param textStringValueIfTrueValue   The type is <code>String</code>
	 */
	public void setTextStringValueIfTrueAttribute(String textStringValueIfTrueValue) {
		TextStringValueIfTrueAttribute attr = new TextStringValueIfTrueAttribute((OdfFileDom) this.ownerDocument);
		setOdfAttribute(attr);
		attr.setValue(textStringValueIfTrueValue);
	}

	@Override
	public void accept(ElementVisitor visitor) {
		if (visitor instanceof DefaultElementVisitor) {
			DefaultElementVisitor defaultVisitor = (DefaultElementVisitor) visitor;
			defaultVisitor.visit(this);
		} else {
			visitor.visit(this);
		}
	}
	/**
	 * Add text content. Only elements which are allowed to have text content offer this method.
	 */
	public void newTextNode(String content) {
		if (content != null && !content.equals("")) {
			this.appendChild(this.getOwnerDocument().createTextNode(content));
		}
	}
}
