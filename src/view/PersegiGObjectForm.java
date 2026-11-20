package view;

import java.util.List;

public class PersegiGObjectForm extends GObjectForm<Persegi> {

    public PersegiGObjectForm(List<GObject> storage) {
        super(storage);
    }

    @Override
    protected String[] getFields() {
        return new String[]{"sisi"};
    }

    @Override
    protected Persegi constructModelFromValues(String label, Double[] values) {
        return new Persegi(label, values[0]);
    }

    @Override
    protected Double[] extractValuesFromModel(Persegi model) {
        return new Double[]{model.sisiGet()};
    }
}