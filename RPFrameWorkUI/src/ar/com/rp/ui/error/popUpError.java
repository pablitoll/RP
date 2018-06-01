package ar.com.rp.ui.error;

import java.awt.Component;
import java.awt.Dialog;

import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.window.WebPopOver;
import com.alee.laf.label.WebLabel;

import ar.com.rp.ui.common.Common;

public class popUpError {

	public static void showError(Component component, String mensaje, Dialog owner) {

		WebPopOver pop = new WebPopOver(owner);
		pop.setCloseOnFocusLoss(true);
		pop.setFont(Common.getStandarFont());
		pop.setMargin(10);
		pop.setLayout(new VerticalFlowLayout());
		WebLabel msg = new WebLabel(mensaje);// , Common.loadIcon(ConstantesRP.IMG_ERROR));
		msg.setFont(Common.getStandarFont());
		pop.add(msg);
		//component.requestFocus();
		pop.show(component);
	}
}