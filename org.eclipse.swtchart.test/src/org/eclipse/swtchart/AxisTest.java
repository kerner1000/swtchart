/*******************************************************************************
 * Copyright (c) 2008, 2019 SWTChart project.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * yoshitaka - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtchart;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swtchart.IAxis.Position;
import org.eclipse.swtchart.ISeries.SeriesType;
import org.eclipse.swtchart.util.ChartTestCase;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test case for axis.
 */
public class AxisTest extends ChartTestCase {

	private IAxis xAxis;
	private IAxis yAxis;

	private static final String[] categorySeries = { "a", "b", "c", "d", "e" };
	private static final double[] series = { 0.2, 0.4, 0.6, 0.8 };
	private static final double[] xSeries1 = { 1, 2, 3, 4, 5 };
	private static final double[] xSeries2 = { -1, 2, 3, 4, 5 };
	private static final double[] ySeries1 = { 0.1, 0.2, 0.3, 0.4, 0.5 };
	private static final double[] ySeries2 = { -0.2, -0.1, 0, 0.1, 0.2 };
	private static final double[] ySeries3 = { 4.3, 4.3, 4.3, 4.3, 4.3 };
	private static final double[] ySeries4 = { 0, 0, 0, 0, 0 };
	private static final double[] ySeries5 = { -1, -2, -3, -4, -5 };

	@Override
	public void setUp() throws Exception {
		super.setUp();
		xAxis = chart.getAxisSet().getXAxis(0);
		yAxis = chart.getAxisSet().getYAxis(0);
	}

	/**
	 * Test for axis position.
	 */
    @Test
	public void testPosition() throws Exception {
		ISeries barSeries = chart.getSeriesSet().createSeries(SeriesType.BAR, "bar series");
		barSeries.setYSeries(ySeries1);
		chart.getAxisSet().adjustRange();

		// check the default position
		Position position = xAxis.getPosition();
		assertEquals(Position.Primary, position);
		position = yAxis.getPosition();
		assertEquals(Position.Primary, position);
		showChart();

		// set X Axis to secondary position
		xAxis.setPosition(Position.Secondary);
		position = xAxis.getPosition();
		assertEquals(Position.Secondary, position);
		showChart();

		// set X Axis to primary position
		xAxis.setPosition(Position.Primary);
		position = xAxis.getPosition();
		assertEquals(Position.Primary, position);
		showChart();

		// set Y Axis to secondary position
		chart.setOrientation(SWT.HORIZONTAL);
		yAxis.setPosition(Position.Secondary);
		position = yAxis.getPosition();
		assertEquals(Position.Secondary, position);
		showChart();

		// set Y Axis to primary position
		yAxis.setPosition(Position.Primary);
		position = yAxis.getPosition();
		assertEquals(Position.Primary, position);
		showChart();

		// set chart to vertical orientation
		chart.setOrientation(SWT.VERTICAL);
		showChart();
		xAxis.setPosition(Position.Secondary);

		// set X Axis to secondary position in vertical orientation
		position = xAxis.getPosition();
		assertEquals(Position.Secondary, position);
		showChart();
		
		// set X Axis to primary position in vertical orientation
		xAxis.setPosition(Position.Primary);
		position = xAxis.getPosition();
		assertEquals(Position.Primary, position);
		showChart();

		// set Y Axis to secondary position in vertical orientation
		yAxis.setPosition(Position.Secondary);
		position = yAxis.getPosition();
		assertEquals(Position.Secondary, position);
		showChart();
		
		// set Y Axis to primary position in vertical orientation
		yAxis.setPosition(Position.Primary);
		position = yAxis.getPosition();
		assertEquals(Position.Primary, position);
		showChart();

		// set X Axis to illegal position
		try {
			xAxis.setPosition(null);
			fail();
		} catch (IllegalArgumentException e) {
			// expected to reach here
		}

		// set Y Axis to illegal position
		try {
			yAxis.setPosition(null);
			fail();
		} catch (IllegalArgumentException e) {
			// expected to reach here
		}
	}

