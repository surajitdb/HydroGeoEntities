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
package it.blogspot.geoframe.hydroGeoEntities.area;

import it.blogspot.geoframe.hydroGeoEntities.line.Pipe;
import it.blogspot.geoframe.hydroGeoEntities.point.HydroGeoPoint;
import it.blogspot.geoframe.utils.GEOunitsTransform;

/**
 *
 *
 * @author sidereus, francesco.serafin.3@gmail.com
 * @version 0.1
 * @date June 16, 2016
 * @copyright GNU Public License v3 GWH-2b4
 */
public class DrainageArea extends HydroGeoArea {

    private Pipe pipe;
    final private Double area; // [ha]
    final private Double urbanRunoffCoefficient; // [ha/ha]
    final private Double averageSlope; // [%]
    final private Double residenceTime; // [min]
    final private Double EXPONENT1 = 0.38;
    final private Double EXPONENT2 = 0.4;
    final private Double EXPONENT3 = 0.4;

    public DrainageArea (final Pipe pipe, final double area,
                         final double urbanRunoffCoefficient,
                         final double alpha, final double averageSlope) {
        this.pipe = pipe;
        this.area = area;
        this.urbanRunoffCoefficient = urbanRunoffCoefficient;
        this.averageSlope = averageSlope;

        this.residenceTime = computeResidenceTime(alpha);

    }

    /**
     * @TODO: HORRIBLE HACK. Unit conversion in computed numerator has no sense.
     *        Further reasoning are required
     *
     * @return
     */
    private Double computeResidenceTime(final double alpha) {
        double numerator = GEOunitsTransform.hours2minutes(alpha) *
                           Math.pow(GEOunitsTransform.centimeters2meters(area), EXPONENT1);

        double denominator = Math.pow(urbanRunoffCoefficient, EXPONENT2) *
                             Math.pow(averageSlope, EXPONENT3);
        return numerator / denominator; 
    }

    public Double getResidenceTime() {
        return residenceTime;
    }

    @Override
    public Double getArea() {
        return area;
    }

    public Pipe getPipe() {
        return pipe;
    }

    @Override
    public HydroGeoPoint getStartPoint() {
        return pipe.getStartPoint();
    }

    @Override
    public HydroGeoPoint getEndPoint() {
        return pipe.getEndPoint();
    }

    public double getUrbanRunoffCoefficient() {
        return urbanRunoffCoefficient;
    }

    public void setPipe(final Pipe pipe) {
        this.pipe = pipe;
    }

}
