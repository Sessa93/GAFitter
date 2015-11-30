package genetic.GAFitter;
import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.terminal.Variable;

public class FormulaFitFunction extends GPFitnessFunction {

	private static final long serialVersionUID = 1L;

    private Float[] _input1;
    private Float[] _input2;
    private Float[] _output;
    private Variable _xVariable;
    private Variable _yVariable;
    
    private static Object[] NO_ARGS = new Object[0];
    
    public FormulaFitFunction(Float[] iNPUT_1, Float[] iNPUT_2, Float[] oUTPUT, Variable x, Variable y) {
        _input1 = iNPUT_1;
        _input2 = iNPUT_2;
        _output = oUTPUT;
        _xVariable = x;
        _yVariable = y;
    }
   
	@Override
	protected double evaluate(IGPProgram program) {
		double result = 0.0f;

        long longResult = 0;
        for (int i = 0; i < _input1.length; i++) {
            // Set the input values
            _xVariable.set(_input1[i]);
            _yVariable.set(_input2[i]);
            //Evaluate a chromosome
            long value = (long) program.execute_float(0, NO_ARGS);

            // The closer longResult gets to 0 the better the algorithm.
            longResult += Math.abs(value - _output[i]);
        }

        result = longResult;

        return result;
	}
}
