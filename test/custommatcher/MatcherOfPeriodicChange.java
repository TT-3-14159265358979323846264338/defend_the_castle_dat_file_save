package custommatcher;

import java.util.List;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

class MatcherOfPeriodicChange extends BaseMatcher<List<Integer>>{
	private int period;
	
	MatcherOfPeriodicChange(int period) {
		this.period = period;
	}
	
	@Override
	public boolean matches(Object obj) {
		if(period < 2) {
			return false;
		}
		if(!instanceCheck(obj)) {
			return false;
		}
		@SuppressWarnings("unchecked")
		List<Integer> list = (List<Integer>) obj;
		for(int i = 0; i < period; i++) {
			if(canLoopEveryCycle(i, list, period)) {
				continue;
			}
			return false;
		}
		return true;
	}
	
	boolean instanceCheck(Object obj) {
		if(!(obj instanceof List<?>)) {
			return false;
		}
		List<?> list = (List<?>) obj;
		for(Object element: list) {
			if(!(element instanceof Integer)) {
				return false;
			}
		}
		return true;
	}
	
	boolean canLoopEveryCycle(int number, List<Integer> position, int cycle) {
		int index = number + cycle;
		do{
			if(position.get(number).equals(position.get(index))) {
				index += cycle;
				continue;
			}
			return false;
		}while(index < position.size());
		return true;
	}

	@Override
	public void describeTo(Description desc) {
		if(period < 2) {
			desc.appendText("指定する周期は2以上にしてください");
			return;
		}
		desc.appendText("与えられたList<Integer>は、指定した周期ごとに値がループしていません。");
	}
}