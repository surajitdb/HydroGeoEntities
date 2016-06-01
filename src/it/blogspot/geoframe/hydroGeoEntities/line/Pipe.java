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

import org.geotools.graph.util.geom.Coordinate2D;

import it.blogspot.geoframe.hydroGeoEntities.HydroGeoEntity;
import it.blogspot.geoframe.utils.UnitsTransform;

public class Pipe extends HydroGeoEntity {

    final private Coordinate2D startPoint;
    final private Coordinate2D endPoint;
    final private Double roughness;
    private Double elevationStartPoint;
    private Double elevationEndPoint;
    private Double length;
    private Double discharge;
    private Double slope;
    private Double diameter;

    public Pipe(final Coordinate2D startPoint, final Coordinate2D endPoint, final Double roughness) {

        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.roughness = roughness;

    }

    private Double computeLength() {
        return Math.sqrt(Math.pow(horizontalProjection(), 2) +
                         Math.pow(altitudeDifference(), 2));
    }

    private Double horizontalProjection() {
        return Math.sqrt(Math.pow(startPoint.x - endPoint.x, 2) +
                         Math.pow(startPoint.y - endPoint.y, 2));
    }

    private Double altitudeDifference() {
        return elevationStartPoint - elevationEndPoint;
    }

    private Double altitudeDifference(final Double slopeRad) {
        return horizontalProjection() * Math.tan(slopeRad);
    }

    @Override
    public Coordinate2D getStartPoint() {
        return startPoint;
    }

    @Override
    public Coordinate2D getEndPoint() {
        return endPoint;
    }

    @Override
    public Coordinate2D getPoint() {

        // eventually return the middle of the pipe
        return super.getPoint();

    }

    public void setElevationEndPoint(final Double elevationEndPoint) {
        this.elevationEndPoint = elevationEndPoint;
        if (this.length == null) this.length = computeLength();
    }

    public void setSlope(final Double slope) {
        this.slope = slope;
        this.elevationEndPoint = computeElevationEndPoint(slope);
    }

    private Double computeElevationEndPoint(final Double slope) {
        return elevationStartPoint - altitudeDifference(UnitsTransform.percentage2radiant(slope));
    }

}
