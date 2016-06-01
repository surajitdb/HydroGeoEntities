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
package it.blogspot.geoframe.hydroGeoEntities.point;

import org.geotools.graph.util.geom.Coordinate2D;

import it.blogspot.geoframe.hydroGeoEntities.HydroGeoEntity;
import net.jcip.annotations.Immutable;

@Immutable
public class Hydrometer extends HydroGeoEntity {

    private final Coordinate2D point;

    public Hydrometer(final Coordinate2D point) {

        this.point = point;

    }

    @Override
    public Coordinate2D getStartPoint() {
        return getPoint();
    }

    @Override
    public Coordinate2D getEndPoint() {
        return getPoint();
    }

    @Override
    public Coordinate2D getPoint() {
        return point;
    }

}
