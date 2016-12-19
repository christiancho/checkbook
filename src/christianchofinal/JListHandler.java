package christianchofinal;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JList;

public class JListHandler extends MouseAdapter implements MouseListener {

	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			JList list = (JList)e.getSource();
			MoneyCents.place = list.locationToIndex(e.getPoint());
			MoneyCents.tempTransaction.copyFrom(MoneyCents.transactions.get(MoneyCents.place));
			AddEditWindow tWindow = new AddEditWindow(MoneyCents.tempTransaction);
			tWindow.setVisible(true);
		}
	}
	
	public void mouseEntered(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	
}
