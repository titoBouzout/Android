package u;

import java.util.Stack;

public class History {

	private static boolean mDebug = false;

	private Stack<String> back = new Stack<String>();
	private Stack<String> forward = new Stack<String>();

	public void change(String to) {
		Shared.log(to, mDebug);

		push(back, forward, to);
	}

	private void push(Stack<String> Object, Stack<String> aOpositeObject,
			String aHistoryObj) {
		Shared.log(aHistoryObj, mDebug);

		if (!Object.empty()) {
			if (!Object.peek().equals(aHistoryObj)) {
				if (aOpositeObject.empty())
					Object.push(aHistoryObj);
				else if (!aOpositeObject.peek().equals(aHistoryObj))
					Object.push(aHistoryObj);
			}

		} else {
			if (aOpositeObject.empty())
				Object.push(aHistoryObj);
			else if (!aOpositeObject.peek().equals(aHistoryObj))
				Object.push(aHistoryObj);
		}
	}

	public String goBack(String from) {
		Shared.log(from, mDebug);

		push(forward, back, from);
		String to = back.pop();

		while (from.equals(to) && canGoBack("")) {
			push(back, forward, to);
			to = back.pop();
		}

		Shared.log("go back in history to: " + to, mDebug);
		return to;
	}

	public String goForward(String from) {
		Shared.log(from, mDebug);

		push(back, forward, from);
		String to = forward.pop();

		while (from.equals(to) && canGoForward("")) {
			back.push(to);
			to = forward.pop();
		}

		Shared.log("go forward in history to: " + to, mDebug);
		return to;
	}

	public boolean canGoBack(String from) {
		Shared.log(from, mDebug);

		while (!back.empty() && from.equals(back.peek()))
			back.pop();
		return !back.empty();
	}

	public boolean canGoForward(String from) {
		Shared.log(from, mDebug);

		while (!forward.empty() && from.equals(forward.peek()))
			forward.pop();
		return !forward.empty();
	}

	public void reset() {
		Shared.log(mDebug);

		back = new Stack<String>();
		forward = new Stack<String>();
	}
}