	/**
	 * Test for axis range.
	 */
    @Test
	public void testRange() throws Exception {
		ISeries barSeries = chart.getSeriesSet().createSeries(SeriesType.BAR, "bar series");
		barSeries.setXSeries(xSeries1);
		barSeries.setYSeries(ySeries1);
		chart.getAxisSet().adjustRange();

		// specify null
		try {
			xAxis.setRange(null);
			fail();
		} catch (IllegalArgumentException e) {
			// expected to reach here
		}

		// specify illegal range (lower > upper)
		Range range = new Range(1, 2);
		range.lower = 3;
		try {
			xAxis.setRange(range);
			fail();
		} catch (IllegalArgumentException e) {
			// expected to reach here
		}

		// specify illegal range (lower = upper) for non-category axis
		try {
			xAxis.setRange(new Range(3.3, 3.3));
			fail();
		} catch (IllegalArgumentException e) {
			// expected to reach here
		}

		// set range for non-category axis
		showChart();
		xAxis.setRange(new Range(-1, 7));
		assertEquals(-1d, xAxis.getRange().lower, 0.1);
		assertEquals(7d, xAxis.getRange().upper, 0.1);
		showChart();

		// set range for log scale
		xAxis.setRange(new Range(0.1, 20));
		xAxis.enableLogScale(true);
		assertEquals(0.1, xAxis.getRange().lower, 0.1);
		assertEquals(20d, xAxis.getRange().upper, 0.1);
		showChart();
		xAxis.setRange(new Range(-10, 200));
		assertEquals(0.1, xAxis.getRange().lower, 0.1);
		assertEquals(200d, xAxis.getRange().upper, 0.1);
		showChart();

		// set range for category axis
		xAxis.setCategorySeries(categorySeries);
		xAxis.enableCategory(true);
		showChart();
		xAxis.setRange(new Range(1, 3));
		assertEquals(1d, xAxis.getRange().lower, 0.1);
		assertEquals(3d, xAxis.getRange().upper, 0.1);
		showChart();
		xAxis.setRange(new Range(2, 8));
		assertEquals(2d, xAxis.getRange().lower, 0.1);
		assertEquals(4d, xAxis.getRange().upper, 0.1);
		showChart();
		xAxis.setRange(new Range(-3.2, 2.6));
		assertEquals(0d, xAxis.getRange().lower, 0.1);
		assertEquals(2d, xAxis.getRange().upper, 0.1);
		showChart();
		xAxis.setRange(new Range(2.1, 2.8));
		assertEquals(2d, xAxis.getRange().lower, 0.1);
		assertEquals(2d, xAxis.getRange().upper, 0.1);
		showChart();
	}

	/**
	 * Test for log scale.
	 */
    @Test
	public void testLogScale() throws Exception {

		// check the default
		assertFalse(xAxis.isLogScaleEnabled());
		assertFalse(yAxis.isLogScaleEnabled());

		// series contain negative value
		ISeries lineSeries = chart.getSeriesSet().createSeries(SeriesType.LINE, "line series");
		lineSeries.setXSeries(xSeries2);
		lineSeries.setYSeries(ySeries2);
		xAxis.enableLogScale(false);
		yAxis.enableLogScale(false);
		
		try {
			xAxis.enableLogScale(true);
			fail();
		} catch (IllegalStateException e) {
			// expected to reach here
		}
		
		try {
			yAxis.enableLogScale(true);
			fail();
		} catch (IllegalStateException e) {
			// expected to reach here
		}
		chart.getSeriesSet().deleteSeries(lineSeries.getId());

		// enable log scale without series
		showChart();
		xAxis.setRange(new Range(-1, 10));
		xAxis.enableLogScale(true);
		assertTrue(xAxis.isLogScaleEnabled());
		assertEquals(0.1, xAxis.getRange().lower, 0.1);
		showChart();
		yAxis.setRange(new Range(-1, 10));
		yAxis.enableLogScale(true);
		assertTrue(yAxis.isLogScaleEnabled());
		assertEquals(0.1, yAxis.getRange().lower, 0.1);
		showChart();

		// enable log scale for the axis whose range is negative
		lineSeries = chart.getSeriesSet().createSeries(SeriesType.LINE, "line series");
		lineSeries.setXSeries(xSeries1);
		lineSeries.setYSeries(ySeries1);
		chart.getAxisSet().adjustRange();
		xAxis.enableLogScale(false);
		yAxis.enableLogScale(false);
		showChart();
		xAxis.setRange(new Range(-1, 10));
		xAxis.enableLogScale(true);
		assertTrue(xAxis.isLogScaleEnabled());
		assertEquals(1d, xAxis.getRange().lower, 0.1);
		showChart();
		yAxis.setRange(new Range(-1, 10));
		yAxis.enableLogScale(true);
		assertTrue(yAxis.isLogScaleEnabled());
		assertEquals(0.1, yAxis.getRange().lower, 0.1);
		showChart();

		// enable log scale for category axis
		xAxis.enableLogScale(false);
		xAxis.setCategorySeries(categorySeries);
		xAxis.enableCategory(true);
		xAxis.setRange(new Range(0, 4));
		showChart();
		xAxis.enableLogScale(true);
		xAxis.setRange(new Range(0, 10));
		assertFalse(xAxis.isCategoryEnabled());
		assertTrue(xAxis.isLogScaleEnabled());
		showChart();
	}

