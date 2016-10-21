package org.ecg.refdata.ui.painter;

import com.cc.framework.ui.painter.def.CellPainterContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.ecs.ConcreteElement;
import org.apache.ecs.Entities;
import org.apache.ecs.html.Div;
import org.apache.ecs.html.TD;
import org.apache.ecs.html.TR;
import org.apache.ecs.html.Table;

import com.cc.framework.ui.control.LineIterator;
import com.cc.framework.ui.model.ColumnDesignModel;
import com.cc.framework.ui.painter.PainterContext;
import com.cc.framework.ui.painter.def.col.ColumnPainterBase;
import org.ecg.refdata.ui.model.RefDataColumnTextDesignModel;

/**
 * Class override <code>paintCell</code> method from CC
 * <code>ColumnPainterBase</code> to create text field with magnifying glass on
 * the right side.
 * 
 */
public class RefDataColumnTextPainter extends ColumnPainterBase {



	/**
	 * 
	 * @see com.cc.framework.ui.painter.def.col.ColumnPainterBase#paintCell(org.apache.ecs.html.TD,
	 *      com.cc.framework.ui.painter.PainterContext,
	 *      com.cc.framework.ui.model.ColumnDesignModel,
	 *      com.cc.framework.ui.control.LineIterator)
	 */

            @Override
            public void paintCell(TD cellElement, CellPainterContext ctx) throws Exception {
                LineIterator iter = ctx.getLineIterator();
                ColumnDesignModel rawcolumn = ctx.getColumn();
//        @Override
//	public void paintCell(TD cellElement, PainterContext ctx, ColumnDesignModel rawcolumn, LineIterator iter) {

		RefDataColumnTextDesignModel column = (RefDataColumnTextDesignModel) rawcolumn;

		String cellValue = String.valueOf(iter.current(column.getProperty()));

		ConcreteElement textCell = null;
		if (cellValue != null) {
			textCell = new Div(ctx.html(cellValue, column.filter(), column
					.getMaxLength()));
		} else {
			textCell = new Div(Entities.NBSP);
		}

		applyStyle(textCell, column);

		HttpServletRequest servletRequest = (HttpServletRequest) ctx
				.getPageContext().getRequest();

		Table table = new Table();
		table.setCellSpacing("0");
		table.setCellPadding("0");
		TR tr = new TR();
		table.addElement(tr);
		tr.addElement(new TD(textCell));
		tr.addElement(new TD(LoupeCreator.createButton(ctx.getPageContext()
				.getServletContext(), servletRequest, column, cellValue)));

		cellElement.addElement(table);
	}

}
