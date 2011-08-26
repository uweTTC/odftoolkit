/************************************************************************
 *
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER
 *
 * Copyright 2008, 2010 Oracle and/or its affiliates. All rights reserved.
 * Copyright 2010 IBM. All rights reserved.
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
package org.odftoolkit.odfdom.pkg;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;

import org.junit.Assert;
import org.junit.Test;
import org.odftoolkit.odfdom.pkg.OdfElement;
import org.odftoolkit.odfdom.pkg.OdfNamespace;
import org.odftoolkit.odfdom.doc.OdfDocument;
import org.odftoolkit.odfdom.doc.OdfSpreadsheetDocument;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.odftoolkit.odfdom.doc.OdfDocument.OdfMediaType;
import org.odftoolkit.odfdom.doc.table.OdfTable;
import org.odftoolkit.odfdom.dom.OdfContentDom;
import org.odftoolkit.odfdom.dom.OdfDocumentNamespace;
import org.odftoolkit.odfdom.dom.attribute.text.TextAnchorTypeAttribute;
import org.odftoolkit.odfdom.dom.element.draw.DrawObjectElement;
import org.odftoolkit.odfdom.dom.element.text.TextHElement;
import org.odftoolkit.odfdom.dom.element.text.TextPElement;
import org.odftoolkit.odfdom.incubator.doc.draw.OdfDrawFrame;
import org.odftoolkit.odfdom.incubator.doc.text.OdfTextSpan;
import org.odftoolkit.odfdom.pkg.manifest.OdfFileEntry;
import org.odftoolkit.odfdom.utils.ResourceUtilities;
import org.w3c.dom.NodeList;

public class EmbeddedDocumentTest {

	private static final Logger LOG = Logger.getLogger(EmbeddedDocumentTest.class.getName());
	private static final String TEST_FILE_FOLDER = ResourceUtilities.getTestOutputFolder();
	private static final String TEST_FILE_EMBEDDED = "Presentation1.odp";
	private static final String TEST_FILE_EMBEDDED_SAVE_OUT = "SaveEmbededDoc.odt";
	private static final String TEST_FILE_EMBEDDED_SIDEBYSIDE_SAVE_OUT = "SaveEmbededDocSideBySide.odt";
	private static final String TEST_FILE_EMBEDDED_INCLUDED_SAVE_OUT = "SaveEmbededDocIncluded.odt";
	private static final String TEST_FILE_REMOVE_EMBEDDED_SAVE_OUT = "RemoveEmbededDoc.odt";
	private static final String TEST_FILE_MODIFIED_EMBEDDED = "TestModifiedEmbeddedDoc.odt";
	private static final String TEST_FILE_MODIFIED_EMBEDDED_SAVE_STANDALONE = "SaveModifiedEmbeddedDocAlone.odt";
	private static final String TEST_SPAN_TEXT = "Modify Header";
	private static final String TEST_PIC = "testA.jpg";
	private static final String TEST_PIC_ANOTHER = "testB.jpg";
	private static final String SLASH = "/";

	/**
	 * The document A contains the embedded document E1, 
	 * this test case is used to embed E1 to another document B
	 */
	@Test
	public void testEmbedEmbeddedDocument() {
		try {
			OdfDocument doc = OdfDocument.loadDocument(ResourceUtilities.getTestResourceAsStream(TEST_FILE_EMBEDDED));
			OdfDocument saveDoc = OdfTextDocument.newTextDocument();
			List<OdfDocument> embeddedDocs = doc.getEmbeddedDocuments();
			List<String> embeddedDocNames = new ArrayList<String>();
			for (OdfDocument childDoc : embeddedDocs) {
				String embeddedDocName = childDoc.getDocumentPackagePath();
				saveDoc.insertDocument(childDoc, embeddedDocName);
				embeddedDocNames.add(embeddedDocName);
			}
			saveDoc.save(TEST_FILE_FOLDER + TEST_FILE_EMBEDDED_SAVE_OUT);
			saveDoc.close();
			saveDoc = OdfDocument.loadDocument(ResourceUtilities.getTestResourceAsStream(TEST_FILE_EMBEDDED_SAVE_OUT));
			List<OdfDocument> saveembeddedDocs = saveDoc.getEmbeddedDocuments();
			Assert.assertTrue(embeddedDocs.size() == saveembeddedDocs.size());
			for (int i = 0; i < embeddedDocs.size(); i++) {
				Assert.assertEquals(embeddedDocs.get(i).getMediaTypeString(), saveembeddedDocs.get(i).getMediaTypeString());
			}
		} catch (Exception ex) {
			LOG.log(Level.SEVERE, null, ex);
			Assert.fail("Failed to embed an embedded Document: '" + ex.getMessage() + "'");
		}
	}

	/**
	 * The document B is embedded to document A 
	 * and the directory path of A and B are absolute from the package
	 * DOCA/ and DOCB/
	 */
	@Test
	public void testembeddedDocumentsLocatedSideBySide() {
		try {
			OdfTextDocument odtDoc1 = OdfTextDocument.newTextDocument();
			odtDoc1.insertDocument(OdfTextDocument.newTextDocument(), "DOCA/");
			OdfDocument docA = odtDoc1.getEmbeddedDocument("DOCA");
			docA.newImage(ResourceUtilities.getURI(TEST_PIC));
			docA.insertDocument(OdfSpreadsheetDocument.newSpreadsheetDocument(), "DOCB/");
			OdfContentDom contentA = docA.getContentDom();
			XPath xpath = contentA.getXPath();
			TextPElement lastPara = (TextPElement) xpath.evaluate("//text:p[last()]", contentA, XPathConstants.NODE);
			addFrameForEmbeddedDoc(contentA, lastPara, "../DOCB");
			OdfDocument docB = odtDoc1.getEmbeddedDocument("DOCB/");
			Assert.assertNotNull(docB);
			Assert.assertNull(odtDoc1.getEmbeddedDocument("DOCA/DOCB/"));
			docB.newImage(ResourceUtilities.getURI(TEST_PIC_ANOTHER));
			OdfTable table1 = docB.getTableList().get(0);
			table1.setTableName("NewTable");
			updateFrameForEmbeddedDoc(contentA, "../DOCB", "./DOCB");
			//if user want to save the docA with the side by side embedded document
			//he has to insert it to the sub document of docA and update the xlink:href link
			docA.insertDocument(docB, "DOCA/DOCB/");
			//save
			docA.save(TEST_FILE_FOLDER + TEST_FILE_EMBEDDED_SIDEBYSIDE_SAVE_OUT);
			OdfDocument testLoad = OdfDocument.loadDocument(TEST_FILE_FOLDER + TEST_FILE_EMBEDDED_SIDEBYSIDE_SAVE_OUT);
			OdfFileEntry imageEntry = testLoad.getPackage().getFileEntry(OdfPackage.OdfFile.IMAGE_DIRECTORY.getPath() + SLASH + TEST_PIC);
			Assert.assertNotNull(imageEntry);
			List<OdfDocument> embDocs = testLoad.getEmbeddedDocuments(OdfDocument.OdfMediaType.SPREADSHEET);
			OdfDocument doc1 = embDocs.get(0);
			imageEntry = doc1.getPackage().getFileEntry(doc1.getDocumentPackagePath() + OdfPackage.OdfFile.IMAGE_DIRECTORY.getPath() + SLASH + TEST_PIC_ANOTHER);
			Assert.assertNotNull(doc1.getTableByName("NewTable"));

		} catch (Exception ex) {
			LOG.log(Level.SEVERE, null, ex);
			Assert.fail("Failed with " + ex.getClass().getName() + ": '" + ex.getMessage() + "'");
		}
	}

	/**
	 * The document B is embedded to document A 
	 * and the directory path of A and B are absolute from the package
	 * DOCA/ and DOCA/DOCB/
	 */
	@Test
	public void testembeddedDocumentWithSubPath() {
		try {
			OdfTextDocument odtDoc1 = OdfTextDocument.newTextDocument();
			odtDoc1.insertDocument(OdfTextDocument.newTextDocument(), "DOCA/");
			OdfDocument docA = odtDoc1.getEmbeddedDocument("DOCA");
			docA.newImage(ResourceUtilities.getURI(TEST_PIC));
			docA.insertDocument(OdfSpreadsheetDocument.newSpreadsheetDocument(), "DOCA/DOCB/");
			OdfContentDom contentA = docA.getContentDom();
			XPath xpath = contentA.getXPath();
			TextPElement lastPara = (TextPElement) xpath.evaluate("//text:p[last()]", contentA, XPathConstants.NODE);
			addFrameForEmbeddedDoc(contentA, lastPara, "./DOCB");
			OdfDocument docB = odtDoc1.getEmbeddedDocument("DOCA/DOCB/");
			docB.newImage(ResourceUtilities.getURI(TEST_PIC_ANOTHER));
			OdfTable table1 = docB.getTableList().get(0);
			table1.setTableName("NewTable");
			Assert.assertNotNull(docB);
			Assert.assertNull(odtDoc1.getEmbeddedDocument("DOCB/"));
			docA.save(TEST_FILE_FOLDER + TEST_FILE_EMBEDDED_INCLUDED_SAVE_OUT);
			OdfDocument testLoad = OdfDocument.loadDocument(TEST_FILE_FOLDER + TEST_FILE_EMBEDDED_INCLUDED_SAVE_OUT);
			OdfFileEntry imageEntry = testLoad.getPackage().getFileEntry(OdfPackage.OdfFile.IMAGE_DIRECTORY.getPath() + SLASH + TEST_PIC);
			Assert.assertNotNull(imageEntry);
			List<OdfDocument> embDocs = testLoad.getEmbeddedDocuments(OdfDocument.OdfMediaType.SPREADSHEET);
			OdfDocument doc1 = embDocs.get(0);
			imageEntry = doc1.getPackage().getFileEntry(doc1.getDocumentPackagePath() + OdfPackage.OdfFile.IMAGE_DIRECTORY.getPath() + SLASH + TEST_PIC_ANOTHER);
			Assert.assertNotNull(doc1.getTableByName("NewTable"));
		} catch (Exception ex) {
			LOG.log(Level.SEVERE, null, ex);
			Assert.fail("Failed with " + ex.getClass().getName() + ": '" + ex.getMessage() + "'");
		}
	}

	/**
	 * The document A contains the embedded document E1,
	 * This test case is used to show how to save the E1 to a stand alone document.
	 */
	@Test
	public void testSaveEmbeddedDocument() {
		try {
			OdfDocument doc = OdfDocument.loadDocument(ResourceUtilities.getTestResourceAsStream(TEST_FILE_EMBEDDED));
			List<OdfDocument> embeddedDocs = doc.getEmbeddedDocuments();
			for (OdfPackageDocument childPackageDoc : embeddedDocs) {
				OdfDocument childDoc = (OdfDocument) childPackageDoc;
				String embedFileName = childDoc.getDocumentPackagePath();

				OdfMediaType embedMediaType = OdfMediaType.getOdfMediaType(childDoc.getMediaTypeString());
				//use '_' replace '/', because '/' is not the valid char in file path
				embedFileName = embedFileName.replaceAll("/", "_") + "." + embedMediaType.getSuffix();
				childDoc.save(TEST_FILE_FOLDER + embedFileName);
				LOG.log(Level.INFO, "Save file : {0}", embedFileName);
				OdfDocument embeddedDoc = OdfDocument.loadDocument(ResourceUtilities.getTestResourceAsStream(embedFileName));
				Assert.assertEquals(embeddedDoc.getMediaTypeString(), embedMediaType.getMediaTypeString());
			}
		} catch (Exception ex) {
			LOG.log(Level.SEVERE, null, ex);
			Assert.fail("Failed with " + ex.getClass().getName() + ": '" + ex.getMessage() + "'");
		}

	}

	/**
	 * There are two document, one is Presentation1.odp 
	 * another is a new text document TestModifiedEmbeddedDoc.odt
	 * Presentation1.odp contains an embed document named "Object 1/", add one paragraph to Object 1
	 * then embed "Object 1" to the new text document, and save this text document
	 * reload TestModifiedEmbeddedDoc.odt, then get and modify embed document "DocA" and save it to a standalone document
	 * load the saved standalone document, and check the content of it
	 */
	@Test
	public void testEmbedModifiedEmbeddedDocument() {
		try {
			OdfDocument doc = OdfDocument.loadDocument(ResourceUtilities.getTestResourceAsStream(TEST_FILE_EMBEDDED));
			OdfDocument saveDoc = OdfTextDocument.newTextDocument();
			OdfDocument embeddedDoc = doc.getEmbeddedDocument("Object 1/");
			//modify content of "Object 1"
			OdfContentDom embedContentDom = embeddedDoc.getContentDom();
			XPath xpath = embedContentDom.getXPath();
			TextHElement header = (TextHElement) xpath.evaluate("//text:h[1]", embedContentDom, XPathConstants.NODE);
			LOG.log(Level.INFO, "First para: {0}", header.getTextContent());
			OdfTextSpan spanElem = new OdfTextSpan(embedContentDom);
			spanElem.setTextContent(TEST_SPAN_TEXT);
			header.appendChild(spanElem);
			//insert image to "Object 1"
			embeddedDoc.newImage(ResourceUtilities.getURI(TEST_PIC));
			//embed "Object 1" to TestModifiedEmbeddedDoc.odt as the path /DocA
			String embedPath = "DocA";
			saveDoc.insertDocument(embeddedDoc, embedPath);
			saveDoc.save(TEST_FILE_FOLDER + TEST_FILE_MODIFIED_EMBEDDED);
			saveDoc.close();
			//reload TestModifiedEmbeddedDoc.odt
			saveDoc = OdfDocument.loadDocument(ResourceUtilities.getTestResourceAsStream(TEST_FILE_MODIFIED_EMBEDDED));
			embeddedDoc = saveDoc.getEmbeddedDocument(embedPath);
			//check the content of "DocA" and modify it again
			embedContentDom = embeddedDoc.getContentDom();
			header = (TextHElement) xpath.evaluate("//text:h[1]", embedContentDom, XPathConstants.NODE);
			Assert.assertTrue(header.getTextContent().contains(TEST_SPAN_TEXT));
			header.setTextContent("");
			OdfFileEntry imageEntry = embeddedDoc.getPackage().getFileEntry(embeddedDoc.getDocumentPackagePath() + OdfPackage.OdfFile.IMAGE_DIRECTORY.getPath() + SLASH + TEST_PIC);
			Assert.assertNotNull(imageEntry);
			embeddedDoc.newImage(ResourceUtilities.getURI(TEST_PIC_ANOTHER));
			//save the "DocA" as the standalone document
			embeddedDoc.save(TEST_FILE_FOLDER + TEST_FILE_MODIFIED_EMBEDDED_SAVE_STANDALONE);
			//load the standalone document and check the content
			OdfDocument standaloneDoc = OdfDocument.loadDocument(ResourceUtilities.getTestResourceAsStream(TEST_FILE_MODIFIED_EMBEDDED_SAVE_STANDALONE));
			embedContentDom = standaloneDoc.getContentDom();
			header = (TextHElement) xpath.evaluate("//text:h[1]", embedContentDom, XPathConstants.NODE);
			Assert.assertTrue(header.getTextContent().length() == 0);
			imageEntry = standaloneDoc.getPackage().getFileEntry(OdfPackage.OdfFile.IMAGE_DIRECTORY.getPath() + SLASH + TEST_PIC);
			Assert.assertNotNull(imageEntry);
			OdfFileEntry anotherImageEntry = standaloneDoc.getPackage().getFileEntry(OdfPackage.OdfFile.IMAGE_DIRECTORY.getPath() + SLASH + TEST_PIC_ANOTHER);
			Assert.assertNotNull(anotherImageEntry);
		} catch (Exception ex) {
			LOG.log(Level.SEVERE, null, ex);
			Assert.fail("Failed with " + ex.getClass().getName() + ": '" + ex.getMessage() + "'");
		}
	}

	@Test
	public void testRemoveEmbeddedDocument() {
		try {
			OdfDocument doc = OdfDocument.loadDocument(ResourceUtilities.getTestResourceAsStream(TEST_FILE_EMBEDDED));
			List<OdfDocument> embeddedDocs = doc.getEmbeddedDocuments();
			List<String> embeddedDocNames = new ArrayList<String>();
			for (OdfPackageDocument childPackageDoc : embeddedDocs) {
				OdfDocument childDoc = (OdfDocument) childPackageDoc;
				String embedFileName = childDoc.getDocumentPackagePath();
				embeddedDocNames.add(embedFileName);
				childDoc.removeEmbeddedDocument(embedFileName);
			}
			doc.save(TEST_FILE_FOLDER + TEST_FILE_REMOVE_EMBEDDED_SAVE_OUT);
			doc.close();
			//check manifest entry for the embed document 
			//the sub entry of the embed document such as the pictures of the document should also be removed
			doc = OdfDocument.loadDocument(ResourceUtilities.getTestResourceAsStream(TEST_FILE_REMOVE_EMBEDDED_SAVE_OUT));
			List<OdfDocument> saveembeddedDocs = doc.getEmbeddedDocuments();
			Assert.assertTrue(0 == saveembeddedDocs.size());
			Set<String> entries = doc.getPackage().getFileEntries();
			Iterator<String> entryIter = null;
			for (int i = 0; i < embeddedDocNames.size(); i++) {
				entryIter = entries.iterator();
				String embeddedDocPath = embeddedDocNames.get(i);
				while (entryIter.hasNext()) {
					String entry = entryIter.next();
					Assert.assertFalse(entry.startsWith(embeddedDocPath));
				}
			}
			doc.close();
		} catch (Exception ex) {
			LOG.log(Level.SEVERE, null, ex);
			Assert.fail("Failed with " + ex.getClass().getName() + ": '" + ex.getMessage() + "'");
		}
	}

	private void addFrameForEmbeddedDoc(OdfFileDom dom, TextPElement para, String path) throws Exception {
		OdfDrawFrame drawFrame = new OdfDrawFrame(dom);
		drawFrame.setDrawNameAttribute(path);
		drawFrame.setTextAnchorTypeAttribute(TextAnchorTypeAttribute.Value.PARAGRAPH.toString());
		drawFrame.setSvgXAttribute("0.834cm");
		drawFrame.setSvgYAttribute("2.919cm");
		drawFrame.setSvgWidthAttribute("13.257cm");
		drawFrame.setSvgHeightAttribute("11.375cm");
		drawFrame.setDrawZIndexAttribute(0);

		DrawObjectElement object = new DrawObjectElement(dom);

		object.setXlinkHrefAttribute(path);
		object.setXlinkActuateAttribute("onLoad");
		object.setXlinkShowAttribute("embed");
		object.setXlinkTypeAttribute("simple");
		drawFrame.appendChild(object);
		para.appendChild(drawFrame);
	}

	private void updateFrameForEmbeddedDoc(OdfFileDom dom, String originPath, String newPath) throws Exception {
		NodeList objNodes = dom.getElementsByTagNameNS(OdfDocumentNamespace.DRAW.getUri(), "object");
		for (int i = 0; i < objNodes.getLength(); i++) {
			OdfElement object = (OdfElement) objNodes.item(i);
			String refObjPath = object.getAttributeNS(OdfDocumentNamespace.XLINK.getUri(), "href");
			if (refObjPath.equals(originPath)) {
				object.setAttributeNS(OdfDocumentNamespace.XLINK.getUri(), "href", newPath);
			}
		}
	}
}
