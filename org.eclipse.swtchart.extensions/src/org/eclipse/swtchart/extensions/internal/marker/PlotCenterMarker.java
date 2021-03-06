/*******************************************************************************
 * Copyright (c) 2017, 2018 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtchart.extensions.internal.marker;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swtchart.extensions.core.BaseChart;
import org.eclipse.swtchart.extensions.marker.AbstractBaseChartPaintListener;
import org.eclipse.swtchart.extensions.marker.IBaseChartPaintListener;

public class PlotCenterMarker extends AbstractBaseChartPaintListener implements IBaseChartPaintListener {

	public PlotCenterMarker(BaseChart baseChart) {
		super(baseChart);
	}

	@Override
	public void paintControl(PaintEvent e) {

		if(isDraw()) {
			int currentLineStyle = e.gc.getLineStyle();
			e.gc.setForeground(getForegroundColor());
			e.gc.setLineStyle(SWT.LINE_DASHDOT);
			int width = e.width / 2;
			e.gc.drawLine(width, 0, width, e.height);
			e.gc.setLineStyle(currentLineStyle);
		}
	}
}
