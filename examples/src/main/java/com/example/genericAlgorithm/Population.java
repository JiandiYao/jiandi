package com.example.genericAlgorithm;

public class Population {
	Individual[] individuals;
	
	public Population(int populationSize, boolean initialize){
		individuals = new Individual[populationSize];
		if(initialize){
			for(int i = 0; i < size(); i++){
				Individual newIndividual = new Individual();
				saveIndividual(i, newIndividual);
			}
		}
	}

	public Individual getIndividual(int index){
		return individuals[index];
	}
	
	public Individual getFittest(){
		Individual fittest = individuals[0];
		for ( int i = 0; i < size(); i++){
			if(fittest.getFitness() <= getIndividual(i).getFitness()){
				fittest = getIndividual(i);
			}
		}
		return fittest;
	}
	private void saveIndividual(int i, Individual newIndividual) {
		// TODO Auto-generated method stub
		individuals[i] = newIndividual;
	}

	private int size() {
		// TODO Auto-generated method stub
		return individuals.length;
	}
}
