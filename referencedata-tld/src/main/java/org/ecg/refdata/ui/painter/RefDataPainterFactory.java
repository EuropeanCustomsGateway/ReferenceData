package org.ecg.refdata.ui.painter;

import com.cc.framework.ui.control.Control;
import com.cc.framework.ui.painter.ControlPainter;
import com.cc.framework.ui.painter.PainterContext;
import com.cc.framework.ui.painter.def.col.ColumnPainterFactory;
import com.cc.framework.ui.painter.html.HtmlPainterFactory;
import org.ecg.refdata.ui.control.RefDataTextControl;
import org.ecg.refdata.ui.model.impl.RefDataColumnTextDesignModelImpl;

import javax.servlet.jsp.JspWriter;
import java.io.IOException;

/**
 * Class override methods <code>doCreateHeaderIncludes</code> and
 * <code>doCreatePainter</code> from <code>HtmlPainterFactory</code>. The
 * <code>doCreateHeaderIncludes</code> method adds couple of significant
 * information to HTML header like location of css, javascripts.
 * 
 */
public class RefDataPainterFactory extends HtmlPainterFactory {

	private static final long serialVersionUID = 1L;

	private static final String LOCATION_CSS = "fw/refdata/style/refdata.css";
	private static final String LOCATION_JAVASCRIPT = "fw/refdata/jscript/refdata.js";
	private static final String LOCATION_PROTOTYPE = "fw/refdata/jscript/prototype-1.6.0.2.js";

	/**
	 * Constructor registers <code>RefDataColumnTextPainter</code> instead of
	 * standars CC ColumnPainterBase.
	 */
	public RefDataPainterFactory() {
		ColumnPainterFactory.registerColumnPainter(
				RefDataColumnTextDesignModelImpl.class,
				new RefDataColumnTextPainter());
	}

	/**
	 * 
	 * @see com.cc.framework.ui.painter.html.HtmlPainterFactory#doCreatePainter(com.cc.framework.ui.painter.PainterContext,
	 *      com.cc.framework.ui.control.Control)
	 */
	@Override
	protected ControlPainter doCreatePainter(PainterContext painterContext,
			Control ctrl) {
		if (ctrl instanceof RefDataTextControl) {
			return new RefDataTextPainter(painterContext,
					(RefDataTextControl) ctrl);
		}

		return null;
	}

	/**
	 * 
	 * @see com.cc.framework.ui.painter.html.HtmlPainterFactory#doCreateHeaderIncludes(javax.servlet.jsp.JspWriter)
	 */
	@Override
	protected void doCreateHeaderIncludes(JspWriter writer) {
		try {

			// CSS resources
			writer.print("\t<link rel='stylesheet' href='");
			writer.print(LOCATION_CSS);
			writer.println("' charset='ISO-8859-1' type='text/css'>");

			// JavaScript resources
			writer
					.print("\t<script type='text/javascript' language='JavaScript' src='");
			writer.print(LOCATION_JAVASCRIPT);
			writer.println("'></script>");
			writer
					.print("\t<script type='text/javascript' language='JavaScript' src='");
			writer.print(LOCATION_PROTOTYPE);
			writer.println("'></script>");

		} catch (IOException e) {
			throw new RuntimeException("Cannot create header includes.", e);
		}

	}

}
