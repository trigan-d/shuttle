package com.pepel.games.shuttle.model.industry;

import static com.pepel.games.shuttle.model.industry.Cargo.*;

import java.util.Set;

import com.google.common.collect.Sets;

public enum Industry {
	Housing(Sets.newHashSet(CargoAmount.one(Passengers), CargoAmount.one(Mail)),
			Sets.newHashSet(CargoAmount.of(Food, 0.5), CargoAmount.of(Goods, 0.5), 
				CargoAmount.of(Lumber, 0.25), CargoAmount.of(Paper, 0.25), CargoAmount.of(Coal, 0.25))),
	
	IronMine(CargoAmount.of(Iron,2)),
	CoalMine(CargoAmount.of(Coal,2)),
	FertilizerFactory(CargoAmount.of(Fertilizer,3)),
	LoggingCamp(Sets.newHashSet(CargoAmount.of(Logs, 2), CargoAmount.one(Pulpwood))),
	
	CattleRanch(CargoAmount.of(Cattle,2), CargoAmount.one(Grain)),	
	SheepFarm(CargoAmount.of(Wool,2), CargoAmount.one(Grain)),
	CottonFarm(CargoAmount.of(Cotton,2), CargoAmount.one(Fertilizer)),
	GrainSilo(CargoAmount.of(Grain,2), CargoAmount.one(Fertilizer)),
	SugarFarm(CargoAmount.of(Sugar,2), CargoAmount.one(Fertilizer)),
	ProduceOrchard(CargoAmount.of(Produce,2), CargoAmount.one(Fertilizer)),

	PaperMill(Procedure.simple(Pulpwood, Paper)),
	LumberMill(Procedure.simple(Logs, Lumber)),
	SteelMill(Procedure.combine(Sets.newHashSet(CargoAmount.one(Iron), CargoAmount.one(Coal)), CargoAmount.of(Steel, 2))),
	ToolAndDie(Procedure.simple(Iron, Goods), Procedure.simple(Steel, Goods)),
	Cannery(Procedure.combine(Sets.newHashSet(CargoAmount.one(Produce), CargoAmount.one(Steel)), CargoAmount.of(Food, 2))),
	MeatPacking(Procedure.simple(Cattle, Food)),
	TextileMill(Procedure.simple(Cotton, Goods), Procedure.simple(Wool, Goods)),
	Bakery(Procedure.simple(Grain, Food), Procedure.simple(Sugar, Food));
	

	private final Set<CargoAmount> supply;
	private final Set<CargoAmount> demand;
	private final Set<Procedure> procedures;

	Industry(Set<CargoAmount> supply, Set<CargoAmount> demand, Set<Procedure> procedures) {
		this.supply = supply;
		this.demand = demand;
		this.procedures = procedures;
	}

	Industry(Set<CargoAmount> supply, Set<CargoAmount> demand) {
		this(supply, demand, null);
	}

	Industry(CargoAmount supply, CargoAmount demand) {
		this(supply.asSet(), demand.asSet());
	}

	Industry(Set<CargoAmount> supply) {
		this(supply, null, null);
	}

	Industry(CargoAmount supply) {
		this(supply.asSet());
	}

	Industry(Procedure... procedures) {
		this(null, null, Sets.newHashSet(procedures));
	}

	public Set<CargoAmount> getDemand() {
		return demand;
	}

	public Set<CargoAmount> getSupply() {
		return supply;
	}

	public Set<Procedure> getProcedures() {
		return procedures;
	}
}
