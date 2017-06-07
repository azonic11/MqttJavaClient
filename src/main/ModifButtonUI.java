package main;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.AbstractButton;
import javax.swing.plaf.metal.MetalButtonUI;


	class ModifButtonUI extends MetalButtonUI {

		@Override
		public void paintButtonPressed(Graphics g, AbstractButton b) {
			//paintText(g, b, b.getBounds(), "ggggggg");
			g.setColor(Color.DARK_GRAY.brighter());
			g.fillRect(0, 0, b.getSize().width, b.getSize().height);

		}
	
}
