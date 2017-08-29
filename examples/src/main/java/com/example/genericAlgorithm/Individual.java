package com.example.genericAlgorithm;

public class Individual {

	static int defaultGeneLength = 64;
	private byte[] genes = new byte[defaultGeneLength];
	private int fitness = 0;
	public void generateIndividual(){
		for(int i = 0; i < size(); i++){
			byte gene = (byte) Math.round(Math.random());
			genes[i] = gene;
		}
	}
	public static void setDefaultGeneLength(int length) {
        defaultGeneLength = length;
    }
	public byte getGene(int index) {
        return genes[index];
    }

    public void setGene(int index, byte value) {
        genes[index] = value;
        fitness = 0;
    }
    
	private int size() {
		// TODO Auto-generated method stub
		return genes.length;
	}
	public int getFitness() {
		// TODO Auto-generated method stub
		if (fitness == 0) {
            fitness = FitnessCalc.getFitness(this);
        }
        return fitness;
	}
	 @Override
    public String toString() {
        String geneString = "";
        for (int i = 0; i < size(); i++) {
            geneString += getGene(i);
        }
        return geneString;
    }
}
