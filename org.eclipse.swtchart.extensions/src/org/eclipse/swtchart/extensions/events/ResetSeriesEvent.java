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
package org.eclipse.swtchart.extensions.events;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swtchart.extensions.core.BaseChart;

public class ResetSeriesEvent extends AbstractHandledEventProcessor implements IHandledEventProcessor {

	@Override
	public int getEvent() {

		return BaseChart.EVENT_MOUSE_DOUBLE_CLICK;
	}

	@Override
	public int getButton() {

		return BaseChart.BUTTON_LEFT;
	}

	@Override
	public int getStateMask() {

		return SWT.SHIFT;
	}

	@Override
	public void handleEvent(BaseChart baseChart, Event event) {

		baseChart.adjustRange(true);
		baseChart.fireUpdateCustomRangeSelectionHandlers(event);
		baseChart.redraw();
	}
}
