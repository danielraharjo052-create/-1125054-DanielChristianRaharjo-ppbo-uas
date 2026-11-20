package view;

import java.util.List;

public class KubusGObjectForm extends GObjectForm<Kubus> {

    public KubusGObjectForm(List<GObject> storage) {
        super(storage);
    }

    @Override
    protected String[] getFields() {
        return new String[]{"sisi"};
    }

    @Override
    protected Kubus constructModelFromValues(String label, Double[] values) {
        return new Kubus(label, values[0]);
    }

    @Override
    protected Double[] 
}