	/**
	 * Test for adjusting range.
	 */
    @Test
	public void testAdjustRange() throws Exception {

		// line
		ISeries lineSeries = chart.getSeriesSet().createSeries(SeriesType.LINE, "line series");
		lineSeries.setYSeries(ySeries2);
		xAxis.setRange(new Range(0, 4));
		yAxis.setRange(new Range(-0.2, 0.2));
		showChart();
		xAxis.adjustRange();
		showChart();
		Range xRange = xAxis.getRange();
		assertEquals(-0.21, xRange.lower, 0.1);
		assertEquals(4.21, xRange.upper, 0.1);
		showChart();
		yAxis.adjustRange();
		showChart();
		Range yRange = yAxis.getRange();
		assertEquals(-0.22, yRange.lower, 0.01);
		assertEquals(0.22, yRange.upper, 0.01);
		showChart();
		yAxis.adjustRange();
		showChart();

		// line + constant series
		lineSeries.setYSeries(ySeries3);
		yAxis.setRange(new Range(2.7, 5.8));
		showChart();
		yAxis.adjustRange();
		showChart();
		yRange = yAxis.getRange();
		assertEquals(2.15, yRange.lower, 0.01);
		assertEquals(6.45, yRange.upper, 0.01);

		// line + negative series
		lineSeries.setYSeries(ySeries5);
		yAxis.setRange(new Range(-5, -1));
		showChart();
		yAxis.adjustRange();
		showChart();
		yRange = yAxis.getRange();
		assertEquals(-5.18, yRange.lower, 0.1);
		assertEquals(-0.82, yRange.upper, 0.1);

		// line + vertical orientation
		lineSeries.setYSeries(ySeries2);
		chart.setOrientation(SWT.VERTICAL);
		xAxis.setRange(new Range(0, 4));
		yAxis.setRange(new Range(-0.2, 0.2));
		showChart();
		xAxis.adjustRange();
		xRange = xAxis.getRange();
		assertEquals(-0.21, xRange.lower, 0.1);
		assertEquals(4.21, xRange.upper, 0.1);
		yAxis.adjustRange();
		yRange = yAxis.getRange();
		assertEquals(-0.22, yRange.lower, 0.01);
		assertEquals(0.22, yRange.upper, 0.01);
		showChart();

		// line + constant series + vertical orientation
		lineSeries.setYSeries(ySeries3);
		yAxis.adjustRange();
		yRange = yAxis.getRange();
		assertEquals(2.15, yRange.lower, 0.01);
		assertEquals(6.45, yRange.upper, 0.01);

		// bar
		chart.setOrientation(SWT.HORIZONTAL);
		chart.getSeriesSet().deleteSeries("line series");
		ISeries barSeries = chart.getSeriesSet().createSeries(SeriesType.BAR, "bar series");
		barSeries.setYSeries(ySeries1);
		xAxis.setRange(new Range(0, 4));
		yAxis.setRange(new Range(0.1, 0.5));
		showChart();
		xAxis.adjustRange();
		xRange = xAxis.getRange();
		assertEquals(-0.51, xRange.lower, 0.1);
		assertEquals(4.53, xRange.upper, 0.1);
		yAxis.adjustRange();
		yRange = yAxis.getRange();
		assertEquals(0d, yRange.lower, 0.01);
		assertEquals(0.51, yRange.upper, 0.01);
		showChart();

		// bar + negative series
		barSeries.setYSeries(ySeries2);
		xAxis.setRange(new Range(0, 4));
		yAxis.setRange(new Range(-0.2, 0.2));
		showChart();
		xAxis.adjustRange();
		xRange = xAxis.getRange();
		assertEquals(-0.51, xRange.lower, 0.1);
		assertEquals(4.53, xRange.upper, 0.1);
		yAxis.adjustRange();
		yRange = yAxis.getRange();
		assertEquals(-0.21, yRange.lower, 0.01);
		assertEquals(0.21, yRange.upper, 0.01);
		showChart();

		// bar + category
		barSeries.setYSeries(ySeries1);
		xAxis.setCategorySeries(categorySeries);
		xAxis.enableCategory(true);
		xAxis.setRange(new Range(0, 4));
		yAxis.setRange(new Range(0.1, 0.5));
		showChart();
		xAxis.adjustRange();
		xRange = xAxis.getRange();
		assertEquals(0d, xRange.lower, 0.01);
		assertEquals(4d, xRange.upper, 0.01);
		yAxis.adjustRange();
		yRange = yAxis.getRange();
		assertEquals(0d, yRange.lower, 0.01);
		assertEquals(0.51, yRange.upper, 0.01);
		showChart();

		// bar + category + negative value
		barSeries.setYSeries(ySeries2);
		xAxis.setRange(new Range(0, 4));
		yAxis.setRange(new Range(-0.2, 0.2));
		showChart();
		xAxis.adjustRange();
		xRange = xAxis.getRange();
		assertEquals(0d, xRange.lower, 0.01);
		assertEquals(4d, xRange.upper, 0.01);
		yAxis.adjustRange();
		yRange = yAxis.getRange();
		assertEquals(-0.21, yRange.lower, 0.01);
		assertEquals(0.21, yRange.upper, 0.01);
		showChart();

		// bar + constant series
		barSeries.setYSeries(ySeries3);
		showChart();
		yAxis.adjustRange();
		showChart();
		yRange = yAxis.getRange();
		assertEquals(0d, yRange.lower, 0.01);
		assertEquals(4.41, yRange.upper, 0.01);
		barSeries.setYSeries(ySeries4);
		showChart();
		yAxis.adjustRange();
		showChart();
		yRange = yAxis.getRange();
		assertEquals(-1d, yRange.lower, 0.01);
		assertEquals(1d, yRange.upper, 0.01);

		// bar + negative series
		barSeries.setYSeries(ySeries5);
		yAxis.setRange(new Range(-5, -1));
		showChart();
		yAxis.adjustRange();
		showChart();
		yRange = yAxis.getRange();
		assertEquals(-5.13, yRange.lower, 0.01);
		assertEquals(0d, yRange.upper, 0.01);

		// bar + vertical orientation
		barSeries.setYSeries(ySeries2);
		chart.setOrientation(SWT.VERTICAL);
		xAxis.enableCategory(false);
		barSeries.setYSeries(ySeries1);
		xAxis.setRange(new Range(0, 4));
		yAxis.setRange(new Range(0.1, 0.5));
		showChart();
		xAxis.adjustRange();
		xRange = xAxis.getRange();
		assertEquals(-0.51, xRange.lower, 0.1);
		assertEquals(4.53, xRange.upper, 0.1);
		yAxis.adjustRange();
		yRange = yAxis.getRange();
		assertEquals(0d, yRange.lower, 0.01);
		assertEquals(0.51, yRange.upper, 0.01);
		showChart();

		// bar + negative series + vertical orientation
		barSeries.setYSeries(ySeries2);
		xAxis.setRange(new Range(0, 4));
		yAxis.setRange(new Range(-0.2, 0.2));
		showChart();
		xAxis.adjustRange();
		xRange = xAxis.getRange();
		assertEquals(-0.51, xRange.lower, 0.1);
		assertEquals(4.53, xRange.upper, 0.1);
		yAxis.adjustRange();
		yRange = yAxis.getRange();
		assertEquals(-0.21, yRange.lower, 0.01);
		assertEquals(0.21, yRange.upper, 0.01);
		showChart();

		// bar + category + vertical orientation
		barSeries.setYSeries(ySeries1);
		xAxis.setCategorySeries(categorySeries);
		xAxis.enableCategory(true);
		xAxis.setRange(new Range(0, 4));
		yAxis.setRange(new Range(0.1, 0.5));
		showChart();
		xAxis.adjustRange();
		xRange = xAxis.getRange();
		assertEquals(0d, xRange.lower, 0.01);
		assertEquals(4d, xRange.upper, 0.01);
		yAxis.adjustRange();
		yRange = yAxis.getRange();
		assertEquals(0d, yRange.lower, 0.01);
		assertEquals(0.51, yRange.upper, 0.01);
		showChart();

		// bar + category + negative series + vertical orientation
		barSeries.setYSeries(ySeries2);
		xAxis.setCategorySeries(categorySeries);
		xAxis.enableCategory(true);
		xAxis.setRange(new Range(0, 4));
		yAxis.setRange(new Range(-0.2, 0.2));
		showChart();
		xAxis.adjustRange();
		xRange = xAxis.getRange();
		assertEquals(0d, xRange.lower, 0.01);
		assertEquals(4d, xRange.upper, 0.01);
		yAxis.adjustRange();
		yRange = yAxis.getRange();
		assertEquals(-0.21, yRange.lower, 0.01);
		assertEquals(0.21, yRange.upper, 0.01);
		showChart();

		// bar + category + log scale
		barSeries.setYSeries(ySeries1);
		yAxis.enableLogScale(true);
		showChart();
		yAxis.adjustRange();
		yRange = yAxis.getRange();
		assertEquals(0.095, yRange.lower, 0.01);
		assertEquals(0.52, yRange.upper, 0.01);
		showChart();
	}

