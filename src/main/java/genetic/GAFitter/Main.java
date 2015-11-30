package genetic.GAFitter;

import org.apache.log4j.BasicConfigurator;
import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.GPProblem;
import org.jgap.gp.function.Add;
import org.jgap.gp.function.Add3;
import org.jgap.gp.function.Cosine;
import org.jgap.gp.function.Divide;
import org.jgap.gp.function.Log;
import org.jgap.gp.function.Multiply;
import org.jgap.gp.function.Multiply3;
import org.jgap.gp.function.Pow;
import org.jgap.gp.function.Subtract;
import org.jgap.gp.impl.DeltaGPFitnessEvaluator;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.terminal.Terminal;
import org.jgap.gp.terminal.Variable;

public class Main extends GPProblem
{
	private static Float[] INPUT_1 = { 1f, 2f, 3f, 33f, 35f};
	private static Float[] INPUT_2 = { 1f, 22f, 3f, 33f, 35f};
	private static Float[] OUTPUT = {2f, 44f, 6f, 66f, 70f};
	
	private Variable _xVariable;
	private Variable _yVariable;
	
	public Main() throws InvalidConfigurationException {
        super(new GPConfiguration());

        GPConfiguration config = getGPConfiguration();

        _xVariable = Variable.create(config, "X", CommandGene.FloatClass);
        _yVariable = Variable.create(config, "Y", CommandGene.FloatClass);

        config.setGPFitnessEvaluator(new DeltaGPFitnessEvaluator());
        config.setMaxInitDepth(4);
        config.setPopulationSize(1000);
        config.setMaxCrossoverDepth(8);
        config.setCrossoverProb(0.8f);
        config.setMutationProb(0.1f);
        config.setFitnessFunction(new FormulaFitFunction(INPUT_1, INPUT_2, OUTPUT, _xVariable, _yVariable));
        config.setStrictProgramCreation(true);
    }
	
	@Override
	public GPGenotype create() throws InvalidConfigurationException {
		GPConfiguration config = getGPConfiguration();

        // The return type of the GP program.
        Class[] types = { CommandGene.FloatClass };

        // Arguments of result-producing chromosome: none
        Class[][] argTypes = { {} };

        // Next, we define the set of available GP commands and terminals to
        // use.
        CommandGene[][] nodeSets = {
            {
                _xVariable,
                _yVariable,
                new Add(config, CommandGene.FloatClass),
                new Multiply(config, CommandGene.FloatClass),
                new Cosine(config, CommandGene.FloatClass),
                new Terminal(config, CommandGene.FloatClass, 0.0, 10.0, true),
                new Divide(config, CommandGene.FloatClass),
                new Pow(config, CommandGene.FloatClass),
                new Subtract(config, CommandGene.FloatClass),
                new Multiply3(config, CommandGene.FloatClass),
                new Add3(config, CommandGene.FloatClass),
                new Log(config, CommandGene.FloatClass)
            }
        };

        GPGenotype result = GPGenotype.randomInitialGenotype(config, types, argTypes, nodeSets, 20, true);

        return result;
	}
	
    public static void main( String[] args ) throws InvalidConfigurationException {
        GPProblem problem = new Main();
        
        //Log4j Configuration
        BasicConfigurator.configure();

        GPGenotype gp = problem.create();
        gp.setVerboseOutput(true);
        gp.evolve(30);

        gp.outputSolution(gp.getAllTimeBest());
    }
}


