/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package calcclient.model.tree;

import calcclient.model.CalculatorModel;
import calcclient.model.state.ErrorState;
import org.apache.commons.lang3.math.NumberUtils;

/**
 *
 * @author Benjamin Arnold
 */
public enum Action {
    ADD("+") {
	@Override
	public Integer execute(Integer a, Integer b) {
	    return a + b;
	}
    },SUBTRACT("-") {
	@Override
	public Integer execute(Integer a, Integer b) {
	    return a - b;
	}
    },MULTIPLY("*") {
	@Override
	public Integer execute(Integer a, Integer b) {
	    return a * b;
	}
    },DIVIDE("/") {
	@Override
	public Integer execute(Integer a, Integer b) {
	    return a / b;
	}
    },EQUALS("=") { 
	@Override
	public Integer execute(Integer a, Integer b) {
	    return (a != null) ? a : 0;
	}
	@Override
	public void handleResult(CalculatorModel model, String result) {
	    super.handleResult(model, result);
	}
	@Override
	public void applyOnRoot(CalculatorModel model, Operator newComponent) {
	    //don't
	}
    },RESET("C") {
	@Override
	public Integer execute(Integer a, Integer b) {
	    return 0;
	}
	@Override
	public void applyOnRoot(CalculatorModel model, Operator newComponent) {
	    model.reset();
	}
    },DISCARD("D") {
	public void applyOnRoot(CalculatorModel model, Operator newComponent) {
	    //don't
	}
	@Override
	public Integer execute(Integer a, Integer b) {
	    return 0;
	}
    };
    
    private final String symbol;
    
    private Action(String symbol) {
	this.symbol = symbol;
    }
    
    public String getSymbol() {
	return symbol;
    }
    
    public void handleResult(CalculatorModel model, String result) {
	if (model.getCurrentState() instanceof ErrorState) {
	    model.setError(result);
	} else {
	    if (NumberUtils.isNumber(result)) {
		model.setText(result); 
		model.setCurrent(Integer.parseInt(result));
	    }
	}
    }
    
   public void applyOnRoot(CalculatorModel model, Operator newComponent) {
       if (! (model.getCurrentState() instanceof ErrorState)) {
	   model.updateRoot(newComponent, Integer.valueOf(model.getText()));
       }
	
    }
    
    abstract public Integer execute(Integer a, Integer b);
    
    public static Action getOperation(String symbol) {
	Action op;
	if (ADD.symbol.equals(symbol)) {
	    op = ADD;
	} else if (SUBTRACT.symbol.equals(symbol)) {
	    op = SUBTRACT;
	} else if (MULTIPLY.symbol.equals(symbol)) {
	    op = MULTIPLY;
	} else if (DIVIDE.symbol.equals(symbol)) {
	    op = DIVIDE;
	} else if (RESET.symbol.equals(symbol) || RESET.name().equalsIgnoreCase(symbol)) {
	    op = RESET;
	} else if (DISCARD.symbol.equals(symbol) || DISCARD.name().equalsIgnoreCase(symbol)) {
	    op = DISCARD;
	} else {
	    op = EQUALS;
	}
	return op;
    }

    
}
