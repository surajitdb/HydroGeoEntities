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

import org.geotools.graph.util.geom.Coordinate2D;

import it.blogspot.geoframe.hydroGeoEntities.HydroGeoEntity;
import it.blogspot.geoframe.hydroGeoEntities.line.River;

import net.jcip.annotations.Immutable;

/**
 *
 *
 * @author sidereus, francesco.serafin.3@gmail.com
 * @version 0.1
 * @date May 15, 2016
 * @copyright GNU Public License v3 GWH-2b4
 */
@Immutable
public class Basin extends HydroGeoEntity {

    final private River river;
    final private Double area;

    public Basin (final River river, final Double area) {

        this.river = river;
        this.area = area;

    }

    public Double getBasinArea() {
        return area;
    }

    @Override
    public Coordinate2D getStartPoint() {
        return river.getStartPoint();
    }

    @Override
    public Coordinate2D getEndPoint() {
        return river.getEndPoint();
    }

    @Override
    public Coordinate2D getPoint() {

        // implement the computing of the centroid
        return super.getPoint();

    }

}
