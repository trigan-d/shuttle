package com.pepel.games.shuttle.model.geography;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.google.common.collect.Lists;
import com.pepel.games.shuttle.util.NamingUtils;

import static com.google.common.base.Preconditions.*;

@Entity
@Table(name = "zones")
public class Zone implements Serializable {
	private static final long serialVersionUID = 7474122973140247977L;

	@Id
	private long id;

	@NotNull
	@Min(0)
	@Column(name = "start_index")
	private int startIndex;

	@NotNull
	@Min(0)
	@Column(name = "end_index")
	private int endIndex;

	public Zone() {
	}

	public Zone(long id, int start, int end) {
		checkArgument(id != 0 || (start == 0 && end == 0),
				"Zone 'zero' should contain only one province with location (0,0)");
		checkArgument(start > 0 || id == 0, "Only zone 'zero' can start from 0");
		checkArgument(start <= end, "Start can't be greater than end");

		this.id = id;
		this.startIndex = start;
		this.endIndex = end;
	}

	public static Zone zeroZone() {
		return new Zone(0l, 0, 0);
	}

	@Transient
	public int getCapacity() {
		if (isZeroZone()) {
			return 1;
		} else {
			int bigSquareSide = 2 * endIndex + 1;
			int smallSquareSide = 2 * startIndex - 1;
			return bigSquareSide * bigSquareSide - smallSquareSide * smallSquareSide;
		}
	}

	@Transient
	public ArrayList<NamedLocation> getLocations() {
		ArrayList<String> names = Lists.newArrayListWithCapacity(getCapacity());
		ArrayList<NamedLocation> locations = Lists.newArrayListWithCapacity(getCapacity());
		
		for (int y = -endIndex; y <= endIndex; y++) {
			for (int x = -endIndex; x <= endIndex; x++) {
				if (Math.abs(y) < startIndex && x == 1 - startIndex) {
					x = startIndex - 1;
				} else {
					NamedLocation location = new NamedLocation(x, y);
					locations.add(location);
					names.add(NamingUtils.encode(location));
				}
			}
		}
		
		Collections.shuffle(names, new Random());
		Collections.shuffle(locations, new Random());

		for(int i=0;i<locations.size();i++) {
			locations.get(i).setName(names.get(i));
		}
		
		return locations;
	}

	@Transient
	public boolean isZeroZone() {
		return id == 0;
	}

	@Transient
	public boolean contains(Location location) {
		int modX = Math.abs(location.getX());
		int modY = Math.abs(location.getY());
		return (modX >= startIndex || modY >= startIndex) && modX <= endIndex && modY <= endIndex;
	}

	public long getId() {
		return id;
	}

	public int getEnd() {
		return endIndex;
	}

	public int getStart() {
		return startIndex;
	}
}
