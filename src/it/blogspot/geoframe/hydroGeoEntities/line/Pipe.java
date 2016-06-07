/*
 * GNU GPL v3 License
 *
 * Copyright 2015 AboutHydrology (Riccardo Rigon)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.blogspot.geoframe.hydroGeoEntities.line;

import java.util.HashMap;

import org.geotools.graph.util.geom.Coordinate2D;

import it.blogspot.geoframe.hydroGeoEntities.HydroGeoEntity;
import it.blogspot.geoframe.hydroGeoEntities.point.Point;
import it.blogspot.geoframe.utils.GEOchecks;
import it.blogspot.geoframe.utils.UnitsTransform;

/**
 *
 *
 * @author sidereus, francesco.serafin.3@gmail.com
 * @version 0.1
 * @date May 15, 2016
 * @copyright GNU Public License v3 GWH-2b4
 */
public class Pipe extends HydroGeoEntity {

    final private HashMap<Integer, Point> inspectionChambers = new HashMap<Integer, Point>();
    final private Point startInspectionChamber;
    final private Point endInspectionChamber;
    final private double gaucklerStricklerCoefficient;
    final private double fillCoefficient;
    private Double length = null;
    private Double discharge = null;
    private Double slope = null;
    private Double diameter = null;

    public Pipe(final double gaucklerStricklerCoefficient, final double fillCoefficient, Point... inspectionChambers) {

        this.gaucklerStricklerCoefficient = gaucklerStricklerCoefficient;
        this.fillCoefficient = fillCoefficient;

        // check if inspectionChambers length is at least 2
        Integer index = 1;
        for (Point inspectionChamber : inspectionChambers) {
            this.inspectionChambers.put(index, inspectionChamber);
            index++;
        }

        this.startInspectionChamber = this.inspectionChambers.get(1);
        this.endInspectionChamber = this.inspectionChambers.get(this.inspectionChambers.size());
    }

    private Double computeLength() {
        return Math.sqrt(Math.pow(horizontalProjection(), 2) +
                         Math.pow(altitudeDifference(), 2));
    }

    private Double horizontalProjection() {
        return Math.sqrt(Math.pow(startInspectionChamber.getPoint().x -
                                  endInspectionChamber.getPoint().x, 2) +
                         Math.pow(startInspectionChamber.getPoint().y -
                                  endInspectionChamber.getPoint().y, 2));
    }

    private Double altitudeDifference() {
        return startInspectionChamber.getElevation() - endInspectionChamber.getElevation();
    }

    private Double altitudeDifference(final double slopeRad) {
        return horizontalProjection() * Math.tan(slopeRad);
    }

    @Override
    public Coordinate2D getStartPoint() {
        return startInspectionChamber.getPoint();
    }

    @Override
    public Coordinate2D getEndPoint() {
        return endInspectionChamber.getPoint();
    }

    @Override
    public Coordinate2D getPoint() {

        // eventually return the middle of the pipe
        return super.getPoint();

    }

    public double getLength() {
        return GEOchecks.checkVariable(length);
    }

    public double getDischarge() {
        return GEOchecks.checkVariable(discharge);
    }

    public double getSlope() {
        return GEOchecks.checkVariable(slope);
    }

    public double getDiameter() {
        return GEOchecks.checkVariable(diameter);
    }

    public double getGaucklerStricklerCoefficient() {
        return gaucklerStricklerCoefficient;
    }

    public double getFillCoefficient() {
        return fillCoefficient;
    }

    public void setElevationEndPoint(final double elevationEndPoint) {
        endInspectionChamber.setElevation(elevationEndPoint);
        inspectionChambers.put(inspectionChambers.size(), endInspectionChamber);

        if (length == 0.0) length = computeLength();
    }

    public void setLength(final double length) {
        this.length = length;
    }

    public void setDischarge(final double discharge) {
        this.discharge = discharge;
    }

    public void setSlope(final double slope) {
        this.slope = slope;
        setElevationEndPoint(computeElevationEndPoint(slope));
    }

    public void setDiameter(final double diameter) {
        this.diameter = diameter;
    }

    private double computeElevationEndPoint(final double slope) {
        return startInspectionChamber.getElevation() - altitudeDifference(UnitsTransform.percentage2radiant(slope));
    }

}