	/**
	 * Test for zooming.
	 */
    @Test
	public void testZoom() throws Exception {
		ISeries lineSeries = chart.getSeriesSet().createSeries(SeriesType.LINE, "line series");
		lineSeries.setXSeries(series);
		lineSeries.setYSeries(series);

		// zoom X axis
		showChart();
		xAxis.zoomIn();
		Range xRange = xAxis.getRange();
		assertEquals(0.2, xRange.lower, 0.01);
		assertEquals(0.8, xRange.upper, 0.01);
		showChart();
		xAxis.zoomOut();
		xRange = xAxis.getRange();
		assertEquals(0d, xRange.lower, 0.001);
		assertEquals(1d, xRange.upper, 0.001);
		showChart();

		// zoom Y axis
		yAxis.zoomIn();
		Range yRange = yAxis.getRange();
		assertEquals(0.2, yRange.lower, 0.01);
		assertEquals(0.8, yRange.upper, 0.01);
		showChart();
		yAxis.zoomOut();
		yRange = yAxis.getRange();
		assertEquals(0d, yRange.lower, 0.001);
		assertEquals(1d, yRange.upper, 0.001);
		showChart();

		// zoom log scale X axis
		xAxis.enableLogScale(true);
		yAxis.enableLogScale(true);
		xAxis.setRange(new Range(0.01, 10));
		yAxis.setRange(new Range(0.01, 10));
		showChart();
		xAxis.zoomIn();
		xRange = xAxis.getRange();
		assertEquals(0.035, xRange.lower, 0.001);
		assertEquals(8.7, xRange.upper, 0.1);
		showChart();
		xAxis.zoomOut();
		xRange = xAxis.getRange();
		assertEquals(0.01, xRange.lower, 0.001);
		assertEquals(10, xRange.upper, 0.4);

		// zoom log scale Y axis
		showChart();
		yAxis.zoomIn();
		yRange = yAxis.getRange();
		assertEquals(0.035, yRange.lower, 0.01);
		assertEquals(8.7, yRange.upper, 0.1);
		showChart();
		yAxis.zoomOut();
		yRange = yAxis.getRange();
		assertEquals(0.01, yRange.lower, 0.001);
		assertEquals(10, yRange.upper, 0.4);
		showChart();

		// zoom category axis
		lineSeries.setYSeries(ySeries1);
		xAxis.setCategorySeries(categorySeries);
		xAxis.enableCategory(true);
		xAxis.setRange(new Range(0, 4));
		yAxis.enableLogScale(false);
		yAxis.setRange(new Range(0.1, 0.5));
		showChart();
		xAxis.zoomIn();
		xRange = xAxis.getRange();
		assertEquals(1d, xRange.lower, 0.001);
		assertEquals(3d, xRange.upper, 0.001);
		showChart();
		xAxis.zoomIn();
		xRange = xAxis.getRange();
		assertEquals(2d, xRange.lower, 0.001);
		assertEquals(2d, xRange.upper, 0.001);
		showChart();
		xAxis.zoomIn();
		xRange = xAxis.getRange();
		assertEquals(2d, xRange.lower, 0.001);
		assertEquals(2d, xRange.upper, 0.001);
		xAxis.zoomOut();
		xRange = xAxis.getRange();
		assertEquals(1d, xRange.lower, 0.001);
		assertEquals(3d, xRange.upper, 0.001);
		showChart();
		xAxis.zoomOut();
		xRange = xAxis.getRange();
		assertEquals(0d, xRange.lower, 0.001);
		assertEquals(4d, xRange.upper, 0.001);
		showChart();
	}

