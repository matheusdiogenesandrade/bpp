package problems.bpp.construction;

import java.util.List;

import problems.bpp.BPP_Inverse;
import problems.bpp.Bin;
import solutions.Solution;

public class FFD implements ConstructionMethod {

	@Override
	public Solution<Bin> construct(BPP_Inverse BPP_Inverse) {
//		no need to sort the items weight, because the items already are sorted in the input
//		the list size was defined based in the FFD aproximation factor
		int upperBoundAttempt = (int) Math.ceil(1.23 * BPP_Inverse.lowerBound);
		Solution<Bin> sol = new Solution<Bin>(upperBoundAttempt);
		for (int i = 0; i < BPP_Inverse.size; i++) {
			boolean inserted = false;
			for (int j = 1; j < sol.size(); j++) {
				Bin bin = sol.get(j);
				if (bin.add(i)) {
					inserted = true;
					break;
				}
			}
			if (!inserted) {						
				Bin newBin = new Bin(BPP_Inverse);
				newBin.add(i);
				sol.add(newBin);
			}
		}
		return sol;
	}

}
