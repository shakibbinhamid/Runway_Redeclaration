package io;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class Print {

	private Print(){}

	public static void print(String calculations){

		PrinterJob job = PrinterJob.getPrinterJob();

		job.setPrintable(new PrintObject1(calculations));

		if (job.printDialog()) {

			try {
				job.print();
			} catch (PrinterException e) {
				System.out.println(e);
			}
		}
	}
}

class PrintObject1 implements Printable {

	private String calculation;
	private static final String DELIM = "\n";
	private int[] pageBreaks;
	private String[] textLines;

	PrintObject1(String calculations) {
		calculation = calculations;
	}

	private void initTextLines() {
		if (textLines == null) {
			textLines = calculation.split(DELIM);
		}
	}

	public int print(Graphics g, PageFormat pf, int pageIndex) {

		Font font = new Font("verdana", Font.PLAIN, 10);
		FontMetrics metrics = g.getFontMetrics(font);
		int lineHeight = metrics.getHeight();

		if (pageBreaks == null) {
			initTextLines();
			int linesPerPage = (int)(pf.getImageableHeight()/lineHeight);
			int numBreaks = (textLines.length-1)/linesPerPage;
			pageBreaks = new int[numBreaks];
			for (int b=0; b<numBreaks; b++) {
				pageBreaks[b] = (b+1)*linesPerPage; 
			}
		}

		if (pageIndex > pageBreaks.length) {
			return NO_SUCH_PAGE;
		}

		Graphics2D g2d = (Graphics2D)g;
		g2d.translate(pf.getImageableX(), pf.getImageableY());

		int y = 0; 
		int start = (pageIndex == 0) ? 0 : pageBreaks[pageIndex-1];
		int end   = (pageIndex == pageBreaks.length)
				? textLines.length : pageBreaks[pageIndex];
		for (int line=start; line<end; line++) {
			y += lineHeight;
			g.drawString(textLines[line], 0, y);
		}

		return PAGE_EXISTS;
	}
}