	/**
	 * Test for zooming at a certain coordinate.
	 */
    @Test
	public void testZoomAt() throws Exception {
		ISeries lineSeries = chart.getSeriesSet().createSeries(SeriesType.LINE, "line series");
		lineSeries.setXSeries(series);
		lineSeries.setYSeries(series);

		// zoom X axis
		showChart();
		xAxis.zoomIn(0.4);
		Range xRange = xAxis.getRange();
		assertEquals(0.16, xRange.lower, 0.001);
		assertEquals(0.76, xRange.upper, 0.001);
		showChart();
		xAxis.zoomOut(0.4);
		xRange = xAxis.getRange();
		assertEquals(0d, xRange.lower, 0.001);
		assertEquals(1d, xRange.upper, 0.001);
		showChart();

		// zoom Y axis
		yAxis.zoomIn(0.4);
		Range yRange = yAxis.getRange();
		assertEquals(0.16, yRange.lower, 0.001);
		assertEquals(0.76, yRange.upper, 0.001);
		showChart();
		yAxis.zoomOut(0.4);
		yRange = yAxis.getRange();
		assertEquals(0d, yRange.lower, 0.001);
		assertEquals(1d, yRange.upper, 0.001);
		showChart();

		// zoom log scale X axis
		xAxis.enableLogScale(true);
		yAxis.enableLogScale(true);
		xAxis.setRange(new Range(0.01, 10));
		yAxis.setRange(new Range(0.01, 10));
		showChart();
		xAxis.zoomIn(0.4);
		xRange = xAxis.getRange();
		assertEquals(0.021, xRange.lower, 0.001);
		assertEquals(5.25, xRange.upper, 0.01);
		showChart();
		xAxis.zoomOut(0.4);
		xRange = xAxis.getRange();
		assertEquals(0.01, xRange.lower, 0.001);
		assertEquals(10, xRange.upper, 0.4);

		// zoom log scale Y axis
		showChart();
		yAxis.zoomIn(0.4);
		yRange = yAxis.getRange();
		assertEquals(0.021, yRange.lower, 0.01);
		assertEquals(5.25, yRange.upper, 0.01);
		showChart();
		yAxis.zoomOut(0.4);
		yRange = yAxis.getRange();
		assertEquals(0.01, yRange.lower, 0.001);
		assertEquals(10, yRange.upper, 0.4);
		showChart();

		// zoom category axis
		lineSeries.setYSeries(ySeries1);
		xAxis.setCategorySeries(categorySeries);
		xAxis.enableCategory(true);
		xAxis.setRange(new Range(0, 4));
		yAxis.enableLogScale(false);
		yAxis.setRange(new Range(0.1, 0.5));
		showChart();
		xAxis.zoomIn(1);
		xRange = xAxis.getRange();
		assertEquals(0d, xRange.lower, 0.001);
		assertEquals(3d, xRange.upper, 0.001);
		showChart();
		xAxis.zoomIn(1);
		xRange = xAxis.getRange();
		assertEquals(0d, xRange.lower, 0.001);
		assertEquals(2d, xRange.upper, 0.001);
		showChart();
		xAxis.zoomIn(1);
		xRange = xAxis.getRange();
		assertEquals(1d, xRange.lower, 0.001);
		assertEquals(1d, xRange.upper, 0.001);
		xAxis.zoomIn(1);
		xRange = xAxis.getRange();
		assertEquals(1d, xRange.lower, 0.001);
		assertEquals(1d, xRange.upper, 0.001);
		showChart();
		xAxis.zoomOut(1);
		xRange = xAxis.getRange();
		assertEquals(0d, xRange.lower, 0.001);
		assertEquals(2d, xRange.upper, 0.001);
		showChart();
		xAxis.zoomOut(1);
		xRange = xAxis.getRange();
		assertEquals(0d, xRange.lower, 0.001);
		assertEquals(3d, xRange.upper, 0.001);
		showChart();
		xAxis.zoomOut(1);
		xRange = xAxis.getRange();
		assertEquals(0d, xRange.lower, 0.001);
		assertEquals(4d, xRange.upper, 0.001);
		showChart();
	}

