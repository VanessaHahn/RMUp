package mi.ur.de.android.runnersmeetup;

/**
 * Created by Medion on 20.08.2016.
 */
public class CalculatorBmi {
    private int kg;
    private int cm;

    public void setValues(int cm, int kg){
        this.kg = kg;
        this.cm = cm;
    }

    public double calculateBMI(){
        double m = (double) cm/100;
        double squareM = (m * m);

        double result = kg/squareM;
        result = (result*10);
        result = Math.round(result);
        result = (result/10);
        double BMI = result;
        return BMI;
    }
}
