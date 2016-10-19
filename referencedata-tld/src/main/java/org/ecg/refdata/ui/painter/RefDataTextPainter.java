package org.ecg.refdata.ui.painter;

import com.cc.framework.ui.painter.PainterContext;
import com.cc.framework.ui.painter.def.DefResources;
import com.cc.framework.ui.painter.html.HtmlTextPainter;
import org.ecg.refdata.ui.config.RefdataExtraAction;
import org.ecg.refdata.ui.config.RefdataExtraActionConfig;
import org.ecg.refdata.ui.config.RefdataExtraActionConfigHolder;
import org.ecg.refdata.ui.control.RefDataTextControl;
import org.ecg.refdata.ui.model.RefDataTextDesignModel;
import org.apache.ecs.AlignType;
import org.apache.ecs.ConcreteElement;
import org.apache.ecs.html.IMG;
import org.apache.ecs.html.TD;
import org.apache.ecs.html.TR;
import org.apache.ecs.html.Table;

import javax.servlet.http.HttpServletRequest;

/**
 * Method overrides <code>doCreateElement</code> to decorate
 * a HTML element from <code>HtmlTextPainter</code>.
 * Modes of button (magnifying glass) depends on
 * <code>RefDataFormEditable</code> flag.
 * 
 */
public class RefDataTextPainter extends HtmlTextPainter {

    /**
     * Constructor takes the same parameters as <code>HtmlTextPainter</code>.
     *
     * @param painterContext context of painter
     * @param textControl RefDataTextControl instance
     */
    public RefDataTextPainter(PainterContext painterContext, RefDataTextControl textControl) {
        super(painterContext, textControl);
    }

    /**
     * @see com.cc.framework.ui.painter.html.HtmlTextPainter#doCreateElement()
     */
    @Override
    protected ConcreteElement doCreateElement() {
        RefDataTextDesignModel designModel = ((RefDataTextControl) getCtrl()).getRefDataDesignModel();
        // base on RefDataFormEditable call doCreateElement or doCreateDisplayElement from ancestor
        if (designModel.getRefDataFormEditable()) {
            return decorate(super.doCreateElement());
        } else {
            return decorate(super.doCreateDisplayElement());
        }
        }

    /**
     * Contains proper implementation of decorator.
     */
    private ConcreteElement decorate(ConcreteElement body) {
        RefDataTextDesignModel designModel = ((RefDataTextControl) getCtrl()).getRefDataDesignModel();
        HttpServletRequest servletRequest = (HttpServletRequest) getPageContext().getRequest();

        Table table = new Table();
        table.setCellSpacing("0");
        table.setCellPadding("0");
        TR tr = new TR();
        table.addElement(tr);
        tr.addElement(new TD(body));
        if (designModel.getRefDataFormEditable()) {
            tr.addElement(new TD(LoupeCreator.createButton(getPageContext().getServletContext(), servletRequest, designModel, null)));
            if (designModel.isCustomRequired()) {
                // standard functionality is disabled so add indicator of requirement
                IMG img = createImage(DefResources.IMAGE_REQUIRED);
                img.setVspace(0);
                img.setAlign(AlignType.absmiddle);
                img.setStyle("margin-left:3px;");
                img.setTitle(getFrameworkString(DefResources.FW_FORM_REQUIRED));
                tr.addElement(new TD(img));
            }

        } else {
            tr.addElement(new TD(LoupeCreator.createButton(getPageContext().getServletContext(), servletRequest, designModel, this.getValue())));
        }
        //add image with onclick after looup (ex. taryfa celna w import declaration)
        if (designModel.getImageOnClick() != null){
            IMG img = new IMG();
            img.setSrc(designModel.getImageOnClick());
            img.setVspace(0);
            img.setAlign(AlignType.absmiddle);
            img.setStyle("margin-left:3px;cursor:pointer");
            img.setOnClick(designModel.getJavascriptImageOnClick());
            img.setTitle("Taryfa celna");
            tr.addElement(new TD(img));
        }


		createExtraActions(tr,body,designModel,servletRequest);


        return table;
    }

	private void createExtraActions(TR tr, ConcreteElement body, RefDataTextDesignModel designModel, HttpServletRequest servletRequest) {

		RefdataExtraActionConfig config = RefdataExtraActionConfigHolder.getInstance().getExtraActionConfig();

		if(config!=null){
			RefdataExtraAction[] actions = config.getExtraActionsForField(designModel,getValue());

			if(actions!=null){

				for(RefdataExtraAction action: actions){


                    IMG img = new IMG();
					img.setStyle("margin-left:3px;cursor:pointer");
					if(action.getActionJS()!=null){
						img.setOnClick(action.getActionJS());
					}

					if(action.getEnabledJS()!=null){
						body.addAttribute("onChange",action.getEnabledJS());
					}

                    if(action.getIcon()!=null){
	                    img.setSrc(action.getIcon());
                    }
					if(action.getTooltip()!=null){
						img.setTitle(action.getTooltip());
					}


				 	tr.addElement(new TD(img));


				}

			}
		}




	}
}