	/**
	 * Test for scrolling.
	 */
    @Test
	public void testScroll() throws Exception {
		ISeries lineSeries = chart.getSeriesSet().createSeries(SeriesType.LINE, "line series");
		lineSeries.setXSeries(xSeries1);
		lineSeries.setYSeries(ySeries1);
		xAxis.setRange(new Range(1, 5));
		yAxis.setRange(new Range(0.1, 0.5));

		// scroll X axis
		showChart();
		xAxis.scrollUp();
		Range xRange = xAxis.getRange();
		assertEquals(1.4, xRange.lower, 0.001);
		assertEquals(5.4, xRange.upper, 0.001);
		showChart();
		xAxis.scrollDown();
		xRange = xAxis.getRange();
		assertEquals(1d, xRange.lower, 0.001);
		assertEquals(5d, xRange.upper, 0.001);
		showChart();

		// scroll Y axis
		yAxis.scrollUp();
		Range yRange = yAxis.getRange();
		assertEquals(0.14, yRange.lower, 0.001);
		assertEquals(0.54, yRange.upper, 0.001);
		showChart();
		yAxis.scrollDown();
		yRange = yAxis.getRange();
		assertEquals(0.1, yRange.lower, 0.001);
		assertEquals(0.5, yRange.upper, 0.001);
		showChart();

		// scroll log scale X axis
		xAxis.enableLogScale(true);
		yAxis.enableLogScale(true);
		xAxis.setRange(new Range(1, 10));
		yAxis.setRange(new Range(0.1, 1));
		showChart();
		xAxis.scrollUp();
		xRange = xAxis.getRange();
		assertEquals(1.26, xRange.lower, 0.01);
		assertEquals(12.6, xRange.upper, 0.1);
		showChart();
		xAxis.scrollDown();
		xRange = xAxis.getRange();
		assertEquals(1d, xRange.lower, 0.001);
		assertEquals(10d, xRange.upper, 0.001);
		showChart();

		// scroll log scale axis
		yAxis.scrollUp();
		yRange = yAxis.getRange();
		assertEquals(0.126, yRange.lower, 0.01);
		assertEquals(1.26, yRange.upper, 0.1);
		showChart();
		yAxis.scrollDown();
		yRange = yAxis.getRange();
		assertEquals(0.1, yRange.lower, 0.001);
		assertEquals(1d, yRange.upper, 0.001);
		showChart();

		// scroll category X axis
		xAxis.setCategorySeries(categorySeries);
		xAxis.enableCategory(true);
		xAxis.setRange(new Range(1, 3));
		showChart();
		xAxis.scrollUp();
		xRange = xAxis.getRange();
		assertEquals(2d, xRange.lower, 0.001);
		assertEquals(4d, xRange.upper, 0.001);
		showChart();
		xAxis.scrollUp();
		xRange = xAxis.getRange();
		assertEquals(2d, xRange.lower, 0.001);
		assertEquals(4d, xRange.upper, 0.001);
		xAxis.scrollDown();
		xRange = xAxis.getRange();
		assertEquals(1d, xRange.lower, 0.001);
		assertEquals(3d, xRange.upper, 0.001);
		showChart();
		xAxis.scrollDown();
		xRange = xAxis.getRange();
		assertEquals(0d, xRange.lower, 0.001);
		assertEquals(2d, xRange.upper, 0.001);
		showChart();
		xRange = xAxis.getRange();
		assertEquals(0d, xRange.lower, 0.001);
		assertEquals(2d, xRange.upper, 0.001);

		// scroll X axis in vertical orientation
		chart.setOrientation(SWT.VERTICAL);
		xAxis.enableCategory(false);
		xAxis.enableLogScale(false);
		yAxis.enableLogScale(false);
		xAxis.setRange(new Range(1, 5));
		yAxis.setRange(new Range(0.1, 0.5));
		showChart();
		xAxis.scrollUp();
		xRange = xAxis.getRange();
		assertEquals(1.4, xRange.lower, 0.001);
		assertEquals(5.4, xRange.upper, 0.001);
		showChart();
		xAxis.scrollDown();
		xRange = xAxis.getRange();
		assertEquals(1d, xRange.lower, 0.001);
		assertEquals(5d, xRange.upper, 0.001);
		showChart();

		// scroll Y axis in vertical orientation
		yAxis.scrollUp();
		yRange = yAxis.getRange();
		assertEquals(0.14, yRange.lower, 0.001);
		assertEquals(0.54, yRange.upper, 0.001);
		showChart();
		yAxis.scrollDown();
		yRange = yAxis.getRange();
		assertEquals(0.1, yRange.lower, 0.001);
		assertEquals(0.5, yRange.upper, 0.001);
		showChart();

		// scroll log scale X axis in vertical orientation
		xAxis.enableLogScale(true);
		yAxis.enableLogScale(true);
		xAxis.setRange(new Range(1, 10));
		yAxis.setRange(new Range(0.1, 1));
		showChart();
		xAxis.scrollUp();
		xRange = xAxis.getRange();
		assertEquals(1.26, xRange.lower, 0.01);
		assertEquals(12.6, xRange.upper, 0.1);
		showChart();
		xAxis.scrollDown();
		xRange = xAxis.getRange();
		assertEquals(1d, xRange.lower, 0.001);
		assertEquals(10d, xRange.upper, 0.001);
		showChart();

		// scroll log scale axis in vertical orientation
		yAxis.scrollUp();
		yRange = yAxis.getRange();
		assertEquals(0.126, yRange.lower, 0.01);
		assertEquals(1.26, yRange.upper, 0.1);
		showChart();
		yAxis.scrollDown();
		yRange = yAxis.getRange();
		assertEquals(0.1, yRange.lower, 0.001);
		assertEquals(1d, yRange.upper, 0.001);
		showChart();

		// scroll category X axis in vertical orientation
		xAxis.setCategorySeries(categorySeries);
		xAxis.enableCategory(true);
		xAxis.setRange(new Range(1, 3));
		showChart();
		xAxis.scrollUp();
		xRange = xAxis.getRange();
		assertEquals(2d, xRange.lower, 0.001);
		assertEquals(4d, xRange.upper, 0.001);
		showChart();
		xAxis.scrollUp();
		xRange = xAxis.getRange();
		assertEquals(2d, xRange.lower, 0.001);
		assertEquals(4d, xRange.upper, 0.001);
		xAxis.scrollDown();
		xRange = xAxis.getRange();
		assertEquals(1d, xRange.lower, 0.001);
		assertEquals(3d, xRange.upper, 0.001);
		showChart();
		xAxis.scrollDown();
		xRange = xAxis.getRange();
		assertEquals(0d, xRange.lower, 0.001);
		assertEquals(2d, xRange.upper, 0.001);
		showChart();
		xAxis.scrollDown();
		xRange = xAxis.getRange();
		assertEquals(0d, xRange.lower, 0.001);
		assertEquals(2d, xRange.upper, 0.001);
	}

