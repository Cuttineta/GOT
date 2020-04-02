import java.util.Comparator;
import java.util.Map;

public class ValueComparator implements Comparator {

	Map<String, Double> base;

	public ValueComparator(Map b1) {
		base = b1;
	}

	public int compare(Object a, Object b) {
		int value = ((Double) base.get(b)).compareTo((Double) base.get(a));
		return value;
	}
}