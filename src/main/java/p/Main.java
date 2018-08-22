package p;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDNonTerminalField;
import java.util.List;

public class Main {
	private static final Logger log = LoggerFactory.getLogger(Main.class);

	private static String FPayerName = "topmostSubform[0].CopyB[0].LeftColumn[0].f1_1[0]";
	private static String FPayerTin = "topmostSubform[0].CopyB[0].LeftColumn[0].f1_2[0]";
	private static String FRecipientTin = "topmostSubform[0].CopyB[0].LeftColumn[0].f1_3[0]";
	private static String FRecipientName = "topmostSubform[0].CopyB[0].LeftColumn[0].f1_4[0]";
	private static String FAddr1 = "topmostSubform[0].CopyB[0].LeftColumn[0].f1_5[0]";
	private static String FAddr2 = "topmostSubform[0].CopyB[0].LeftColumn[0].f1_6[0]";
	private static String FBox7 = "topmostSubform[0].CopyB[0].RightCol[0].f1_14[0]";

	public static void main(String[] args) throws Exception {
		try(PDDocument pdf = PDDocument.load(Main.class.getClassLoader().getResourceAsStream("f1099msc.pdf"))) {
			PDAcroForm form = pdf.getDocumentCatalog().getAcroForm();
			//show(form.getFields().get(0), "");
			String fname = "", part = "";
			for(PDField f : form.getFieldTree()) {
				fname += f.getPartialName();
				if(f instanceof PDNonTerminalField) {
					fname += ".";
				} else {
					log.info(": " + f.getValueAsString());
				}
			}
			/*form.getField(FPayerName).setValue("Mr. Bob\n55 Bob Ln.\nCity-State, GA, 30117\nUnited States\n404-555-5555");
			form.getField(FPayerTin).setValue("0000009");
			form.getField(FRecipientTin).setValue("01010102");
			form.getField(FRecipientName).setValue("The Receiver");
			form.getField(FAddr1).setValue("101 My Street");
			form.getField(FAddr2).setValue("Beverly Hills, CA, U.S., 90210");
			form.getField(FBox7).setValue("39.99");

			pdf.save("updated.pdf");
			*/
		}
	}

	private static void show(List<PDField> field, String fname) {
		for(PDField f : field) {
			fname += f.getPartialName();
			if(f instanceof PDNonTerminalField) {
				fname += ".";
				show(((PDNonTerminalField)f).getChildren(), fname);
			} else {
				log.info(fname + ": " + f.getValueAsString());
			}
		}
	}
}