	/**
	 * Test for category.
	 */
    @Test
	public void testCategory() throws Exception {

		// enable category for Y axis
		try {
			yAxis.enableCategory(true);
			fail();
		} catch (IllegalStateException e) {
			// expected to reach here
		}
		assertFalse(yAxis.isCategoryEnabled());

		// set category series for Y axis
		try {
			yAxis.setCategorySeries(categorySeries);
			fail();
		} catch (IllegalStateException e) {
			// expected to reach here
		}
		assertNull(yAxis.getCategorySeries());

		// set null for category series
		try {
			xAxis.setCategorySeries(null);
			fail();
		} catch (IllegalArgumentException e) {
			// expected to reach here
		}
		assertNull(xAxis.getCategorySeries());

		// enable category
		xAxis.setRange(new Range(-1, 10));
		xAxis.setCategorySeries(categorySeries);
		xAxis.enableCategory(true);
		Range range = xAxis.getRange();
		// check if range is adjusted
		assertEquals(0d, range.lower, 0.001);
		assertEquals(categorySeries.length - 1, range.upper, 0.001);
		assertTrue(xAxis.isCategoryEnabled());
		xAxis.enableCategory(false);
		assertFalse(xAxis.isCategoryEnabled());
		String[] series1 = xAxis.getCategorySeries();
		// check if category series remains
		assertEquals(categorySeries.length, series1.length);
		for (int i = 0; i < series1.length; i++) {
			assertTrue(categorySeries[i].equals(series1[i]));
		}
	}

