package view;

import java.util.List;

public class SegiEmpatGObjectForm extends GObjectForm<SegiEmpat> {

    public SegiEmpatGObjectForm(List<GObject> storage) {
        super(storage);
    }

    @Override
    protected String[] getFields() {
        return new String[]{"panjang", "lebar"};
    }

    @Override
    protected SegiEmpat constructModelFromValues(String label, Double[] values) {
        return new SegiEmpat(label, values[0], values[1]);
    }

    @Override
    protected Double[] extractValuesFromModel(SegiEmpat model) {
        return new Double[]{
            model.panjangGet(),
            model.lebarGet()
        };
    }
}