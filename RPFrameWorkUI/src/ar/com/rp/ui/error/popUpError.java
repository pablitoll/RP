package ar.com.rp.ui.error;

import java.awt.Component;

import com.alee.laf.optionpane.WebOptionPane;

public class popUpError {

	public static void showError(Component component, String mensaje) {

//		final WebPopOver pop = new WebPopOver(owner);
//		pop.setCloseOnFocusLoss(true);		
//		pop.setFont(Common.getStandarFont());
//		pop.setMargin(10);
//		pop.setLayout(new VerticalFlowLayout());
//		final WebLabel msg = new WebLabel(mensaje);// , Common.loadIcon(ConstantesRP.IMG_ERROR));
//		msg.setFont(Common.getStandarFont());
//		pop.add(msg);
//		component.requestFocus();
//		pop.show(component);
		
		WebOptionPane.showMessageDialog(component, mensaje, "Error", WebOptionPane.ERROR_MESSAGE);
		component.requestFocus();
	}
}