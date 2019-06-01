package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import gurobi.GRB;
import gurobi.GRBEnv;
import gurobi.GRBException;
import gurobi.GRBModel;
import metaheuristics.vns.AbstractVNS;
import problems.bpp.construction.BFD;
import problems.bpp.construction.FFD;
import problems.bpp.construction.NFD;
import problems.bpp.localSearch.P_Q_Exchange;
import problems.bpp.localSearch.Swap;
import problems.bpp.solvers.Gurobi_BPP;
import problems.bpp.solvers.VNS_BPP;
import utils.heap.ArrayHeap;
import utils.heap.Heap;

public class Main {
	public static void main(String[] args) {
	
		String[] bpp_instances = new String[] {
			"./bpp_instances/instance0.bpp",
			"./bpp_instances/instance1.bpp",
			"./bpp_instances/instance2.bpp",
			"./bpp_instances/instance3.bpp",
			"./bpp_instances/instance4.bpp",
			"./bpp_instances/instance5.bpp",	
			"./bpp_instances/instance6.bpp",
			"./bpp_instances/instance7.bpp",		
			"./bpp_instances/instance8.bpp",			
			"./bpp_instances/instance9.bpp",										
		};
//		try {
//			runExacts(bpp_instances);
//		} catch (IOException | GRBException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		Heap<Integer> heap = new ArrayHeap<Integer>(new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				int cmp = o1.compareTo(o2);
				if (cmp == -1)
					return 1;
				if (cmp == 1)
					return- 1;
				return 0;
			}
		}, 4);
	
		heap.add(3);
		heap.add(5);
		heap.add(4);
		heap.add(1);
		heap.add(6);
//		heap.add(0);
//		heap.add(6);
//		heap.add(7);
		System.out.println(heap.toString());
		
	}
	
	public static void runVNS(String[] instances) {
		BFD bfd = new BFD();
		FFD ffd = new FFD();
		NFD nfd = new NFD();		
		P_Q_Exchange kampkes = new P_Q_Exchange();
		
		for (int i = 0; i < instances.length; i++) {
//			try {
//				VNS_BPP vns_bpp = new VNS_BPP(bpp_instances[i], 1000, 180000, nfd, kampkes, Arrays.asList(new Swap(1, 2)));
//				System.out.println(vns_bpp.solve().size());
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			break;
			
		}	
	}
	
	public static void runExacts(String[] instances) throws IOException, GRBException {
		for (int i = 0; i < instances.length; i++) {
			// instance name
			Gurobi_BPP gurobi = new Gurobi_BPP(instances[i]);
//			gurobi.env = new GRBEnv("mip1.log");
			gurobi.env = new GRBEnv();
			gurobi.model = new GRBModel(gurobi.env);
			// execution time in seconds 
			gurobi.model.getEnv().set(GRB.DoubleParam.TimeLimit, 1800.0);
//			upper bound
			gurobi.model.getEnv().set(GRB.DoubleParam.BestBdStop, 1800.0);
//			lower bound
			gurobi.model.getEnv().set(GRB.DoubleParam.BestObjStop, 1800.0);
			// generate the model
			gurobi.populateNewModel(gurobi.model);
			// write model to file
//			gurobi.model.write("model.lp");
			long time = System.currentTimeMillis();
			gurobi.model.optimize();
			time = System.currentTimeMillis() - time;
			System.out.println("\n\nZ* = " + gurobi.model.get(GRB.DoubleAttr.ObjVal));
			String str = "Z* = " + gurobi.model.get(GRB.DoubleAttr.ObjVal)+"\n";
			System.out.println("\nTime = "+time);
			str += "Time = " + time +"\n";
			System.out.print("Y = [");
			str += "Y = [";
			for (int j = 0; j < gurobi.bpp.size; j++) {
	//				          System.out.print(gurobi.x[j].get(GRB.DoubleAttr.X) + ", ");
		          str += gurobi.y[j].get(GRB.DoubleAttr.X) + ", ";
			}			
			System.out.println("]");
			str += "]\n";
			System.out.print("X = [");
			str += "X = [";
			for (int j = 0; j < gurobi.bpp.size; j++) {
				System.out.print("[");
				str += "[";
				for (int k = 0; k < gurobi.bpp.size; k++) {
					System.out.print(gurobi.x[j][k].get(GRB.DoubleAttr.X) + ", ");
					str += gurobi.x[j][k].get(GRB.DoubleAttr.X) + ", ";
				}	
				System.out.println("]");
				str += "]\n";
			}			
			System.out.print("]");
			str += "]";
//			Write file			
		    BufferedWriter writer = new BufferedWriter(new FileWriter("results/"+instances[i]+"_results.txt"));
		    writer.write(str);		     
		    writer.close();
			gurobi.model.dispose();
			gurobi.env.dispose();
		}
	}
}
