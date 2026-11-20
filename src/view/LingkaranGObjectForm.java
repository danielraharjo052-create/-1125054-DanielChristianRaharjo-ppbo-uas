package view;

import java.util.List;

public class LingkaranGObjectForm extends GObjectForm<Lingkaran> {

    public LingkaranGObjectForm(List<GObject> storage) {
        super(storage);
    }

    @Override
    protected String[] getFields() {
        return new String[]{"jari-jari"};
    }

    @Override
    protected Lingkaran constructModelFromValues(String label, Double[] values) {
        return new Lingkaran(label, values[0]);
    }

    @Override
    protected Double[] extractValuesFromModel(Lingkaran model) {
        return new Double[]{model.jariJariGet()};
    }
}