	/**
	 * Test for coordinate conversion.
	 */
    @Test
    @Ignore("environment dependent")
	public void testCoordinate() throws Exception {

		xAxis.setRange(new Range(0, 100));
		yAxis.setRange(new Range(0, 10));
		Rectangle r = chart.getPlotArea().getBounds();
		double dataX = xAxis.getDataCoordinate((int) (r.width * 0.4));
		double dataY = yAxis.getDataCoordinate((int) (r.height * 0.3));
		showChart();
		assertEquals(40, dataX, 1);
		assertEquals(7d, dataY, 0.04);
		int pixelX = xAxis.getPixelCoordinate(40);
		int pixelY = yAxis.getPixelCoordinate(7);
		assertEquals(r.width * 0.4, pixelX, 1);
		assertEquals(r.height * 0.3, pixelY, 1);

		// log scale
		xAxis.enableLogScale(true);
		yAxis.enableLogScale(true);
		xAxis.setRange(new Range(0.1, 1000));
		yAxis.setRange(new Range(0.1, 100));
		r = chart.getPlotArea().getBounds();
		dataX = xAxis.getDataCoordinate((int) (r.width / 4d));
		dataY = yAxis.getDataCoordinate((int) (r.height * 2 / 3d));
		assertEquals(1d, dataX, 0.02);
		assertEquals(1d, dataY, 0.03);
		pixelX = xAxis.getPixelCoordinate(100.0);
		pixelY = yAxis.getPixelCoordinate(10.0);
		assertEquals(r.width * 3 / 4d, pixelX, 1);
		assertEquals(r.height / 3d, pixelY, 1);

		// category
		xAxis.setCategorySeries(categorySeries);
		xAxis.enableCategory(true);
		xAxis.adjustRange();
		r = chart.getPlotArea().getBounds();
		dataX = xAxis.getDataCoordinate((int) (r.width * 0.21));
		assertEquals(1.0, dataX, 0.001);
		dataX = xAxis.getDataCoordinate((int) (r.width * 0.39));
		assertEquals(1.0, dataX, 0.001);
		dataX = xAxis.getDataCoordinate((int) (r.width * 0.41));
		assertEquals(2.0, dataX, 0.001);
		pixelX = xAxis.getPixelCoordinate(3);
		assertEquals(r.width * 0.7, pixelX, 1);

		// vertical orientation
		chart.setOrientation(SWT.VERTICAL);
		xAxis.enableCategory(false);
		xAxis.enableLogScale(false);
		yAxis.enableLogScale(false);
		xAxis.setRange(new Range(0, 100));
		yAxis.setRange(new Range(0, 10));
		r = chart.getPlotArea().getBounds();
		dataX = xAxis.getDataCoordinate((int) (r.height * 0.4));
		dataY = yAxis.getDataCoordinate((int) (r.width * 0.3));
		assertEquals(60, dataX, 1);
		assertEquals(3d, dataY, 0.02);
		pixelX = xAxis.getPixelCoordinate(40);
		pixelY = yAxis.getPixelCoordinate(7);
		assertEquals(r.height * 0.6, pixelX, 1);
		assertEquals(r.width * 0.7, pixelY, 1);

		// log scale + vertical orientation
		xAxis.enableLogScale(true);
		yAxis.enableLogScale(true);
		xAxis.setRange(new Range(0.1, 1000));
		yAxis.setRange(new Range(0.1, 100));
		r = chart.getPlotArea().getBounds();
		dataX = xAxis.getDataCoordinate((int) (r.height / 4d));
		dataY = yAxis.getDataCoordinate((int) (r.width * 2 / 3d));
		showChart();
		assertEquals(100d, dataX, 3);
		assertEquals(10d, dataY, 0.3);
		pixelX = xAxis.getPixelCoordinate(10d);
		pixelY = yAxis.getPixelCoordinate(1d);
		assertEquals(r.height / 2d, pixelX, 1);
		assertEquals(r.width / 3d, pixelY, 1);

		// category + vertical orientation
		xAxis.setCategorySeries(categorySeries);
		xAxis.enableCategory(true);
		r = chart.getPlotArea().getBounds();
		showChart();
		dataX = xAxis.getDataCoordinate((int) (r.height * 0.21));
		assertEquals(3.0, dataX, 0.001);
		dataX = xAxis.getDataCoordinate((int) (r.height * 0.39));
		assertEquals(3.0, dataX, 0.001);
		dataX = xAxis.getDataCoordinate((int) (r.height * 0.41));
		assertEquals(2.0, dataX, 0.001);
		pixelX = xAxis.getPixelCoordinate(3);
		assertEquals(r.height * 0.3, pixelX, 1);
	}
    
}