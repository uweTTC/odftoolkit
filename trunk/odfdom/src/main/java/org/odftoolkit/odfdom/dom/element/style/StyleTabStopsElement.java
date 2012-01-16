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
package org.odftoolkit.odfdom.dom.element.style;

import org.odftoolkit.odfdom.pkg.OdfElement;
import org.odftoolkit.odfdom.pkg.ElementVisitor;
import org.odftoolkit.odfdom.pkg.OdfFileDom;
import org.odftoolkit.odfdom.pkg.OdfName;
import org.odftoolkit.odfdom.dom.OdfDocumentNamespace;
import org.odftoolkit.odfdom.dom.DefaultElementVisitor;

/**
 * DOM implementation of OpenDocument element  {@odf.element style:tab-stops}.
 *
 */
public class StyleTabStopsElement extends OdfElement {

	public static final OdfName ELEMENT_NAME = OdfName.newName(OdfDocumentNamespace.STYLE, "tab-stops");

	/**
	 * Create the instance of <code>StyleTabStopsElement</code>
	 *
	 * @param  ownerDoc     The type is <code>OdfFileDom</code>
	 */
	public StyleTabStopsElement(OdfFileDom ownerDoc) {
		super(ownerDoc, ELEMENT_NAME);
	}

	/**
	 * Get the element name
	 *
	 * @return  return   <code>OdfName</code> the name of element {@odf.element style:tab-stops}.
	 */
	public OdfName getOdfName() {
		return ELEMENT_NAME;
	}

	/**
	 * Create child element {@odf.element style:tab-stop}.
	 *
	 * @param stylePositionValue  the <code>String</code> value of <code>StylePositionAttribute</code>, see {@odf.attribute  style:position} at specification
	 * @param styleTypeValue  the <code>String</code> value of <code>StyleTypeAttribute</code>, see {@odf.attribute  style:type} at specification
	 * Child element is new in Odf 1.2
	 *
	 * @return the element {@odf.element style:tab-stop}
	 */
	 public StyleTabStopElement newStyleTabStopElement(String stylePositionValue, String styleTypeValue) {
		StyleTabStopElement styleTabStop = ((OdfFileDom) this.ownerDocument).newOdfElement(StyleTabStopElement.class);
		styleTabStop.setStylePositionAttribute(stylePositionValue);
		styleTabStop.setStyleTypeAttribute(styleTypeValue);
		this.appendChild(styleTabStop);
		return styleTabStop;
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
}
